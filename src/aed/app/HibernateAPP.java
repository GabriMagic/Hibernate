package aed.app;

import aed.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HibernateAPP extends Application {

	private MainController mainController;

	@Override
	public void start(Stage primaryStage) throws Exception {

		mainController = new MainController(primaryStage);

		primaryStage.setScene(new Scene(mainController.getView()));
		primaryStage.setResizable(true);
		primaryStage.getIcons().add(new Image("/resources/db.png"));
		primaryStage.setTitle("HIBERNATE");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
