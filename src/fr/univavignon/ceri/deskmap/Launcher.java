package fr.univavignon.ceri.deskmap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class is used to launch the software.
 * 
 * @author Mohamed BEN YAMNA
 * @author Quentin Capdepon
 * @author Yanis Labrak
 * @author Zihao Zheng
 */
public class Launcher extends Application
{
	
	/**
	 * Launches the software.
	 * Add things for testing
	 * 
	 * @param args
	 * 		Not used here.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

	    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		
	    Parent layout = FXMLLoader.load(getClass().getClassLoader().getResource("main_view.fxml"));

		Scene my_scene = new Scene(layout, layout.getLayoutY(), layout.getLayoutX());
		
		primaryStage.setTitle("DESKMAP V1.0.5");
		
		primaryStage.setScene(my_scene);
		primaryStage.show();
		
	}	
}
