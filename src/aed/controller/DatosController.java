package aed.controller;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import aed.model.Autor;
import aed.model.DepositoLegal;
import aed.model.Ejemplar;
import aed.model.Libro;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DatosController {

	@FXML
	private TableView<Libro> librosTable;

	@FXML
	private TableColumn<Libro, Integer> codigoColumn;

	@FXML
	private TableColumn<Libro, String> nombreColumn;

	@FXML
	private TableColumn<Libro, String> isbnColumn;

	@FXML
	private TableColumn<Libro, java.util.Date> fechaColumn;

	@FXML
	private TableColumn<Libro, Ejemplar> ejemplarColumn;

	@FXML
	private TableColumn<Libro, Autor> autorColumn;

	@FXML
	private TableColumn<Libro, DepositoLegal> depositoColumn;

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

	public void cargarTodos(){
		
		@SuppressWarnings("unchecked")
		List<Libro> e = session.createQuery("FROM Ejemplar e LEFT JOIN e.codLibro").list();
		
		for (Libro l : e) {
			System.out.println(l);
		}
		
		librosTable.setItems(FXCollections.observableArrayList(e));
		
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

	public TableView<Libro> getLibrosTable() {
		return librosTable;
	}

	public TableColumn<Libro, Integer> getCodigoColumn() {
		return codigoColumn;
	}

	public TableColumn<Libro, String> getNombreColumn() {
		return nombreColumn;
	}

	public TableColumn<Libro, String> getIsbnColumn() {
		return isbnColumn;
	}

	public TableColumn<Libro, java.util.Date> getFechaColumn() {
		return fechaColumn;
	}

	public TableColumn<Libro, Ejemplar> getEjemplarColumn() {
		return ejemplarColumn;
	}

	public TableColumn<Libro, Autor> getAutorColumn() {
		return autorColumn;
	}

	public TableColumn<Libro, DepositoLegal> getDepositoColumn() {
		return depositoColumn;
	}

}
