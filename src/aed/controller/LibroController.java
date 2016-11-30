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

	private VBox view;

	@FXML
	private TableView<Libro> librosTable;

	@FXML
	private TableColumn<Libro, Integer> codLibroColumn;

	@FXML
	private TableColumn<Libro, String> nombreLibroColumn, ISBNColumn;

	@FXML
	private TableColumn<Libro, Date> fechaIntroColumn;

	public LibroController() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/librosview.fxml"));
			loader.setController(this);
			view = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		librosTable.setFocusTraversable(false);
	}

	public VBox getView() {
		return view;
	}

	public TableView<Libro> getLibrosTable() {
		return librosTable;
	}

	public TableColumn<Libro, Integer> getCodLibroColumn() {
		return codLibroColumn;
	}

	public TableColumn<Libro, String> getNombreLibroColumn() {
		return nombreLibroColumn;
	}

	public TableColumn<Libro, String> getISBNColumn() {
		return ISBNColumn;
	}

	public TableColumn<Libro, Date> getFechaIntroColumn() {
		return fechaIntroColumn;
	}

}
