package aed.controller;

import java.io.IOException;

import org.hibernate.Session;

import aed.model.Autor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

	@FXML
	private GridPane insertView;

	@FXML
	private TextField codigoText;

	@FXML
	private TextField nombreText;

	@FXML
	private Button confirmButton;

	@FXML
	private Button cancelButton;

	private Session session;
	private Stage stage;

	public AutorController(Session session) {

		this.session = session;

		FXMLloaders();

		codColumn.setCellValueFactory(new PropertyValueFactory<>("codAutor"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));

		stage = new Stage();
		stage.setTitle("Insertar Autor");
		stage.getIcons().add(new Image(getClass().getResource("/resources/db.png").toExternalForm()));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(new VBox()));

		cargarAutores();

		confirmButton.disableProperty()
				.bind(codigoText.textProperty().isEmpty().and(nombreText.textProperty().isEmpty()));
		delAutor.disableProperty().bind(autoresTable.getSelectionModel().selectedItemProperty().isNull());
	}

	@FXML
	void onConfirm(ActionEvent event) {

		Autor a1 = new Autor();
		a1.setCodAutor(codigoText.getText());
		a1.setNombreAutor(nombreText.getText());

		session.beginTransaction();
		session.save(a1);
		session.getTransaction().commit();

		cargarAutores();
		nombreText.setText("");
		codigoText.setText("");
		stage.close();

	}

	@FXML
	void onCancel(ActionEvent event) {
		nombreText.setText("");
		codigoText.setText("");
		stage.close();
	}

	@FXML
	void onAddAutor(ActionEvent event) {
		codigoText.requestFocus();
		stage.getScene().setRoot(insertView);
		stage.show();
	}

	@FXML
	void onDelAutor(ActionEvent event) {
			session.beginTransaction();
			session.delete(autoresTable.getSelectionModel().getSelectedItem());
			session.getTransaction().commit();
			cargarAutores();
	}

	@SuppressWarnings("unchecked")
	private void cargarAutores() {
		autoresTable.setItems(FXCollections.observableArrayList(session.createQuery("FROM Autor").list()));
	}

	private void FXMLloaders() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/AutoresView.fxml"));
			loader.setController(this);
			autoresTable = loader.load();

			FXMLLoader loaderInsert = new FXMLLoader(getClass().getResource("/aed/view/InsertAutorView.fxml"));
			loaderInsert.setController(this);
			insertView = loaderInsert.load();
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
