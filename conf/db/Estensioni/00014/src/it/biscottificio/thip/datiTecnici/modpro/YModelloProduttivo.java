/*
 * @(#)YModelloProduttivo.java
 */

/**
 * null
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
import java.sql.*;
import java.util.*;
import it.thera.thip.datiTecnici.modpro.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;

public class YModelloProduttivo extends ModelloProduttivo {

  
  /**
   * Attributo iNumeroRicetta
   */
  protected Integer iNumeroRicetta;

  
  /**
   * YModelloProduttivo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public YModelloProduttivo() {
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param numeroRicetta
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setNumeroRicetta(Integer numeroRicetta) {
    this.iNumeroRicetta = numeroRicetta;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getNumeroRicetta() {
    return iNumeroRicetta;
  }

  /**
   * setEqual
   * @param obj
   * @throws CopyException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setEqual(Copyable obj) throws CopyException {
    super.setEqual(obj);
  }

}

