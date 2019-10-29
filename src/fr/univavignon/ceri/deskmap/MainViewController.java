package fr.univavignon.ceri.deskmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import fr.univavignon.ceri.deskmap.geopoint.City;
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
public class MainViewController implements Initializable {
	
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
	private Button searchBtn;
	
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
	private ObservableList<City> listCity;
	
	/**
	 * The observable variable for the text field {@code cityName}
	 */
	private ObservableList<City> listCitySorted;
	
	/**
	 * The default list of streets for a specific city
	 */
	private ObservableList<Street> listStreetName = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code FROM} comboBox
	 */
	private ObservableList<Street> listStreetNameSortedFrom = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code TO} comboBox
	 */
	private ObservableList<Street> listStreetNameSortedTo = FXCollections.observableArrayList();
	
	/**
	 * The map instance which will contain all the objects to display
	 */
	public static Map map = new Map();
	
	/**
	 * Automatically started when the program start
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Initialize");
		
		try {
			// Fetch all the French cities
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
	 * Send the HTTP GET request to the Overpass API
	 * Get all the city from a specific country
	 * @param country {@code String} Country name
	 * @throws Exception {@code no informations}
	 * @author Yanis Labrak
	 */
	private void getAllCity(String country) throws Exception {

		OSM queryOverpass = new OSM();
		
		queryOverpass.output("csv", "::id,::lat,::lon,name", false, "|");
		queryOverpass.area(country);
		queryOverpass.start();
		queryOverpass.node("place", "city");
		queryOverpass.node("place", "town");
		queryOverpass.way("place");
		queryOverpass.out();
		
		String query = queryOverpass.toString();
		
		final String CITIES_FILE = "cities.csv";
		
		System.out.println("Query created: " + query);
		
		File f = new File(CITIES_FILE);
		
		if (!f.exists()) {
			System.out.println("File not found !");
			this.laodQueryInFile(query, CITIES_FILE);	
		}

		// Load the cities from the file		
		this.listCity = FXCollections.observableArrayList(this.getCities());
		this.listCitySorted= FXCollections.observableArrayList(this.listCity);
	}
	
	/**
	 * Fetch all the cities from the API inside a file
	 * @param city {@code City} From where we will get all the streets
	 * @throws Exception Throw a exception if the file cannot be create
	 * @author Yanis Labrak
	 */
	private void getAllStreet(City city) throws Exception {
		
		OSM queryOverpass = new OSM();
		
		queryOverpass.output("csv", "::id,name", false, "|");
		queryOverpass.area(city.name);
		queryOverpass.start();
		queryOverpass.node("highway", "primary");
		queryOverpass.node("highway", "secondary");
		queryOverpass.node("highway", "tertiary");
		queryOverpass.node("highway", "residential");
		queryOverpass.node("highway", "unclassified");
		queryOverpass.way("highway");
		queryOverpass.out();
		
		String query = queryOverpass.toString();
		
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
		System.out.println("Cache creation");
		
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
			System.out.println("Caching done !");
		    
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
			BufferedReader buffer = new BufferedReader(new FileReader("cities.csv"));
					
		    String line;
		    
		    // For each lines
		    while ((line = buffer.readLine()) != null) {
		    	
		    	// Escape the separator
		        String[] values = line.split("\\|");
		        
		        // The City need to be fully complete to be insert
		        if (values.length == 4 &&
		        	!values[0].isEmpty() &&
		        	!values[1].isEmpty() &&
		        	!values[2].isEmpty() &&
		        	!values[3].isEmpty()) {
		        	
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
		    buffer.close();
		    
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
		        
		        try {
		        	
		        	/**
		        	 * Check if the current Street is already inside the List
		        	 * throw a exception if it's in
		        	 */
		        	Street isAlreadyPresent = records.stream()
			        		.filter(s -> values[1].toLowerCase().equals(s.name.toLowerCase()))
			        		.findFirst()
			        		.orElse(null);

			        // If all the fields isn't null
			        if (values.length == 2 && !values[0].isEmpty() && !values[1].isEmpty() && isAlreadyPresent == null) {
				        	
				        	Street street = new Street(
				        		values[0],
				        		values[1]
					        );
					        
					        records.add(street);
					}
			        
				} catch (Exception e) {
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
	 * Method trigged when the user click on the search button
	 * @param event {@code no informations}
	 * @throws Exception {@code no informations}
	 * @author Yanis Labrak
	 */
	@FXML
	public void searching(ActionEvent event) throws Exception {
		
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
		}
	}
	
	/**
	 * Return the coordinates of a city
	 * @param city {@code String} Name of the city
	 * @return {@code String} Coordinates
	 */
	private String getCityCoordinates(String city) {
		try {
			
			// Open a stream for the file which contain all the streets
			BufferedReader bfr = new BufferedReader(new FileReader("cities.csv"));
			
			String line;
			
			// While the file have lines
			while ((line = bfr.readLine()) != null) {
				
				String[] values = line.split("\\|");
		        
		        // The City need to be fully complete to be processed
		        if (values.length == 4 &&
		        	!values[0].isEmpty() &&
		        	!values[1].isEmpty() &&
		        	!values[2].isEmpty() &&
		        	!values[3].isEmpty()) {
					
		        	// When we found the city
					if (values[3].toLowerCase().equals(city.toLowerCase())) {
						bfr.close();
						return values[1] + "|" + values[2];
						
					}
				}		        
			}
			
			bfr.close();
			
			return null;
			
		} catch(Exception e) {
			return null;
		}		
	}
	
	/**
	 * Build a Overpass Query in the way to fetch all the objects necessary to display the map
	 * @param bbox The Bounding box in which we want the data
	 * @return The OSM query
	 * @throws UnsupportedEncodingException Thrown when the encoding process failed
	 */
	private String fullMapQuery(String bbox) throws UnsupportedEncodingException {
		// TODO: Full query
		OSM queryOverpass = new OSM();
		
		queryOverpass.output("json", "", false, "");
		queryOverpass.start();
		
		queryOverpass.nodeMulti("landuse", "residential|industrial|commercial|retail|railway|cemetery|forest|grass", bbox);
		queryOverpass.way("landuse","residential",bbox);
		queryOverpass.way("landuse","industrial",bbox);
		queryOverpass.way("landuse","commercial",bbox);
		queryOverpass.way("landuse","retail",bbox);
		queryOverpass.way("landuse","railway",bbox);
		queryOverpass.way("landuse","cemetery",bbox);
		queryOverpass.way("landuse","forest",bbox);
		queryOverpass.way("landuse","grass",bbox);
		queryOverpass.relation("landuse",bbox);

		queryOverpass.node("landuse", "school", bbox);
		queryOverpass.way("amenity","school",bbox);
		queryOverpass.relation("amenity",bbox);

		queryOverpass.nodeMulti("leisure", "sports_centre|park|golf_course", bbox);
		queryOverpass.way("leisure","sports_centre",bbox);
		queryOverpass.way("leisure","park",bbox);
		queryOverpass.way("leisure","golf_course",bbox);
		queryOverpass.relation("leisure",bbox);

		queryOverpass.nodeMulti("highway", "primary|secondary|trunk|residential|living_street|pedestrian|motorway", bbox);
		queryOverpass.way("highway","primary",bbox);
		queryOverpass.way("highway","secondary",bbox);
		queryOverpass.way("highway","trunk",bbox);
		queryOverpass.way("highway","residential",bbox);
		queryOverpass.way("highway","living_street",bbox);
		queryOverpass.way("highway","pedestrian",bbox);
		queryOverpass.way("highway","motorway",bbox);
		queryOverpass.relation("highway",bbox);

		queryOverpass.node("building","yes",bbox);
		queryOverpass.way("building","yes",bbox);
		queryOverpass.way("building","school",bbox);
		queryOverpass.relation("building",bbox);
		
		queryOverpass.node("natural","water",bbox);
		queryOverpass.way("natural","water",bbox);
		queryOverpass.relation("natural",bbox);
		
		queryOverpass.outBodySkel();
		
		String query = queryOverpass.query;
		
		System.out.println("Query full map created: " + query);
		return query;
	}
	
	/**
	 * Parse the JSON file and make Object from It
	 * @param city {@code String} Name of the city
	 * @throws org.json.simple.parser.ParseException If the file wasn't find
	 */
	private void loadCityAsObject(String city) throws org.json.simple.parser.ParseException {
		
		// TODO: Loading
		
		// Load all the nodes
		Map.loadNodes(city);
		
		// Load all the ways
		Map.loadWays(city);
		
		// Load all the relations
		Map.loadRelations(city);
		
	}
	
	/**
	 * Make the object for the map
	 * @param cityName {@code String} Name of the city
	 * @throws Exception If the coordinates wasn't found
	 * @author Yanis Labrak
	 */
	private void fetchAllCity(String cityName) throws Exception {
		
		// Fetch the coordinates of the city
		String cityCoordinate = this.getCityCoordinates(cityName);
		
		this.addStateBar("Coordinates find");
		
		if (cityCoordinate == null) {
			throw new NullPointerException("cityCoordinate is empty !");
		}
		
		String[] coordinates = cityCoordinate.split("\\|");
		
		// Make the bbox
		String bbox = OSM.bboxCalc(
				Double.parseDouble(coordinates[0]),
				Double.parseDouble(coordinates[1])
		);
		
		this.addStateBar("BBox created");
		
		// Make the query for getting the full map
		String query = this.fullMapQuery(bbox);
		
		final String CITIES_FILE = cityName + "Map.json";
		System.out.println("File name: " + CITIES_FILE);
		
		File f = new File(CITIES_FILE);
		
		long startTime = System.nanoTime();
		
		// If the file doesn't exist
		if (!f.exists()) {
			System.out.println("File not found !");
			this.laodQueryInFile(query, CITIES_FILE);	
		}
		
		// How long the query take to be totally downloaded
		long endTime = System.nanoTime();
		this.addStateBar("Duration: " + TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS) + " seconds");
		
		// Parse the JSON file as Java Objects
		this.loadCityAsObject(cityName);
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
		
		this.searchBtn.setDisable(true);
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
			this.fromName.getSelectionModel().getSelectedIndex() < 0 ||
			this.toName.getSelectionModel().getSelectedIndex() < 0
 		) {
			this.searchBtn.setDisable(true);
		}
		else {			
			this.searchBtn.setDisable(false);
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
		
		// Destroy the input if it's not a Integer
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
		
		for (City city : this.listCity) {
					
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
	public void setCity(ActionEvent event) throws Exception
	{
		// If the city name isn't known
		if (!this.cityName.getText().isEmpty()) {
			
			// TODO: Map print and path calculation here
			this.fetchAllCity(this.cityName.getText());
			
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
			this.setCity(new ActionEvent());
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
			
			this.searchBtn.setDisable(true);
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
	public void setFullscreen(ActionEvent event) {
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
