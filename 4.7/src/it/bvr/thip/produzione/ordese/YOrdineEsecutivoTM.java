package it.bvr.thip.produzione.ordese;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;

import it.thera.thip.produzione.ordese.OrdineEsecutivoTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 17/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	17/04/2024</b>
 * <p>Prima stesura.<br>
 *  Aggiungere {@value #ID_SPECIFICA_PRODUZIONE}.<br>
 * </p>
 */

public class YOrdineEsecutivoTM extends OrdineEsecutivoTM {

	public static final String YSTATOINDUSTRIA = "YSTATOINDUSTRIA";
	
	public static final String YCONT_ESAUR_IMP = "YCONT_ESAUR_IMP";

	public static final String ID_SPECIFICA_PRODUZIONE = "R_SPECIFICA_PRODUZIONE";
	
	public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YORD_ESEC";

	private static final String CLASS_NAME = it.bvr.thip.produzione.ordese.YOrdineEsecutivo.class.getName();

	public YOrdineEsecutivoTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		super.initialize();
		setObjClassName(CLASS_NAME);
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		linkTable(TABLE_NAME_EXT);
		addAttributeOnTable("Ystatoindustria", YSTATOINDUSTRIA, TABLE_NAME_EXT);
		addAttributeOnTable("YcontEsaurImp", YCONT_ESAUR_IMP, TABLE_NAME_EXT);
		addAttributeOnTable("IdSpecificaProduzione", ID_SPECIFICA_PRODUZIONE, TABLE_NAME_EXT);

	}

}
