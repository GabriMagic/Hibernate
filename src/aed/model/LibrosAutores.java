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
	private Autor codAutor;

	@Id
	@ManyToOne
	@JoinColumn(name = "Libros")
	private Libro codLibro;

	public Autor getCodAutor() {
		return codAutor;
	}

	public void setCodAutor(Autor codAutor) {
		this.codAutor = codAutor;
	}

	public Libro getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(Libro codLibro) {
		this.codLibro = codLibro;
	}

}
