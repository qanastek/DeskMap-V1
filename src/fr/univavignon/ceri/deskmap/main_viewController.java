package fr.univavignon.ceri.deskmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


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

import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The controller of the FXML file
 */
public class main_viewController implements Initializable {
	
	/**
	 * Map canvas
	 */
	@FXML
	private Canvas canvasMap;
	
	/**
	 * Name of the city
	 */
	@FXML
	private TextField cityName;
	
	/**
	 * Start house number
	 */
	@FXML
	private TextField fromNumber;
	
	/**
	 * Start street name
	 */
	@FXML
	private ComboBox<Street> fromName;
	
	/**
	 * Destination house number
	 */
	@FXML
	private TextField toNumber;
	
	/**
	 * Destination street name
	 */
	@FXML
	private ComboBox<Street> toName;
	
	/**
	 * Path
	 */
	@FXML
	private TextArea mapPath;
	
	/**
	 * Zoom slider
	 */
	@FXML
	private Slider slider;
	
	/**
	 * Search street for the city
	 */
	@FXML
	private Button cityButton;
	
	/**
	 * Reset all fields
	 */
	@FXML
	private Button resetBtn;
	
	/**
	 * Set fullscreen / windowed mode
	 */
	@FXML
	private Button fullscreen;
	
	/**
	 * Search the path
	 */
	@FXML
	private Button SearchBtn;
	
	/**
	 * Hide the left panel
	 */
	@FXML
	private Button hideLeft;
	
	/**
	 * Show the left panel
	 */
	@FXML
	private Button showLeft;
	
	/**
	 * The horizontal {@code splitPane}
	 */
	@FXML
	private SplitPane splitPane;
	
	/**
	 * Bottom status bar
	 */
	@FXML
	private TextArea statusBar;
	
	/**
	 * List of {@code City}
	 */
	ObservableList<City> listeVille;
	
	/**
	 * The observable variable for the text field {@code cityName}
	 */
	ObservableList<City> listeVilleSorted;
	
	/**
	 * The default list of streets for a specific city
	 */
	ObservableList<Street> listStreetName = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code FROM} comboBox
	 */
	ObservableList<Street> listStreetNameSortedFrom = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code TO} comboBox
	 */
	ObservableList<Street> listStreetNameSortedTo = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Initialize");
		
		try {
			// Fetch all the french cities
			this.getAllCity("France");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assign both lists to their comboBox's
		this.fromName.setItems(this.listStreetNameSortedFrom);
		this.toName.setItems(this.listStreetNameSortedTo);
		
	}
	
	/**
	 * Add informations into the status bar {@code textArea}
	 * @param newLine {@code String} The line to add
	 * @author Yanis Labrak
	 */
	private void addStateBar(String newLine) {
		
		// If its empty
		if (this.statusBar.getText().equals("No errors...")) {
			this.statusBar.setText(newLine);
		} else {
			// Add to the next line
			this.statusBar.setText(this.statusBar.getText() + '\n' + newLine);
		}
		
		// Auto scroll down
		this.statusBar.selectPositionCaret(this.statusBar.getLength());
		this.statusBar.deselect();
	}
	
	/**
	 * Fetch all the cities from the API inside a file
	 * @param city {@code City} City from where we will get all the streets
	 * @throws Exception Throw a exception if the file cannot be create
	 * @author Yanis Labrak
	 */
	private void getAllStreet(City city) throws Exception {
		
		String query = Launcher.URL_OSM + "[out:csv(::id,\"name\";false;\"|\")];(area[name=\"" + city.name + "\"];)->.SA;(node[\"highway\"=\"primary\"](area.SA);node[\"highway\"=\"secondary\"](area.SA);node[\"highway\"=\"tertiary\"](area.SA);node[\"highway\"=\"residential\"](area.SA);node[\"highway\"=\"unclassified\"](area.SA);way[\"highway\"](area.SA););out;";
		
		final String STREET_FILE = city.name + ".csv";
		
		this.addStateBar("Search for the streets of " + city.name);
		
		File f = new File(STREET_FILE);
		
		if (!f.exists()) {
			this.addStateBar("File not found !");
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
	 * @param event {@code no informations}
	 * @author Yanis Labrak
	 */
	private void FullScreen(ActionEvent event)
    {
		// Get the parent stage
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
	 * Load cities from cache or API
	 * @param query {@code String} Query to send to the OSM API
	 * @param outputName {@code String} name of the output file
	 * @author Yanis Labrak
	 */
	private void laodQueryInFile(String query, String outputName) {
		
		this.addStateBar("Cache creation");
		
		try {
			
			// Make the URL
			URL queryUrl = new URL(query);
			
			// Open the stream
			ReadableByteChannel stream = Channels.newChannel(queryUrl.openStream());
			
			// Create the output file
			FileOutputStream outputFile = new FileOutputStream(outputName);
			
			// Copy the line into the output file
			outputFile.getChannel().transferFrom(stream, 0, Long.MAX_VALUE);
			
			outputFile.close();
			
			this.addStateBar("Caching done !");
		    
		} catch (Exception e) {
			this.addStateBar("Cannot reach servers !");
//			System.err.println(e);
		}
	}
	
	/**
	 * @throws Exception Throw an exception when the file cannot be read
	 * @return {@code List<City>} A list fill up with the cities
	 * @author Yanis Labrak
	 */
	private List<City> getCities() throws Exception {		
		List<City> records = new ArrayList<City>();
		
		try {
			
			// Open a stream for the file which contain all the cities names
			BufferedReader br = new BufferedReader(new FileReader("cities.csv"));
					
		    String line;
		    
		    // For each lines
		    while ((line = br.readLine()) != null) {
		    	
		    	// Escape the separator
		        String[] values = line.split("\\|");
		        
		        // To be readed a city need to be fully complete
		        if (values.length == 4 && !values[0].isEmpty() && !values[1].isEmpty() && !values[2].isEmpty() && !values[3].isEmpty()) {
		        	
		        	City city = new City(
		        		values[0],
		        		Double.parseDouble(values[1]),
		        		Double.parseDouble(values[2]),
		        		values[3]
			        );
			        
			        records.add(city);
				}
		        
		    }
		    
		    // Close the stream
		    br.close();
		    
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		// Return all the cities
		return records;
	}
	
	/**
	 * Load streets from cache or API
	 * @param city {@code City} City object from where we want the streets
	 * @return The list fill up with the streets
	 * @throws Exception Throw a exception if the file which contain the streets isn't find
	 * @author Yanis Labrak
	 */
	private List<Street> getStreet(City city) throws Exception {		
		List<Street> records = new ArrayList<Street>();
		
		try {
			this.addStateBar("Try to access to the cached streets.");
			
			// Open a stream for the file which contain all the streets
			BufferedReader buffer = new BufferedReader(new FileReader(city.name + ".csv"));
					
		    String line;
		    
		    // While the file have lines
		    while ((line = buffer.readLine()) != null) {
		    	
		    	// Escape the separator
		        String[] values = line.split("\\|");
		        
		        // If all the fields isn't null
		        if (values.length == 2 && !values[0].isEmpty() && !values[1].isEmpty()) {
			        	
			        	Street street = new Street(
			        		values[0],
			        		values[1]
				        );
				        
				        records.add(street);
				}
		        
		    }
		    
		    // Close the stream
		    buffer.close();
		    
		    this.addStateBar("All streets of " + city.name + " readed");
		    
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		// return all the streets of the city
		return records;
	}
	
	/**
	 * Send the HTTP GET request to the Overpass API
	 * Get all the city from a specific country
	 * @param country {@code String} Country name
	 * @throws Exception {@code no informations}
	 * @author Yanis Labrak
	 */
	private void getAllCity(String country) throws Exception {

		final String query = Launcher.URL_OSM + "[out:csv(::id,::lat,::lon,\"name\";false;\"|\")];(area[name=\"" + country + "\"];)->.SA;(node[\"place\"~\"city|town\"](area.SA););out;";
		
		// TODO: My new OSM query builder here
		
		final String CITIES_FILE = "cities.csv";
		
		System.out.println("Query created: " + query);
		
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
	 * @param event {@code no informations}
	 * @throws Exception {@code no informations}
	 * @author Yanis Labrak
	 */
	@FXML
	public void Searching(ActionEvent event) throws Exception {
		
		// Get fields string values
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
			this.addStateBar("Searching for the best path");
			this.addStateBar(fromNumber + " " + this.fromName.getSelectionModel().getSelectedItem() + " -> " + toNumber + " " + this.toName.getSelectionModel().getSelectedItem());
			// TODO: Map print here
		}
	}
	
	/**
	 * Method trigged when the user click on the reset button
	 * @param event {@code ActionEvent}
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
		
		this.fromName.getSelectionModel().clearSelection();
		this.toName.getSelectionModel().clearSelection();
		
		this.toNumber.clear();
		this.fromNumber.clear();
		
		this.SearchBtn.setDisable(true);
		this.resetBtn.setDisable(true);
		
		this.listStreetName.clear();
		this.listStreetNameSortedFrom.clear();
		this.listStreetNameSortedTo.clear();
		
		this.addStateBar("Fields reseted");
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
	 * Check if the input is a integer
	 * @param event {@code KeyEvent} The input character
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
	 * Check if the input is a letter
	 * @param event {@code KeyEvent} The input character
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
	 * Check if the input key is a integer
	 * @param event {@code keyEvent}
	 * @author Yanis Labrak
	 */
	@FXML
	public void checkInputFrom(KeyEvent event) {
		
		// Check if the input key is a integer
		this.checkInputIsInteger(event);
		
		// Update the status of the reset / search button
		this.checkAllFields();
	}

	/**
	 * Check if the input key is a integer
	 * @param event {@code KeyEvent}
	 * @author Yanis Labrak
	 */
	@FXML
	public void checkInputTo(KeyEvent event) {
		
		// Check if the input key is a integer
		this.checkInputIsInteger(event);
		
		// Update the status of the reset / search button
		this.checkAllFields();
	}
	
	/**
	 * Check all the input when a comboBox value change
	 * @param event {@code ActionEvent}
	 * @author Yanis Labrak
	 */
	@FXML
	public void checkAllComboBox(ActionEvent event) {
		this.checkAllFields();
	}
	
	/**
	 * Check if the city is a real one.
	 * @param cityName {@code String} Name of the city
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
	 * Fetch the street for this city
	 * @param event {@code ActionEvent}
	 * @throws Exception Throw a exception when the city doesn't exists
	 * @author Yanis Labrak
	 */
	@FXML
	public void SetCity(ActionEvent event) throws Exception
	{
		// If the city name isn't known
		if (!this.cityName.getText().isEmpty()) {
			
			this.addStateBar("Search for " + this.cityName.getText());
			
			try {

				// Check if the city exists
				City theCity = this.isInListCity(this.cityName.getText());
				
				// Get all the streets of this city
				this.getAllStreet(new City(theCity));
				
				if (!this.cityName.getText().isEmpty()) {
					
					// Reset the fields
					this.fromNumber.clear();
					this.fromName.getSelectionModel().clearSelection();
					this.toNumber.clear();
					this.toName.getSelectionModel().clearSelection();
					
					// Reset the button states
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
				this.addStateBar("Aucune ville correspondante");
			}
		}
		else {
			System.out.println("Empty field");
		}
	}
	
	/**
	 * When a key is pressed inside {@code cityName}
	 * @param event {@code KeyEvent}
	 * @throws Exception Throw a exception when the city doesn't exists
	 * @author Yanis Labrak
	 */
	@FXML
	public void KeyPressCity(KeyEvent event) throws Exception {
		
		// When the ENTER key is pressed
		if (event.getCode() == KeyCode.ENTER) {
			this.SetCity(new ActionEvent());
		}
		
		// If the field isn't empty
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
			this.fromName.getSelectionModel().clearSelection();
			this.toNumber.clear();
			this.toName.getSelectionModel().clearSelection();
			
			this.SearchBtn.setDisable(true);
			this.resetBtn.setDisable(true);
		}
	}
	
	/**
	 * Zoom in the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void zoomUp(ActionEvent event) {
		System.out.println("Zoom UP");
	}
	
	/**
	 * Zoom out the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void zoomDown(ActionEvent event) {
		System.out.println("Zoom Down");
	}
	
	/**
	 * Move to the left in the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void left(ActionEvent event) {
		System.out.println("Left Move");
	}
	
	/**
	 * Move to the right in the map
	 * @param event {@code ActionEvent}
	 * @author @author Mohammed Benyamna
	 */
	@FXML
	public void right(ActionEvent event) {
		System.out.println("Right Move");
	}
	
	/**
	 * Move to the top in the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void up(ActionEvent event) {
		System.out.println("UP Move");
	}
	
	/**
	 * Move to the bottom in the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void down(ActionEvent event) {
		System.out.println("Down Move");
	}
	
	/**
	 * Set the window to fullscreen / windowed mode
	 * @param event {@code ActionEvent}
	 * @author Zihao Zheng
	 */
	@FXML
	public void SetFullscreen(ActionEvent event) {
		this.FullScreen(event);
	}
	
	/**
	 * Hide the left panel
	 * @param event {@code ActionEvent}
	 * @author Zihao Zheng
	 */
	@FXML
	public void hideLeft(ActionEvent event)
	{
		System.out.println("hideLeft");
		this.splitPane.setDividerPositions(0.0);
	}
	
	/**
	 * Show the left panel
	 * @param event {@code ActionEvent}
	 * @author Zihao Zheng
	 */
	@FXML
	public void showLeft(ActionEvent event)
	{
		System.out.println("showLeft");
		this.splitPane.setDividerPositions(0.29);
	}
	
	/**
	 * Auto-complete for the {@code FROM} comboBox
	 * @param event {@code KeyEvent}
	 * @author Zihao Zheng
	 */
	@FXML
	public void autoCompleteFrom(KeyEvent event) {
		
		// Current value of the comboBox {@code FROM} street
		String current_value = this.fromName.getEditor().getText();	
		
		// If the user write nothing
		if (!current_value.isEmpty()) {
			
			// If the last pressed key is TAB
			if (event.getCode() == KeyCode.CONTROL) {
				
				if (this.listStreetNameSortedFrom.size() > 0) {
					// Set the current value to the first element of the list which contain the current word
					this.fromName.setValue(this.listStreetNameSortedFrom.get(0));
				}
				
				// Don't read this char
				event.consume();
				
			} else {

				this.listStreetNameSortedFrom.clear();
				
				// Take the {@code street} which contain inside it name the current input
				for (Street street : this.listStreetName) {					
					if (street.name.toLowerCase().contains(current_value)) {
						this.listStreetNameSortedFrom.add(street);
					}
				}
				
				// If no result
				if (this.listStreetNameSortedFrom.size() <= 0) {
					this.addStateBar("Unknown start street");
				}
				
			}			
		}
		else {
			this.listStreetNameSortedFrom.setAll(this.listStreetName);
		}
	}
	
	/**
	 * Auto-complete for the TO comboBox
	 * @param event {@code KeyEvent}
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
				
				// Take the street which contain inside it name the current input
				for (Street street : this.listStreetName) {
					if (street.name.toLowerCase().contains(current_value)) {
						this.listStreetNameSortedTo.add(street);
					}
				}
				
				// If no result
				if (this.listStreetNameSortedTo.size() <= 0) {
					this.addStateBar("Unknown destination street");
				}
				
			}
			
		}
		else {
			this.listStreetNameSortedTo.setAll(this.listStreetName);
		}
	}
}
