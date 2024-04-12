package it.thera.thip.produzione.ordese;

import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;

import it.bvr.thip.produzione.ordese.YOrdineEsecutivo;

/**
 * OrdineEsecutivoCopiaPO
 *
 * <br></br><b>Copyright (C) : Thera s.p.a.</b>
 * Classe di business per le istanze di gestione della copia di un ordine
 * esecutivo.
 *
 * @author LR 30/03/2006
 */
/*
 * Revisions:
 * Number   Date        Owner   Description
 * 05246    30/03/2006  LR      Versione iniziale.
 * 08602    Genn 2008   LR      Spostati i metodi GN sincronizzaAttivitaPrecedenti, sincronizzaAttivitaPrecedenti in OrdineEsecutivo
 * 09821    26/09/2008  FR      Commentato metodo retrieve(int) e spostato logica nel metodo
 *                              initializeOwnedObjects(boolean) di OrdineEsecutivoCopia.java
 * 16981    24/10/2012  TF      Correzione dell'errore alla copia di un ordine esecutivo con data fine non valorizzata
 */
public class OrdineEsecutivoCopiaPO extends YOrdineEsecutivo {

    /**
     * Ridefinisce la retrieve della classe padre: ne adotta lo stesso
     * comportamento e, se l'oggetto è recuperato: ripristina il numeratore
     * con la data corrente e sincronizza le attività precedenti per ogni
     * attività dell'ordine caricato.
     * @param lockType tipo di lock
     * @return true o false a seconda che l'oggetto sia stato recuperato o meno
     * @throws SQLException
     */
	/* Fix 09821 FR : commento metodo e sposto logica in initializeOwnedObjects(boolean) di OrdineEsecutivoCopia.java TODO Fix 09281 ok???
    public boolean retrieve(int lockType) throws SQLException {
        boolean result = super.retrieve(lockType);
        if (result) {
            String serie = iIdNumeroOrdine.substring(0, 2); //Fix 5515
            String sotto = iIdNumeroOrdine.substring(2, 4); //Fix 5515
            sincronizzaAttivitaPrecedenti();
            getNumeratoreHandler().ripristina();
            getNumeratoreHandler().setDataDocumento(TimeUtils.getCurrentDate());
            getNumeratoreHandler().setIdSerie(serie); //Fix 5515
            getNumeratoreHandler().setIdSottoserie(sotto); //Fix 5515
        }
        return result;
    }*/

    /**
     * Ridefinisce la checkall della classe padre: ne adotta lo stesso
     * comportamento, ma, prima, setta setWithModelloProduttivo() a false.
     * @param components components
     * @return vr di (eventuali) errori
     */
    public Vector checkAll(BaseComponentsCollection components) {
        setWithModelloProduttivo("false");
        insertAltraDataRichiesta();//Fix 16981
        return super.checkAll(components);
    }



}
