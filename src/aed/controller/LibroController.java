package aed.controller;

import java.io.IOException;
import java.util.Date;

import aed.model.Libro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class LibroController {

	@FXML
	private TableView<Libro> librosTable;

	@FXML
	private TableColumn<Libro, Integer> codColumn;

	@FXML
	private TableColumn<Libro, Libro> nombreColumn, isbnColumn;

	@FXML
	private TableColumn<Libro, Date> fechaColumn;

	public LibroController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/librosview.fxml"));
			loader.setController(this);
			librosTable = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
