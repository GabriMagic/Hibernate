package aed.controller;

import java.io.IOException;
import org.hibernate.Session;
import aed.model.DepositoLegal;
import aed.model.Libro;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class DepositoLegalController {

	@FXML
	private TableView<DepositoLegal> depositoLegalTable;

	@FXML
	private TableColumn<DepositoLegal, Libro> libroColumn;

	@FXML
	private TableColumn<DepositoLegal, String> depositoLegalColumn;

	private Session sesison;

	public DepositoLegalController(Session session) {

		this.sesison = session;

		FXMLloads();

		libroColumn.setCellValueFactory(new PropertyValueFactory<>("codLibroDeposito"));
		depositoLegalColumn.setCellValueFactory(new PropertyValueFactory<>("depositoLegal"));
		depositoLegalColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		depositoLegalColumn.setOnEditCommit(e -> updateDepositoLegal(e));

		cargarDepositoLegal();

	}

	private void updateDepositoLegal(CellEditEvent<DepositoLegal, String> e) {
		sesison.beginTransaction();
		e.getRowValue().setDepositoLegal(e.getNewValue());
		sesison.save(e.getRowValue());
		sesison.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public void cargarDepositoLegal() {
		depositoLegalTable.getItems()
				.removeAll(FXCollections.observableArrayList(sesison.createQuery("FROM DepositoLegal").list()));
		depositoLegalTable
				.setItems(FXCollections.observableArrayList(sesison.createQuery("FROM DepositoLegal").list()));
	}

	private void FXMLloads() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/aed/view/DepositoLegalView.fxml"));
			loader.setController(this);
			depositoLegalTable = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TableView<DepositoLegal> getDepositoLegalTable() {
		return depositoLegalTable;
	}

	public TableColumn<DepositoLegal, String> getDepositoLegalColumn() {
		return depositoLegalColumn;
	}
}
