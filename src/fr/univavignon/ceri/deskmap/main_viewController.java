package fr.univavignon.ceri.deskmap;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.scene.control.Slider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.input.KeyEvent;

public class main_viewController {
	@FXML
	private TextField toNumber;
	@FXML
	private TextField cityName;
	@FXML
	private ComboBox fromName;
	@FXML
	private ComboBox toName;
	@FXML
	private TextField fromNumber;
	@FXML
	private TextArea trajet;
	@FXML
	private Slider slider;
	@FXML
	private Button cityButton;
	@FXML
	private Button resetBtn;
	@FXML
	private Button SearchBtn;
	
	/**
	 * Lorsque l'ont clique sur fullscreen
	 * @param event évènement
	 */
	private void FullScreen(MouseEvent event)
    {
		Node source = (Node) event.getSource();
	    Window theStage = source.getScene().getWindow();
	    ((Stage) theStage).setFullScreen(true);
    }
	
	/**
	 * Checking if its a integer
	 * @param event e
	 */
	private void checkInputIsInteger(KeyEvent event)
    {		
		char value = event.getCharacter().charAt(0);
		
		if (!Character.isDigit(value)) {
			event.consume();
		}
    }

	// Event Listener on TextField[#toNumber].onKeyTyped
	@FXML
	public void checkInputTo(KeyEvent event) {
		this.checkInputIsInteger(event);
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void Searching(MouseEvent event) {
		if (this.fromNumber.getText().isEmpty()) {
			System.out.println("Numéro de rue de départ vide");
		}
		else if (this.toNumber.getText().isEmpty()) {
			System.out.println("Numéro de rue d'arrivé vide"); 
		}
		else {
			System.out.println("Search done");
		}
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void Reset(MouseEvent event) {
		System.out.println("Reset clicked");
	}
	
	// Event Listener on TextField[#fromNumber].onKeyTyped
	@FXML
	public void checkInputFrom(KeyEvent event) {
		this.checkInputIsInteger(event);
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void SetCity(MouseEvent event)
	{
		// Si le nom de la ville est pas renseigné
		if (!this.cityName.getText().isEmpty()) {
			System.out.println("Value: " + this.cityName.getText());
		}
		else {
			System.out.println("Empty field");
		}
	}
	
	@FXML
	public void KeyPressCity(KeyEvent event) {

		System.out.println("PRESS:" + this.cityName.getText());
		
		if (!this.cityName.getText().isEmpty()) {
			this.cityButton.setDisable(false);
			
			this.fromNumber.setDisable(false);
			this.fromName.setDisable(false);
			this.toNumber.setDisable(false);
			this.toName.setDisable(false);
			
			this.resetBtn.setDisable(false);
			this.SearchBtn.setDisable(false);
		}
		else {
			this.cityButton.setDisable(true);
			
			this.fromNumber.setDisable(true);
			this.fromName.setDisable(true);
			this.toNumber.setDisable(true);
			this.toName.setDisable(true);
			
			this.fromNumber.setText(null);
			this.fromName.setValue(null);
			this.toNumber.setText(null);
			this.toName.setValue(null);
			
			this.resetBtn.setDisable(true);
			this.SearchBtn.setDisable(true);
		}
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void zoomDown(MouseEvent event) {
		System.out.println("Zoom Down");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void zoomUp(MouseEvent event) {
		System.out.println("Zoom UP");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void right(MouseEvent event) {
		System.out.println("Right Move");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void left(MouseEvent event) {
		System.out.println("Left Move");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void up(MouseEvent event) {
		System.out.println("UP Move");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void down(MouseEvent event) {
		System.out.println("Down Move");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void SetFullscreen(MouseEvent event) {
		this.FullScreen(event);
	}
}
