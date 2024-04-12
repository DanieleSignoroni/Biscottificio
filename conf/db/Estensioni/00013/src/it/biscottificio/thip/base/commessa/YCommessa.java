/*
 * @(#)YCommessa.java
 */

/**
 * null
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
import java.sql.*;
import java.util.*;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.base.commessa.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;

public class YCommessa extends Commessa {

  
  /**
   * Attributo iOrdineesecutivoorigine
   */
  protected Proxy iOrdineesecutivoorigine = new Proxy(it.thera.thip.produzione.ordese.OrdineEsecutivo.class);

  
  /**
   * YCommessa
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public YCommessa() {
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param ordineesecutivoorigine
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setOrdineesecutivoorigine(OrdineEsecutivo ordineesecutivoorigine) {
    String oldObjectKey = getKey();
    this.iOrdineesecutivoorigine.setObject(ordineesecutivoorigine);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * Restituisce l'attributo. 
   * @return OrdineEsecutivo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public OrdineEsecutivo getOrdineesecutivoorigine() {
    return (OrdineEsecutivo)iOrdineesecutivoorigine.getObject();
  }

  /**
   * setOrdineesecutivoorigineKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setOrdineesecutivoorigineKey(String key) {
    String oldObjectKey = getKey();
    iOrdineesecutivoorigine.setKey(key);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * getOrdineesecutivoorigineKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getOrdineesecutivoorigineKey() {
    return iOrdineesecutivoorigine.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param idAzienda
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdAzienda(String idAzienda) {
    super.setIdAzienda(idAzienda);
    if(iOrdineesecutivoorigine != null) {
      String key = iOrdineesecutivoorigine.getKey();
      iOrdineesecutivoorigine.setKey(KeyHelper.replaceTokenObjectKey(key , 1, idAzienda));
    }
    
  }

  /**
   * Valorizza l'attributo. 
   * @param rAnnoOrdEsec
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setRAnnoOrdEsec(String rAnnoOrdEsec) {
    String key = iOrdineesecutivoorigine.getKey();
    iOrdineesecutivoorigine.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rAnnoOrdEsec));
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getRAnnoOrdEsec() {
    String key = iOrdineesecutivoorigine.getKey();
    String objRAnnoOrdEsec = KeyHelper.getTokenObjectKey(key,2);
    return objRAnnoOrdEsec;
    
  }

  /**
   * Valorizza l'attributo. 
   * @param rNumeroOrdEsec
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setRNumeroOrdEsec(String rNumeroOrdEsec) {
    String key = iOrdineesecutivoorigine.getKey();
    iOrdineesecutivoorigine.setKey(KeyHelper.replaceTokenObjectKey(key , 3, rNumeroOrdEsec));
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 10/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getRNumeroOrdEsec() {
    String key = iOrdineesecutivoorigine.getKey();
    String objRNumeroOrdEsec = KeyHelper.getTokenObjectKey(key,3);
    return objRNumeroOrdEsec;
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
    YCommessa yCommessa = (YCommessa)obj;
    iOrdineesecutivoorigine.setEqual(yCommessa.iOrdineesecutivoorigine);
  }

}

