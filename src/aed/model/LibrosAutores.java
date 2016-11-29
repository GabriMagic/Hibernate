package aed.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "librosautores")
@SuppressWarnings("serial")
public class LibrosAutores implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "Autores")
	private Autores codAutor;

	@Id
	@ManyToOne
	@JoinColumn(name = "Libros")
	private Libros codLibro;

	public Autores getCodAutor() {
		return codAutor;
	}

	public void setCodAutor(Autores codAutor) {
		this.codAutor = codAutor;
	}

	public Libros getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(Libros codLibro) {
		this.codLibro = codLibro;
	}

}
