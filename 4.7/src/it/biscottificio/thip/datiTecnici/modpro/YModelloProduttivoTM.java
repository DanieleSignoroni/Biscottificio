package it.biscottificio.thip.datiTecnici.modpro;

import java.sql.*;
import it.thera.thip.datiTecnici.modpro.*;
import com.thera.thermfw.base.*;

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

public class YModelloProduttivoTM extends ModelloProduttivoTM {

	public static final String NUM_RICETTA = "NUM_RICETTA";

	public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YMODPRO";

	private static final String CLASS_NAME = it.biscottificio.thip.datiTecnici.modpro.YModelloProduttivo.class.getName();

	public YModelloProduttivoTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		super.initialize();
		setObjClassName(CLASS_NAME);
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		linkTable(TABLE_NAME_EXT);
		addAttributeOnTable("NumeroRicetta", NUM_RICETTA, "getIntegerObject", TABLE_NAME_EXT);
	}

}

