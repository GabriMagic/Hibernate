package aed.controller;

import java.io.IOException;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;

import aed.model.Autor;
import aed.model.Datos;
import aed.model.DepositoLegal;
import aed.model.Ejemplar;
import aed.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DatosController {

	@FXML
	private TableView<Datos> librosTable;

	@FXML
	private TableColumn<Datos, Integer> codigoColumn;

	@FXML
	private TableColumn<Datos, String> nombreColumn;

	@FXML
	private TableColumn<Datos, String> isbnColumn;

	@FXML
	private TableColumn<Datos, Double> importeColumn;

	@FXML
	private TableColumn<Datos, Integer> ejemplarColumn;

	@FXML
	private TableColumn<Datos, String> autorColumn;

	@FXML
	private TableColumn<Datos, DepositoLegal> depositoColumn;

	@FXML
	private MenuItem actualizarMenu;

	private Session session;
	private ObservableList<Datos> listaDatos;

	public DatosController(Session session) {

		this.session = session;

		FXMLloads();

		codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codLibro"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreLibro"));
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		importeColumn.setCellValueFactory(new PropertyValueFactory<>("importe"));
		ejemplarColumn.setCellValueFactory(new PropertyValueFactory<>("codEjemplar"));
		autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
		depositoColumn.setCellValueFactory(new PropertyValueFactory<>("depositoLegal"));

		listaDatos = FXCollections.observableArrayList();

		cargarTodos();
	}

	@FXML
	void onActualizar(ActionEvent event) {
		librosTable.getItems().removeAll(listaDatos);
		cargarTodos();
	}

	public void cargarTodos() {

		Query query2 = session.createQuery("FROM LibrosAutores la "
				+ "LEFT JOIN la.codLibro li "
				+ "LEFT JOIN li.ejemplares ej "
				+ "LEFT JOIN li.codLibroDeposito ld"
				+ "INNER JOIN la.codAutor au ");

		Iterator<?> iterator = query2.iterate();
		while (iterator.hasNext()) {

			Object[] par = (Object[]) iterator.next();

		

			Libro li = new Libro();
			li = (Libro) par[1];
			Ejemplar ej = (Ejemplar) par[2];
			DepositoLegal dl = (DepositoLegal) par[3];
			Autor au = (Autor) par[4];

//			System.out.println(dl.getDepositoLegal());

			Datos datos = new Datos(ej, li, au, dl);
			listaDatos.add(datos);
		}
		librosTable.setItems(listaDatos);

	}

	
	private void FXMLloads() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/DatosView.fxml"));
			loader.setController(this);
			librosTable = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TableView<Datos> getLibrosTable() {
		return librosTable;
	}

	public TableColumn<Datos, Integer> getCodigoColumn() {
		return codigoColumn;
	}

	public TableColumn<Datos, String> getNombreColumn() {
		return nombreColumn;
	}

	public TableColumn<Datos, String> getIsbnColumn() {
		return isbnColumn;
	}

	public TableColumn<Datos, Double> getImporteColumn() {
		return importeColumn;
	}

	public TableColumn<Datos, Integer> getEjemplarColumn() {
		return ejemplarColumn;
	}

	public TableColumn<Datos, String> getAutorColumn() {
		return autorColumn;
	}

	public TableColumn<Datos, DepositoLegal> getDepositoColumn() {
		return depositoColumn;
	}

}
