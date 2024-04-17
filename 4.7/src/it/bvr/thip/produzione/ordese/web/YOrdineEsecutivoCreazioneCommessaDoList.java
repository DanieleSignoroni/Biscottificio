package it.bvr.thip.produzione.ordese.web;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.thera.thermfw.ad.ClassAD;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.type.DateType;
import com.thera.thermfw.web.WebDOList;

import it.biscottificio.thip.base.commessa.YCommessa;
import it.thera.thip.YArticoliDatiInd;
import it.thera.thip.base.commessa.CommessaTM;
import it.thera.thip.datiTecnici.modpro.AttivitaProdMateriale;
import it.thera.thip.datiTecnici.modpro.EspNodoArticolo;
import it.thera.thip.datiTecnici.modpro.EspNodoAttivita;
import it.thera.thip.datiTecnici.modpro.ModelloProduttivo;
import it.thera.thip.datiTecnici.modpro.ModproEsplosione;
import it.thera.thip.produzione.ordese.OrdineEsecutivoTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 17/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	17/04/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class YOrdineEsecutivoCreazioneCommessaDoList extends WebDOList {

	@SuppressWarnings("unchecked")
	@Override
	public void setRestrictCondition(ClassAD[] attributes, String[] values) {
		ClassAD[] attributesOk = Arrays.copyOf(attributes, 1);
		String[] valuesOk = Arrays.copyOf(values, 1);
		super.setRestrictCondition(attributesOk, valuesOk);
		if(attributes.length > 2 && values.length > 2) {
			String idAzienda = values[0];
			String idArticolo = values[1];
			String priorita = values[2];
			String idStabilim = values[3];
			String dominio = values[4];
			String dataInizio = values[5];
			if(idArticolo == NULL_VALUE && priorita == NULL_VALUE && idStabilim == NULL_VALUE && dominio == NULL_VALUE && dataInizio == NULL_VALUE) {
				specificWhereClause += " AND ("+CommessaTM.TABLE_NAME+"."+CommessaTM.ID_COMMESSA+" = 'XXXXXX') ";
			}else{
				YArticoliDatiInd estensione = YArticoliDatiInd.recuperaEstensioneArticolo(idAzienda, idArticolo);
				if(estensione != null && estensione.getTipologiaArticolo() == YArticoliDatiInd.NON_SIGNIFICATIVO) {
					char[] tipi = new char[3]; 
					tipi[0] = ModelloProduttivo.PRODUZIONE;
					tipi[1] = ModelloProduttivo.ATTREZZAGGIO;
					tipi[2] = ModelloProduttivo.RILAVORAZIONE;
					try {
						ModelloProduttivo modPro = ModproEsplosione.trovaModelloProduttivo(idAzienda, idArticolo, idStabilim, null, null, dominio.charAt(0), tipi);
						if(modPro == null) {
							specificWhereClause += " AND ("+CommessaTM.TABLE_NAME+"."+CommessaTM.ID_COMMESSA+" = 'XXXXXX') ";
						}else {
							ModproEsplosione modproEsplosione = creaModproEsplosione(modPro); 
							modproEsplosione.run();
							EspNodoArticolo radiceArticolo = modproEsplosione.getNodoRadice();
							List<EspNodoAttivita> attivitaList = radiceArticolo.getNodiFigli();
							int size = attivitaList.size();
							List<String> idArticoli = new ArrayList<String>();
							for (int i = 0; i < size; i++) { 
								EspNodoAttivita radiceAttivita = (EspNodoAttivita) attivitaList.get(i);
								List<EspNodoArticolo> attivitaMatList = radiceAttivita.getNodiMaterialiFigli();
								Iterator<EspNodoArticolo> iterMat = attivitaMatList.iterator();
								while (iterMat.hasNext()) {
									EspNodoArticolo nodo = (EspNodoArticolo) iterMat.next();
									AttivitaProdMateriale apMat = nodo.getAttivitaProdMateriale();
									idArticoli.add(apMat.getIdArticolo());
								}
							}
							DateType dateType = new DateType();
							Date dataIniziosql = (Date) dateType.stringToObject(dataInizio);
							String IN = getInnPerListaMateriali(idArticoli, OrdineEsecutivoTM.R_ARTICOLO);
							java.sql.Date limitDate = java.sql.Date.valueOf("9999-12-31");
							List<String> commesse = YCommessa.listaCommesseCompatibiliOrdiniFiniti(IN,
									dataIniziosql,
											limitDate,
													limitDate);
							if(!commesse.isEmpty()) {
								String in = getInCommesse(commesse,CommessaTM.ID_COMMESSA);
								specificWhereClause += " AND "+CommessaTM.TABLE_NAME+"."+in+" ";
							}else {
								specificWhereClause += " AND ("+CommessaTM.TABLE_NAME+"."+CommessaTM.ID_COMMESSA+" = 'XXXXXX') ";
							}
						}
					} catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}else {
					//non e' un finito quindi non mi interessa la commmessa
					specificWhereClause += " AND ("+CommessaTM.TABLE_NAME+"."+CommessaTM.ID_COMMESSA+" = 'XXXXXX') ";
				}
			}
		}else {
			specificWhereClause += " AND ("+CommessaTM.TABLE_NAME+"."+CommessaTM.ID_COMMESSA+" = 'XXXXXX') ";
		}
	}

	public ModproEsplosione creaModproEsplosione(ModelloProduttivo modPro){
		ModproEsplosione modproEsplosione = (ModproEsplosione)Factory.createObject(ModproEsplosione.class);
		modproEsplosione.setTipoEsplosione(ModproEsplosione.PRODUZIONE);
		if (modPro != null) {
			modproEsplosione.setModello(modPro);
			modproEsplosione.setIdArticolo(modPro.getIdArticolo());
			modproEsplosione.setIdCommessa(null); 
		}
		modproEsplosione.setVersione(null);
		modproEsplosione.setConfigurazione(null);

		modproEsplosione.setData(null);
		modproEsplosione.setQuantita(null);
		modproEsplosione.setTipoCiclo(null);
		modproEsplosione.setGesConfigTmp(ModproEsplosione.CREATE_MEMORIZZATE);
		modproEsplosione.setApplicaFattoreScarto(true);
		modproEsplosione.setApplicaEfficienzaRsr(true);
		modproEsplosione.setRilascioOrdineEsecutivo(true);
		modproEsplosione.setUsaArticoliWIP(true); //Fix 5814
		modproEsplosione.setLivelloMassimo(1);
		return modproEsplosione;
	}

	protected String getInCommesse(List<String> commesse, String columnName) {
		StringBuilder IN = new StringBuilder();
		if (!commesse.isEmpty()) {
			IN.append(columnName).append(" IN (");
			Iterator<String> iterMateriali = commesse.iterator();
			while(iterMateriali.hasNext()) {
				String idCommessa = iterMateriali.next();
				IN.append("'").append(idCommessa).append("'");
				if (iterMateriali.hasNext()) {
					IN.append(", ");
				}
			}
			IN.append(")");
		}
		return IN.toString();
	}

	public static String getInnPerListaMateriali(List<String> materiali, String columnName) {
		StringBuilder IN = new StringBuilder();
		if (!materiali.isEmpty()) {
			IN.append(columnName).append(" IN (");
			Iterator<String> iterMateriali = materiali.iterator();
			while(iterMateriali.hasNext()) {
				String materiale = iterMateriali.next();
				IN.append("'").append(materiale).append("'");
				if (iterMateriali.hasNext()) {
					IN.append(", ");
				}
			}
			IN.append(")");
		}
		return IN.toString();
	}

}
