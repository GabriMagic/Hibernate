package aed.controller;

import aed.model.Autor;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AutorController {
	@FXML
	private TableView<Autor> autoresTable;

	@FXML
	private TableColumn<Autor, String> codColumn, nombreColumn;

	
	public AutorController() {
		
	}
}
