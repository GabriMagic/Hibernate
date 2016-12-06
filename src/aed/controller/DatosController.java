package aed.controller;

import java.io.IOException;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;

import aed.model.Autor;
import aed.model.Datos;
import aed.model.DepositoLegal;
import aed.model.Ejemplar;
import aed.model.Libro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DatosController {

	@FXML
	private TableView<Datos> librosTable;

	@FXML
	private TableColumn<Datos, Integer> codigoColumn;

	@FXML
	private TableColumn<Datos, String> nombreColumn;

	@FXML
	private TableColumn<Datos, String> isbnColumn;

	@FXML
	private TableColumn<Datos, java.util.Date> fechaColumn;

	@FXML
	private TableColumn<Datos, Ejemplar> ejemplarColumn;

	@FXML
	private TableColumn<Datos, Autor> autorColumn;

	@FXML
	private TableColumn<Datos, DepositoLegal> depositoColumn;

	private Session session;

	public DatosController(Session session) {

		this.session = session;

		FXMLloads();

		codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codLibro"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreLibro"));
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaIntro"));
		ejemplarColumn.setCellValueFactory(new PropertyValueFactory<>("codEjemplra"));
		autorColumn.setCellValueFactory(new PropertyValueFactory<>("codAutor"));
		depositoColumn.setCellValueFactory(new PropertyValueFactory<>("codLibroDeposito"));

		cargarTodos();
	}

	public void cargarTodos() {

		Datos datos = new Datos();
		
		// CLASE LIBROS COMPLETOS CON TODOS LO DATOS PARA FORMAR LA TABLA
		Query query = session.createQuery("FROM Ejemplar e "
				+ "LEFT JOIN e.codLibro "
				+ "LEFT JOIN e.codLibroDeposito "
				);

		Iterator iterator = query.iterate();
		while (iterator.hasNext()) {
			Object[] par = (Object[]) iterator.next();
			Ejemplar ej = (Ejemplar) par[0];
			Libro li = (Libro) par[1];
			
			datos.setCodEjemplar(ej.getCodEjemplar());
			datos.setCodLibro(li.getCodLibro());
			datos.setNombreLibro(li.getNombreLibro());
			datos.setFechaIntro(li.getFechaIntro());
			
//			Autor au = (Autor) par[2];
			
			System.out.println("COdLibro: " + li.getCodLibro() + " CodEjemplar: " + ej.getCodEjemplar());
		}
		// librosTable.setItems(FXCollections.observableArrayList());

	}

	private void FXMLloads() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/DatosView.fxml"));
			loader.setController(this);
			librosTable = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TableView<Datos> getLibrosTable() {
		return librosTable;
	}

	public TableColumn<Datos, Integer> getCodigoColumn() {
		return codigoColumn;
	}

	public TableColumn<Datos, String> getNombreColumn() {
		return nombreColumn;
	}

	public TableColumn<Datos, String> getIsbnColumn() {
		return isbnColumn;
	}

	public TableColumn<Datos, java.util.Date> getFechaColumn() {
		return fechaColumn;
	}

	public TableColumn<Datos, Ejemplar> getEjemplarColumn() {
		return ejemplarColumn;
	}

	public TableColumn<Datos, Autor> getAutorColumn() {
		return autorColumn;
	}

	public TableColumn<Datos, DepositoLegal> getDepositoColumn() {
		return depositoColumn;
	}

}
