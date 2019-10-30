package fr.univavignon.ceri.deskmap;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import DeskMapExceptions.CannotReachServerException;
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
import javafx.scene.canvas.GraphicsContext;
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
	public static ObservableList<City> listCity;
	
	/**
	 * The observable variable for the text field {@code cityName}
	 */
	public static ObservableList<City> listCitySorted;
	
	/**
	 * The default list of streets for a specific city
	 */
	public static ObservableList<Street> listStreetName = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code FROM} comboBox
	 */
	public static ObservableList<Street> listStreetNameSortedFrom = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code TO} comboBox
	 */
	public static ObservableList<Street> listStreetNameSortedTo = FXCollections.observableArrayList();
	
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
			// Build the query to fetch all the cities of the country
			String queryCities = QueriesBuilding.buildFetchCitiesQuery("France");
			
			// Download all the cities of the country
			QueriesLoading.downloadCities(queryCities);
			
			// Load all the cities of the country
			QueriesLoading.loadCities();
			
		} catch (CannotReachServerException e) {
			this.addStateBar("Server cannot be reached !");
		} catch (Exception e) {
			System.err.println(e);
		} 
		
		// Assign both lists to their comboBox's
		this.fromName.setItems(MainViewController.listStreetNameSortedFrom);
		this.toName.setItems(MainViewController.listStreetNameSortedTo);
		
	}

	/**
	 * Add informations into the status bar {@code textArea}
	 * @param newLine {@code String} The line to add
	 * @author Yanis Labrak
	 */
	protected void addStateBar(String newLine) {
		
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
	 * Make the objects for the map
	 * @param cityName {@code String} Name of the city
	 * @throws Exception If the coordinates wasn't found
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @author Yanis Labrak
	 */
	private void fetchAllContentCity(String cityName) throws Exception, CannotReachServerException {
		
		try {
			// Fetch the coordinates of the city
			String cityCoordinate = City.getCityCoordinates(cityName);
			
			this.addStateBar("City coordinates find");
			
			if (cityCoordinate == null) {
				throw new NullPointerException("cityCoordinate wasn't found !");
			}
			
			String[] coordinates = cityCoordinate.split("\\|");
			
			// Make the bbox
			String bbox = OSM.bboxCalc(
					Double.parseDouble(coordinates[0]),
					Double.parseDouble(coordinates[1])
			);
			
			this.addStateBar("BBox created");
			
			// Make the query for getting the full map
			String query = QueriesBuilding.fullMapQuery(bbox);
			
			final String CITIES_FILE = cityName + "Map.json";
			System.out.println("File name: " + CITIES_FILE);
			
			File f = new File(CITIES_FILE);
			
			long startTime = System.nanoTime();
			
			// If the file doesn't exist
			if (!f.exists()) {
				this.addStateBar("File not found !");
				QueriesLoading.laodQueryInFile(query, CITIES_FILE);	
			}
			
			// How long the query take to be totally downloaded
			long endTime = System.nanoTime();
			this.addStateBar("Duration: " + TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS) + " seconds");
			
		} 
		catch (CannotReachServerException e) {
			this.addStateBar("Server cannot be reached !");
		}
		catch (Exception e) {
			System.err.println(e);
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
		
		this.searchBtn.setDisable(true);
		this.resetBtn.setDisable(true);
		
		MainViewController.listStreetName.clear();
		MainViewController.listStreetNameSortedFrom.clear();
		MainViewController.listStreetNameSortedTo.clear();
		
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
	 * Fetch the street for this city
	 * @param event {@code ActionEvent}
	 * @throws Exception Throw a exception when the city doesn't exists
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @author Yanis Labrak
	 */
	@FXML
	public void setCity(ActionEvent event) throws Exception, CannotReachServerException
	{
		// If the city name isn't known
		if (!this.cityName.getText().isEmpty()) {
			
			// Fetch everything we need to display the map			
			this.fetchAllContentCity(this.cityName.getText());			

			// Parse the JSON file as Java Objects
			Map.loadCityAsObject(this.cityName.getText());
			
			// Render all the objects of the canvas
			this.renderMap();
			
			this.addStateBar("Search for " + this.cityName.getText());
			
			// Load streets etc ... In the way to all the user to make a path research
			try {

				// Check if the city exists
				City theCity = City.isInListCity(this.cityName.getText());
				
				// Build the query for getting all the streets of a city
				String streetQuery = QueriesBuilding.buildFetchStreetsQuery(new City(theCity));
				
				// Download the streets
				QueriesLoading.downloadStreets(theCity, streetQuery);
				
			    this.addStateBar("Streets of " + theCity.name + " downloaded");
				
				// Load them inside comboBox's
				QueriesLoading.loadStreets(theCity);
			    
			    this.addStateBar("Streets of " + theCity.name + " loaded");
				
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
				
			}
			catch (CannotReachServerException e) {
				this.addStateBar("Server cannot be reached !");
			}
			catch (NullPointerException e) {
				this.addStateBar("Aucune ville correspondante");
			}
		}
		else {
			System.out.println("Empty field");
		}
	}
	
	/**
	 * Render all the objects of the canvas
	 */
	private void renderMap() {
		GraphicsContext gc = this.canvasMap.getGraphicsContext2D();
		
		Map.width = this.canvasMap.getWidth();
		Map.height = this.canvasMap.getHeight();
		
		// Draw Nodes
		Draw.drawNodes(gc);
	}
	
	/**
	 * When a key is pressed inside {@code cityName}
	 * @param event {@code KeyEvent} The key pressed
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @throws Exception Throw a exception when the city doesn't exists
	 * @author Yanis Labrak
	 */
	@FXML
	public void KeyPressCity(KeyEvent event) throws Exception, CannotReachServerException {
		
		try {
			
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
			
		} catch (CannotReachServerException e) {
			this.addStateBar("Server cannot be reached !");
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
				
				if (MainViewController.listStreetNameSortedFrom.size() > 0) {
					// Set the current value to the first element of the list which contain the current word
					this.fromName.setValue(MainViewController.listStreetNameSortedFrom.get(0));
				}
				
				// Don't read this character
				event.consume();
				
			} else {

				MainViewController.listStreetNameSortedFrom.clear();
				
				// Take the {@code street} which contain inside it name the current input
				for (Street street : MainViewController.listStreetName) {					
					if (street.name.toLowerCase().contains(current_value)) {
						MainViewController.listStreetNameSortedFrom.add(street);
					}
				}
				
				// If no result
				if (MainViewController.listStreetNameSortedFrom.size() <= 0) {
					this.addStateBar("Unknown start street");
				}
				
			}			
		}
		else {
			MainViewController.listStreetNameSortedFrom.setAll(MainViewController.listStreetName);
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
				
				if (MainViewController.listStreetNameSortedTo.size() > 0) {
					// Set the current value to the first element of the list which contain the current word
					this.toName.setValue(MainViewController.listStreetNameSortedTo.get(0));
				}
				
				// Destroy the entered key stroke
				event.consume();
				
			} else {

				MainViewController.listStreetNameSortedTo.clear();
				
				// Take the street which contain inside it name the current input
				for (Street street : MainViewController.listStreetName) {
					if (street.name.toLowerCase().contains(current_value)) {
						MainViewController.listStreetNameSortedTo.add(street);
					}
				}
				
				// If no result
				if (MainViewController.listStreetNameSortedTo.size() <= 0) {
					this.addStateBar("Unknown destination street");
				}
				
			}
			
		}
		else {
			MainViewController.listStreetNameSortedTo.setAll(MainViewController.listStreetName);
		}
	}
}
