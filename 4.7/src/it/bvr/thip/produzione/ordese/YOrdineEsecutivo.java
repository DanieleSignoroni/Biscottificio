package it.bvr.thip.produzione.ordese;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Database;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;

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
 * <b>71XXX	DSSOF3	17/04/2024</b>
 * <p>
 * Aggiungere obbligatorieta' della commessa per articoli finiti.<br>
 * </p>
 */
public class YOrdineEsecutivo extends OrdineEsecutivo {

	public static final int UPDATE_OK =  1;
	public static final int UPDATE_KO = -100;

	protected static String STMT_UPD_STATO_INDUSTRIA = "UPDATE "+YOrdineEsecutivoTM.TABLE_NAME_EXT+" "
			+ "SET "+YOrdineEsecutivoTM.YSTATOINDUSTRIA+" = ? "
			+ "WHERE "+YOrdineEsecutivoTM.ID_AZIENDA+" = ? "
			+ "AND "+YOrdineEsecutivoTM.ID_ANNO_ORD+" = ? "
			+ "AND "+YOrdineEsecutivoTM.ID_NUMERO_ORD+" = ? ";

	protected static CachedStatement csUpdtStatoIndustria = new CachedStatement(STMT_UPD_STATO_INDUSTRIA);

	//YStatoIndustria --> Attribute.Ref = Ystatoindustria4.0
	public static final char DA_ESPORTARE = '0';
	public static final char ESPORTATO = '1';
	public static final char DA_NON_ESPORTARE = '9';

	protected char iYstatoindustria = DA_ESPORTARE;
	protected boolean iYcontEsaurImp;

	protected boolean isInCopia = false;

	protected Proxy iSpecificaProduzione = new Proxy(it.bvr.thip.produzione.ordese.YAnagrSpecProd.class);

	public static final String BISC_00002 = "BISC_00002";
	public static final String BISC_00003 = "BISC_00003";

	public YOrdineEsecutivo() {
		super();
		setYstatoindustria(DA_ESPORTARE);
		setAziendaInternal(getIdAzienda());
	}

	/**
	 * @author Daniele Signoroni 17/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * E' uno specchio del setIdAziendaInternal padre.<br>
	 * Putroppo non possiamo fare l'override e agire sulla nostra nuova proxy in quanto nelle new() le Proxy di questa classe non sono state ancora inizializzate
	 * e quindi andrebbe in NullPointerException, quindi una volta che i costruttori padri mi hanno valorizzato l'azienda,
	 * cerco di portarla sulle proxy di questa classe, se ci sono, e se l'azienda e' != null.<br></br>
	 * 
	 * Perfavore non toccare, a meno che il seguente replacement non presenti Proxy.<br>
	 * </p>
	 * @param idAzienda
	 */
	protected void setAziendaInternal(String idAzienda) {
		if(idAzienda != null) { //all'avvio e' null
			String key0 = iSpecificaProduzione.getKey();
			iSpecificaProduzione.setKey(KeyHelper.replaceTokenObjectKey(key0, 1, idAzienda));
		}
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

	public void setSpecificaProduzione(YAnagrSpecProd specificaProduzione) {
		String oldObjectKey = getKey();
		String idAzienda = null;
		if (specificaProduzione != null) {
			idAzienda = KeyHelper.getTokenObjectKey(specificaProduzione.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iSpecificaProduzione.setObject(specificaProduzione);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public YAnagrSpecProd getSpecificaProduzione() {
		return (YAnagrSpecProd)iSpecificaProduzione.getObject();
	}

	public void setSpecificaProduzioneKey(String key) {
		String oldObjectKey = getKey();
		iSpecificaProduzione.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getSpecificaProduzioneKey() {
		return iSpecificaProduzione.getKey();
	}

	public void setIdSpecificaProduzione(Integer idSpecificaProduzione) {
		String key = iSpecificaProduzione.getKey();
		iSpecificaProduzione.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idSpecificaProduzione));
		setDirty();
	}

	public Integer getIdSpecificaProduzione() {
		String key = iSpecificaProduzione.getKey();
		String objIdSpecificaProduzione = KeyHelper.getTokenObjectKey(key,2);
		return KeyHelper.stringToIntegerObj(objIdSpecificaProduzione);
	}

	public boolean isInCopia() {
		return isInCopia;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors =  super.checkAll(components);
		ErrorMessage em = checkPersArticoloFinito();
		if(em != null) {
			errors.add(em);
		}
		return errors;
	}

	/**
	 * @author Daniele Signoroni 17/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Se l'articolo dell'ordine esecutivo e' un finito allora voglio che la commessa sia obbligatoria.<br>
	 * Se l'articolo dell'ordine esecutivo e' un finito allora voglio che la specifica sia obbligatoria.<br>
	 * </p>
	 * @return
	 */
	protected ErrorMessage checkPersArticoloFinito() {
		ErrorMessage em = null;
		if(isInCopia()) {
			return em;
		}
		if(getIdArticolo() != null) {
			YArticoliDatiInd datiExt = YArticoliDatiInd.recuperaEstensioneArticolo(getIdAzienda(), getIdArticolo());
			if(datiExt != null && datiExt.getTipologiaArticolo() == YArticoliDatiInd.NON_SIGNIFICATIVO) {
				//e' un finito e quindi voglio che ci sia la commessa
				if(getIdCommessa() != null) {
					//devo checkare che quella che ci sia e' corretta
				}else {
					em = new ErrorMessage(BISC_00002,"La commessa e' obbligatoria per il prodotto finito");
				}
				if(em == null && getSpecificaProduzione() == null) {
					em = new ErrorMessage(BISC_00003);
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

	/**
	 * @author Daniele Signoroni 24/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Si occupa di ritornare il primo materiale impasto tra quelli presenti nell'ordine esecutivo
	 * </p>
	 * @param ordine
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static AttivitaEsecMateriale getPrimoArticoloImpastoTraMateriali(OrdineEsecutivo ordine) {
		List attivitaList = ordine.getAttivitaEsecutive();
		if (attivitaList.size() >= 1) {
			for (int i = 0; i < attivitaList.size(); i++) {
				List materialiList = ((AttivitaEsecutiva) attivitaList.get(i)).getMateriali();
				for(int j = 0; j < materialiList.size(); j++) {
					AttivitaEsecMateriale materiale = (AttivitaEsecMateriale) materialiList.get(j);
					YArticoliDatiInd estensione = YArticoliDatiInd.recuperaEstensioneArticolo(materiale.getIdAzienda(), materiale.getIdArticolo());
					if(estensione != null && estensione.getTipologiaArticolo() == YArticoliDatiInd.IMPASTO) {
						return materiale;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @author Daniele Signoroni 24/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Si occupa di ritornare il codice articolo che corrisponde alla tipologia passata.<br>
	 * </p>
	 * @param ordine
	 * @param tipologiaArticolo
	 * @return idArticolo se c'e' un match altrimenti null
	 */
	@SuppressWarnings("rawtypes")
	public static String getIdArticoloDaTipologiaTraMateriali(OrdineEsecutivo ordine, char tipologiaArticolo) {
		List attivitaList = ordine.getAttivitaEsecutive();
		if (attivitaList.size() >= 1) {
			for (int i = 0; i < attivitaList.size(); i++) {
				List materialiList = ((AttivitaEsecutiva) attivitaList.get(i)).getMateriali();
				for(int j = 0; j < materialiList.size(); j++) {
					AttivitaEsecMateriale materiale = (AttivitaEsecMateriale) materialiList.get(j);
					YArticoliDatiInd estensione = YArticoliDatiInd.recuperaEstensioneArticolo(materiale.getIdAzienda(), materiale.getIdArticolo());
					if(estensione != null && estensione.getTipologiaArticolo() == tipologiaArticolo) {
						return materiale.getIdArticolo();
					}
				}
			}
		}
		return null;
	}

	/**
	 * @author Daniele Signoroni 24/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Ritorna una lista di ordini esecutivi da esportare verso le tabelle del MES.<br>
	 * </p>
	 * @return
	 */
	public static List<YOrdineEsecutivo> estraiOrdiniEsecutiviDaEsportareMES(){
		List<YOrdineEsecutivo> ordini = new ArrayList<YOrdineEsecutivo>();
		String select = "SELECT "+YOrdineEsecutivoTM.ID_AZIENDA+","+YOrdineEsecutivoTM.ID_ANNO_ORD+","+YOrdineEsecutivoTM.ID_NUMERO_ORD+" ";
		String from = "FROM "+YOrdineEsecutivoTM.TABLE_NAME_EXT+" ";
		String where = "WHERE "+YOrdineEsecutivoTM.ID_AZIENDA+" = '"+Azienda.getAziendaCorrente()+"' "
				+ " AND "+YOrdineEsecutivoTM.YSTATOINDUSTRIA+" = '"+DA_ESPORTARE+"' ";
		CachedStatement cs = null;
		ResultSet rs = null;
		List<String> keys = new ArrayList<String>();
		try {
			cs = new CachedStatement(select+from+where);
			rs = cs.executeQuery();
			while(rs.next()) {
				keys.add(KeyHelper.buildObjectKey(new String[] {
						rs.getString(YOrdineEsecutivoTM.ID_AZIENDA),
						rs.getString(YOrdineEsecutivoTM.ID_ANNO_ORD),
						rs.getString(YOrdineEsecutivoTM.ID_NUMERO_ORD)
				}));
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(cs != null) {
					cs.free();
				}
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		for(String key : keys) {
			try {
				ordini.add((YOrdineEsecutivo) YOrdineEsecutivo.elementWithKey(YOrdineEsecutivo.class, key, PersistentObject.NO_LOCK));
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return ordini;

	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
	}

	/**
	 * @author Daniele Signoroni 24/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Si occupa di aggiornare il campo {@value YOrdineEsecutivoTM#YSTATOINDUSTRIA} a database.<br>
	 * </p>
	 * @param statoIndustria uno dei valori dell'enumerato Ystatoindustria4.0
	 * @return {@value #UPDATE_OK} se tutto ok, {@value #UPDATE_KO} se qualcosa e' andato storto
	 * @throws IllegalArgumentException se e' stato passato un char che non rientra nei valori del riferimento attributo
	 */
	public int aggiornaStatoIndustriaNoSave(char statoIndustria) throws IllegalArgumentException {
		if(statoIndustria == DA_ESPORTARE || statoIndustria == DA_NON_ESPORTARE || statoIndustria == ESPORTATO) {
			PreparedStatement ps = null;
			try {
				ps = csUpdtStatoIndustria.getStatement();
				Database db = ConnectionManager.getCurrentDatabase();

				db.setString(ps, 1, String.valueOf(statoIndustria));                             
				db.setString(ps, 2, getIdAzienda());                        		    
				db.setString(ps, 3, getIdAnnoOrdine());        
				db.setString(ps, 4, getIdNumeroOrdine());      

				if (ps.executeUpdate() >= 0)
					return UPDATE_OK;


			}catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}finally {
				if(ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}
			}
		}else {
			throw new IllegalArgumentException("Riferimento attributo Ystatoindustria4.0: Valore \"" + statoIndustria + "\" non trovato."); 
		}
		return UPDATE_KO;
	}

}
