package it.bvr.thip.produzione.ordese;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.ConnectionDescriptor;

/**
 * 
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 23/04/2024
 * <br><br>
 * <b>71XXX	DSSOF3	23/04/2024</b>
 * <p>Prima stesura.<br>
 *  Rappresenta un oggetto della tabella <b>[WIN-PN6J3SBHSSQ\SQLEXPRESS].[BiscottificioVerona].[dbo].[Tbl_Produzione]</b>
 * </p>
 */

public class TblProduzione {

	//Spiegazione colonna Flag
	public static final char DA_PRODURRE = '1';
	public static final char IN_PRODUZIONE = '2';
	public static final char TERMINATO = '3';
	public static final char IMPORTATO_A_GESTIONALE = '4';
	public static final char ANNULLATO = 'X';

	public static final String TABLE_NAME = "Tbl_Produzione";

	private static final String STMT_INSERT_TESTATA = "INSERT INTO "+TABLE_NAME+" "
			+ "           (DataOra "
			+ "           ,Turno "
			+ "           ,Lotto "
			+ "           ,CodArticolo "
			+ "           ,DescImpasto "
			+ "           ,Ricetta "
			+ "           ,CodImpasto "
			+ "           ,\"Qt.Cartoni\" "
			+ "           ,Qt_CartoniProdotti "
			+ "           ,KgImpasto "
			+ "           ,Rif_ODP "
			+ "           ,Flag) "
			+ "     VALUES "
			+ "           (?,?,?,?,?,?,?,?,?,?,?,?) ";

	protected BigInteger id;
	protected Date DataOra;
	protected Integer Turno;
	protected String Lotto;
	protected String CodArticolo;
	protected String DescImpasto;
	protected Integer Ricetta;
	protected String CodImpasto;
	protected Integer QtCartoni;
	protected Integer QtCartoniProdotti;
	protected float KgImpasto;
	protected String Rif_ODP;
	protected char Flag;

	protected List<TblDettaglioProduzione> dettagli = null;

	public TblProduzione() {
		dettagli = new ArrayList<TblDettaglioProduzione>();
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getDataOra() {
		return DataOra;
	}
	public void setDataOra(Date dataOra) {
		DataOra = dataOra;
	}
	public Integer getTurno() {
		return Turno;
	}
	public void setTurno(Integer turno) {
		Turno = turno;
	}
	public String getLotto() {
		return Lotto;
	}
	public void setLotto(String lotto) {
		Lotto = lotto;
	}
	public String getCodArticolo() {
		return CodArticolo;
	}
	public void setCodArticolo(String codArticolo) {
		CodArticolo = codArticolo;
	}
	public String getDescImpasto() {
		return DescImpasto;
	}
	public void setDescImpasto(String descImpasto) {
		DescImpasto = descImpasto;
	}
	public Integer getRicetta() {
		return Ricetta;
	}
	public void setRicetta(Integer ricetta) {
		Ricetta = ricetta;
	}
	public String getCodImpasto() {
		return CodImpasto;
	}
	public void setCodImpasto(String codImpasto) {
		CodImpasto = codImpasto;
	}
	public Integer getQtCartoni() {
		return QtCartoni;
	}
	public void setQtCartoni(Integer qtCartoni) {
		QtCartoni = qtCartoni;
	}
	public Integer getQtCartoniProdotti() {
		return QtCartoniProdotti;
	}
	public void setQtCartoniProdotti(Integer qtCartoniProdotti) {
		QtCartoniProdotti = qtCartoniProdotti;
	}
	public float getKgImpasto() {
		return KgImpasto;
	}
	public void setKgImpasto(float kgImpasto) {
		KgImpasto = kgImpasto;
	}
	public String getRif_ODP() {
		return Rif_ODP;
	}
	public void setRif_ODP(String rif_ODP) {
		Rif_ODP = rif_ODP;
	}
	public char getFlag() {
		return Flag;
	}
	public void setFlag(char flag) {
		Flag = flag;
	}

	public List<TblDettaglioProduzione> getDettagli() {
		return dettagli;
	}

	public void setDettagli(List<TblDettaglioProduzione> dettagli) {
		this.dettagli = dettagli;
	}

	/**
	 * @author Daniele Signoroni 23/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Ritorna la somma degli impasti cartone del dettaglio di una testata.<br>
	 * La somma e' data da (impasti_cartone * cartoni_prodotti).<br>
	 * </p>
	 * @return il totale > 0 se ci sono dettagli, altrimenti 0
	 */
	public BigDecimal getSommaImpastiCartoneDettaglio() {
		BigDecimal val = BigDecimal.ZERO;
		for(TblDettaglioProduzione dettaglio : getDettagli()) {
			if(dettaglio.getImpasti_Cartone() != null) {
				BigDecimal impastiCartone = dettaglio.getImpasti_Cartone();
				BigDecimal cartoniProdotti = new BigDecimal(dettaglio.getCartoni_prodotti());
				BigDecimal tot = impastiCartone.multiply(cartoniProdotti);
				val = val.add(tot);
			}
		}
		return val;
	}

	/**
	 * @author Daniele Signoroni 24/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Filtra i dettagli passati ai soli che hanno come Riferimento_Tbl_Produzione il riferimento passato.
	 * </p>
	 * @param dettagli
	 * @param riferimento
	 * @return
	 */
	public static List<TblDettaglioProduzione> searchDettagliByRifTblProduzione(List<TblDettaglioProduzione> dettagli, String riferimento) {
		return dettagli.stream()
				.filter(dettaglio -> riferimento.equals(dettaglio.getRiferimento_Tbl_Produzione()))
				.collect(Collectors.toList());
	}

	public int getSommaQuantitaCartoniDettagli(ToIntFunction<TblDettaglioProduzione> fieldExtractor) {
		return dettagli.stream()
				.mapToInt(fieldExtractor)
				.sum();
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
			descrittoreConnessioneEsterna.openConnection();
			PreparedStatement ps1 = descrittoreConnessioneEsterna.getConnection().prepareStatement(STMT_INSERT_TESTATA);
			ps1.setDate(1, getDataOra());
			if (getTurno() != null) {
				ps1.setInt(2, getTurno());
			} else {
				ps1.setNull(2, Types.INTEGER);
			}
			ps1.setString(3, getLotto());
			ps1.setString(4, getCodArticolo());
			ps1.setString(5, getDescImpasto());
			if (getRicetta() != null) {
				ps1.setInt(6, getRicetta());
			} else {
				ps1.setInt(6, 0);
			}
			ps1.setString(7, getCodImpasto());
			ps1.setInt(8, getQtCartoni());
			ps1.setNull(9, Types.INTEGER);
			ps1.setFloat(10, getKgImpasto());
			ps1.setString(11, getRif_ODP());
			ps1.setString(12, String.valueOf(getFlag()));
			rc = ps1.executeUpdate();
			if(rc > 0) {
				for(TblDettaglioProduzione dettaglio : getDettagli()) {
					rc = dettaglio.esportaVersoTabellaDiFrontiera(descrittoreConnessioneEsterna);
					if(rc == YOrdineEsecutivo.UPDATE_KO) {
						return YOrdineEsecutivo.UPDATE_KO;
					}
				}
			}else {
				return YOrdineEsecutivo.UPDATE_KO;
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
			return YOrdineEsecutivo.UPDATE_KO;
		}
		return YOrdineEsecutivo.UPDATE_OK;
	}

}
