package aed.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "libros")
@SuppressWarnings("serial")
public class Libros implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codLibro;

	@Column(columnDefinition = "VARCHAR(40)")
	private String nombreLibro;

	@Column(columnDefinition = "VARCHAR(20)")
	private String ISBN;

	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private Date fechaIntro;

	public int getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(int codLibro) {
		this.codLibro = codLibro;
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

}
