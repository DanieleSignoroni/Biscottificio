/*
 * @(#)YLgmSpecProdTM.java
 */

/**
 * YLgmSpecProdTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
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
import com.thera.thermfw.common.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

public class YLgmSpecProdTM extends TableManager {

  
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
   * Attributo ID_LEGAME
   */
  public static final String ID_LEGAME = "ID_LEGAME";

  /**
   * Attributo R_ARTICOLO
   */
  public static final String R_ARTICOLO = "R_ARTICOLO";

  /**
   * Attributo R_CLIENTE
   */
  public static final String R_CLIENTE = "R_CLIENTE";

  /**
   * Attributo R_SPECIFICA
   */
  public static final String R_SPECIFICA = "R_SPECIFICA";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YLGM_SPEC_PROD";

  /**
   *  instance
   */
  private static TableManager cInstance;

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.bvr.thip.produzione.ordese.YLgmSpecProd.class.getName();

  
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
      cInstance = (TableManager)Factory.createObject(YLgmSpecProdTM.class);
    }
    return cInstance;
  }

  /**
   *  YLgmSpecProdTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YLgmSpecProdTM() throws SQLException {
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
    addAttribute("IdLegame", ID_LEGAME, "getIntegerObject");
    addAttribute("IdAzienda", ID_AZIENDA);
    addAttribute("IdArticolo", R_ARTICOLO);
    addAttribute("IdCliente", R_CLIENTE);
    addAttribute("IdSpecifica", R_SPECIFICA, "getIntegerObject");
    
    addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
    setKeys(ID_AZIENDA + "," + ID_LEGAME);

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
    configure(ID_LEGAME + ", " + ID_AZIENDA + ", " + R_ARTICOLO + ", " + R_CLIENTE
         + ", " + R_SPECIFICA + ", " + STATO + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ
         + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
  }

}

