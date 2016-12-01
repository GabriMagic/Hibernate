package aed.controller;

import java.io.IOException;

import org.hibernate.Session;

import aed.model.Ejemplar;
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
	private TableView<Ejemplar> ejemplarTable;

	@FXML
	private TableColumn<Ejemplar, Integer> codColumn;

	@FXML
	private TableColumn<Ejemplar, String> libroColumn;

	@FXML
	private TableColumn<Ejemplar, Double> importeColumn;

	@FXML
	private TableColumn<Ejemplar, String> tipoMonedaColumn;

	@FXML
	private ContextMenu menuLibros;

	@FXML
	private MenuItem add;

	@FXML
	private MenuItem delete;

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
		stage.close();
		nombreText.setText("");
		libroCombo.setValue(null);
		importeText.setText("");
		tipoMonedaText.setText("");
	}

	@FXML
	void onConfirmAdd(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	@FXML
	void onAdd(ActionEvent event) {
		libroCombo.setItems(FXCollections.observableArrayList(session.createQuery("from Libro").list()));
		stage.getScene().setRoot(insertView);
		stage.show();
	}

	@FXML
	void onDelete(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	private void cargarEjemplares() {
		ejemplarTable.setItems(FXCollections.observableArrayList(session.createQuery("FROM Ejemplar").list()));
	}

	public TableView<Ejemplar> getEjemplarTable() {
		return ejemplarTable;
	}

	public TableColumn<Ejemplar, Integer> getCodColumn() {
		return codColumn;
	}

	public TableColumn<Ejemplar, String> getLibroColumn() {
		return libroColumn;
	}

	public TableColumn<Ejemplar, Double> getImporteColumn() {
		return importeColumn;
	}

	public TableColumn<Ejemplar, String> getTipoMonedaColumn() {
		return tipoMonedaColumn;
	}

}
