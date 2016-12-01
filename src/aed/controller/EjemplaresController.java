package aed.controller;

import java.io.IOException;

import org.hibernate.Session;

import aed.model.Autor;
import aed.model.Libro;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EjemplaresController {
	@FXML
	private TableView<Autor> ejemplarTable;

	@FXML
	private TableColumn<Autor, Integer> codColumn;

	@FXML
	private TableColumn<Autor, String> libroColumn;

	@FXML
	private TableColumn<Autor, Double> importeColumn;

	@FXML
	private TableColumn<Autor, String> tipoMonedaColumn;

	@FXML
	private ContextMenu menuLibros;

	@FXML
	private MenuItem addLibro;

	@FXML
	private MenuItem delLibros;

	@FXML
	private GridPane insertView;

	@FXML
	private TextField nombreText;

	@FXML
	private Button confirmButton;

	@FXML
	private Button cancelButton;

	@FXML
	private ComboBox<Libro> libroCombo;

	@FXML
	private TextField importeText, tipoMonedaText;

	private Session session;
	private Stage stage;

	public EjemplaresController(Session session) {

		this.session = session;

		stage = new Stage();
		stage.setScene(new Scene(new VBox()));

		FXMLloads();
		cargarEjemplares();
	}

	private void FXMLloads() {
		try {
			FXMLLoader loaderEjemplaresView = new FXMLLoader(getClass().getResource("/aed/view/EjemplaresView.fxml"));
			loaderEjemplaresView.setController(this);
			ejemplarTable = loaderEjemplaresView.load();

			FXMLLoader loaderInsertView = new FXMLLoader(getClass().getResource("/aed/view/InsertEjemplarView.fxml"));
			loaderInsertView.setController(this);
			insertView = loaderInsertView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		codColumn.setCellValueFactory(new PropertyValueFactory<>("codEjemplar"));
		libroColumn.setCellValueFactory(new PropertyValueFactory<>("codLibro"));
		importeColumn.setCellValueFactory(new PropertyValueFactory<>("importe"));
		tipoMonedaColumn.setCellValueFactory(new PropertyValueFactory<>("tipo_moneda"));

	}

	@FXML
	void onCancelButton(ActionEvent event) {

	}

	@FXML
	void onConfirmAdd(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	@FXML
	void onAddLibro(ActionEvent event) {
		libroCombo.setItems(FXCollections.observableArrayList(session.createQuery("from Libro").list()));
		stage.getScene().setRoot(insertView);
		stage.show();
	}

	@FXML
	void onDelLibros(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	private void cargarEjemplares() {
		getEjemplarTable().setItems(FXCollections.observableArrayList(session.createQuery("FROM Ejemplar").list()));
	}

	public TableView<Autor> getEjemplarTable() {
		return ejemplarTable;
	}

	public TableColumn<Autor, Integer> getCodColumn() {
		return codColumn;
	}

	public TableColumn<Autor, String> getLibroColumn() {
		return libroColumn;
	}

	public TableColumn<Autor, Double> getImporteColumn() {
		return importeColumn;
	}

	public TableColumn<Autor, String> getTipoMonedaColumn() {
		return tipoMonedaColumn;
	}

}
