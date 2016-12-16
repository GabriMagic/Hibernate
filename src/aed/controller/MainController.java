package aed.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;

import aed.model.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML
	private TabPane view;

	@FXML
	private Tab datosTab;

	@FXML
	private Tab librosTab;

	@FXML
	private Tab autoresTab;

	@FXML
	private Tab ejemplaresTab;

	@FXML
	private Tab depositoLegalTab;

	@FXML
	private Tab librosAutoresTab;

	private LibroController librosController;
	private AutorController autorController;
	private EjemplaresController ejemplaresController;
	private DatosController datosController;
	private LibrosAutoresController librosAutoresController;
	private DepositoLegalController depositoLegalController;

	public MainController(Stage primaryStage) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Cargar la vista principal
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/MainView.fxml"));
			loader.setController(this);
			view = loader.load();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		primaryStage.setOnCloseRequest(e -> {
			session.close();
			System.out.println("Cerrando...");
			try {
				Runtime.getRuntime().exec("java -jar src/aed/app/Hibernateapp.jar");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		session.getTransaction().commit();

		// Cargar las pestañas
		librosController = new LibroController(session);
		autorController = new AutorController(session);
		ejemplaresController = new EjemplaresController(session);
		datosController = new DatosController(session);
		librosAutoresController = new LibrosAutoresController(session);
		depositoLegalController = new DepositoLegalController(session);

		librosTab.setContent(librosController.getLibrosTable());
		autoresTab.setContent(autorController.getAutoresTable());
		ejemplaresTab.setContent(ejemplaresController.getEjemplarTable());
		datosTab.setContent(datosController.getLibrosTable());
		librosAutoresTab.setContent(librosAutoresController.getLibrosAutoresTable());
		depositoLegalTab.setContent(depositoLegalController.getDepositoLegalTable());

		librosController.getLibrosTable().itemsProperty().addListener(e -> {
			ejemplaresController.cargarEjemplares();
			datosController.cargarTodos();
			librosAutoresController.cargarLibrosAutores();
			depositoLegalController.cargarDepositoLegal();
		});

		autorController.getAutoresTable().itemsProperty().addListener(e -> {
			datosController.cargarTodos();
			librosAutoresController.cargarLibrosAutores();
		});

		ejemplaresController.getEjemplarTable().itemsProperty().addListener(e -> datosController.cargarTodos());
		depositoLegalController.getDepositoLegalTable().itemsProperty().addListener(e -> datosController.cargarTodos());
	}

	public TabPane getView() {
		return view;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
