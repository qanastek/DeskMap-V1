package fr.univavignon.ceri.deskmap;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
	 * Checking if its a integer
	 * @param event e
	 */
	@FXML
	private void checkInput(KeyEvent event)
    {		
		char value = event.getCharacter().charAt(0);
		
		if (!Character.isDigit(value)) {
			event.consume();
		}
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
