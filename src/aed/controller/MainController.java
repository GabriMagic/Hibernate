package aed.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.hibernate.Session;

import aed.model.HibernateUtil;
import aed.model.Libro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController implements Initializable {

	@FXML
	private TabPane view;

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

	public MainController() {

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

		// Cargar las pestañas
		librosController = new LibroController(session);
		autorController = new AutorController(session);

		librosTab.setContent(librosController.getLibrosTable());
		autoresTab.setContent(autorController.getAutoresTable());

		// Libro l1 = new Libro();
		// l1.setISBN("12-963-6469-X");
		// l1.setFechaIntro(Date.valueOf(LocalDate.now()));
		// l1.setNombreLibro("Memorias de Idhún");
		//
		// Autor a1 = new Autor();
		// a1.setCodAutor("GAB");
		// a1.setNombreAutor("GabriMagic");
		//
		// Ejemplar ej1 = new Ejemplar();
		// ej1.setCodEjemplar(1);
		// ej1.setCodLibro(l1);
		// ej1.setImporte(95.30);
		// ej1.setTipo_moneda("EUROS");
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
		// session.save(a1);
		// session.save(ej1);
		// session.save(dl1);
		// session.save(la1);

		session.getTransaction().commit();
		session.close();

	}

	public TabPane getView() {
		return view;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
