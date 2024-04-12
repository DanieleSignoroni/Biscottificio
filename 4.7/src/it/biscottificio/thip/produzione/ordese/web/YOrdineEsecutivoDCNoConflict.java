package it.biscottificio.thip.produzione.ordese.web;

import it.thera.thip.YArticoliDatiInd;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.produzione.ordese.web.OrdineEsecutivoDCNoConflict;

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

public class YOrdineEsecutivoDCNoConflict extends OrdineEsecutivoDCNoConflict {
	
	@Override
	public void updateHandlingModeOnComponentManagers() {
		super.updateHandlingModeOnComponentManagers();
		OrdineEsecutivo ordEsec = (OrdineEsecutivo) getBo();
		if(ordEsec.isOnDB()) {
			YArticoliDatiInd artExt = YArticoliDatiInd.recuperaEstensioneArticolo(ordEsec.getIdAzienda(), ordEsec.getIdArticolo());
			if(artExt != null
					&& artExt.getTipologiaArticolo() != YArticoliDatiInd.NON_SIGNIFICATIVO) {
				//vuol dire che non e' un prodotto finito
				denyComponent(getComponent("IdCommessa"));
			}
		}
	}
}
