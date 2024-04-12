package it.bvr.thip.produzione.ordese;

import java.sql.SQLException;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.security.Authorizable;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 10/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	10/04/2024</b>
 * <p>Prima stesura.<br>
 *  Lancio della stored procedure.<br>
 *  In futuro verra' implementata la logica a programma.
 * </p>
 */

public class YExpOrdiniEsecutivi extends BatchRunnable implements Authorizable {

	@Override
	protected boolean run() {
		boolean isOk = false;
		output.println("** Inizio esportazione ordini di produzione verso tabella di frontiera **");
		isOk = lanciaStoredProcedure();
		output.println("** Termine esportazione ordini di produzione verso tabella di frontiera **");
		return isOk;
	}

	/**
	 * @author Daniele Signoroni 10/04/2024
	 * <p>
	 * Prima stesura.<br>
	 *
	 * </p>
	 * @return true se la stored e' finita correttamente, false altrimenti
	 */
	protected boolean lanciaStoredProcedure() {
		boolean isOk = false;
		CachedStatement cs = null;
		String stmt  = " EXEC SOFTRE.Y_MACCHINE_P01 ";
		cs = new CachedStatement(stmt);
		try {
			int ris = cs.executeUpdate();
			isOk = ris > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(cs != null) {
					cs.free();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isOk;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YExpOrdiniEsecutivi";
	}

}
