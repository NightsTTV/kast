import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class javaFx extends Application {
	@Override
	public void start(Stage primaryStage) {
	Button button = new Button("Click Me");
	StackPane layout = new StackPane();
	layout.getChildren().add(button);

	Scene scene = new Scene(layout, 300, 200);
	primaryStage.setTitle("JavaFX UI");
	primaryStage.setScene(scene);
	primaryStage.show();
}

public static void main(String[] args) {
		launch(args);
	}
}