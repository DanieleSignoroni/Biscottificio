/*
 * @(#)YModelloProduttivoTM.java
 */

/**
 * YModelloProduttivoTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 10/04/2024 at 15:44:53
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 10/04/2024    Wizard     Codice generato da Wizard
 *
 */
package it.biscottificio.thip.datiTecnici.modpro;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.common.*;
import java.sql.*;
import it.thera.thip.datiTecnici.modpro.*;
import com.thera.thermfw.base.*;

public class YModelloProduttivoTM extends ModelloProduttivoTM {

  
  /**
   * Attributo NUM_RICETTA
   */
  public static final String NUM_RICETTA = "NUM_RICETTA";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YMODPRO";

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.biscottificio.thip.datiTecnici.modpro.YModelloProduttivo.class.getName();

  
  /**
   *  YModelloProduttivoTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YModelloProduttivoTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected void initialize() throws SQLException {
    super.initialize();
    setObjClassName(CLASS_NAME);
  }

  /**
   *  initializeRelation
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void initializeRelation() throws SQLException {
    super.initializeRelation();
    linkTable(TABLE_NAME_EXT);
    addAttributeOnTable("NumeroRicetta", NUM_RICETTA, "getIntegerObject", TABLE_NAME_EXT);
    
    

  }

}

