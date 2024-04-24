package it.bvr.thip.produzione.ordese;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionDescriptor;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Authorizable;

import it.biscottificio.thip.base.articolo.YArticoloCliente1;
import it.biscottificio.thip.base.commessa.YCommessa;
import it.biscottificio.thip.datiTecnici.modpro.YModelloProduttivo;
import it.biscottificio.thip.magazzino.generalemag.YLottoAssResp;
import it.biscottificio.thip.magazzino.generalemag.YLottoAssRespTM;
import it.thera.thip.YArticoliDatiInd;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.articolo.ArticoloCliente;
import it.thera.thip.base.articolo.ArticoloUnitaMisura;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.magazzino.generalemag.Lotto;
import it.thera.thip.nicim.base.AmbienteNICIM;
import it.thera.thip.nicim.base.PersDatiNICIMUtils;
import it.thera.thip.produzione.ordese.AttivitaEsecLottiPrd;
import it.thera.thip.produzione.ordese.AttivitaEsecMateriale;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.produzione.ordese.OrdineEsecutivoTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 10/04/2024
 * <br><br>
 * <b>71501	DSSOF3	10/04/2024</b>
 * <p>Prima stesura.<br>
 *  Lancio della stored procedure.<br>
 *  In futuro verra' implementata la logica a programma.
 * </p>
 */

public class YExpOrdiniEsecutivi extends BatchRunnable implements Authorizable {

	private AmbienteNICIM ambiente = null;

	private PersDatiNICIMUtils persDatiNICIM = (PersDatiNICIMUtils) Factory.createObject(PersDatiNICIMUtils.class);

	private ConnectionDescriptor descrittoreConnessioneEsterna = null;

	@Override
	protected boolean run() {
		boolean isOk = false;
		output.println("** Inizio esportazione ordini di produzione verso tabella di frontiera **");
		try {
			ambiente = (AmbienteNICIM) AmbienteNICIM.elementWithKey(AmbienteNICIM.class,KeyHelper.buildObjectKey(new String[] {
					Azienda.getAziendaCorrente(),
					"BISC_001"
			}), PersistentObject.NO_LOCK);

			descrittoreConnessioneEsterna = (ConnectionDescriptor) persDatiNICIM.getConnessioneDBEsterno(
					ambiente.getDBEsternoTipo(),
					ambiente.getDBEsternoServer(),
					String.valueOf(ambiente.getDBEsternoPorta()),
					ambiente.getDBEsternoNome(),
					ambiente.getDBEsternoUtente(),
					ambiente.getDBEsternoPassword());
			if(ambiente != null) {
				isOk = runEsportazione();
			}else {

			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		//isOk = lanciaStoredProcedure();
		output.println("** Termine esportazione ordini di produzione verso tabella di frontiera **");
		return isOk;
	}

	@SuppressWarnings({ "unchecked" })
	protected boolean runEsportazione() {
		output.println(" --> ...Estraggo gli ordini da esportare verso il MES ");

		List<YOrdineEsecutivo> ordini = YOrdineEsecutivo.estraiOrdiniEsecutiviDaEsportareMES();

		output.println(" --> ...Ho estratto "+ordini.size()+" ordini da esportare ");
		List<TblDettaglioProduzione> tblsDettagli = new ArrayList<TblDettaglioProduzione>();//conterra' i dettagli da inserire nella tabella di f
		List<TblProduzione> tblsProduzione = new ArrayList<TblProduzione>(); //conterra' i tbl da inserire nella tabella di frontiera
		HashMap<String, OrdineEsecutivo> ordinDaNonProcessare = new HashMap<String, OrdineEsecutivo>();
		List<YOrdineEsecutivo> impastiDaProcessare = new ArrayList<YOrdineEsecutivo>();
		Iterator<YOrdineEsecutivo> iterator = ordini.iterator();
		while (iterator.hasNext()) {
			YOrdineEsecutivo ordine = iterator.next();
			output.println();
			try {
				output.println(" <--- Processo l'ordine esecutivo : {"+ordine.getKey()+"}");
				YArticoliDatiInd tipoArticolo = YArticoliDatiInd.recuperaEstensioneArticolo(ordine.getIdAzienda(), ordine.getIdArticolo());
				if(tipoArticolo != null && (
						tipoArticolo.getTipologiaArticolo() == YArticoliDatiInd.NON_SIGNIFICATIVO)) {
					//prima processiamo solo i finiti
					String idCommessa = ordine.getIdCommessa(); //commessa del finito
					if(idCommessa != null) {
						List<String> commesseCompatibiliPerQuestoOrdine = YCommessa.listaCommesseCompatibiliOrdiniFiniti(
								YOrdineEsecutivo.getInnPerListaMateriali(ordine, OrdineEsecutivoTM.R_ARTICOLO),
								ordine.getDateProgrammate().getStartDate(),
								ordine.getDateEffettive().getStartDate(),
								ordine.getDateRichieste().getStartDate());
						//se la lista non contiene la commessa allora errore dato che non e' compatibile
						//altrimenti prendo la commessa oggetto dove ho l'ordine esecutivo di origine
						if(!commesseCompatibiliPerQuestoOrdine.contains(idCommessa)) {
							//errore dato che le commmesse non sono compatibili
						}else {
							YCommessa commessaPadre = (YCommessa) ordine.getCommessa();
							OrdineEsecutivo ordinePadre = commessaPadre.getOrdineesecutivoorigine();
							if(ordinePadre != null && !ordinDaNonProcessare.containsKey(ordinePadre.getKey())) { //e non e' da non processare
								//controllo che ci sia il lotto sul prodotto del finito
								//altrimenti non considero questo ordine per l'esportazione
								//e nemmeno quello padre
								boolean lottiProdottiOk = false;
								String idLotto = null;
								List<AttivitaEsecLottiPrd> lottiProdotti = ordine.getRigaProdottoPrimario().getLottiProdotti();
								for(AttivitaEsecLottiPrd lotto : lottiProdotti) {
									if(!lotto.getIdLotto().equals(Lotto.LOTTO_DUMMY) && lottiProdotti.size() == 1) {
										lottiProdottiOk = true;
										idLotto = lotto.getIdLotto();
									}
								}
								if(lottiProdottiOk) {
									//popolo la lista dei tbl dettagli
									TblDettaglioProduzione dettaglio = creaTblDettaglio(ordine,ordinePadre,idLotto);
									if(dettaglio != null) {
										tblsDettagli.add(dettaglio);
									}else {
										//scadenza non trovata quindi errore
									}
								}else {
									output.println(" ** Attenzione, l'ordine ha prodotto con lotto dummy...non puo' essere quindi esportato ");
									//considero entrambi gli ordini come da non processare
									ordinDaNonProcessare.put(ordine.getKey(), ordine);
									ordinDaNonProcessare.put(ordinePadre.getKey(), ordinePadre);
									iterator.remove(); //rimuovo il finito
								}
							}else {
								//errore?
							}
						}
					}else {
						output.println(" ** Attenzione, l'ordine non ha la commessa...non puo' essere quindi esportato ");
					}
				}else if(tipoArticolo != null && tipoArticolo.getTipologiaArticolo() != YArticoliDatiInd.IMPASTO){ //se non e' neanche impasto allora flaggo
					int rc = ordine.aggiornaStatoIndustriaNoSave(YOrdineEsecutivo.DA_NON_ESPORTARE);
					if(rc == YOrdineEsecutivo.UPDATE_OK) {
						ConnectionManager.commit();
					}else {
						output.println(" ** Ci sono stati errori nel tentativo di update dello stato industria per l'ordine : {"+ordine.getKey()+"}");
						ConnectionManager.rollback();
					}
				}else if(tipoArticolo != null && tipoArticolo.getTipologiaArticolo() == YArticoliDatiInd.IMPASTO) {
					//aggiungo nella lista degli impasti da processare
					impastiDaProcessare.add(ordine);
				}
			}catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
			output.println(" Ho finito di processare l'ordine esecutivo : {"+ordine.getKey()+"} --->");
			output.println();
		}

		//se ho dettagli allora processo gli impasti
		if(tblsDettagli.size() > 0) {
			Iterator<YOrdineEsecutivo> iterImpasti = impastiDaProcessare.iterator();
			while(iterImpasti.hasNext()) {
				YOrdineEsecutivo ordineImpasto = iterImpasti.next();
				if(!ordinDaNonProcessare.containsKey(ordineImpasto.getKey())) { //altrimenti il padre non e' da processare
					TblProduzione tbl = creaTblTestata(ordineImpasto,tblsDettagli);
					if(tbl != null) {
						tblsProduzione.add(tbl);
					}
				}
			}
		}

		if(tblsDettagli.size() > 0 && tblsProduzione.size() > 0) {
			output.println(" --> Inizio l'esportazione dei dati verso le tabelle di frontiera ");
			//ora esportiamoli di la
			//e poi flagghiamo l'ordine come esportato
			//consideriamo commit finale pero'
			boolean commit = true;
			for(TblProduzione testata : tblsProduzione) {
				output.println();
				YOrdineEsecutivo ordine = (YOrdineEsecutivo) YOrdineEsecutivo.recuperaOrdineEsecutivoDaNumeroFMT(testata.getRif_ODP());
				output.println(" <--- Processo l'ordine esecutivo : {"+ordine.getKey()+"}");
				int rc = testata.esportaVersoTabellaDiFrontiera(descrittoreConnessioneEsterna);
				if(rc == YOrdineEsecutivo.UPDATE_OK) {
					rc = ordine.aggiornaStatoIndustriaNoSave(YOrdineEsecutivo.ESPORTATO);
					if(rc == YOrdineEsecutivo.UPDATE_KO) {
						commit = false;
						output.println(" ** Qualcosa e' andato storto nell'update dello stato industria 4.0 dell'ordine... ");
					}
				}else {
					commit = false;
					output.println(" ** Qualcosa e' andato storto nell'esportazione dei dati verso le tabelle di frontiera... ");
				}
				output.println(" Ho finito di processare l'ordine esecutivo : {"+ordine.getKey()+"} --->");
				output.println();
			}
			try {
				if(commit) {
					output.println("--> Procedo con la commit sui due connection descriptor ");

					descrittoreConnessioneEsterna.getConnection().commit();
					ConnectionManager.commit();

				}else {
					ConnectionManager.rollback();
					output.println("Rilascio la connessione : \n "+descrittoreConnessioneEsterna.toString());
					//rilascio la connessione
					descrittoreConnessioneEsterna.closeConnection();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return false;
	}

	protected TblProduzione creaTblTestata(YOrdineEsecutivo ordineImpasto, List<TblDettaglioProduzione> tblsDettagliTotale) {
		TblProduzione testata = (TblProduzione) Factory.createObject(TblProduzione.class);
		testata.setDataOra(ordineImpasto.getDataOrdine());
		testata.setTurno(null);
		testata.setLotto("P" + ordineImpasto.getIdAnnoOrdine().substring(2,4) + ordineImpasto.getIdNumeroOrdine());
		testata.setCodArticolo(ordineImpasto.getIdArticolo());
		String descrizione = ordineImpasto.getArticolo().getDescrizioneArticoloNLS().getDescrizioneEstesa() != null ? ordineImpasto.getArticolo().getDescrizioneArticoloNLS().getDescrizioneEstesa() : ordineImpasto.getArticolo().getDescrizioneArticoloNLS().getDescrizione();
		if(descrizione.length() > 100) {
			descrizione = descrizione.substring(0,100);
		}
		testata.setDescImpasto(descrizione);
		if(ordineImpasto.getModelloProduttivo() != null) { 
			//il campo personalizzato NUM_RICETTA
			testata.setRicetta(((YModelloProduttivo)ordineImpasto.getModelloProduttivo()).getNumeroRicetta() != null ? ((YModelloProduttivo)ordineImpasto.getModelloProduttivo()).getNumeroRicetta() : 0);
		}
		testata.setCodImpasto(ordineImpasto.getIdArticolo());
		List<TblDettaglioProduzione> dettagli = TblProduzione.searchDettagliByRifTblProduzione(tblsDettagliTotale, ordineImpasto.getNumeroOrdFmt());
		if(dettagli.size() > 0){
			testata.setDettagli(dettagli);
			testata.setQtCartoni(testata.getSommaQuantitaCartoniDettagli(TblDettaglioProduzione::getCartoni_Tot));
		}else {
			//se non ci sono dettagli c'e' qualcosa che non va
			return null;
		}
		testata.setQtCartoniProdotti(null);
		testata.setKgImpasto(ordineImpasto.getSommaKgMaterialiImpasto());
		testata.setRif_ODP(ordineImpasto.getNumeroOrdFmt());
		testata.setFlag(TblProduzione.DA_PRODURRE);
		return testata;
	}

	/**
	 * @author Daniele Signoroni 24/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Ritorna il dettaglio di produzione che verra' poi inserito nelle tabelle di frontiera.<br>
	 * </p>
	 * @param ordine
	 * @param ordinePadre
	 * @param idLotto il lotto del prodotto
	 * @return un istanza di {@linkplain TblDettaglioProduzione}
	 */
	protected TblDettaglioProduzione creaTblDettaglio(YOrdineEsecutivo ordine, OrdineEsecutivo ordinePadre, String idLotto) {
		TblDettaglioProduzione dettaglio = (TblDettaglioProduzione) Factory.createObject(TblDettaglioProduzione.class);
		String descrizione = ordine.getArticolo().getDescrizioneArticoloNLS().getDescrizioneEstesa() != null ? ordine.getArticolo().getDescrizioneArticoloNLS().getDescrizioneEstesa() : ordine.getArticolo().getDescrizioneArticoloNLS().getDescrizione();
		if(descrizione.length() > 100) {
			descrizione = descrizione.substring(0,100);
		}
		dettaglio.setDescrizione_prodotto(descrizione);
		dettaglio.setRif_ODP(ordine.getNumeroOrdFmt());
		dettaglio.setRif_cliente(ordine.getCliente() != null ? ordine.getCliente().getRagioneSociale() : null);
		dettaglio.setRif_codice_cliente(ordine.getIdCliente());
		dettaglio.setRif_ordine_cliente(KeyHelper.formatKeyString(ordine.getOrdineVenditaRigaKey())); //formattato con il video separator		
		dettaglio.setCod_Articolo(ordine.getIdArticolo());
		Date scadenza = recuperaScadenzaDaLottoProdotto(ordine);
		if(scadenza == null) {
			return null;
		}else {
			dettaglio.setScadenza(scadenza);
		}
		dettaglio.setLotto(idLotto);
		dettaglio.setPezzi_crt(recuperaPezziCartoneDaOrdineEsecutivo(ordinePadre));
		dettaglio.setCartoni_plt(recuperaCartoniPalletDaOrdineEsecutivo(ordinePadre));
		dettaglio.setCartoni_Tot(ordine.getQtaOrdinataUMPrm().intValue());
		if(dettaglio.getCartoni_plt() > 0) {
			int palletTotali = dettaglio.getCartoni_Tot() / dettaglio.getCartoni_plt();
			dettaglio.setPallet_Tot(palletTotali);
		}
		BigDecimal impastiCartone = BigDecimal.ZERO;
		AttivitaEsecMateriale impasto = YOrdineEsecutivo.getPrimoArticoloImpastoTraMateriali(ordinePadre);
		if(impasto != null) {
			impastiCartone = impasto.getCoeffImpiego();
		}
		dettaglio.setImpasti_Cartone(impastiCartone);
		dettaglio.setCod_vaschetta(YOrdineEsecutivo.getIdArticoloDaTipologiaTraMateriali(ordinePadre, YArticoliDatiInd.VASCHETTA));
		dettaglio.setCod_incarto(YOrdineEsecutivo.getIdArticoloDaTipologiaTraMateriali(ordinePadre, YArticoliDatiInd.INCARTO));
		dettaglio.setCod_astuccio(YOrdineEsecutivo.getIdArticoloDaTipologiaTraMateriali(ordinePadre, YArticoliDatiInd.ASTUCCIO));
		dettaglio.setCod_cartone(YOrdineEsecutivo.getIdArticoloDaTipologiaTraMateriali(ordinePadre, YArticoliDatiInd.CARTONE));
		dettaglio.setCod_coperchio(YOrdineEsecutivo.getIdArticoloDaTipologiaTraMateriali(ordinePadre, YArticoliDatiInd.COPERCHIO));
		dettaglio.setRiferimento_Tbl_Produzione(ordinePadre.getNumeroOrdFmt());
		dettaglio.setFlag(TblProduzione.DA_PRODURRE);
		dettaglio.setEsaurimento_Impasto(ordine.isYcontEsaurImp() ? '1' : '0');
		return dettaglio;
	}

	protected int recuperaCartoniPalletDaOrdineEsecutivo(OrdineEsecutivo ordine) {
		int cartoniPallet = 0;
		try {
			ArticoloCliente artCli = ArticoloCliente.getArticoloCliente(ordine.getIdAzienda(), ordine.getIdCliente(), ordine.getIdArticolo(), ordine.getIdConfigurazione());
			if(artCli != null  && artCli instanceof YArticoloCliente1) {
				cartoniPallet = ((YArticoloCliente1)artCli).getQtaMinVen() != null ? ((YArticoloCliente1)artCli).getQtaMinVen().intValue() : 0;
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		if(cartoniPallet == 0) {
			cartoniPallet = ordine.getArticolo().getQtaMinimaVendita() != null ? ordine.getArticolo().getQtaMinimaVendita().intValue() : 0;
		}
		return cartoniPallet;
	}

	@SuppressWarnings("unchecked")
	protected int recuperaPezziCartoneDaOrdineEsecutivo(OrdineEsecutivo ordine) {
		int pezzi_cartone = 0;
		Articolo articolo = ordine.getArticolo();
		List<ArticoloUnitaMisura> unitaMisure = articolo.getUMSpecifiche();
		for(ArticoloUnitaMisura unitaMisura : unitaMisure) {
			if(unitaMisura.getIdUnitaMisura().equals("cr")) {
				pezzi_cartone = unitaMisura.getFattoreConverUM().intValue();
			}
		}
		return pezzi_cartone;

	}

	/**
	 * @author Daniele Signoroni 24/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Recupera la data di scadenza del lotto dal prodotto primario.<br>
	 * La data di scadenza viene prima ricercata nell'estensione per il cliente dell'ordine,
	 * se questa non viene trovata viene considerata la data di scadenza e se anche questa e' null
	 * allora viene considerata la data di apertura del lotto.<br>
	 * </p>
	 * @param ordine
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Date recuperaScadenzaDaLottoProdotto(OrdineEsecutivo ordine) {
		Date dataScadenza = null;
		Iterator<AttivitaEsecLottiPrd> lottiPrd = ordine.getRigaProdottoPrimario().getLottiProdotti().iterator();
		while(lottiPrd.hasNext()) {
			AttivitaEsecLottiPrd lottoProdotto = (AttivitaEsecLottiPrd) lottiPrd.next();
			Lotto lotto = lottoProdotto.getLotto();
			if(ordine.getCliente() != null) {
				String where = " "+YLottoAssRespTM.ID_AZIENDA+" = '"+ordine.getRigaProdottoPrimario().getIdAzienda()+"' "
						+ " AND "+YLottoAssRespTM.ID_ARTICOLO+" = '"+ordine.getRigaProdottoPrimario().getIdArticolo()+"' "
						+ "AND "+YLottoAssRespTM.ID_LOTTO+" = '"+lotto.getCodiceLotto()+"'  "
						+ "AND "+YLottoAssRespTM.R_CLIENTE+" = '"+ordine.getIdCliente()+"' ";
				try {
					List<YLottoAssResp> assunzioniResponsabilita = YLottoAssResp.retrieveList(YLottoAssResp.class, where, "", false);
					if(assunzioniResponsabilita.size() > 0) {
						YLottoAssResp assunzione = assunzioniResponsabilita.get(0);
						return assunzione.getDataScad();
					}
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
			if(lotto.getDataScadenza() != null) {
				dataScadenza = lotto.getDataScadenza();
			}else {
				dataScadenza = lotto.getDataApertura(); //questa e' obbligatoria 
			}
		}
		return dataScadenza;
	}

	/**
	 * @author Daniele Signoroni 10/04/2024
	 * <p>
	 * Prima stesura.<br>
	 *
	 * </p>
	 * @return true se la stored e' finita correttamente, false altrimenti
	 */
	protected boolean lanciaStoredProcedure() {
		boolean isOk = false;
		CachedStatement cs = null;
		String stmt  = " EXEC SOFTRE.Y_MACCHINE_P01 ";
		cs = new CachedStatement(stmt);
		try {
			int ris = cs.executeUpdate();
			isOk = ris > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(cs != null) {
					cs.free();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isOk;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YExpOrdiniEsecutivi";
	}

}
