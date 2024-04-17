package it.bvr.thip.produzione.ordese;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;

import it.biscottificio.thip.base.commessa.YCommessa;
import it.thera.thip.YArticoliDatiInd;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.produzione.ordese.AttivitaEsecMateriale;
import it.thera.thip.produzione.ordese.AttivitaEsecutiva;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.produzione.ordese.OrdineEsecutivoTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 10/04/2024
 * <br><br>
 * <b>71501	DSSOF3	10/04/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */
public class YOrdineEsecutivo extends OrdineEsecutivo {

	//YStatoIndustria --> Attribute.Ref = Ystatoindustria4.0
	public static final char DA_ESPORTARE = '0';
	public static final char ESPORTATO = '1';
	public static final char DA_NON_ESPORTARE = '9';

	protected char iYstatoindustria = DA_ESPORTARE;
	protected boolean iYcontEsaurImp;
	
	public static final String BISC_00002 = "BISC_00002";

	public YOrdineEsecutivo() {
		setYstatoindustria(DA_ESPORTARE);
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setYstatoindustria(char ystatoindustria) {
		this.iYstatoindustria = ystatoindustria;
		setDirty();
	}

	public char getYstatoindustria() {
		return iYstatoindustria;
	}
	
	public boolean isYcontEsaurImp() {
		return iYcontEsaurImp;
	}

	public void setYcontEsaurImp(boolean iYcontEsaurImp) {
		this.iYcontEsaurImp = iYcontEsaurImp;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors =  super.checkAll(components);
		if(!isOnDB()) {
			ErrorMessage em = checkCommessaPerArticoloFinito();
			if(em != null) {
				errors.add(em);
			}
		}
		return errors;
	}

	protected ErrorMessage checkCommessaPerArticoloFinito() {
		ErrorMessage em = null;
		if(getIdArticolo() != null) {
			YArticoliDatiInd datiExt = YArticoliDatiInd.recuperaEstensioneArticolo(getIdAzienda(), getIdArticolo());
			if(datiExt != null && datiExt.getTipologiaArticolo() == YArticoliDatiInd.NON_SIGNIFICATIVO) {
				//e' un finito e quindi voglio che ci sia la commessa
				if(getIdCommessa() != null) {
					//devo checkare che quella che ci sia e' corretta
				}else {
					em = new ErrorMessage(BISC_00002,"La commessa e' obbligatoria per il prodotto finito");
				}
			}
		}
		return em;
	}

	@Override
	public int save() throws SQLException {
		boolean isOnDB = isOnDB();
		int rc = super.save();
		if (!isOnDB && rc > 0) {
			codificaAutomaticaCommessaOrdiniImpasto();
		}
		return rc;
	}

	/**
	 * @author Daniele Signoroni
	 * <p>
	 * Nella creazione di un nuovo Ordine Esecutivo, per gli articoli impasto va codificata una nuova commessa.<br>
	 * Ogni impasto rappresenta di fatto una nuova commessa.<br>
	 * </p>
	 */
	protected void codificaAutomaticaCommessaOrdiniImpasto() {
		YArticoliDatiInd artDatiExt = YArticoliDatiInd.recuperaEstensioneArticolo(getIdAzienda(), getIdArticolo());
		if(artDatiExt != null
				&& artDatiExt.getTipologiaArticolo() == YArticoliDatiInd.IMPASTO) {
			if(Factory.getName(YCommessa.class.getName(), Factory.CLASS) != null) { //solo se e' attivo il repl della classe
				YCommessa commessa = (YCommessa) Factory.createObject(YCommessa.class);
				commessa.setIdAzienda(getIdAzienda());
				commessa.setIdCommessa(YCommessa.getNextProgressivoCommessaArticoliImpasto());
				commessa.getDescrizione().setDescrizione("Commessa da ordine esec.");
				commessa.getDescrizione().setDescrizioneRidotta("Comm.ord.esec");
				commessa.setOrdineesecutivoorigine(this);
				commessa.setDataApertura(getDateRichieste().getStartDate());
				try {
					int rc = commessa.save();
					if(rc > 0) {
						setCommessa(commessa);
						save(); //qui risalviamo per portare su la commessa
					}
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
	}

	/**
	 * @author Daniele Signoroni 12/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Dato un ordine esecutivo e un nome di colonna ritorna la IN per il codice articolo del materiale.<br>
	 * </p>
	 * @param ordEsec
	 * @param columnName dovrebbe essere sempre e solo {@value OrdineEsecutivoTM#R_ARTICOLO}
	 * @return una IN con la lista degli articoli, se non ci sono materiali o l'ordine e' null una stringa vuota
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getInnPerListaMateriali(OrdineEsecutivo ordEsec, String columnName) {
		StringBuilder IN = new StringBuilder();
		List materiali = getListaMateriali(ordEsec);
		if (!materiali.isEmpty()) {
			IN.append(columnName).append(" IN (");
			Iterator<AttivitaEsecMateriale> iterMateriali = materiali.iterator();
			while(iterMateriali.hasNext()) {
				AttivitaEsecMateriale materiale = iterMateriali.next();
				IN.append("'").append(materiale.getIdArticolo()).append("'");
				if (iterMateriali.hasNext()) {
					IN.append(", ");
				}
			}
			IN.append(")");
		}
		return IN.toString();
	}

	/**
	 * @author Daniele Signoroni 12/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Dato un ordine esecutivo ritorna la lista dei materiali.<br>
	 * </p>
	 * @param ordine, se null ritorna una lista vuota
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getListaMateriali(OrdineEsecutivo ordine){
		List<AttivitaEsecMateriale> materiali = new ArrayList<AttivitaEsecMateriale>();
		if(ordine == null)
			return materiali;
		List attivitaList = ordine.getAttivitaEsecutive();
		if (attivitaList.size() >= 1) {
			for (int i = 0; i < attivitaList.size(); i++) {
				List materialiList = ((AttivitaEsecutiva) attivitaList.get(i)).getMateriali();
				materiali.addAll(materialiList);
			}
		}
		return materiali;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
	}

}
