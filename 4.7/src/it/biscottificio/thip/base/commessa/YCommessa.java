package it.biscottificio.thip.base.commessa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.*;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.base.commessa.*;
import it.thera.thip.base.azienda.Azienda;

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

public class YCommessa extends Commessa {

	protected Proxy iOrdineesecutivoorigine = new Proxy(it.thera.thip.produzione.ordese.OrdineEsecutivo.class);

	public YCommessa() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setOrdineesecutivoorigine(OrdineEsecutivo ordineesecutivoorigine) {
		String oldObjectKey = getKey();
		this.iOrdineesecutivoorigine.setObject(ordineesecutivoorigine);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public OrdineEsecutivo getOrdineesecutivoorigine() {
		return (OrdineEsecutivo)iOrdineesecutivoorigine.getObject();
	}

	public void setOrdineesecutivoorigineKey(String key) {
		String oldObjectKey = getKey();
		iOrdineesecutivoorigine.setKey(key);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getOrdineesecutivoorigineKey() {
		return iOrdineesecutivoorigine.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		super.setIdAzienda(idAzienda);
		if(iOrdineesecutivoorigine != null) {
			String key = iOrdineesecutivoorigine.getKey();
			iOrdineesecutivoorigine.setKey(KeyHelper.replaceTokenObjectKey(key , 1, idAzienda));
		}
	}

	public void setRAnnoOrdEsec(String rAnnoOrdEsec) {
		String key = iOrdineesecutivoorigine.getKey();
		iOrdineesecutivoorigine.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rAnnoOrdEsec));
		setDirty();
	}

	public String getRAnnoOrdEsec() {
		String key = iOrdineesecutivoorigine.getKey();
		String objRAnnoOrdEsec = KeyHelper.getTokenObjectKey(key,2);
		return objRAnnoOrdEsec;

	}

	public void setRNumeroOrdEsec(String rNumeroOrdEsec) {
		String key = iOrdineesecutivoorigine.getKey();
		iOrdineesecutivoorigine.setKey(KeyHelper.replaceTokenObjectKey(key , 3, rNumeroOrdEsec));
		setDirty();
	}

	public String getRNumeroOrdEsec() {
		String key = iOrdineesecutivoorigine.getKey();
		String objRNumeroOrdEsec = KeyHelper.getTokenObjectKey(key,3);
		return objRNumeroOrdEsec;
	}

	/**
	 * @author Daniele Signoroni 10/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Recupera il prossimo progressivo personalizzato per alcune commesse.<br>
	 * Il progressivo e' composto da : <br>
	 * <b>C + (prime due cifre anno in corso) + (progressivo di 6 numeri)</b>.
	 * </p>
	 * @return il progressivo buildato
	 */
	public static String getNextProgressivoCommessaArticoliImpasto() {
		String nuovoNumeratore = null;
		String prefix = "C";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String prefAnno = String.valueOf(year).substring(2);
		nuovoNumeratore = prefix + prefAnno;
		String lastNumber = getLastNumberProgressivoArticoliImpasto();
		int sizeLastNumber = lastNumber.length();
		int zeriMancanti = 6-sizeLastNumber;
		for(int i = 0; i < zeriMancanti; i++) {
			nuovoNumeratore += "0";
		}
		nuovoNumeratore += lastNumber;
		return nuovoNumeratore;
	}

	/**
	 * @author Daniele Signoroni 10/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Recupera da database il prossimo progressivo per le commesse che iniziano con C.<br>
	 * </p>
	 * @return il prossimo progressivo
	 */
	public static String getLastNumberProgressivoArticoliImpasto() {
		String lastN = null;
		CachedStatement cs = null;
		ResultSet rs = null;
		try {
			String select = "SELECT "
					+ "COALESCE(MAX(SUBSTRING(TRIM("+CommessaTM.ID_COMMESSA+"),4,10)),'0')+1 "
					+ "FROM "+CommessaTM.TABLE_NAME+"  "
					+ "WHERE "+CommessaTM.ID_COMMESSA+" LIKE 'C%'";
			cs = new CachedStatement(select);
			rs = cs.executeQuery();
			if(rs.next())
				lastN = rs.getString(1);
		}catch(SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(rs != null)
					rs.close();
			}catch(Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return lastN;
	}

	/**
	 * @author Daniele Signoroni 10/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Non copio l'Ordine Esecutivo di origine volutamente.<br>
	 * </p>
	 * @param obj
	 * @throws CopyException
	 */
	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		//YCommessa yCommessa = (YCommessa)obj;
		//iOrdineesecutivoorigine.setEqual(yCommessa.iOrdineesecutivoorigine);
	}

}

