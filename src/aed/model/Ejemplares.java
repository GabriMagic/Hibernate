package aed.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
public class Ejemplares implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codEjemplar;

	@ManyToOne
	@JoinColumn(name = "Libros")
	private Libros codLibro;

	@Column(columnDefinition = "DECIMAL(5,2)")
	private Double importe;

	@Column(columnDefinition = "VARCHAR(20")
	private String tipo_moneda;

	public int getCodEjemplar() {
		return codEjemplar;
	}

	public void setCodEjemplar(int codEjemplar) {
		this.codEjemplar = codEjemplar;
	}

	public Libros getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(Libros codLibro) {
		this.codLibro = codLibro;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getTipo_moneda() {
		return tipo_moneda;
	}

	public void setTipo_moneda(String tipo_moneda) {
		this.tipo_moneda = tipo_moneda;
	}

}
