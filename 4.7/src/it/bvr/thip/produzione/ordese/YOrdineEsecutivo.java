package it.bvr.thip.produzione.ordese;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;

import it.biscottificio.thip.base.commessa.YCommessa;
import it.thera.thip.YArticoliDatiInd;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.produzione.ordese.AttivitaEsecMateriale;
import it.thera.thip.produzione.ordese.AttivitaEsecutiva;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 10/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	10/04/2024</b>
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
		} else {
			IN.append(columnName).append(" IN ()");
		}

		return IN.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getListaMateriali(OrdineEsecutivo ordine){
		List<AttivitaEsecMateriale> materiali = new ArrayList<AttivitaEsecMateriale>();
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
