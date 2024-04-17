/*
 * @(#)YAnagrSpecProdTM.java
 */

/**
 * YAnagrSpecProdTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 16/04/2024 at 15:12:16
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 16/04/2024    Wizard     Codice generato da Wizard
 *
 */
package it.bvr.thip.produzione.ordese;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.common.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

public class YAnagrSpecProdTM extends TableManager {

  
  /**
   * Attributo ID_AZIENDA
   */
  public static final String ID_AZIENDA = "ID_AZIENDA";

  /**
   * Attributo STATO
   */
  public static final String STATO = "STATO";

  /**
   * Attributo R_UTENTE_CRZ
   */
  public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

  /**
   * Attributo TIMESTAMP_CRZ
   */
  public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

  /**
   * Attributo R_UTENTE_AGG
   */
  public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

  /**
   * Attributo TIMESTAMP_AGG
   */
  public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

  /**
   * Attributo ID_SPECIFICA
   */
  public static final String ID_SPECIFICA = "ID_SPECIFICA";

  /**
   * Attributo TESTO
   */
  public static final String TESTO = "TESTO";

  /**
   * Attributo TITOLO_SPECIFICA
   */
  public static final String TITOLO_SPECIFICA = "TITOLO_SPECIFICA";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YANAGR_SPEC_PROD";

  /**
   *  instance
   */
  private static TableManager cInstance;

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.bvr.thip.produzione.ordese.YAnagrSpecProd.class.getName();

  
  /**
   *  getInstance
   * @return TableManager
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public synchronized static TableManager getInstance() throws SQLException {
    if (cInstance == null) {
      cInstance = (TableManager)Factory.createObject(YAnagrSpecProdTM.class);
    }
    return cInstance;
  }

  /**
   *  YAnagrSpecProdTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YAnagrSpecProdTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected void initialize() throws SQLException {
    setTableName(TABLE_NAME);
    setObjClassName(CLASS_NAME);
    init();
  }

  /**
   *  initializeRelation
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void initializeRelation() throws SQLException {
    super.initializeRelation();
    addAttribute("IdSpecifica", ID_SPECIFICA, "getIntegerObject");
    addAttribute("Testo", TESTO);
    addAttribute("TitoloSpecifica", TITOLO_SPECIFICA);
    addAttribute("IdAzienda", ID_AZIENDA);
    
    addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
    setKeys(ID_AZIENDA + "," + ID_SPECIFICA);

    setTimestampColumn("TIMESTAMP_AGG");
    ((it.thera.thip.cs.DatiComuniEstesiTTM)getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
  }

  /**
   *  init
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  private void init() throws SQLException {
    configure(ID_SPECIFICA + ", " + TESTO + ", " + TITOLO_SPECIFICA + ", " + ID_AZIENDA
         + ", " + STATO + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG
         + ", " + TIMESTAMP_AGG);
  }

}

