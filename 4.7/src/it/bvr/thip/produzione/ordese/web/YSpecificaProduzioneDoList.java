package it.bvr.thip.produzione.ordese.web;

import java.util.Arrays;

import com.thera.thermfw.ad.ClassAD;
import com.thera.thermfw.web.WebDOList;

import it.bvr.thip.produzione.ordese.YAnagrSpecProdTM;
import it.bvr.thip.produzione.ordese.YLgmSpecProdTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 17/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	17/04/2024</b>
 * <p>Prima stesura.<br>
 *  Specific do list della specifica di produzione.<br>
 *  Anche in questa mi passo due attributi di coodo che sono IdArticolo,IdCliente.<br>
 *  Se entrambi sono NULL allora non mostro nessuna specifica, altrimenti filtro per articolo e cliente.<br>
 * </p>
 */

public class YSpecificaProduzioneDoList extends WebDOList {

	@Override
	public void setRestrictCondition(ClassAD[] attributes, String[] values) {
		
		ClassAD[] attributesOk = Arrays.copyOf(attributes, 1);
		String[] valuesOk = Arrays.copyOf(values, 1);
		
		super.setRestrictCondition(attributesOk, valuesOk);
		
		String idCliente = null;
		String idArticolo = null;
		if(attributes.length > 1 && values.length > 1) {
			idArticolo = values[1];
		}
		if(attributes.length > 2 && values.length > 2) {
			idCliente = values[2];
		}
		
		if(idCliente == NULL_VALUE && idArticolo == NULL_VALUE) {
			specificWhereClause += " AND ("+YAnagrSpecProdTM.TABLE_NAME+"."+YAnagrSpecProdTM.ID_SPECIFICA+" = -1 ) ";
		}else {
			String exists = " AND ( EXISTS ( "
					+ "	SELECT * FROM "+YLgmSpecProdTM.TABLE_NAME+" L "
					+ "	WHERE "+YAnagrSpecProdTM.TABLE_NAME+"."+YAnagrSpecProdTM.ID_AZIENDA+" = L."+YLgmSpecProdTM.ID_AZIENDA+" "
					+ "	AND "+YAnagrSpecProdTM.TABLE_NAME+"."+YAnagrSpecProdTM.ID_SPECIFICA+" = L."+YLgmSpecProdTM.R_SPECIFICA+" ";
			if(idArticolo != NULL_VALUE) {
				exists += " AND L."+YLgmSpecProdTM.R_ARTICOLO+" = '"+idArticolo+"' ";
			}
			if(idCliente != NULL_VALUE) {
				exists += " AND L."+YLgmSpecProdTM.R_CLIENTE+" = '"+idCliente+"' ";
			}
			exists += " )) ";
			specificWhereClause += exists;
		}
	}
}
