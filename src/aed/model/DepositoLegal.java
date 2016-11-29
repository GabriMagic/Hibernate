package aed.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@SuppressWarnings("serial")
public class DepositoLegal  implements Serializable{

	@Id
	@JoinColumn(name="Libros")
	private Libros codLibroDeposito;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String depositoLegal;


}
