package fr.univavignon.ceri.deskmap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
		
		// Set the title of the window
		primaryStage.setTitle("DESKMAP V1.0.68");

		// Set a favicon to the window
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

		// Get the dimensions of the monitor
	    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		
	    // Create and link the layout to a FXML file
	    // This is the 'WINDOW' of the app
	    Parent layout = FXMLLoader.load(getClass().getClassLoader().getResource("main_view.fxml"));

	    // The area inside of the layout
		Scene my_scene = new Scene(layout, layout.getLayoutY(), layout.getLayoutX());
		
		// Link to the scene a CSS stylesheet for all the styles
		my_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		// Set the width of the window to the half of the monitor width
		primaryStage.setMinWidth(screenBounds.getWidth() * 0.6);

		// Set the height of the window to the half of the monitor height
		primaryStage.setMinHeight(screenBounds.getHeight() * 0.6);
		
		primaryStage.setScene(my_scene);
		primaryStage.show();
		
	}	
}
