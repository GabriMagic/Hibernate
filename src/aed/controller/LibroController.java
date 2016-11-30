package aed.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import aed.model.Libro;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibroController {

	private ListProperty<Libro> libros;
	List<Libro> librosList;

	@FXML
	private TableView<Libro> librosTable;

	@FXML
	private TableColumn<Libro, Integer> codColumn;

	@FXML
	private TableColumn<Libro, Libro> nombreColumn, isbnColumn;

	@FXML
	private TableColumn<Libro, Date> fechaColumn;

	public LibroController(Session session) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/librosview.fxml"));
			loader.setController(this);
			librosTable = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		codColumn.setCellValueFactory(new PropertyValueFactory<>("codLibro"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreLibro"));
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaIntro"));

		cargarLibros(session);

		libros = new SimpleListProperty<>(this, "libros", FXCollections.observableArrayList(librosList));
		librosTable.itemsProperty().bind(libros);

	}

	private void cargarLibros(Session session) {
		Query query = session.createQuery("from Libro");
		librosList = query.list();
	}

	public TableView<Libro> getLibrosTable() {
		return librosTable;
	}

	public TableColumn<Libro, Integer> getCodColumn() {
		return codColumn;
	}

	public TableColumn<Libro, Libro> getNombreColumn() {
		return nombreColumn;
	}

	public TableColumn<Libro, Libro> getIsbnColumn() {
		return isbnColumn;
	}

	public TableColumn<Libro, Date> getFechaColumn() {
		return fechaColumn;
	}

}
