package it.bvr.thip.produzione.ordese;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.persist.Factory;

import it.thera.thip.cs.ResultSetIterator;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 23/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	23/04/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class TblProduzioneIterator extends ResultSetIterator {

	public TblProduzioneIterator(ResultSet rs) {
		super(rs);
	}

	@Override
	protected Object createObject() throws SQLException {
		TblProduzione obj = (TblProduzione) Factory.createObject(TblProduzione.class);
		obj.setId(BigInteger.valueOf(cursor.getInt("ID")));
		obj.setDataOra(cursor.getDate("DataOra"));
		obj.setTurno(cursor.getInt("Turno"));
		obj.setLotto(cursor.getString("Lotto").trim());
		obj.setCodArticolo(cursor.getString("CodArticolo").trim());
		obj.setDescImpasto(cursor.getString("DescImpasto").trim());
		obj.setQtCartoni(cursor.getInt("Qt.Cartoni"));
		obj.setQtCartoniProdotti(cursor.getInt("Qt_CartoniProdotti"));
		obj.setKgImpasto(cursor.getFloat("KgImpasto"));
		obj.setRif_ODP(cursor.getString("Rif_ODP").trim());
		obj.setFlag(cursor.getString("Flag").trim().charAt(0));
		return obj;
	}

}
