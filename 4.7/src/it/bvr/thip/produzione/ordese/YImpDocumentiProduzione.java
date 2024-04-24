package it.bvr.thip.produzione.ordese;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.persist.ConnectionDescriptor;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.nicim.base.AmbienteNICIM;
import it.thera.thip.nicim.base.ConfigIntDocPrdCausali;
import it.thera.thip.nicim.base.PersDatiNICIMUtils;
import it.thera.thip.nicim.interfacce.InterfacciaConsNic;
import it.thera.thip.nicim.interfacce.InterfacciaStdNICIM;
import it.thera.thip.produzione.ordese.AttivitaEsecRisorsa;
import it.thera.thip.produzione.ordese.AttivitaEsecutiva;
import it.thera.thip.vendite.proposteEvasione.CreaMessaggioErrore;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 18/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	18/04/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class YImpDocumentiProduzione extends BatchRunnable implements Authorizable {

	private static final String STMT_TBL_PRODUZIONE = " SELECT * FROM "+TblProduzione.TABLE_NAME+" WHERE Flag = ? ";
	private static final String STMT_TBL_PRODUZIONE_DETT = " SELECT * FROM "+TblDettaglioProduzione.TABLE_NAME+" WHERE Flag = ? AND Riferimento_Tbl_Produzione = ? ";

	private static final String UPD_FLAG_TBL_PRODUZIONE = "UPDATE "+TblProduzione.TABLE_NAME+" SET Flag = ? WHERE ID = ? ";
	private static final String UPD_FLAG_TBL_PRODUZIONE_DETT = "UPDATE "+TblDettaglioProduzione.TABLE_NAME+" SET Flag = ? WHERE ID = ? ";

	//Attribute ref : YStatoImpDocProdMES
	public static final char DA_CONVALIDARE = '0';
	public static final char CONVALIDATO = '1';

	private AmbienteNICIM ambiente = null;

	private PersDatiNICIMUtils persDatiNICIM = (PersDatiNICIMUtils) Factory.createObject(PersDatiNICIMUtils.class);

	private ConnectionDescriptor descrittoreConnessioneEsterna = null;

	protected char iStatoDocumenti;

	public char getStatoDocumenti() {
		return iStatoDocumenti;
	}

	public void setStatoDocumenti(char iStatoDocumenti) {
		this.iStatoDocumenti = iStatoDocumenti;
	}

	@Override
	protected boolean run() {
		boolean isOk = true;
		output.println("** INIZIO IMPORTAZIONE DOCUMENTI PRODUZIONE DA DATABASE ESTERNO **");
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
				isOk = runImportazione();
			}else {
				isOk = false;
				output.println("  --> Valorizzare tutti i parametri personalizzati che riguardano la connessione al database esterno ");
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		output.println("** TERMINE IMPORTAZIONE DOCUMENTI PRODUZIONE DA DATABASE ESTERNO **");;
		return isOk;
	}

	protected boolean runImportazione() {
		boolean isOk = true;
		try {
			List<TblProduzione> testate = retrieveListTblProduzione();
			if(testate.isEmpty()) {
				output.println(" ** Non sono presenti record in stato TERMINATO...");
			}
			for(TblProduzione testata : testate) {
				output.println();
				output.println(" --> Processo la testata {"+testata.getId()+"}, la testata ha :"+testata.getDettagli().size()+" dettagli <-- ");
				YOrdineEsecutivo ordEsec = (YOrdineEsecutivo) YOrdineEsecutivo.recuperaOrdineEsecutivoDaNumeroFMT(testata.getRif_ODP());
				if(ordEsec != null) {
					AttivitaEsecutiva atv = ordEsec.getAttivitaEsecutive().size() > 0 ? (AttivitaEsecutiva) ordEsec.getAttivitaEsecutive().get(0) : null; //ne avra' sempre e solo 1
					if(atv != null) {
						AttivitaEsecRisorsa risorsa = atv.getRisorse().size() > 0 ? (AttivitaEsecRisorsa) atv.getRisorse().get(0) : null;
						InterfacciaConsNic nicCons = (InterfacciaConsNic) Factory.createObject(InterfacciaConsNic.class);
						nicCons.setAmbiente(ambiente);
						nicCons.setTipoOperazione(InterfacciaStdNICIM.INSERIMENTO);
						String idOrdine = "P" + ordEsec.getIdAnnoOrdine().substring(2,4) + ordEsec.getIdNumeroOrdine();
						nicCons.setIdOrdine(idOrdine);		
						nicCons.setIdFase(Integer.parseInt(atv.getIdOperazione()));
						nicCons.setCodiceFase(Integer.parseInt(atv.getIdOperazione()));
						nicCons.setNumRitorno(atv.getNumeroRitorno());
						nicCons.setFlagAttivita(ConfigIntDocPrdCausali.LAVORAZIONE);
						nicCons.setTipoAttivita(ConfigIntDocPrdCausali.SOSPENSIONE);
						nicCons.setIdAnnoOrdine(ordEsec.getIdAnnoOrdine());
						nicCons.setIdNumeroOrdine(ordEsec.getIdNumeroOrdine());
						nicCons.setIdRigaOrdine(0);
						nicCons.setDataRegistrazione(testata.getDataOra());
						nicCons.setQuantita(testata.getSommaImpastiCartoneDettaglio().doubleValue()); //non so pk lo vuole double ma ok
						if(nicCons.getQuantita() <= 0) {
							output.println(" ** Attenzione, la somma delle quantita' e' 0, non importo il consuntivo ... ");
							continue;
						}
						//nicCons.setTempoMacchina(BigDecimal.ONE);
						nicCons.setIdProgressivo(nicCons.getNuovoProgressivo());
						double zero = 0;
						nicCons.setQuantitaScarto(zero);
						if(risorsa != null) {
							nicCons.setIdRisorsa(risorsa.getIdRisorsa());
						}
						int rc = nicCons.save();
						if(rc > 0) {
							rc = aggiornaFlagTestataRigheTBL(testata);
							if(rc > 0) {
								output.println(" -- Record :  "+nicCons.getAbstractTableManager().getMainTableName()+", {"+nicCons.getKey()+"}, salvato correttamente con rc ="+rc);

								ConnectionManager.commit();
							}else {
								output.println(" ** Impossibile updatare il Flag per il TBL con ID = "+testata.getId());

								ConnectionManager.rollback();
							}
						}else {
							output.println(" ** Impossibile salvare l'interfaccia  "+nicCons.getClass().getName()+", per il TBL con ID = "+testata.getId());
							output.println(" ** Reason : "+CreaMessaggioErrore.daRcAErrorMessage(rc, (SQLException) nicCons.getException()));
						}
					}else {
						output.println(" -- Qualcosa e' andato storto, l'ordine esecutivo : "+ordEsec.getKey()+" non ha nessun attivita' ");
					}
				}else {
					output.println(" -- Qualcosa e' andato storto nel recuperare l'ordine esecutivo, NUM_ORD_FMT = "+testata.getRif_ODP());
				}
				output.println(" --> Ho finito di processare la testata {"+testata.getId()+"}  <-- ");
				output.println();
			}
		} catch (SQLException e) {
			isOk = false;
			output.println(e.getMessage());
			e.printStackTrace(Trace.excStream);
		}
		return isOk;
	}

	protected int aggiornaFlagTestataRigheTBL(TblProduzione testata) {
		int rc = -1;
		try {
			descrittoreConnessioneEsterna.openConnection();
			PreparedStatement ps1 = descrittoreConnessioneEsterna.getConnection().prepareStatement(UPD_FLAG_TBL_PRODUZIONE);
			ps1.setString(1, String.valueOf(TblProduzione.IMPORTATO_A_GESTIONALE));
			ps1.setInt(2, testata.getId().intValue());
			rc = ps1.executeUpdate();
			if(rc > 0) {
				ps1.close();
				for(TblDettaglioProduzione dettaglio : testata.getDettagli()) {
					PreparedStatement ps2 = descrittoreConnessioneEsterna.getConnection().prepareStatement(UPD_FLAG_TBL_PRODUZIONE_DETT);
					ps2.setString(1, String.valueOf(TblProduzione.IMPORTATO_A_GESTIONALE));
					ps2.setInt(2, dettaglio.getId().intValue());
					rc = ps2.executeUpdate();
					if(rc <= 0) {
						descrittoreConnessioneEsterna.getConnection().rollback();
						return -1;
					}
				}
			}
			descrittoreConnessioneEsterna.getConnection().commit();
			if(descrittoreConnessioneEsterna.getConnection() != null && !descrittoreConnessioneEsterna.getConnection().isClosed()) {
				descrittoreConnessioneEsterna.closeConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return rc;
	}

	protected List<TblProduzione> retrieveListTblProduzione() throws SQLException {
		List<TblProduzione> tblTestate = new ArrayList<TblProduzione>();
		descrittoreConnessioneEsterna.openConnection();
		ResultSet rs = null;
		TblProduzioneIterator iterTblProduzione = null;
		try{
			PreparedStatement ps = descrittoreConnessioneEsterna.getConnection().prepareStatement(STMT_TBL_PRODUZIONE);
			ps.setString(1, String.valueOf(TblProduzione.TERMINATO));
			rs = ps.executeQuery();
			iterTblProduzione = new TblProduzioneIterator(rs);
			while(iterTblProduzione.hasNext()) {
				TblProduzione tbl = (TblProduzione) iterTblProduzione.next();
				if(tbl != null)
					tblTestate.add(tbl);
			}
		}finally{
			try{
				iterTblProduzione.closeCursor();
			}
			catch(SQLException e){
				e.printStackTrace(Trace.excStream);
			}
		}
		if(!tblTestate.isEmpty()) {
			for(TblProduzione testata : tblTestate) {
				TblDettaglioProduzioneIterator iterTblProduzioneDettaglio = null;
				PreparedStatement ps = descrittoreConnessioneEsterna.getConnection().prepareStatement(STMT_TBL_PRODUZIONE_DETT);
				ps.setString(1, String.valueOf(TblProduzione.TERMINATO));
				ps.setString(2, testata.getRif_ODP());
				rs = ps.executeQuery();
				iterTblProduzioneDettaglio = new TblDettaglioProduzioneIterator(rs);
				while(iterTblProduzioneDettaglio.hasNext()) {
					TblDettaglioProduzione tbl = (TblDettaglioProduzione) iterTblProduzioneDettaglio.next();
					if(tbl != null)
						testata.getDettagli().add(tbl);
				}
			}
			if(iterTblProduzione != null) {
				iterTblProduzione.closeCursor();
			}
		}
		if(descrittoreConnessioneEsterna.getConnection() != null && !descrittoreConnessioneEsterna.getConnection().isClosed()) {
			descrittoreConnessioneEsterna.closeConnection();
		}
		return tblTestate;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YImportazioneDocProduz";
	}

}
