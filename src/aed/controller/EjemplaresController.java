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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

public class EjemplaresController {
	@FXML
	private TableView<Ejemplar> ejemplarTable;

	@FXML
	private TableColumn<Ejemplar, Integer> codColumn;

	@FXML
	private TableColumn<Ejemplar, Libro> libroColumn;

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
	private Button confirmButton;

	@FXML
	private Button cancelButton;

	@FXML
	private ComboBox<Libro> libroCombo;

	@FXML
	private TextField importeText, tipoMonedaText;

	private Session session;
	private Stage stage;
	private Alert message;

	@SuppressWarnings("unchecked")
	public EjemplaresController(Session session) {

		this.session = session;

		stage = new Stage();
		stage.setScene(new Scene(new VBox()));
		stage.getIcons().add(new Image(getClass().getResource("/resources/db.png").toExternalForm()));
		stage.setTitle("Insertar Ejemplar");
		stage.initModality(Modality.APPLICATION_MODAL);
		
		message = new Alert(AlertType.ERROR);

		FXMLloads();

		codColumn.setCellValueFactory(new PropertyValueFactory<>("codEjemplar"));
		libroColumn.setCellValueFactory(new PropertyValueFactory<>("codLibro"));
		libroColumn.setCellFactory(ComboBoxTableCell
				.forTableColumn(FXCollections.observableArrayList(session.createQuery("FROM Libro").list())));
		importeColumn.setCellValueFactory(new PropertyValueFactory<>("importe"));
		importeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		tipoMonedaColumn.setCellValueFactory(new PropertyValueFactory<>("tipoMoneda"));
		tipoMonedaColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		libroColumn.setOnEditCommit(e -> updateLibro(e));
		importeColumn.setOnEditCommit(e -> updateImporte(e));
		tipoMonedaColumn.setOnEditCommit(e -> updateTipoMoneda(e));

		cargarEjemplares();
	}

	private void updateTipoMoneda(CellEditEvent<Ejemplar, String> e) {
		e.getRowValue().setTipoMoneda(e.getNewValue());
		session.beginTransaction();
		session.createQuery("UPDATE Ejemplar SET tipoMoneda=? WHERE codEjemplar=?").setString(0, e.getNewValue())
				.setInteger(1, e.getRowValue().getCodEjemplar());
		session.getTransaction().commit();
		cargarEjemplares();
	}

	private void updateImporte(CellEditEvent<Ejemplar, Double> e) {
		e.getRowValue().setImporte(e.getNewValue());
		session.beginTransaction();
		session.createQuery("UPDATE Ejemplar SET importe=? WHERE codEjemplar=?").setDouble(0, e.getNewValue())
				.setInteger(1, e.getRowValue().getCodEjemplar());
		session.getTransaction().commit();
		cargarEjemplares();
	}

	private void updateLibro(CellEditEvent<Ejemplar, Libro> e) {
		e.getRowValue().setCodLibro(e.getNewValue());
		session.beginTransaction();
		session.createQuery("UPDATE Ejemplar SET codLibro=? WHERE codEjemplar=?").setEntity(0, e.getNewValue())
				.setInteger(1, e.getRowValue().getCodEjemplar());
		session.getTransaction().commit();
		cargarEjemplares();
	}

	@FXML
	void onCancelButton(ActionEvent event) {
		stage.close();
		libroCombo.setValue(null);
		importeText.setText("");
		tipoMonedaText.setText("");
	}

	@FXML
	void onConfirmAdd(ActionEvent event) {

		try {
			try {
				session.beginTransaction();

				Ejemplar ej1 = new Ejemplar();
				ej1.setCodLibro(libroCombo.getValue());
				ej1.setImporte(Double.parseDouble(importeText.getText()));
				ej1.setTipoMoneda(tipoMonedaText.getText());

				session.save(ej1);
				session.getTransaction().commit();

				cargarEjemplares();

				stage.close();
				libroCombo.setValue(null);
				importeText.setText("");
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
			}
			tipoMonedaText.setText("");
		} catch (NumberFormatException e) {
			message.setTitle("Código Ejemplar");
			message.setHeaderText("Error al insertar el código del ejemplar.");
			message.setContentText("El código debe ser númerico.");
			message.show();
		}

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

	}

	public TableView<Ejemplar> getEjemplarTable() {
		return ejemplarTable;
	}

	public TableColumn<Ejemplar, Integer> getCodColumn() {
		return codColumn;
	}

	public TableColumn<Ejemplar, Libro> getLibroColumn() {
		return libroColumn;
	}

	public TableColumn<Ejemplar, Double> getImporteColumn() {
		return importeColumn;
	}

	public TableColumn<Ejemplar, String> getTipoMonedaColumn() {
		return tipoMonedaColumn;
	}

}
