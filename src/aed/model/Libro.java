package aed.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "libros")
@SuppressWarnings("serial")
public class Libro implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codLibro;

	@Column(columnDefinition = "VARCHAR(40)")
	private String nombreLibro;

	@Column(columnDefinition = "VARCHAR(20) UNIQUE")
	private String ISBN;

	@Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Date fechaIntro;

	public void setEjemplares(List<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}

	public void setCodLibroDeposito(DepositoLegal codLibroDeposito) {
		this.codLibroDeposito = codLibroDeposito;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "codLibro")
	private List<Ejemplar> ejemplares = new ArrayList<Ejemplar>();

	@OneToOne(cascade = { javax.persistence.CascadeType.ALL })
	@PrimaryKeyJoinColumn
	private DepositoLegal codLibroDeposito;

	public int getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(int codLibro) {
		this.codLibro = codLibro;
	}

	public List<Ejemplar> getEjemplares() {
		return ejemplares;
	}

	public DepositoLegal getCodLibroDeposito() {
		return codLibroDeposito;
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

	public Date getFechaIntro() {
		return fechaIntro;
	}

	public void setFechaIntro(Date fechaIntro) {
		this.fechaIntro = fechaIntro;
	}

	@Override
	public String toString() {
		return getNombreLibro();
	}
}
