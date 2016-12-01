package aed.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ejemplares")
@SuppressWarnings("serial")
public class Ejemplar implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codEjemplar;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codLibro")
	private Libro codLibro;

	@Column(columnDefinition = "DECIMAL(5,2)")
	private Double importe;

	@Column(columnDefinition = "VARCHAR(20)")
	private String tipo_moneda;

	public int getCodEjemplar() {
		return codEjemplar;
	}

	public void setCodEjemplar(int codEjemplar) {
		this.codEjemplar = codEjemplar;
	}

	public Libro getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(Libro codLibro) {
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
