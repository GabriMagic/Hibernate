package aed.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;

import aed.model.Autor;
import aed.model.HibernateUtil;
import aed.model.Libro;
import aed.model.LibrosAutores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LibroController {

	@FXML
	private TableView<Libro> librosTable;

	@FXML
	private TableColumn<Libro, Integer> codColumn;

	@FXML
	private TableColumn<Libro, String> nombreColumn, isbnColumn;

	@FXML
	private TableColumn<Libro, java.util.Date> fechaColumn;

	@FXML
	private ContextMenu menuLibros;

	@FXML
	private MenuItem addLibro;

	@FXML
	private MenuItem delLibros;

	@FXML
	private MenuItem mostrarAutores;

	@FXML
	private MenuItem addAutor;

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

	@FXML
	private ListView<Autor> listaAutores;

	@FXML
	private GridPane addLibroAutor;

	@FXML
	private ComboBox<Libro> librosCombo;

	@FXML
	private ComboBox<Autor> autoresCombo;

    @FXML
    private Button confirmLibroAutorButton;

    @FXML
    private Button cancelLibroAutorButton;


	private Session session;
	private Stage stage;
	private Pattern pattern;
	private Alert messageAlert;
	private ObservableList<Autor> autores;

	public LibroController(Session session) {

		FXMLloads();

		this.session = session;
		pattern = Pattern.compile("[0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]-[a-zA-Z]");
		messageAlert = new Alert(AlertType.ERROR);

		codColumn.setCellValueFactory(new PropertyValueFactory<>("codLibro"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreLibro"));
		nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaIntro"));

		isbnColumn.setOnEditCommit(e -> updateIsbnLibro(e));
		nombreColumn.setOnEditCommit(e -> updateNombre(e));

		stage = new Stage();
		stage.getIcons().add(new Image(getClass().getResource("/resources/db.png").toExternalForm()));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(new VBox()));

		cargarLibros();

		delLibros.disableProperty().bind(librosTable.getSelectionModel().selectedItemProperty().isNull());

	}

	private void updateNombre(CellEditEvent<Libro, String> e) {
		e.getRowValue().setNombreLibro(e.getNewValue());
		session.beginTransaction();
		session.createQuery("UPDATE Libro SET nombreLibro=? WHERE codLibro=?").setString(0, e.getNewValue())
				.setInteger(1, e.getRowValue().getCodLibro()).executeUpdate();
		session.getTransaction().commit();
		cargarLibros();
	}

	private void updateIsbnLibro(CellEditEvent<Libro, String> e) {
		try {
			Matcher mat = pattern.matcher(e.getNewValue());
			if (mat.matches()) {
				e.getRowValue().setISBN(e.getNewValue());
				session.beginTransaction();
				session.createQuery("UPDATE Libro SET ISBN=:isbn WHERE codLibro=:libro")
						.setString("isbn", e.getNewValue()).setInteger("libro", e.getRowValue().getCodLibro())
						.executeUpdate();
				session.getTransaction().commit();
				cargarLibros();
			}
		} catch (Exception e1) {
			messageAlert.setAlertType(AlertType.ERROR);
			messageAlert.setTitle("Duplicate Key " + e.getNewValue());
			messageAlert.setHeaderText("Error al insertar el nuevo ISBN.");
			messageAlert.setContentText("Ya existe un libro con el ISBN: " + e.getNewValue());
			messageAlert.show();
			session.getTransaction().rollback();
			cargarLibros();
		}
	}

	@FXML
    void onCancel(ActionEvent event) {
		librosCombo.setValue(null);
		autoresCombo.setValue(null);
		stage.close();
    }

    @FXML
    void onLibroAutorConfirm(ActionEvent event) {

    	try {
			session.beginTransaction();
			
			LibrosAutores la = new LibrosAutores();
			la.setCodAutor(autoresCombo.getValue());
			la.setCodLibro(librosCombo.getValue());
			
			session.save(la);
			session.getTransaction().commit();
			stage.close();
		} catch (NonUniqueObjectException e) {
			messageAlert.setAlertType(AlertType.ERROR);
			messageAlert.setTitle("Libro - Autor");
			messageAlert.setHeaderText("Error al añadir el autor al libro seleccionado.");
			messageAlert.setContentText("Ya existe esa relación en la tabla, elija otro libro u otro autor.");
			messageAlert.show();
			session.getTransaction().rollback();
		}
    	
    	
    }
	
	@SuppressWarnings("unchecked")
	@FXML
	void onAddAutor(ActionEvent event) {
		
		librosCombo.setItems(FXCollections.observableArrayList(session.createQuery("FROM Libro").list()));
		autoresCombo.setItems(FXCollections.observableArrayList(session.createQuery("FROM Autor").list()));
		
		stage.getScene().setRoot(addLibroAutor);
		stage.setTitle("Añadir Autor");
		stage.show();
	}

	@FXML
	void onMostrar(ActionEvent event) {

		autores = FXCollections.observableArrayList();

		session = HibernateUtil.getSessionFactory().openSession();
		Query query = session
				.createQuery("FROM LibrosAutores la " + "INNER JOIN la.codAutor " + "WHERE la.codLibro = ?")
				.setInteger(0, librosTable.getSelectionModel().getSelectedItem().getCodLibro());

		Iterator<?> iterator = query.iterate();
		while (iterator.hasNext()) {
			Object[] result = (Object[]) iterator.next();

			Autor au = (Autor) result[1];

			System.out.println(au);

			autores.add(au);
		}

		listaAutores.setItems(FXCollections.observableArrayList(autores));
		stage.setTitle("Libro: " + librosTable.getSelectionModel().getSelectedItem());
		stage.getScene().setRoot(listaAutores);
		stage.show();
	}

	@FXML
	void onConfirmAdd(ActionEvent event) {

		Matcher mat = pattern.matcher(isbnText.getText());
		if (mat.matches()) {
			Libro l1 = new Libro();
			l1.setNombreLibro(nombreText.getText());
			l1.setISBN(isbnText.getText());
			l1.setFechaIntro(Date.valueOf(LocalDate.now()));

			session.beginTransaction();
			session.save(l1);
			session.getTransaction().commit();

			stage.close();
			nombreText.setText("");
			isbnText.setText("");
			cargarLibros();
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

		session.beginTransaction();
		try {
			session.createQuery("DELETE FROM Libro WHERE codLibro = ?")
					.setInteger(0, librosTable.getSelectionModel().getSelectedItem().getCodLibro()).executeUpdate();
			session.getTransaction().commit();
			cargarLibros();
		} catch (Exception e) {
			try {
				messageAlert.setAlertType(AlertType.ERROR);
				messageAlert.setTitle("Eliminar Libro");
				messageAlert.setHeaderText("Error al eliminar el libro. Tiene ejemplares.");
				messageAlert.setContentText("¿Desea eliminar todos sus ejemplares?");
				if (messageAlert.showAndWait().get() == ButtonType.OK) {
					session.createQuery("DELETE FROM Ejemplar WHERE codLibro = ?")
							.setInteger(0, librosTable.getSelectionModel().getSelectedItem().getCodLibro())
							.executeUpdate();
					session.getTransaction().commit();

					session.beginTransaction();
					session.createQuery("DELETE FROM Libro WHERE codLibro = ?")
							.setInteger(0, librosTable.getSelectionModel().getSelectedItem().getCodLibro())
							.executeUpdate();
					session.getTransaction().commit();
					cargarLibros();
				}
			} catch (NoSuchElementException e1) {
			}

		}

	}

	@SuppressWarnings("unchecked")
	public void cargarLibros() {
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

			FXMLLoader loaderAutores = new FXMLLoader(getClass().getResource("/aed/view/ListaAutoresView.fxml"));
			loaderAutores.setController(this);
			listaAutores = loaderAutores.load();

			FXMLLoader loaderLibrosAutores = new FXMLLoader(getClass().getResource("/aed/view/AddLibroAutorView.fxml"));
			loaderLibrosAutores.setController(this);
			addLibroAutor = loaderLibrosAutores.load();
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

	public TableColumn<Libro, String> getNombreColumn() {
		return nombreColumn;
	}

	public TableColumn<Libro, String> getIsbnColumn() {
		return isbnColumn;
	}

	public TableColumn<Libro, java.util.Date> getFechaColumn() {
		return fechaColumn;
	}

}
