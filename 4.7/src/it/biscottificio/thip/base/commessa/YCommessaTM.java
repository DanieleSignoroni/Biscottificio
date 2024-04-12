package it.biscottificio.thip.base.commessa;

import java.sql.*;
import it.thera.thip.base.commessa.*;
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

public class YCommessaTM extends CommessaTM {

	public static final String R_ANNO_ORD_ESEC = "R_ANNO_ORD_ESEC";

	public static final String R_NUMERO_ORD_ESEC = "R_NUMERO_ORD_ESEC";

	public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YCOMMESSE";

	private static final String CLASS_NAME = it.biscottificio.thip.base.commessa.YCommessa.class.getName();

	public YCommessaTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		super.initialize();
		setObjClassName(CLASS_NAME);
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		linkTable(TABLE_NAME_EXT);
		addAttributeOnTable("RAnnoOrdEsec", R_ANNO_ORD_ESEC, TABLE_NAME_EXT);
		addAttributeOnTable("RNumeroOrdEsec", R_NUMERO_ORD_ESEC, TABLE_NAME_EXT);
	}

}

