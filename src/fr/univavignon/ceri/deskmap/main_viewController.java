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
import java.net.Socket;
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
import javafx.scene.canvas.Canvas;
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
	private Canvas canvasMap;
	
	@FXML
	private TextField toNumber;
	
	@FXML
	private TextField cityName;
	
	@FXML
	private ComboBox<Street> fromName;
	
	@FXML
	private ComboBox<Street> toName;
	
	ObservableList<Street> listStreetName = FXCollections.observableArrayList();
	ObservableList<Street> listStreetNameSortedFrom = FXCollections.observableArrayList();
	ObservableList<Street> listStreetNameSortedTo = FXCollections.observableArrayList();
	
	ObservableList<City> listeVille;
	ObservableList<City> listeVilleSorted;
	
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
	
	@FXML
	private TextArea infoArea;
	
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
		
		this.fromName.setItems(this.listStreetNameSortedFrom);
		this.toName.setItems(this.listStreetNameSortedTo);
		
	}
	
	/**
	 * Add informations into the status bar
	 * @param newInfo
	 * @author Yanis Labrak
	 */
	private void addInfoArea(String newInfo) {
		if (this.infoArea.getText().equals("No errors...")) {
			this.infoArea.setText(newInfo);
		} else {
			this.infoArea.setText(this.infoArea.getText() + '\n' + newInfo);
		}
		
		// Auto scroll down
		this.infoArea.selectPositionCaret(this.infoArea.getLength());
		this.infoArea.deselect();
	}
	
	/**
	 * Fetch all the cities from the API inside a file
	 * @param city
	 * @throws Exception
	 * @author Yanis Labrak
	 */
	private void getAllStreet(City city) throws Exception {
		// TODO: Fix nothing back from the query
		String query = this.URL_OSM + "[out:csv(::id,\"name\";false;\"|\")];(area[name=\"" + city.name + "\"];)->.SA;(node[\"highway\"=\"primary\"](area.SA);node[\"highway\"=\"secondary\"](area.SA);node[\"highway\"=\"tertiary\"](area.SA);node[\"highway\"=\"residential\"](area.SA);node[\"highway\"=\"unclassified\"](area.SA);way[\"highway\"](area.SA););out;";
		
		final String STREET_FILE = city.name + ".csv";
		
		this.addInfoArea("Search for the streets of " + city.name);
		
		final Path path = Files.createTempFile(city.name, ".csv");
		File f = new File(STREET_FILE);
		
		if (!f.exists()) {
			this.addInfoArea("File not found !");
			this.laodQueryInFile(query, STREET_FILE);			
			
		}

		// Clear list and load the cities from the file	
		this.listStreetName.clear();
		this.listStreetName.addAll(this.getStreet(city));
		
		this.listStreetNameSortedFrom.clear();
		this.listStreetNameSortedFrom.addAll(this.listStreetName);
		
		this.listStreetNameSortedTo.clear();
		this.listStreetNameSortedTo.addAll(this.listStreetName);
	}
	
	/**
	 * Action trigged when we click on the fullscreen button
	 * @param event évènement
	 * @author Yanis Labrak
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
	 * Load cities from cache or the API
	 * @param query
	 * @param outputName
	 * @author Yanis Labrak
	 */
	private void laodQueryInFile(String query, String outputName) {
		
		this.addInfoArea("Cache creation");
		
		try {
			
			// Make the URL
			URL queryUrl = new URL(query);
			
			// Open the stream
			ReadableByteChannel stream = Channels.newChannel(queryUrl.openStream());
			
			// Create the output file
			FileOutputStream outputFile = new FileOutputStream(outputName);
			
			// Copy the line into the output file
			outputFile.getChannel().transferFrom(stream, 0, Long.MAX_VALUE);
			
			this.addInfoArea("Caching done !");
		    
		} catch (Exception e) {
			this.addInfoArea("Cannot reach servers !");
//			System.err.println(e);
		}
	}
	
	/**
	 * @return The list fill up with the cities
	 * @throws Exception
	 * @author Yanis Labrak
	 */
	private List<City> getCities() throws Exception {		
		List<City> records = new ArrayList<City>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("cities.csv"));
					
		    String line;
		    
		    // While the file have lines
		    while ((line = br.readLine()) != null) {
		    	
		    	// Escape the separator
		        String[] values = line.split("\\|");
		        
		        if (values.length == 4 && !values[0].isEmpty() && !values[1].isEmpty() && !values[2].isEmpty() && !values[3].isEmpty()) {
		        	City city = new City(
		        		values[0],
		        		Double.parseDouble(values[1]),
		        		Double.parseDouble(values[2]),
		        		values[3]
			        );
			        
			        records.add(city);
			        
			        // System.out.println(city);
				}
		        
		    }
		    
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		return records;
	}
	
	/**
	 * @param city
	 * @return The list fill up with the streets
	 * @throws Exception
	 * @author Yanis Labrak
	 */
	private List<Street> getStreet(City city) throws Exception {		
		List<Street> records = new ArrayList<Street>();
		
		try {
			this.addInfoArea("Try to access to the cached streets.");
			
			BufferedReader buffer = new BufferedReader(new FileReader(city.name + ".csv"));
					
		    String line;
		    
		    // While the file have lines
		    while ((line = buffer.readLine()) != null) {
		    	
		    	// Escape the separator
		        String[] values = line.split("\\|");
		        
		        if (values.length == 2 && !values[0].isEmpty()) {
			        	
			        	Street street = new Street(
			        		values[0],
			        		values[1]
				        );
				        
				        records.add(street);
				}
		        
		    }
		    
		    this.addInfoArea("All streets of " + city.name + " readed");
		    
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		return records;
	}
	
	/**
	 * Send the HTTP GET request to the Overpass API
	 * @throws Exception
	 * @author Yanis Labrak
	 */
	private void getAllCityFrance() throws Exception {

		final String query = this.URL_OSM + "[out:csv(::id,::lat,::lon,\"name\";false;\"|\")];(area[name=\"France\"];)->.SA;(node[\"place\"~\"city|town\"](area.SA););out;";
		final String CITIES_FILE = "cities.csv";
		
		System.out.println("Query created: " + query);
		
		final Path path = Files.createTempFile("cities", ".csv");
		File f = new File(CITIES_FILE);
		
		if (!f.exists()) {
			System.out.println("File not found !");
			this.laodQueryInFile(query, CITIES_FILE);	
		}

		// Load the cities from the file		
		this.listeVille = FXCollections.observableArrayList(this.getCities());
		this.listeVilleSorted= FXCollections.observableArrayList(this.listeVille);
	}
	
	/**
	 * Method trigged when the user click on the search button
	 * @param event
	 * @throws Exception
	 * @author Yanis Labrak
	 */
	@FXML
	public void Searching(ActionEvent event) throws Exception {
		String fromNumber = this.fromNumber.getText();
		Boolean fromName = this.fromName.getSelectionModel().isEmpty();
		
		String toNumber = this.toNumber.getText();
		Boolean toName = this.toName.getSelectionModel().isEmpty();
		
		if (fromNumber.isEmpty() || fromName) {
			System.out.println("Invalid starting adress");
		}
		else if (toNumber.isEmpty() || toName) {
			System.out.println("Invalid destination adress");
		}
		else {
			this.addInfoArea("Searching for the best path");
			this.addInfoArea(fromNumber + " " + this.fromName.getSelectionModel().getSelectedItem() + " -> " + toNumber + " " + this.toName.getSelectionModel().getSelectedItem());
			// Fonction affichage de la carte
		}
	}
	
	/**
	 * Method trigged when the user click on the reset button
	 * @param event
	 * @author Yanis Labrak
	 */
	@FXML
	public void Reset(ActionEvent event) {
		this.cityName.clear();
		this.cityButton.setDisable(true);
		
		this.fromNumber.setDisable(true);
		this.fromName.setDisable(true);
		this.toNumber.setDisable(true);
		this.toName.setDisable(true);
		
		this.fromNumber.clear();
		this.fromName.getSelectionModel().clearSelection();
		this.toNumber.clear();
		this.toName.getSelectionModel().clearSelection();
		
		this.SearchBtn.setDisable(true);
		this.resetBtn.setDisable(true);
		
		// TODO: Check here
		this.listStreetName.clear();
		this.listStreetNameSortedFrom.clear();
		this.listStreetNameSortedTo.clear();
		
		this.addInfoArea("Fields reseted");
	}
	
	/**
	 * Disable search until all field wasn't fill up
	 * @author Yanis Labrak
	 */
	private void checkAllFields() {
		if (this.cityName.getText().isEmpty() ||
			this.fromNumber.getText().isEmpty() ||
			this.toNumber.getText().isEmpty() ||
			this.fromName.getSelectionModel().isEmpty() ||
			this.toName.getSelectionModel().isEmpty()
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
	 * @author Yanis Labrak
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
	 * @author Yanis Labrak
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
	 * @author Yanis Labrak
	 */
	@FXML
	public void checkInputFrom(KeyEvent event) {
		this.checkInputIsInteger(event);
		this.checkAllFields();
	}
	
	/**
	 * Check all the input when a comboBox value change
	 * @param event
	 * @author Yanis Labrak
	 */
	@FXML
	public void checkAllComboBox(ActionEvent event) {
		this.checkAllFields();
	}

	/**
	 * Everytime a key is pressed inside the 'TO' field we check if its a integer and enable/disable all buttons
	 * @param event
	 * @author Yanis Labrak
	 */
	@FXML
	public void checkInputTo(KeyEvent event) {
		this.checkInputIsInteger(event);
		this.checkAllFields();
	}
	
	/**
	 * Check if the input is a real city.
	 * @param cityName {@code String}
	 * @return {@code City} if found else {@code null}
	 * @author Yanis Labrak
	 */
	private City isInListCity(String cityName) {
		
		System.out.println("The city: " + cityName);
		
		for (City city : this.listeVille) {
					
			if (!city.name.isEmpty() && city.name.toLowerCase().equals(cityName.toLowerCase())) {
				System.out.println("Inside: " + city.name.toLowerCase());
				return city;
			}
		}
		return null;
		
	}
	
	/**
	 * Method trigged when the 'GO' button beside of the city field is pressed
	 * @param event
	 * @throws Exception
	 * @author Yanis Labrak
	 */
	@FXML
	public void SetCity(ActionEvent event) throws Exception
	{
		// Si le nom de la ville est pas renseigné
		if (!this.cityName.getText().isEmpty()) {
			
			System.out.println("Value: " + this.cityName.getText());
			this.addInfoArea("Search for " + this.cityName.getText());
			
			try {

				City theCity = this.isInListCity(this.cityName.getText());
				
				this.getAllStreet(new City(theCity));
				
				if (!this.cityName.getText().isEmpty()) {
					this.cityButton.setDisable(false);
					this.resetBtn.setDisable(false);
					
					this.fromNumber.setDisable(false);
					this.fromName.setDisable(false);
					this.toNumber.setDisable(false);
					this.toName.setDisable(false);
				}
				else {
					this.Reset(event);
				}
				
			} catch (NullPointerException e) {
				this.addInfoArea("Aucune ville correspondante");
			}
		}
		else {
			System.out.println("Empty field");
		}
	}
	
	/**
	 * Method trigged when the user press a key inside the cityName field
	 * @param event
	 * @throws Exception 
	 * @author Yanis Labrak
	 */
	@FXML
	public void KeyPressCity(KeyEvent event) throws Exception {
		
		// When the ENTER key is pressed
		if (event.getCode() == KeyCode.ENTER) {
			this.SetCity(new ActionEvent());
		}
		
		if (!this.cityName.getText().isEmpty()) {
			this.cityButton.setDisable(false);
			this.resetBtn.setDisable(false);
		}
		else {
			this.cityButton.setDisable(true);
			
			this.fromNumber.setDisable(true);
			this.fromName.setDisable(true);
			this.toNumber.setDisable(true);
			this.toName.setDisable(true);
			
			this.fromNumber.clear();
//			this.fromName.setValue("");
			this.toNumber.clear();
//			this.toName.setValue("");
			
			this.SearchBtn.setDisable(true);
			this.resetBtn.setDisable(true);
		}
	}
	
	/**
	 * @param event
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void zoomDown(ActionEvent event) {
		System.out.println("Zoom Down");
	}
	
	/**
	 * @param event
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void zoomUp(ActionEvent event) {
		System.out.println("Zoom UP");
	}
	
	/**
	 * @param event
	 * @author @author Mohammed Benyamna
	 */
	@FXML
	public void right(ActionEvent event) {
		System.out.println("Right Move");
	}
	
	/**
	 * @param event
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void left(ActionEvent event) {
		System.out.println("Left Move");
	}
	
	/**
	 * @param event
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void up(ActionEvent event) {
		System.out.println("UP Move");
	}
	
	/**
	 * @param event
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void down(ActionEvent event) {
		System.out.println("Down Move");
	}
	
	/**
	 * @param event
	 * @author Zihao Zheng
	 */
	@FXML
	public void SetFullscreen(ActionEvent event) {
		this.FullScreen(event);
	}
	
	/**
	 * @param event
	 * @author Zihao Zheng
	 */
	@FXML
	public void hideLeft(ActionEvent event)
	{
		System.out.println("hideLeft");
		this.splitPane.setDividerPositions(0.0);
	}
	
	/**
	 * @param event
	 * @author Zihao Zheng
	 */
	@FXML
	public void showLeft(ActionEvent event)
	{
		System.out.println("showLeft");
		this.splitPane.setDividerPositions(0.29);
	}
	
	/**
	 * Autocomplete for the from comboBox
	 * @param event
	 * @author Zihao Zheng
	 */
	@FXML
	public void autoCompleteFrom(KeyEvent event) {
		
		// Current value of the comboBox from/to street
		String current_value = this.fromName.getEditor().getText();	
		
		// If the user write nothing
		if (!current_value.isEmpty()) {
			
			// If the last pressed key is TAB
			if (event.getCode() == KeyCode.CONTROL) {
				
				if (this.listStreetNameSortedFrom.size() > 0) {
					// Set the current value to the first element of the list which contain the current word
					this.fromName.setValue(this.listStreetNameSortedFrom.get(0));
				}
				
				// Dont read this char
				event.consume();
				
			} else {

				this.listStreetNameSortedFrom.clear();
				
				for (Street street : this.listStreetName) {					
					if (street.name.toLowerCase().contains(current_value)) {
						this.listStreetNameSortedFrom.add(street);
					}
				}
				
				// If no result
				if (this.listStreetNameSortedFrom.size() <= 0) {
					this.addInfoArea("Unknown start street");
				}
				
//				System.out.println(this.listStreetNameSortedFrom);
				
			}
			
		}
		else {
			this.listStreetNameSortedFrom.setAll(this.listStreetName);
//			System.out.println(this.listStreetNameSortedFrom);
		}
	}
	
	/**
	 * Autocomplete for comboBox
	 * @param event
	 * @author Zihao Zheng
	 */
	@FXML
	public void autoCompleteTo(KeyEvent event) {
		
		// Current value of the comboBox from/to street
		String current_value = this.toName.getEditor().getText();	
		
		// If the user write nothing
		if (!current_value.isEmpty()) {
			
			// If the last pressed key is TAB
			if (event.getCode() == KeyCode.CONTROL) {
				
				if (this.listStreetNameSortedTo.size() > 0) {
					// Set the current value to the first element of the list which contain the current word
					this.toName.setValue(this.listStreetNameSortedTo.get(0));
				}
				
				// Destroy the entered key stroke
				event.consume();
				
			} else {

				this.listStreetNameSortedTo.clear();
				
				for (Street street : this.listStreetName) {
					if (street.name.toLowerCase().contains(current_value)) {
						this.listStreetNameSortedTo.add(street);
					}
				}
				
				// If no result
				if (this.listStreetNameSortedTo.size() <= 0) {
					this.addInfoArea("Unknown destination street");
				}
				
			}
			
		}
		else {
			this.listStreetNameSortedTo.setAll(this.listStreetName);
		}
	}
}
