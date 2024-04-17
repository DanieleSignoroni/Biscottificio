/*
 * @(#)YLgmSpecProdPO.java
 */

/**
 * null
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
import java.sql.*;
import java.util.*;
import it.thera.thip.base.azienda.AziendaEstesa;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;

public abstract class YLgmSpecProdPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

  
  /**
   *  instance
   */
  private static YLgmSpecProd cInstance;

  /**
   * Attributo iIdLegame
   */
  protected Integer iIdLegame;

  /**
   * Attributo iArticolo
   */
  protected Proxy iArticolo = new Proxy(it.thera.thip.base.articolo.Articolo.class);

  /**
   * Attributo iCliente
   */
  protected Proxy iCliente = new Proxy(it.thera.thip.base.cliente.ClienteVendita.class);

  /**
   * Attributo iSpecifica
   */
  protected Proxy iSpecifica = new Proxy(it.bvr.thip.produzione.ordese.YAnagrSpecProd.class);

  
  /**
   *  retrieveList
   * @param where
   * @param orderBy
   * @param optimistic
   * @return Vector
   * @throws SQLException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (cInstance == null)
      cInstance = (YLgmSpecProd)Factory.createObject(YLgmSpecProd.class);
    return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
  }

  /**
   *  elementWithKey
   * @param key
   * @param lockType
   * @return YLgmSpecProd
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static YLgmSpecProd elementWithKey(String key, int lockType) throws SQLException {
    return (YLgmSpecProd)PersistentObject.elementWithKey(YLgmSpecProd.class, key, lockType);
  }

  /**
   * YLgmSpecProdPO
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public YLgmSpecProdPO() {
    setIdLegame(new Integer(0));
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param idLegame
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdLegame(Integer idLegame) {
    this.iIdLegame = idLegame;
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getIdLegame() {
    return iIdLegame;
  }

  /**
   * Valorizza l'attributo. 
   * @param articolo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setArticolo(Articolo articolo) {
    String oldObjectKey = getKey();
    String idAzienda = getIdAzienda();
    if (articolo != null) {
      idAzienda = KeyHelper.getTokenObjectKey(articolo.getKey(), 1);
    }
    setIdAziendaInternal(idAzienda);
    this.iArticolo.setObject(articolo);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * Restituisce l'attributo. 
   * @return Articolo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public Articolo getArticolo() {
    return (Articolo)iArticolo.getObject();
  }

  /**
   * setArticoloKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setArticoloKey(String key) {
    String oldObjectKey = getKey();
    iArticolo.setKey(key);
    String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
    setIdAziendaInternal(idAzienda);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * getArticoloKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getArticoloKey() {
    return iArticolo.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param idArticolo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdArticolo(String idArticolo) {
    String key = iArticolo.getKey();
    iArticolo.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idArticolo));
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getIdArticolo() {
    String key = iArticolo.getKey();
    String objIdArticolo = KeyHelper.getTokenObjectKey(key,2);
    return objIdArticolo;
  }

  /**
   * Valorizza l'attributo. 
   * @param cliente
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setCliente(ClienteVendita cliente) {
    String oldObjectKey = getKey();
    String idAzienda = getIdAzienda();
    if (cliente != null) {
      idAzienda = KeyHelper.getTokenObjectKey(cliente.getKey(), 1);
    }
    setIdAziendaInternal(idAzienda);
    this.iCliente.setObject(cliente);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * Restituisce l'attributo. 
   * @return ClienteVendita
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public ClienteVendita getCliente() {
    return (ClienteVendita)iCliente.getObject();
  }

  /**
   * setClienteKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setClienteKey(String key) {
    String oldObjectKey = getKey();
    iCliente.setKey(key);
    String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
    setIdAziendaInternal(idAzienda);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * getClienteKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getClienteKey() {
    return iCliente.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param idCliente
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdCliente(String idCliente) {
    String key = iCliente.getKey();
    iCliente.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idCliente));
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getIdCliente() {
    String key = iCliente.getKey();
    String objIdCliente = KeyHelper.getTokenObjectKey(key,2);
    return objIdCliente;
  }

  /**
   * Valorizza l'attributo. 
   * @param specifica
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setSpecifica(YAnagrSpecProd specifica) {
    String oldObjectKey = getKey();
    String idAzienda = getIdAzienda();
    if (specifica != null) {
      idAzienda = KeyHelper.getTokenObjectKey(specifica.getKey(), 1);
    }
    setIdAziendaInternal(idAzienda);
    this.iSpecifica.setObject(specifica);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * Restituisce l'attributo. 
   * @return YAnagrSpecProd
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public YAnagrSpecProd getSpecifica() {
    return (YAnagrSpecProd)iSpecifica.getObject();
  }

  /**
   * setSpecificaKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setSpecificaKey(String key) {
    String oldObjectKey = getKey();
    iSpecifica.setKey(key);
    String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
    setIdAziendaInternal(idAzienda);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * getSpecificaKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getSpecificaKey() {
    return iSpecifica.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param idAzienda
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdAzienda(String idAzienda) {
    setIdAziendaInternal(idAzienda);
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getIdAzienda() {
    String key = iAzienda.getKey();
    return key;
  }

  /**
   * Valorizza l'attributo. 
   * @param idSpecifica
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdSpecifica(Integer idSpecifica) {
    String key = iSpecifica.getKey();
    iSpecifica.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idSpecifica));
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getIdSpecifica() {
    String key = iSpecifica.getKey();
    String objIdSpecifica = KeyHelper.getTokenObjectKey(key,2);
    return KeyHelper.stringToIntegerObj(objIdSpecifica);
  }

  /**
   * setEqual
   * @param obj
   * @throws CopyException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setEqual(Copyable obj) throws CopyException {
    super.setEqual(obj);
    YLgmSpecProdPO yLgmSpecProdPO = (YLgmSpecProdPO)obj;
    iArticolo.setEqual(yLgmSpecProdPO.iArticolo);
    iCliente.setEqual(yLgmSpecProdPO.iCliente);
    iSpecifica.setEqual(yLgmSpecProdPO.iSpecifica);
  }

  /**
   * checkAll
   * @param components
   * @return Vector
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public Vector checkAll(BaseComponentsCollection components) {
    Vector errors = new Vector();
    if (!isOnDB()) {
      setIdLegame(new Integer(0));
    }
    components.runAllChecks(errors);
    return errors;
  }

  /**
   *  setKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setKey(String key) {
    setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
    setIdLegame(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 2)));
  }

  /**
   *  getKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getKey() {
    String idAzienda = getIdAzienda();
    Integer idLegame = getIdLegame();
    Object[] keyParts = {idAzienda, idLegame};
    return KeyHelper.buildObjectKey(keyParts);
  }

  /**
   * isDeletable
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public boolean isDeletable() {
    return checkDelete() == null;
  }

  /**
   * toString
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  public String toString() {
    return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
  }

  /**
   *  getTableManager
   * @return TableManager
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected TableManager getTableManager() throws SQLException {
    return YLgmSpecProdTM.getInstance();
  }

  /**
   * setIdAziendaInternal
   * @param idAzienda
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 16/04/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void setIdAziendaInternal(String idAzienda) {
    iAzienda.setKey(idAzienda);
        String key2 = iArticolo.getKey();
    iArticolo.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
    String key3 = iCliente.getKey();
    iCliente.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
    String key4 = iSpecifica.getKey();
    iSpecifica.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
  }

}

