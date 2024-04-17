/*
 * @(#)YAnagrSpecProd.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera s.p.a.</b>
 * @author Wizard 16/04/2024 at 14:14:55
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 16/04/2024    Wizard     Codice generato da Wizard
 *
 */
package it.bvr.thip.produzione.ordese;
import com.thera.thermfw.persist.*;
import java.sql.*;
import it.thera.thip.base.azienda.AziendaEstesa;
import com.thera.thermfw.common.*;
import com.thera.thermfw.base.*;

public class YAnagrSpecProd extends YAnagrSpecProdPO {

  
  
  /**
   * checkDelete
   * @return ErrorMessage
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public ErrorMessage checkDelete() {
    /**@todo*/
    return null;
  }

  /**
   * save
   * @return int
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public int save() throws SQLException {
    if (!isOnDB()) {
      try {
        setIdSpecifica(new Integer(Numerator.getNextInt("YAnagrSpecProd")));
      }
      catch(NumeratorException e) {e.printStackTrace(Trace.excStream);}
    }
    return super.save();
  }

}

