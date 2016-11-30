#Hibernate
```javafx
package aed.app;

import aed.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HibernateAPP extends Application {

	private MainController mainController;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;

		mainController = new MainController();

		primaryStage.setScene(new Scene(mainController.getView()));
		primaryStage.getIcons().add(new Image("/resources/db.png"));
		primaryStage.setTitle("HIBERNATE");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}

```
