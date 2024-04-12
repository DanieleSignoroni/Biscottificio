package it.bvr.thip.produzione.ordese.web;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.thera.thermfw.ad.ClassAD;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.bvr.thip.produzione.ordese.YOrdineEsecutivo;
import it.thera.thip.YArticoliDatiInd;
import it.thera.thip.base.commessa.CommessaTM;
import it.thera.thip.cs.web.AziendaDOList;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.produzione.ordese.OrdineEsecutivoTM;

public class YOrdineEsecutivoCommessaDoList extends AziendaDOList {

	@Override
	public void setRestrictCondition(ClassAD[] attributes, String[] values) {
		String idAnnoOrdine = null;
		String idNumeroOrdine = null;
		if(attributes.length > 2 && values.length > 2) {
			idAnnoOrdine = values[1];
			idNumeroOrdine = values[2];
		}
		ClassAD[] attributesOk = Arrays.copyOf(attributes, 1);
		String[] valuesOk = Arrays.copyOf(values, 1);
		super.setRestrictCondition(attributesOk, valuesOk);
		if(idAnnoOrdine != null && idNumeroOrdine != null) {
			try {
				OrdineEsecutivo ordEsec = (OrdineEsecutivo) OrdineEsecutivo.elementWithKey(OrdineEsecutivo.class, 
						KeyHelper.buildObjectKey(new String[] {
								values[0],
								idAnnoOrdine,
								idNumeroOrdine
						}), PersistentObject.NO_LOCK);
				if(ordEsec != null) {
					YArticoliDatiInd datiArtExt = YArticoliDatiInd.recuperaEstensioneArticolo(ordEsec.getIdAzienda(), ordEsec.getIdArticolo());
					if(datiArtExt == null
							|| datiArtExt.getTipologiaArticolo() != YArticoliDatiInd.NON_SIGNIFICATIVO) {
						//non ce' estensione o non e' un finito quindi non voglio mostrare nulla
						specificWhereClause += " AND ("+CommessaTM.TABLE_NAME+"."+CommessaTM.ID_COMMESSA+" = 'XXXXXX') "; //un valore casuale ??
					}else {
						SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
						String IN = YOrdineEsecutivo.getInnPerListaMateriali(ordEsec, OrdineEsecutivoTM.R_ARTICOLO);
						if(IN != null) {
							String tableName = CommessaTM.TABLE_NAME;
							specificWhereClause += " AND ( EXISTS ( "
									+ "    SELECT 1 "
									+ "    FROM THIPPERS.YCOMMESSE Y "
									+ "    LEFT OUTER JOIN THIP.ORD_ESEC O  "
									+ "    ON "+tableName+".ID_AZIENDA = O.ID_AZIENDA  "
									+ "    WHERE "+tableName+".ID_AZIENDA = Y.ID_AZIENDA  "
									+ "    AND "+tableName+".ID_COMMESSA = Y.ID_COMMESSA  "
									+ "    AND (Y.R_ANNO_ORD_ESEC IS NOT NULL AND Y.R_NUMERO_ORD_ESEC IS NOT NULL) "
									+ "    AND "+IN+" ";
							boolean isDateOk = false;
							if (ordEsec.getDateEffettive().getStartDate() != null) {
								specificWhereClause += "    AND O.DATA_INIZIO_EFF = '" + f.format(ordEsec.getDateEffettive().getStartDate()) + "' ";
								isDateOk = true;
							}
							if (ordEsec.getDateProgrammate().getStartDate() != null && !isDateOk) {
								specificWhereClause += "    AND O.DATA_INIZIO_PGM = '" + f.format(ordEsec.getDateProgrammate().getStartDate()) + "' ";
								isDateOk = true;
							}
							if (ordEsec.getDateRichieste().getStartDate() != null && !isDateOk) {
								specificWhereClause += "    AND O.DATA_INIZIO_RCS = '" + f.format(ordEsec.getDateRichieste().getStartDate()) + "' ";
							}
							specificWhereClause += "))";
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
	}
}
