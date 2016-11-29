package aed.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;

import aed.model.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

public class MainController implements Initializable {

	@FXML
	private TabPane view;

	public MainController() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.close();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
			loader.setController(this);
			view = loader.load();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public TabPane getView() {
		return view;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
