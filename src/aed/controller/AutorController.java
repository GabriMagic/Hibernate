package aed.controller;

import java.io.IOException;

import org.hibernate.Session;

import aed.model.Autor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AutorController {

	@FXML
	private TableView<Autor> autoresTable;

	@FXML
	private TableColumn<Autor, String> codColumn, nombreColumn;

	private Session session;

	public AutorController(Session session) {

		this.session = session;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/AutoresView.fxml"));
			loader.setController(this);
			autoresTable = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		codColumn.setCellValueFactory(new PropertyValueFactory<>("codAutor"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));

		mostrarAutores();

	}

	@SuppressWarnings("unchecked")
	private void mostrarAutores() {
		autoresTable.setItems(FXCollections.observableArrayList(session.createQuery("FROM Autor").list()));
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
