package fr.univavignon.ceri.deskmap;

/**
 * This class is used to launch the software.
 *
 * @author Mohamed BEN YAMNA
 * @author Quentin Capdepon
 * @author Yanis Labrak
 * @author Zihao Zheng
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author zihao
 *
 */
public class Launcher extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{

		primaryStage.setMinHeight(650);

		primaryStage.setMinWidth(1280);

		primaryStage.setTitle("PUBG");



		Parent layout = FXMLLoader.load(getClass().getResource("test.fxml"));

		Scene my_scene = new Scene(layout, layout.getLayoutY(), layout.getLayoutX());

		primaryStage.setScene(my_scene);


		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
