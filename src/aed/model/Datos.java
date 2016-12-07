package aed.model;

import java.sql.Date;

public class Datos {

	private int codLibro, codEjemplar;
	private String nombreLibro, ISBN, tipoMoneda, autor, codAutor, depositoLegal;
	private Date fechaIntro;
	private Double importe;

	public Datos(Ejemplar ej, Libro li, Autor au, DepositoLegal dl) {

		nombreLibro = li.getNombreLibro();
		ISBN = li.getISBN();
		codLibro = li.getCodLibro();
		fechaIntro = li.getFechaIntro();

		importe = ej.getImporte();
		codEjemplar = ej.getCodEjemplar();

		autor = au.getNombreAutor();
		
		depositoLegal = dl.getDepositoLegal();
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

	public Date getFechaIntro() {
		return fechaIntro;
	}

	public void setFechaIntro(Date fechaIntro) {
		this.fechaIntro = fechaIntro;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

}
