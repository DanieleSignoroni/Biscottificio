package it.bvr.thip.produzione.ordese;

import it.thera.thip.produzione.ordese.OrdineEsecutivoCopia;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 15/04/2024
 * <br><br>
 * <b>71501	DSSOF3	15/04/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class YOrdineEsecutivoCopia extends OrdineEsecutivoCopia {

	/**
	 * @author Daniele Signoroni 15/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * In copia voglio annullare la compilazione della commessa in quanto esistono dei meccanismi,
	 * per i quali la commessa non puo' essere copiata.<br>
	 * Attenzione non settare l'oggetto della proxy in quanto svuota l'azienda internal.<br>
	 * </p>
	 */
	@Override
	protected void initOrdineEsecutivoCopia() {
		super.initOrdineEsecutivoCopia();
		setIdCommessa(null);
		isInCopia = true;
	}
	
}
