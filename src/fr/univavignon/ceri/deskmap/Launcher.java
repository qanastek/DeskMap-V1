package fr.univavignon.ceri.deskmap;

import java.awt.geom.Point2D;

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
	 * URL de Overpass API
	 */
	public static String URL_OSM = new String("https://lz4.overpass-api.de/api/interpreter?data=");
	
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
		
		primaryStage.setTitle("DESKMAP V1.0.17");
		
		// Définit la largeur minimal de la fenêtre à la moitié de la largeur de l'écran
		primaryStage.setMinWidth(screenBounds.getWidth() * 0.5);
		
		// Définit la hauteur minimal de la fenêtre à la moitié de la hauteur de l'écran
		primaryStage.setMinHeight(screenBounds.getHeight() * 0.5);
		
		primaryStage.setScene(my_scene);
		primaryStage.show();
		
	}	
}
