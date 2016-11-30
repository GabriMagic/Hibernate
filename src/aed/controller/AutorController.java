package aed.controller;

import java.io.IOException;

import org.hibernate.Session;

import aed.model.Autor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AutorController {

	@FXML
	private TableView<Autor> autoresTable;

	@FXML
	private TableColumn<Autor, String> codColumn, nombreColumn;

	public AutorController(Session session) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/AutoresView.fxml"));
			loader.setController(this);
			autoresTable = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TableView<Autor> getAutoresTable() {
		return autoresTable;
	}

	public TableColumn<Autor, String> getCodColumn() {
		return codColumn;
	}

	public TableColumn<Autor, String> getNombreColumn() {
		return nombreColumn;
	}

}
