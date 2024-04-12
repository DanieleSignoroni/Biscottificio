package it.thera.thip;

import java.sql.SQLException;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

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

public class YArticoliDatiInd extends YArticoliDatiIndPO {

	//TipologiaArticolo --> Attribute.Ref = YTipologiaArticolo
	public static final char NON_SIGNIFICATIVO = '0';
	public static final char IMPASTO = '1';
	public static final char VASCHETTA = '2';
	public static final char INCARTO = '3';
	public static final char ASTUCCIO = '4';
	public static final char CARTONE = '5';
	public static final char COPERCHIO = '6';

	public ErrorMessage checkDelete() {
		return null;
	}

	/**
	 * @author Daniele Signoroni
	 * <p>
	 * Recupera l'oggetto persistente dell'estensione dati i due parametri chiave.<br>
	 * </p>
	 * @param idAzienda {0} key
	 * @param idArticolo {1} key
	 * @return il PersistentObject se c'e' altrimenti null
	 */
	public static YArticoliDatiInd recuperaEstensioneArticolo(String idAzienda,String idArticolo) {
		try {
			return (YArticoliDatiInd) YArticoliDatiInd.elementWithKey(YArticoliDatiInd.class, 
					KeyHelper.buildObjectKey(new String[] {
							idAzienda,
							idArticolo
					}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

}
