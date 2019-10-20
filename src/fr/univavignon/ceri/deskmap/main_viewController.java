package fr.univavignon.ceri.deskmap;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class main_viewController implements Initializable {
	@FXML
	private TextField toNumber;
	
	@FXML
	private TextField cityName;
	
	@FXML
	private ComboBox fromName;
	
	ObservableList<String> listCityName = FXCollections.observableArrayList("Jean", "Guy", "Pierre");	
	ObservableList<String> listCityNameSortedFrom = FXCollections.observableArrayList(this.listCityName);
	ObservableList<String> listCityNameSortedTo = FXCollections.observableArrayList(this.listCityName);
	
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
	private Button fullscreen;
	
	@FXML
	private Button SearchBtn;
	
	@FXML
	private Button hideLeft;
	
	@FXML
	private Button showLeft;
	
	@FXML
	private SplitPane splitPane;
	
	/**
	 * URL de Overpass API
	 */
	public static String URL_OSM = "https://lz4.overpass-api.de/api/interpreter?data=";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Initalise");
		
		try {
			this.getAllCityFrance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.fromName.setItems(listCityNameSortedFrom);
		this.toName.setItems(listCityNameSortedTo);
		
	}
	
	private void getStreet(String city) throws Exception {
		// String query = this.URL_OSM + "[out:json][timeout:50];{{geocodeArea:" + city + "}}->.SA;(node[\"highway\"=\"primary\"](area.SA);node[\"highway\"=\"secondary\"](area.SA);node[\"highway\"=\"tertiary\"](area.SA);node[\"highway\"=\"residential\"](area.SA);node[\"highway\"=\"unclassified\"](area.SA); way[\"highway\"](area.SA););out;";
		String query = this.URL_OSM + "[out:json][timeout:50]; area(3600102478)->.SA; ( node[\"highway\"=\"primary\"](area.SA); node[\"highway\"=\"secondary\"](area.SA); node[\"highway\"=\"tertiary\"](area.SA); node[\"highway\"=\"residential\"](area.SA); node[\"highway\"=\"unclassified\"](area.SA); way[\"highway\"](area.SA); ); out;";
		URL obj = new URL(query);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		// con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		
		System.out.println("\nSending 'GET' request to URL : " + query);
		System.out.println("Response Code : " + responseCode);

		// Open a socket between the endpoint and the app
		// BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		// String inputLine;
		// StringBuffer response = new StringBuffer();

		// Add every single line of the file to the response
		// while ((inputLine = in.readLine()) != null) {
		// response.append(inputLine);
		// }
		// in.close();

		// Display result
		// System.out.println(response.toString());
	}
	
	/**
	 * Action trigged when we click on the fullscreen button
	 * @param event évènement
	 */
	private void FullScreen(ActionEvent event)
    {
	    Window theStage = ((javafx.scene.Node) event.getSource()).getScene().getWindow();
	    
	    // Switch between FULL / NROMAL mode
	    if (this.fullscreen.getText().equals("FULL")) {		    
		    ((Stage) theStage).setFullScreen(true);	
		    this.fullscreen.setText("NORMAL");		
		}
	    else {
		    ((Stage) theStage).setFullScreen(false);
		    this.fullscreen.setText("FULL");
		}
    }
	
	/**
	 * If cities are not cached get them
	 * @param query
	 * @param outputName
	 */
	private void getCitiesFile(String query, String outputName) {
		
		System.out.println("Création du fichier...");
		
		try {
			
			// Make the URL
			URL queryUrl = new URL(query);
			
			// Open the stream
			ReadableByteChannel rbc = Channels.newChannel(queryUrl.openStream());
			
			// Create the output file
			FileOutputStream fos = new FileOutputStream(outputName);
			
			// Copy the line into the output file
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			System.out.println("Fichier créer.");
		    
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private List<City> getCities() throws Exception {		
		List<City> records = new ArrayList<City>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("cities.csv"));
					
		    String line;
		    
		    // While the file have lines
		    while ((line = br.readLine()) != null) {
		    	
		    	// Escape the separator
		        String[] values = line.split("\\|");
		        
		        City city = new City(
	        		Integer.parseInt(values[0]),
	        		Double.parseDouble(values[1]),
	        		Double.parseDouble(values[2]),
	        		values[3]
		        );
		        
		        records.add(city);
		        
		        // System.out.println(city);
		        
		    }
		    
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		return records;
	}
	
	/**
	 * Send the HTTP GET request to the Overpass API
	 * @throws Exception
	 */
	private void getAllCityFrance() throws Exception {

		final String query = this.URL_OSM + "[out:csv(::id,::lat,::lon,\"name\";false;\"|\")][timeout:25];(area[name=\"France\"];)->.SA;(node[\"place\"~\"city|town\"](area.SA););out;";
		final String CITIES_FILE = "cities.csv";
		
		System.out.println("Query created: " + query);
		
		final Path path = Files.createTempFile("cities", ".csv");
		File f = new File(CITIES_FILE);
		
		if (!f.exists()) {
			System.out.println("Fichier non trouvé.");
			this.getCitiesFile(query, CITIES_FILE);	
		}

		List<City> listeVille = this.getCities();
		
		System.out.println("Name:" + listeVille.get(0).name);

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
			// Fonction affichage de la carte
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
	 * 
	 * Ajouter la prise en charge des combobox vides
	 */
	private void checkAllFields() {
		if (this.fromNumber.getText().isEmpty() || 
			this.toNumber.getText().isEmpty() || 
			this.cityName.getText().isEmpty()
		) {
			this.SearchBtn.setDisable(true);
		}
		else {			
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
		
		if (value != ' ' && value != '-' && !Character.isLetter(value)) {
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
	 * @throws Exception 
	 */
	@FXML
	public void SetCity(ActionEvent event) throws Exception
	{
		// Si le nom de la ville est pas renseigné
		if (!this.cityName.getText().isEmpty()) {
			System.out.println("Value: " + this.cityName.getText());
			this.getStreet(this.cityName.getText());
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

		System.out.println("CITY KEY PRESSED:" + this.cityName.getText());
		
		if (!this.cityName.getText().isEmpty()) {

			this.resetBtn.setDisable(false);
			
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
			
			this.fromNumber.clear();
			this.fromName.setValue("");
			this.toNumber.clear();
			this.toName.setValue("");
			
			this.SearchBtn.setDisable(true);
			this.resetBtn.setDisable(true);
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
	
	/**
	 * Autocomplete for comboBox
	 * @param event
	 */
	@FXML
	public void autoCompleteFrom(KeyEvent event) {
		
		// Current value of the comboBox from/to street
		String current_value = this.fromName.getEditor().getText();	
		
		// If the user write nothing
		if (!current_value.isEmpty()) {
			
			// If the last pressed key is TAB
			if (event.getCode() == KeyCode.CONTROL) {
				
				if (this.listCityNameSortedFrom.size() > 0) {
					// Set the current value to the first element of the list which contain the current word
					this.fromName.setValue(this.listCityNameSortedFrom.get(0));
				}
				
				// Dont read this char
				event.consume();
				
			} else {

				this.listCityNameSortedFrom.clear();
				
				for (String street : this.listCityName) {					
					if (street.toLowerCase().contains(current_value)) {
						this.listCityNameSortedFrom.add(street.toLowerCase());
					}
				}
				
				// If no result
				if (this.listCityNameSortedFrom.size() <= 0) {
					this.fromName.getStyleClass().add("warning");
					System.out.println("Warning");
				}
				
				System.out.println(this.listCityNameSortedFrom);
				
			}
			
		}
		else {
			this.listCityNameSortedFrom.setAll(this.listCityName);
			System.out.println(this.listCityNameSortedFrom);
		}
	}
	
	/**
	 * Autocomplete for comboBox
	 * @param event
	 */
	@FXML
	public void autoCompleteTo(KeyEvent event) {
		
		// Current value of the comboBox from/to street
		String current_value = this.toName.getEditor().getText();	
		
		// If the user write nothing
		if (!current_value.isEmpty()) {
			
			// If the last pressed key is TAB
			if (event.getCode() == KeyCode.CONTROL) {
				
				if (this.listCityNameSortedTo.size() > 0) {
					// Set the current value to the first element of the list which contain the current word
					this.toName.setValue(this.listCityNameSortedTo.get(0));
				}
				
				// Dont read this char
				event.consume();
				
			} else {

				this.listCityNameSortedTo.clear();
				
				for (String street : this.listCityName) {
					if (street.toLowerCase().contains(current_value)) {
						this.listCityNameSortedTo.add(street.toLowerCase());
					}
				}
				
				// If no result
				if (this.listCityNameSortedTo.size() <= 0) {
					this.toName.getStyleClass().add("warning");
					System.out.println("Warning");
				}
				
				System.out.println(this.listCityNameSortedTo);
				
			}
			
		}
		else {
			this.listCityNameSortedTo.setAll(this.listCityName);
			System.out.println(this.listCityNameSortedTo);
		}
	}
}
