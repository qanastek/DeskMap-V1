package fr.univavignon.ceri.deskmap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
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
	@FXML
	private Button hideLeft;
	@FXML
	private Button showLeft;
	@FXML
	private SplitPane splitPane;
	
	/**
	 * Action trigged when we click on the fullscreen button
	 * @param event évènement
	 */
	private void FullScreen(ActionEvent event)
    {
		Node source = (Node) event.getSource();
	    Window theStage = source.getScene().getWindow();
	    ((Stage) theStage).setFullScreen(true);
    }
	
	/**
	 * Send the HTTP GET request to the Overpass API
	 * @throws Exception
	 */
	private void sendGet() throws Exception {

		String url = "https://lz4.overpass-api.de/api/interpreter?data=[out:json][timeout:25];(area[name=\"Avignon\"];)->.SA;(node[\"tourism\"=\"museum\"](area.SA);way[\"tourism\"=\"museum\"](area.SA););out;";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		// con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		// Open a socket between the endpoint and the app
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		// Add every single line of the file to the response
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// Display result
		System.out.println(response.toString());

	}
	
	/**
	 * Method trigged when the user click on the search button
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void Searching(ActionEvent event) throws Exception {
		if (this.fromNumber.getText().isEmpty()) {
			System.out.println("Numéro de rue de départ vide");
		}
		else if (this.toNumber.getText().isEmpty()) {
			System.out.println("Numéro de rue d'arrivé vide"); 
		}
		else {
			System.out.println("Search done. Waiting for the response...");
			this.sendGet();
		}
	}
	
	/**
	 * Method trigged when the user click on the reset button
	 * @param event
	 */
	@FXML
	public void Reset(ActionEvent event) {
		System.out.println("Reset clicked");
	}
	
	/**
	 * The method which check if all the field are filled.
	 * If not, disable all the buttons
	 * Else, enable them
	 */
	private void checkAllFields() {
		if (this.fromNumber.getText().isEmpty() || 
			this.toNumber.getText().isEmpty() || 
			this.cityName.getText().isEmpty()
		) {
			this.resetBtn.setDisable(true);
			this.SearchBtn.setDisable(true);
		}
		else {			
			this.resetBtn.setDisable(false);
			this.SearchBtn.setDisable(false);
		}
	}

	/**
	 * Check if the event is a integer
	 * @param event e
	 */
	private void checkInputIsInteger(KeyEvent event)
    {		
		char value = event.getCharacter().charAt(0);
		
		if (!Character.isDigit(value)) {
			event.consume();
		}
    }
	
	/**
	 * Check if the event is a letter
	 * @param event e
	 */
	@FXML
	public void checkInputIsLetter(KeyEvent event)
    {		
		char value = event.getCharacter().charAt(0);
		
		if (!Character.isLetter(value)) {
			event.consume();
		}
    }
	
	/**
	 * Everytime a key is pressed inside the 'FROM' field we check if its a integer and enable/disable all buttons
	 * @param event
	 */
	@FXML
	public void checkInputFrom(KeyEvent event) {
		this.checkInputIsInteger(event);
		this.checkAllFields();
	}

	/**
	 * Everytime a key is pressed inside the 'TO' field we check if its a integer and enable/disable all buttons
	 * @param event
	 */
	@FXML
	public void checkInputTo(KeyEvent event) {
		this.checkInputIsInteger(event);
		this.checkAllFields();
	}
	
	/**
	 * Method trigged when the 'GO' button beside of the city field is pressed
	 * @param event
	 */
	@FXML
	public void SetCity(ActionEvent event)
	{
		// Si le nom de la ville est pas renseigné
		if (!this.cityName.getText().isEmpty()) {
			System.out.println("Value: " + this.cityName.getText());
		}
		else {
			System.out.println("Empty field");
		}
	}
	
	/**
	 * Method trigged when the user press a key inside the cityName field
	 * @param event
	 */
	@FXML
	public void KeyPressCity(KeyEvent event) {

		System.out.println("PRESS:" + this.cityName.getText());
		
		if (!this.cityName.getText().isEmpty()) {
			this.cityButton.setDisable(false);
			
			this.fromNumber.setDisable(false);
			this.fromName.setDisable(false);
			this.toNumber.setDisable(false);
			this.toName.setDisable(false);
		}
		else {
			this.cityButton.setDisable(true);
			
			this.fromNumber.setDisable(true);
			this.fromName.setDisable(true);
			this.toNumber.setDisable(true);
			this.toName.setDisable(true);
			
			this.fromNumber.setText("");
			this.fromName.setValue("");
			this.toNumber.setText("");
			this.toName.setValue("");
			
			this.resetBtn.setDisable(true);
			this.SearchBtn.setDisable(true);
		}
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void zoomDown(ActionEvent event) {
		System.out.println("Zoom Down");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void zoomUp(ActionEvent event) {
		System.out.println("Zoom UP");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void right(ActionEvent event) {
		System.out.println("Right Move");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void left(ActionEvent event) {
		System.out.println("Left Move");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void up(ActionEvent event) {
		System.out.println("UP Move");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void down(ActionEvent event) {
		System.out.println("Down Move");
	}
	
	// Event Listener on Button.onMouseClicked
	@FXML
	public void SetFullscreen(ActionEvent event) {
		this.FullScreen(event);
	}
	
	@FXML
	public void hideLeft(ActionEvent event)
	{
		System.out.println("hideLeft");
		this.splitPane.setDividerPositions(0.0);
	}
	
	@FXML
	public void showLeft(ActionEvent event)
	{
		System.out.println("showLeft");
		this.splitPane.setDividerPositions(0.29);
	}
}
