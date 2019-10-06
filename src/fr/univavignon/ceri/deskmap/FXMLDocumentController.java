package fr.univavignon.ceri.deskmap;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author Labrak Yanis
 *
 */
public class FXMLDocumentController implements Initializable {
	
	@FXML private TextField cityName;
	
	/**
	 * Coordonées de la ville
	 */
	public static Point2D.Double cityCoordinate = new Point2D.Double(43.93009, 4.84706);
	
	/**
	 * Lien de l'APIs
	 */
	public static String URL_OSM = new String("https://master.apis.dev.openstreetmap.org");
	
	/**
	 * Lorsque l'ont clique sur fullscreen
	 * @param event évènement
	 */
	@FXML
	private void SetFullscreen(MouseEvent event)
    {
		Node source = (Node) event.getSource();
	    Window theStage = source.getScene().getWindow();
	    ((Stage) theStage).setFullScreen(true);
    }


	/**
	 * Reset
	 * @param event évènement
	 */
	@FXML
	private void Reset(MouseEvent event)
    {
		System.out.println("Reset clicked");
    }

	/**
	 * Searching
	 * @param event e
	 */
	@FXML
	private void Searching(MouseEvent event)
    {		
		System.out.println("Searching clicked");
    }
	
	/**
	 * SetCity
	 * @param event e
	 */
	@FXML
	private void SetCity(MouseEvent event)
    {
		System.out.println("SetCity clicked");
		
		System.out.println("Value:");
		System.out.println(this.cityName.getText());
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
