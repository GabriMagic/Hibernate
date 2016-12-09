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
		});

		// Libro l1 = new Libro();
		// l1.setISBN("12-963-6469-X");
		// l1.setFechaIntro(Date.valueOf(LocalDate.now()));
		// l1.setNombreLibro("Memorias de Idhún");
		//
		// Libro l2 = new Libro();
		// l2.setISBN("12-963-6429-X");
		// l2.setFechaIntro(Date.valueOf(LocalDate.now()));
		// l2.setNombreLibro("Juego de Tronos");
		//
		// Autor a1 = new Autor();
		// a1.setCodAutor("GAB");
		// a1.setNombreAutor("GabriMagic");
		//
		//// Autor a2 = new Autor();
		//// a1.setCodAutor("XIL");
		//// a1.setNombreAutor("Xilerth");
		//
		// Ejemplar ej1 = new Ejemplar();
		// ej1.setCodLibro(l1);
		// ej1.setImporte(95.30);
		// ej1.setTipoMoneda("EUROS");
		//
		// DepositoLegal dl1 = new DepositoLegal();
		// dl1.setCodLibroDeposito(l1);
		// dl1.setDepositoLegal("Que va aqui?");
		//
		// LibrosAutores la1 = new LibrosAutores();
		// la1.setCodAutor(a1);
		// la1.setCodLibro(l1);
		//
		// session.save(l1);
		// session.save(l2);
		// session.save(a1);
		// // session.save(a2);
		// session.save(ej1);
		// session.save(dl1);
		// session.save(la1);

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

		ejemplaresController.getEjemplarTable().itemsProperty().addListener(e -> datosController.cargarTodos());
		depositoLegalController.getDepositoLegalTable().itemsProperty().addListener(e -> {
			System.out.println("WHAT");
			datosController.cargarTodos();
		});

	}

	public TabPane getView() {
		return view;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
