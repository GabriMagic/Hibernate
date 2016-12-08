package aed.model;

import java.sql.Date;

public class Datos {

	private int codLibro, codEjemplar;
	private String nombreLibro, ISBN, tipoMoneda, autor, codAutor, depositoLegal;
	private Double importe;

	public Datos(Ejemplar ej, Libro li, Autor au, DepositoLegal dl) {

		nombreLibro = li.getNombreLibro();
		ISBN = li.getISBN();
		codLibro = li.getCodLibro();

		try {
			importe = ej.getImporte();
		} catch (NullPointerException e1) {
			importe = 0.0;
		}

		try {
			codEjemplar = ej.getCodEjemplar();
		} catch (NullPointerException e1) {
			codEjemplar = 0;
		}

		try {
			autor = au.getNombreAutor();
		} catch (NullPointerException e1) {
			autor = "";
		}

		try {
			depositoLegal = dl.getDepositoLegal();
		} catch (NullPointerException e) {
			depositoLegal = "";
		}

	}

	public int getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(int codLibro) {
		this.codLibro = codLibro;
	}

	public int getCodEjemplar() {
		return codEjemplar;
	}

	public void setCodEjemplar(int codEjemplar) {
		this.codEjemplar = codEjemplar;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCodAutor() {
		return codAutor;
	}

	public void setCodAutor(String codAutor) {
		this.codAutor = codAutor;
	}

	public String getDepositoLegal() {
		return depositoLegal;
	}

	public void setDepositoLegal(String depositoLegal) {
		this.depositoLegal = depositoLegal;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

}
