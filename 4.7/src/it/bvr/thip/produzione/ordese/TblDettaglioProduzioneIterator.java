package it.bvr.thip.produzione.ordese;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.persist.Factory;

import it.thera.thip.cs.ResultSetIterator;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 23/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	23/04/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class TblDettaglioProduzioneIterator extends ResultSetIterator {

	public TblDettaglioProduzioneIterator(ResultSet rs) {
		super(rs);
	}

	@Override
	protected Object createObject() throws SQLException {
		TblDettaglioProduzione obj = (TblDettaglioProduzione) Factory.createObject(TblDettaglioProduzione.class);
		obj.setId(BigInteger.valueOf(cursor.getInt("ID")));
		obj.setDescrizione_prodotto(cursor.getString("Descrizione_prodotto") != null ? cursor.getString("Descrizione_prodotto").trim() : null);
		obj.setRif_ODP(cursor.getString("Rif_ODP") != null ? cursor.getString("Rif_ODP").trim() : null);
		obj.setRif_cliente(cursor.getString("Rif_cliente") != null ? cursor.getString("Rif_cliente").trim() : null);
		obj.setRif_codice_cliente(cursor.getString("Rif_codice_cliente") != null ? cursor.getString("Rif_codice_cliente").trim() : null);
		obj.setRif_ordine_cliente(cursor.getString("Rif_ordine_cliente") != null ? cursor.getString("Rif_ordine_cliente").trim() : null);
		obj.setCod_Articolo(cursor.getString("Cod_Articolo"));
		obj.setScadenza(cursor.getDate("Scadenza"));
		obj.setLotto(cursor.getString("Lotto"));
		obj.setCartoni_plt(cursor.getInt("Cartoni_plt"));
		obj.setCartoni_Tot(cursor.getInt("Cartoni_Tot"));
		obj.setPallet_Tot(cursor.getFloat("Pallet_Tot"));
		obj.setCartoni_prodotti(cursor.getInt("Cartoni_prodotti"));
		obj.setImpasti_Cartone(cursor.getBigDecimal("Impasti_cartone"));
		obj.setEsaurimento_Impasto(cursor.getString("Esaurimento_impasto").trim().charAt(0));
		obj.setCod_vaschetta(cursor.getString("Cod_vaschetta") != null  ? cursor.getString("Cod_vaschetta").trim() : null);
		obj.setCod_incarto(cursor.getString("Cod_incarto") != null ? cursor.getString("Cod_incarto").trim() : null);
		obj.setCod_astuccio(cursor.getString("Cod_astuccio") != null ? cursor.getString("Cod_astuccio").trim() : null);
		obj.setCod_cartone(cursor.getString("Cod_cartone") != null ? cursor.getString("Cod_cartone").trim() : null);
		obj.setCod_coperchio(cursor.getString("Cod_coperchio") != null ? cursor.getString("Cod_coperchio").trim() : null);
		obj.setStampa_cartone(cursor.getString("Stampa_cartone") != null ? cursor.getString("Stampa_cartone").trim() : null);
		obj.setStampa_sacchetto(cursor.getString("Stampa_sacchetto") != null ? cursor.getString("Stampa_sacchetto").trim() : null);
		obj.setStampa_etichetta_astuccio(cursor.getString("Stampa_etichetta_astuccio") != null ? cursor.getString("Stampa_etichetta_astuccio").trim() : null);
		obj.setTipo_pallet(cursor.getString("Tipo_pallet") != null ? cursor.getString("Tipo_pallet").trim() : null);
		obj.setNote(cursor.getString("Note") != null ? cursor.getString("Note").trim() : null);
		obj.setNote_cartoni_parziali(cursor.getString("Note_cartoni_parziali") != null ? cursor.getString("Note_cartoni_parziali").trim() : null);
		obj.setRiferimento_Tbl_Produzione(cursor.getString("Riferimento_Tbl_Produzione") != null ? cursor.getString("Riferimento_Tbl_Produzione").trim() : null);
		obj.setFlag(cursor.getString("Flag").trim().charAt(0));
		return obj;
	}

}
