/*
 * @(#)YCommessaTM.java
 */

/**
 * YCommessaTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 10/04/2024 at 14:56:42
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 10/04/2024    Wizard     Codice generato da Wizard
 *
 */
package it.biscottificio.thip.base.commessa;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.common.*;
import java.sql.*;
import it.thera.thip.base.commessa.*;
import com.thera.thermfw.base.*;

public class YCommessaTM extends CommessaTM {

  
  /**
   * Attributo R_ANNO_ORD_ESEC
   */
  public static final String R_ANNO_ORD_ESEC = "R_ANNO_ORD_ESEC";

  /**
   * Attributo R_NUMERO_ORD_ESEC
   */
  public static final String R_NUMERO_ORD_ESEC = "R_NUMERO_ORD_ESEC";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YCOMMESSE";

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.biscottificio.thip.base.commessa.YCommessa.class.getName();

  
  /**
   *  YCommessaTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YCommessaTM() throws SQLException {
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
    addAttributeOnTable("RAnnoOrdEsec", R_ANNO_ORD_ESEC, TABLE_NAME_EXT);
    addAttributeOnTable("RNumeroOrdEsec", R_NUMERO_ORD_ESEC, TABLE_NAME_EXT);
    
    

  }

}

