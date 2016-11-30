package aed.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;

import aed.model.HibernateUtil;
import aed.model.Libro;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibroController {

	@FXML
	private TableView<Libro> librosTable;

	@FXML
	private TableColumn<Libro, Integer> codColumn;

	@FXML
	private TableColumn<Libro, Libro> nombreColumn, isbnColumn;

	@FXML
	private TableColumn<Libro, java.util.Date> fechaColumn;

	@FXML
	private ContextMenu menuLibros;

	@FXML
	private MenuItem addLibro;

	@FXML
	private MenuItem delLibros;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField isbnText;

	@FXML
	private Button confirmButton;

	@FXML
	private Button cancelButton;

	@FXML
	private GridPane insertView;

	private Session session;
	private Stage stage;
	private Pattern pattern;
	private Alert messageAlert;

	public LibroController(Session session) {

		if (session.isOpen()) {
			System.out.println("ABIERTA");
		} else {
			System.out.println("CERRADA");
		}

		FXMLloads();

		this.session = session;
		pattern = Pattern.compile("[0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]-[a-zA-Z]");
		messageAlert = new Alert(AlertType.ERROR);

		codColumn.setCellValueFactory(new PropertyValueFactory<>("codLibro"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreLibro"));
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaIntro"));

		stage = new Stage();
		stage.setScene(new Scene(new VBox()));

		cargarLibros(session);

	}

	@FXML
	void onConfirmAdd(ActionEvent event) {

		Matcher mat = pattern.matcher(isbnText.getText());
		if (mat.matches()) {
			Libro l1 = new Libro();
			l1.setNombreLibro(nombreText.getText());
			l1.setISBN(isbnText.getText());
			l1.setFechaIntro(Date.valueOf(LocalDate.now()));

			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(l1);
			session.getTransaction().commit();

			stage.close();
			nombreText.setText("");
			isbnText.setText("");
			cargarLibros(session);
		} else {
			messageAlert.setAlertType(AlertType.ERROR);
			messageAlert.setTitle("ISBN");
			messageAlert.setHeaderText("Error al insertar el ISBN del libro: Formato incorrecto.");
			messageAlert.setContentText("Inserte un ISBN con el formato: 11-111-1111-X");
			messageAlert.show();
		}

	}

	@FXML
	void onCancelButton(ActionEvent event) {
		stage.close();
		nombreText.setText("");
		isbnText.setText("");
	}

	@FXML
	void onAddLibro(ActionEvent event) {
		stage.setTitle("Insertar Libro");
		stage.getScene().setRoot(insertView);
		stage.show();
	}

	@FXML
	void onDelLibros(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	void cargarLibros(Session session) {
		// Query query = session.createQuery("from Libro");
		// List<Libro> libros = query.list();
		// Version resumida
		librosTable.setItems(FXCollections.observableArrayList(session.createQuery("from Libro").list()));
	}

	private void FXMLloads() {
		try {
			FXMLLoader loaderLibrosView = new FXMLLoader(getClass().getResource("/aed/view/librosview.fxml"));
			loaderLibrosView.setController(this);
			librosTable = loaderLibrosView.load();

			FXMLLoader loaderInsert = new FXMLLoader(getClass().getResource("/aed/view/InsertLibroView.fxml"));
			loaderInsert.setController(this);
			insertView = loaderInsert.load();
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

	public TableColumn<Libro, java.util.Date> getFechaColumn() {
		return fechaColumn;
	}

}
