package it.bvr.thip.produzione.ordese;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 23/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	23/04/2024</b>
 * <p>Prima stesura.<br>
 *  Rappresenta un oggetto della tabella <b>[WIN-PN6J3SBHSSQ\SQLEXPRESS].[BiscottificioVerona].[dbo].[Tbl_Dettaglio_Produzione]</b>
 * </p>
 */

public class TblDettaglioProduzione {
	
	public static final String TABLE_NAME = "Tbl_Dettaglio_Produzione";
	
	protected BigInteger id;
	protected String Descrizione_prodotto;
	protected String Rif_ODP;
	protected String Rif_cliente;
	protected String Rif_codice_cliente;
	protected String Rif_ordine_cliente;
	protected String Cod_Articolo;
	protected Date Scadenza;
	protected String Lotto;
	protected int Pezzi_crt;
	protected int Cartoni_plt;
	protected int Cartoni_Tot;
	protected float Pallet_Tot;
	protected int Cartoni_prodotti;
	protected BigDecimal Impasti_Cartone;
	protected char Esaurimento_Impasto; //0 false, 1 true
	protected String Cod_vaschetta;
	protected String Cod_incarto;
	protected String Cod_astuccio;
	protected String Cod_cartone;
	protected String Cod_coperchio;
	protected String Stampa_cartone;
	protected String Stampa_sacchetto;
	protected String Stampa_etichetta_astuccio;
	protected String Tipo_pallet;
	protected String Note;
	protected String Note_cartoni_parziali;
	protected String Riferimento_Tbl_Produzione;
	protected char Flag;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getDescrizione_prodotto() {
		return Descrizione_prodotto;
	}
	public void setDescrizione_prodotto(String descrizione_prodotto) {
		Descrizione_prodotto = descrizione_prodotto;
	}
	public String getRif_ODP() {
		return Rif_ODP;
	}
	public void setRif_ODP(String rif_ODP) {
		Rif_ODP = rif_ODP;
	}
	public String getRif_cliente() {
		return Rif_cliente;
	}
	public void setRif_cliente(String rif_cliente) {
		Rif_cliente = rif_cliente;
	}
	public String getRif_codice_cliente() {
		return Rif_codice_cliente;
	}
	public void setRif_codice_cliente(String rif_codice_cliente) {
		Rif_codice_cliente = rif_codice_cliente;
	}
	public String getRif_ordine_cliente() {
		return Rif_ordine_cliente;
	}
	public void setRif_ordine_cliente(String rif_ordine_cliente) {
		Rif_ordine_cliente = rif_ordine_cliente;
	}
	public String getCod_Articolo() {
		return Cod_Articolo;
	}
	public void setCod_Articolo(String cod_Articolo) {
		Cod_Articolo = cod_Articolo;
	}
	public Date getScadenza() {
		return Scadenza;
	}
	public void setScadenza(Date scadenza) {
		Scadenza = scadenza;
	}
	public String getLotto() {
		return Lotto;
	}
	public void setLotto(String lotto) {
		Lotto = lotto;
	}
	public int getPezzi_crt() {
		return Pezzi_crt;
	}
	public void setPezzi_crt(int pezzi_crt) {
		Pezzi_crt = pezzi_crt;
	}
	public int getCartoni_plt() {
		return Cartoni_plt;
	}
	public void setCartoni_plt(int fCartoni_plt) {
		Cartoni_plt = fCartoni_plt;
	}
	public int getCartoni_Tot() {
		return Cartoni_Tot;
	}
	public void setCartoni_Tot(int cartoni_Tot) {
		Cartoni_Tot = cartoni_Tot;
	}
	public float getPallet_Tot() {
		return Pallet_Tot;
	}
	public void setPallet_Tot(float pallet_Tot) {
		Pallet_Tot = pallet_Tot;
	}
	public int getCartoni_prodotti() {
		return Cartoni_prodotti;
	}
	public void setCartoni_prodotti(int cartoni_prodotti) {
		Cartoni_prodotti = cartoni_prodotti;
	}
	public BigDecimal getImpasti_Cartone() {
		return Impasti_Cartone;
	}
	public void setImpasti_Cartone(BigDecimal impasti_Cartone) {
		Impasti_Cartone = impasti_Cartone;
	}
	public char getEsaurimento_Impasto() {
		return Esaurimento_Impasto;
	}
	public void setEsaurimento_Impasto(char esaurimento_Impasto) {
		Esaurimento_Impasto = esaurimento_Impasto;
	}
	public String getCod_vaschetta() {
		return Cod_vaschetta;
	}
	public void setCod_vaschetta(String cod_vaschetta) {
		Cod_vaschetta = cod_vaschetta;
	}
	public String getCod_incarto() {
		return Cod_incarto;
	}
	public void setCod_incarto(String cod_incarto) {
		Cod_incarto = cod_incarto;
	}
	public String getCod_astuccio() {
		return Cod_astuccio;
	}
	public void setCod_astuccio(String cod_astuccio) {
		Cod_astuccio = cod_astuccio;
	}
	public String getCod_cartone() {
		return Cod_cartone;
	}
	public void setCod_cartone(String cod_cartone) {
		Cod_cartone = cod_cartone;
	}
	
	public String getCod_coperchio() {
		return Cod_coperchio;
	}
	public void setCod_coperchio(String cod_coperchio) {
		Cod_coperchio = cod_coperchio;
	}
	public String getStampa_cartone() {
		return Stampa_cartone;
	}
	public void setStampa_cartone(String stampa_cartone) {
		Stampa_cartone = stampa_cartone;
	}
	public String getStampa_sacchetto() {
		return Stampa_sacchetto;
	}
	public void setStampa_sacchetto(String stampa_sacchetto) {
		Stampa_sacchetto = stampa_sacchetto;
	}
	public String getStampa_etichetta_astuccio() {
		return Stampa_etichetta_astuccio;
	}
	public void setStampa_etichetta_astuccio(String stampa_etichetta_astuccio) {
		Stampa_etichetta_astuccio = stampa_etichetta_astuccio;
	}
	public String getTipo_pallet() {
		return Tipo_pallet;
	}
	public void setTipo_pallet(String tipo_pallet) {
		Tipo_pallet = tipo_pallet;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	public String getNote_cartoni_parziali() {
		return Note_cartoni_parziali;
	}
	public void setNote_cartoni_parziali(String note_cartoni_parziali) {
		Note_cartoni_parziali = note_cartoni_parziali;
	}
	public String getRiferimento_Tbl_Produzione() {
		return Riferimento_Tbl_Produzione;
	}
	public void setRiferimento_Tbl_Produzione(String riferimento_Tbl_Produzione) {
		Riferimento_Tbl_Produzione = riferimento_Tbl_Produzione;
	}
	public char getFlag() {
		return Flag;
	}
	public void setFlag(char flag) {
		Flag = flag;
	}

}
