package aed.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "autores")
@SuppressWarnings("serial")
public class Autor implements Serializable {

	@Id
	@Column(columnDefinition = "CHAR(4)")
	private String codAutor;

	@Column(columnDefinition = "VARCHAR(30)")
	private String nombreAutor;

	public Autor() {
		nombreAutor = "";
		codAutor = "";
	}

	public String getNombreAutor() {
		return nombreAutor;
	}

	public String getCodAutor() {
		return codAutor;
	}

	public void setCodAutor(String string) {
		this.codAutor = string;
	}

	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}

}
