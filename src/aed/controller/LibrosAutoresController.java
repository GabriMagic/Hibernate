package aed.controller;

import java.io.IOException;

import org.hibernate.Session;

import aed.model.Autor;
import aed.model.Libro;
import aed.model.LibrosAutores;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibrosAutoresController {

	@FXML
	private TableView<LibrosAutores> librosAutoresTable;

	@FXML
	private TableColumn<LibrosAutores, Libro> librosColumn;

	@FXML
	private TableColumn<LibrosAutores, Autor> autorColumn;

	private Session session;

	public LibrosAutoresController(Session session) {

		this.session = session;

		FXMLloads();
		
		librosColumn.setCellValueFactory(new PropertyValueFactory<>("codLibro"));
		autorColumn.setCellValueFactory(new PropertyValueFactory<>("codAutor"));
		
		cargarLibrosAutores();
	}

	@SuppressWarnings("unchecked")
	public void cargarLibrosAutores() {
		librosAutoresTable
				.setItems(FXCollections.observableArrayList(session.createQuery("FROM LibrosAutores").list()));
	}

	private void FXMLloads() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/LibrosAutoresView.fxml"));
			loader.setController(this);
			librosAutoresTable = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TableView<LibrosAutores> getLibrosAutoresTable() {
		return librosAutoresTable;
	}
}
