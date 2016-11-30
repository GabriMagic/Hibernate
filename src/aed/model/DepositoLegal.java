package aed.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "depositoLegal")
@SuppressWarnings("serial")
public class DepositoLegal implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "Libros")
	private Libro codLibroDeposito;

	@Column(columnDefinition = "VARCHAR(20)")
	private String depositoLegal;

	public Libro getCodLibroDeposito() {
		return codLibroDeposito;
	}

	public String getDepositoLegal() {
		return depositoLegal;
	}

	public void setCodLibroDeposito(Libro codLibroDeposito) {
		this.codLibroDeposito = codLibroDeposito;
	}

	public void setDepositoLegal(String depositoLegal) {
		this.depositoLegal = depositoLegal;
	}
}
