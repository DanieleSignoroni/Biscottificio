/*
 * @(#)YLgmSpecProd.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera s.p.a.</b>
 * @author Wizard 16/04/2024 at 14:22:35
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
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.cliente.ClienteVendita;
import com.thera.thermfw.common.*;
import com.thera.thermfw.base.*;

public class YLgmSpecProd extends YLgmSpecProdPO {

  
  
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
        setIdLegame(new Integer(Numerator.getNextInt("YLgmSpecProd")));
      }
      catch(NumeratorException e) {e.printStackTrace(Trace.excStream);}
    }
    return super.save();
  }

}

