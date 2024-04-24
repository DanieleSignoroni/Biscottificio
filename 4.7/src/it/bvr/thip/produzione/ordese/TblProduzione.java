package it.bvr.thip.produzione.ordese;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

	protected BigInteger id;
	protected Date DataOra;
	protected int Turno;
	protected String Lotto;
	protected String CodArticolo;
	protected String DescImpasto;
	protected int Ricetta;
	protected String CodImpasto;
	protected int QtCartoni;
	protected int QtCartoniProdotti;
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
	public int getTurno() {
		return Turno;
	}
	public void setTurno(int turno) {
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
	public int getRicetta() {
		return Ricetta;
	}
	public void setRicetta(int ricetta) {
		Ricetta = ricetta;
	}
	public String getCodImpasto() {
		return CodImpasto;
	}
	public void setCodImpasto(String codImpasto) {
		CodImpasto = codImpasto;
	}
	public int getQtCartoni() {
		return QtCartoni;
	}
	public void setQtCartoni(int qtCartoni) {
		QtCartoni = qtCartoni;
	}
	public int getQtCartoniProdotti() {
		return QtCartoniProdotti;
	}
	public void setQtCartoniProdotti(int qtCartoniProdotti) {
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

}
