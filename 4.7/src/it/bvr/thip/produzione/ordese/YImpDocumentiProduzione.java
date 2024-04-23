package it.bvr.thip.produzione.ordese;

import java.sql.Connection;
import java.sql.SQLException;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.persist.ConnectionDescriptor;
import com.thera.thermfw.persist.SQLServerJTDSNoUnicodeDatabase;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.generale.ParametroPsn;

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

	private static String host = "localhost"; //so che risiede sulla macchina dove e' presente Panthera
	private static String port = null;
	private static String dbname = null;
	private static String userName = null;
	private static String password = null;

	//Attribute ref : YStatoImpDocProdMES
	public static final char DA_CONVALIDARE = '0';
	public static final char CONVALIDATO = '1';

	protected char iStatoDocumenti;

	public char getStatoDocumenti() {
		return iStatoDocumenti;
	}

	public void setStatoDocumenti(char iStatoDocumenti) {
		this.iStatoDocumenti = iStatoDocumenti;
	}

	@Override
	protected boolean run() {
		output.println("** INIZIO IMPORTAZIONE DOCUMENTI PRODUZIONE DA DATABASE ESTERNO **");
		boolean isOk = true;
		retrieveConnectionValues();
		if(port != null && password != null && userName != null && dbname != null) {

		}else {
			isOk = false;
			output.println("  --> Valorizzare tutti i parametri personalizzati che riguardano la connessione al database esterno ");
		}
		output.println("** TERMINE IMPORTAZIONE DOCUMENTI PRODUZIONE DA DATABASE ESTERNO **");;
		return isOk;
	}

	/**
	 * @author Daniele Signoroni 18/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Apre una connessione al database esterno mediante connection descriptor di therm.<br>
	 * La chisura e' a cura dell'utente.<br>
 	 * </p>
	 * @return a {@link Connection} se riesce a connettersi, null altrimenti
	 */
	private static Connection apriConnessioneDatabaseEsterno() {
		if(dbname == null || userName == null || password == null)
			retrieveConnectionValues();
		Connection connection = null;
		ConnectionDescriptor cnd = new ConnectionDescriptor(dbname,userName, password, new SQLServerJTDSNoUnicodeDatabase(host, port));
		try {
			cnd.openConnection();
			connection = cnd.getConnection();
		} catch (SQLException e) {
			connection = null;
			e.printStackTrace(Trace.excStream);
		}
		return connection;
	}

	/**
	 * @author Daniele Signoroni 18/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Recupera dai parametri personalizzati, i parametri per la connessione.<br>
	 * </p>
	 */
	private static void retrieveConnectionValues() {
		//		ParametroPsn hostP = ParametroPsn.getParametroPsn("YWINPN6J3SBHSSQ", "Host");
		ParametroPsn portP = ParametroPsn.getParametroPsn("YWINPN6J3SBHSSQ", "Port");
		ParametroPsn dbnameP = ParametroPsn.getParametroPsn("YWINPN6J3SBHSSQ", "DBname");
		ParametroPsn userNameP = ParametroPsn.getParametroPsn("YWINPN6J3SBHSSQ", "UserName");
		ParametroPsn passwordP = ParametroPsn.getParametroPsn("YWINPN6J3SBHSSQ", "Password");
		//		if (hostP != null && hostP.getValore() != null) {
		//			host = hostP.getValore();
		//		}

		if (portP != null && portP.getValore() != null) {
			port = portP.getValore();
		}

		if (dbnameP != null && dbnameP.getValore() != null) {
			dbname = dbnameP.getValore();
		}

		if (userNameP != null && userNameP.getValore() != null) {
			userName = userNameP.getValore();
		}

		if (passwordP != null && passwordP.getValore() != null) {
			password = passwordP.getValore();
		}

	}

	@Override
	protected String getClassAdCollectionName() {
		return "YImportazioneDocProduz";
	}

}
