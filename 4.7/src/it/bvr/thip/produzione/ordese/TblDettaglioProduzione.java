package it.bvr.thip.produzione.ordese;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.ConnectionDescriptor;

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

	private static final String STMT_INSERT_DETTAGLIO = "INSERT INTO Tbl_Dettaglio_Produzione "
			+ "           (Descrizione_prodotto "
			+ "           ,Rif_ODP "
			+ "           ,Rif_cliente "
			+ "           ,Rif_codice_cliente "
			+ "           ,Rif_ordine_cliente "
			+ "           ,Cod_Articolo "
			+ "           ,Scadenza "
			+ "           ,Lotto "
			+ "           ,Pezzi_crt "
			+ "           ,Cartoni_plt "
			+ "           ,Cartoni_Tot "
			+ "           ,Pallet_Tot "
			+ "           ,Cartoni_prodotti "
			+ "           ,Impasti_cartone "
			+ "           ,Esaurimento_impasto "
			+ "           ,Cod_vaschetta "
			+ "           ,Cod_incarto "
			+ "           ,Cod_astuccio "
			+ "           ,Cod_cartone "
			+ "           ,Cod_coperchio "
			+ "           ,Stampa_cartone "
			+ "           ,Stampa_sacchetto "
			+ "           ,Stampa_etichetta_astuccio "
			+ "           ,Tipo_pallet "
			+ "           ,Note "
			+ "           ,Note_cartoni_parziali "
			+ "           ,Riferimento_Tbl_Produzione "
			+ "           ,Flag) "
			+ "     VALUES "
			+ "           (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

	protected BigInteger id;
	protected String Descrizione_prodotto;
	protected String Rif_ODP;
	protected String Rif_cliente;
	protected String Rif_codice_cliente;
	protected String Rif_ordine_cliente;
	protected String Cod_Articolo;
	protected Date Scadenza;
	protected String Lotto;
	protected Integer Pezzi_crt;
	protected Integer Cartoni_plt;
	protected Integer Cartoni_Tot;
	protected float Pallet_Tot;
	protected Integer Cartoni_prodotti;
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
	public Integer getPezzi_crt() {
		return Pezzi_crt;
	}
	public void setPezzi_crt(Integer pezzi_crt) {
		Pezzi_crt = pezzi_crt;
	}
	public Integer getCartoni_plt() {
		return Cartoni_plt;
	}
	public void setCartoni_plt(Integer fCartoni_plt) {
		Cartoni_plt = fCartoni_plt;
	}
	public Integer getCartoni_Tot() {
		return Cartoni_Tot;
	}
	public void setCartoni_Tot(Integer cartoni_Tot) {
		Cartoni_Tot = cartoni_Tot;
	}
	public float getPallet_Tot() {
		return Pallet_Tot;
	}
	public void setPallet_Tot(float pallet_Tot) {
		Pallet_Tot = pallet_Tot;
	}
	public Integer getCartoni_prodotti() {
		return Cartoni_prodotti;
	}
	public void setCartoni_prodotti(Integer cartoni_prodotti) {
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

	/**
	 * @author Daniele Signoroni 24/04/2024
	 * <p>
	 * Prima stesura.<br>
	 *
	 * </p>
	 * @param descrittoreConnessioneEsterna
	 * @return
	 */
	public int esportaVersoTabellaDiFrontiera(ConnectionDescriptor descrittoreConnessioneEsterna) {
		int rc = 0;
		try {
			if(descrittoreConnessioneEsterna.getConnection() != null && descrittoreConnessioneEsterna.getConnection().isClosed())
				descrittoreConnessioneEsterna.openConnection();
			PreparedStatement ps1 = descrittoreConnessioneEsterna.getConnection().prepareStatement(STMT_INSERT_DETTAGLIO);
			ps1.setString(1, getDescrizione_prodotto());
			ps1.setString(2, getRif_ODP());
			ps1.setString(3, getRif_cliente() != null ? getRif_cliente() : "-");
			ps1.setString(4, getRif_codice_cliente() != null ? getRif_codice_cliente() : "-");
			ps1.setString(5, getRif_ordine_cliente() != null ? getRif_ordine_cliente() : "-");
			ps1.setString(6, getCod_Articolo());
			ps1.setDate(7, getScadenza());
			ps1.setString(8, getLotto());
			ps1.setInt(9, getPezzi_crt());
			ps1.setInt(10, getCartoni_plt());
			ps1.setInt(11, getCartoni_Tot());
			ps1.setFloat(12, getPallet_Tot());
			if(getCartoni_prodotti() != null) {
				ps1.setInt(13,getCartoni_prodotti());
			}else {
				ps1.setNull(13, Types.INTEGER);
			}
			ps1.setBigDecimal(14, getImpasti_Cartone());
			ps1.setString(15, String.valueOf(getEsaurimento_Impasto()));
			ps1.setString(16, getCod_vaschetta() != null ? getCod_vaschetta() : null);
			ps1.setString(17, getCod_incarto() != null ? getCod_incarto() : null);
			ps1.setString(18, getCod_astuccio() != null ? getCod_astuccio() : null);
			ps1.setString(19, getCod_cartone() != null ? getCod_cartone() : null);
			ps1.setString(20, getCod_coperchio() != null ? getCod_coperchio() : null);
			ps1.setString(21, getStampa_cartone() != null ? getStampa_cartone() : null);
			ps1.setString(22, getStampa_sacchetto() != null ? getStampa_sacchetto() : null);
			ps1.setString(23, getStampa_etichetta_astuccio() != null ? getStampa_etichetta_astuccio() : null);
			ps1.setString(24, getTipo_pallet() != null ? getTipo_pallet() : null);
			ps1.setString(25, getNote() != null ? getNote() : null);
			ps1.setString(26, getNote_cartoni_parziali() != null ? getNote_cartoni_parziali() : null);
			ps1.setString(27, getRiferimento_Tbl_Produzione() != null ? getRiferimento_Tbl_Produzione() : null);
			ps1.setString(28, String.valueOf(getFlag()));
			rc = ps1.executeUpdate();
			if(rc > 0) {
				return YOrdineEsecutivo.UPDATE_OK;
			}else {
				return YOrdineEsecutivo.UPDATE_KO;
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
			return YOrdineEsecutivo.UPDATE_KO;
		}
	}

}
