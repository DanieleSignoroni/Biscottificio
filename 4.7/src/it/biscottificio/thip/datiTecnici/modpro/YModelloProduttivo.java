package it.biscottificio.thip.datiTecnici.modpro;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.*;
import it.thera.thip.datiTecnici.modpro.*;
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

public class YModelloProduttivo extends ModelloProduttivo {

	public static final String BISC_00001 = "BISC_00001"; //codice di errore

	protected Integer iNumeroRicetta;

	public YModelloProduttivo() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setNumeroRicetta(Integer numeroRicetta) {
		this.iNumeroRicetta = numeroRicetta;
		setDirty();
	}

	public Integer getNumeroRicetta() {
		return iNumeroRicetta;
	}

	/**
	 * @author Daniele Signoroni 10/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Se siamo in inserimento controlla che la ricetta non esista gia'.<br>
	 * </p>
	 * @return
	 */
	public ErrorMessage checkNumeroRicetta() {
		ErrorMessage em = null;
		if(esisteRicettaDatabase(getNumeroRicetta())) {
			em = new ErrorMessage(BISC_00001, new String[] {getNumeroRicetta().toString()});
		}
		return em;
	}

	/**
	 * @author Daniele Signoroni 10/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Serve per capire se dato un numero, esiste gia' un record in {@value YModelloProduttivoTM#TABLE_NAME_EXT},
	 * con il medesimo {@value YModelloProduttivoTM#NUM_RICETTA}.
	 * </p>
	 * @param numeroRicetta
	 * @return true se esiste, false se non esiste, false se numeroRicetta == null
	 */
	protected static boolean esisteRicettaDatabase(Integer numeroRicetta) {
		if(numeroRicetta == null) {
			return false;
		}
		ResultSet rs = null;
		CachedStatement cs = null;
		String stmt = " SELECT * FROM "+YModelloProduttivoTM.TABLE_NAME_EXT+" Y "
				+ " WHERE "+YModelloProduttivoTM.ID_AZIENDA+" = '"+Azienda.getAziendaCorrente()+"' "
				+ " AND "+YModelloProduttivoTM.NUM_RICETTA+" = "+numeroRicetta+" ";
		try {
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return false;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
	}

}

