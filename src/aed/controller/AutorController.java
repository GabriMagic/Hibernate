package aed.controller;

import java.io.IOException;

import org.hibernate.Session;

import aed.model.Autor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AutorController {

	@FXML
	private TableView<Autor> autoresTable;

	@FXML
	private TableColumn<Autor, String> codColumn, nombreColumn;

	@FXML
	private ContextMenu menuLibros;

	@FXML
	private MenuItem addAutor;

	@FXML
	private MenuItem delAutor;

	private Session session;
	private Stage stage;

	public AutorController(Session session) {

		this.session = session;

		FXMLloaders();

		codColumn.setCellValueFactory(new PropertyValueFactory<>("codAutor"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));

		stage = new Stage();
		stage.setScene(new Scene(new VBox()));

		mostrarAutores();

	}

	@FXML
	void onAddAutor(ActionEvent event) {
//		stage.getScene().setRoot();
	}

	@FXML
	void onDelAutor(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	private void mostrarAutores() {
		autoresTable.setItems(FXCollections.observableArrayList(session.createQuery("FROM Autor").list()));
	}

	private void FXMLloaders() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/AutoresView.fxml"));
			loader.setController(this);
			autoresTable = loader.load();
			
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/AutoresView.fxml"));
//			loader.setController(this);
//			autoresTable = loader.load();
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
