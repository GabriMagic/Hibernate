package aed.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "autores")
@SuppressWarnings("serial")
public class Autores implements Serializable {

	@Id
	@Column(columnDefinition = "CHAR(4")
	private int codAutor;

	@Column(columnDefinition = "VARCHAR(30)")
	private String nombreAutor;

	public Autores() {
		nombreAutor = "";
		codAutor = 0;
	}

	public String getNombreAutor() {
		return nombreAutor;
	}

	public int getCodAutor() {
		return codAutor;
	}
}
