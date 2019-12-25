package fr.univavignon.ceri.deskmap.controllers;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import DeskMapExceptions.CannotReachServerException;
import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.config.Settings;
import fr.univavignon.ceri.deskmap.models.Bbox;
import fr.univavignon.ceri.deskmap.models.GeoData;
import fr.univavignon.ceri.deskmap.models.Node;
import fr.univavignon.ceri.deskmap.models.Street;
import fr.univavignon.ceri.deskmap.models.geopoint.City;
import fr.univavignon.ceri.deskmap.models.line.Road;
import fr.univavignon.ceri.deskmap.services.Draw;
import fr.univavignon.ceri.deskmap.services.OSM;
import fr.univavignon.ceri.deskmap.services.QueriesBuilding;
import fr.univavignon.ceri.deskmap.services.QueriesLoading;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The controller of the FXML file
 * @author Quentin Capdepon
 */
public class MainViewController implements Initializable {
	
	/**
	 * Map canvas
	 */
	@FXML
	private Canvas canvasMap;
	
	/**
	 * Node canvas
	 */
	@FXML
	private Canvas canvasNodes;
	
	/**
	 * Name of the city
	 */
	@FXML
	private ComboBox<City> cityName;
	
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
	 * Bottom status bar
	 */
	@FXML
	private TextArea statusBar;
	
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
	 * The {@code Pane} which contain the {@code Canvas}
	 */
	@FXML
	private Pane canvasPane;
	
	/**
	 * The value of the scale
	 */
	@FXML
    private Text scaleValue;
	
	/**
	 * List of {@code City}
	 */
	public static ObservableList<City> listCity;
	
	/**
	 * The default list of streets for a specific city
	 */
	public static ObservableList<Street> listStreetName = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code FROM} comboBox
	 */
	public static ObservableList<Street> listStreetNameSortedFrom = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code City} comboBox
	 */
	public static ObservableList<City> listCitySorted = FXCollections.observableArrayList();
	
	/**
	 * The observable variable for the {@code TO} comboBox
	 */
	public static ObservableList<Street> listStreetNameSortedTo = FXCollections.observableArrayList();
	
	/**
	 * The map instance which will contain all the objects to display
	 */
	public static Map map = new Map();	

	/**
	 * GraphicsContext for the canvas
	 */
	private GraphicsContext gc;
	
	/**
	 * GraphicsContext for the nodes canvas
	 */
	private GraphicsContext gcNodes;
	
	/**
	 * Automatically started when the program start
	 * @author Yanis Labrak
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Initialize");
		
		// Get the graphics context of the canvas
		this.gc = this.canvasMap.getGraphicsContext2D();
		this.gcNodes = this.canvasNodes.getGraphicsContext2D();
		
		// Link the value of the scale to the Text object
		this.scaleValue.textProperty().bind(Map.scaleMeter.asString());
		
		// Set the min and max value of the slider
		this.slider.setMax(Settings.MAX_SCALE / 6);
		this.slider.setMin(Settings.MIN_SCALE);
		this.slider.setMajorTickUnit(Settings.MAX_SCALE / 6);
		this.slider.setMinorTickCount(Settings.MIN_SCALE);
		
		try {
			
			// Async load of the map
			CompletableFuture.runAsync(() -> {
				try {
					this.renderCityMap(Settings.DEFAULT_CITY);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			// When the canvas width change
			this.canvasPane.widthProperty().addListener((obs, oldVal, newVal) -> {
				
				Double size;
				
				if (newVal.doubleValue() > this.canvasPane.heightProperty().get()) {
					size = newVal.doubleValue();
				} else {
					size = this.canvasPane.heightProperty().get();
				}
				
				this.canvasMap.setHeight(size);
				this.canvasMap.setWidth(size);
				this.renderMap();
			});

			// When the canvas height change
			this.canvasPane.heightProperty().addListener((obs, oldVal, newVal) -> {
				
				Double size;
				
				if (this.canvasPane.widthProperty().get() > newVal.doubleValue()) {
					size = this.canvasPane.widthProperty().get();
				} else {
					size = newVal.doubleValue();
				}
				
				this.canvasMap.setHeight(size);
				this.canvasMap.setWidth(size);
				this.renderMap();
			});
			
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
	 * @author Quentin Capdepon
	 */
	protected void addStateBar(String newLine) {
		
		// If its empty
		if (this.statusBar.getText().equals("No logs...")) {
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
	 * Add informations into the path area {@code textArea}
	 * @param newLine {@code String} The line to add
	 * @author Quentin Capdepon
	 */
	protected void addMapPath(String newLine) {
		
		// If its empty
		if (this.mapPath.getText().equals("Informations about the path...")) {
			this.mapPath.setText(newLine);
		} else {
			// Add to the next line
			this.mapPath.setText(this.mapPath.getText() + '\n' + newLine);
		}
		
		// Auto scroll down
		this.mapPath.selectPositionCaret(this.statusBar.getLength());
		this.mapPath.deselect();
	}
	
	/**
	 * Method trigged when the user click on the search button
	 * @param event {@code no informations}
	 * @throws Exception {@code no informations}
	 * @author Quentin Capdepon
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
			this.addMapPath(fromNumber + " " + this.fromName.getSelectionModel().getSelectedItem() + " -> " + toNumber + " " + this.toName.getSelectionModel().getSelectedItem());
		}
	}
	
	/**
	 * Make the objects for the map
	 * @param city {@code City} The city
	 * @throws Exception If the coordinates wasn't found
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @author Yanis Labrak
	 */
	private void fetchAllContentCity(City city) throws Exception, CannotReachServerException {
		
		System.out.println("City loaded: " + city.name + "," + city.point);
		
		try {
			// Fetch the coordinates of the city
			String cityCoordinate = city.point.lat + "|" + city.point.lon;
			
			String[] coordinates = cityCoordinate.split("\\|");
			
			if (coordinates.length <= 0) {
				System.err.println("cityCoordinate wasn't found !");
			} else {
				this.addStateBar("City coordinates find");
			}
			
			// Make the bbox
			String bbox = OSM.bboxCalc(
					Double.parseDouble(coordinates[0]),
					Double.parseDouble(coordinates[1])
			).toString();
			
			this.addStateBar("BBox created");
			
			// Make the query for getting the full map
			String query = QueriesBuilding.fullMapQuery(bbox);
			
			String CITIES_FILE = URLEncoder.encode(city.name
			.replaceAll("\\.", "\\_")
			.replaceAll("\\/", "\\_")
			.toLowerCase(), "UTF-8") + "Map.json";
			
			System.out.println("File name: " + CITIES_FILE);
			
			File f = new File(CITIES_FILE);
			
			// If the file doesn't exist
			if (!f.exists()) {
				this.addStateBar("File not found !");
				QueriesLoading.laodQueryInFile(query, CITIES_FILE);	
			}
			
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
	 * @author Quentin Capdepon
	 */
	@FXML
	public void Reset(ActionEvent event) {
		this.cityName.getSelectionModel().clearSelection();
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
	 * @author Quentin Capdepon
	 */
	private void checkAllFields() {
		if (this.cityName.getSelectionModel().isEmpty() ||
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
	 * @author Quentin Capdepon
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
	 * @author Quentin Capdepon
	 */
	public void checkInputCity()
    {
		
		String currentCityName = this.cityName.getEditor().getText();
		
		// If is empty
		if (currentCityName.length() == 0) {
			return;
		}
		
		int idLast = currentCityName.length() > 1 ? currentCityName.length() - 1 : 0;
		char value = currentCityName.charAt(idLast);
		
		if (currentCityName.length() >= 1) {
			
			// If it currently looks like GPS coordinates
			if (currentCityName.matches(".*\\d.*")) {
				
				long countComma = currentCityName.chars().filter(ch -> ch == ',').count();
				
				if ((value != ',' && !Character.isDigit(value)) || countComma > 3) {

					System.out.println(this.cityName.getEditor().getText());
					
					// Delete the new char
					this.cityName.getEditor().setText(
						currentCityName.substring(0, this.cityName.getEditor().getCaretPosition() - 1)
						+
						currentCityName.substring(this.cityName.getEditor().getCaretPosition())
					);
					this.cityName.getEditor().positionCaret(currentCityName.length() - 1);
					
					System.out.println(this.cityName.getEditor().getText());
					
				}
				
			} else {
				
				if (value != ' ' && value != '-' && !Character.isLetter(value)) {
					
					// Delete the new char
					this.cityName.getEditor().setText(
						currentCityName.substring(0, this.cityName.getEditor().getCaretPosition() - 1)
						+
						currentCityName.substring(this.cityName.getEditor().getCaretPosition())
					);
					this.cityName.getEditor().positionCaret(currentCityName.length() - 1);
					
				}
				
			}
			
		}
		
    }
	
	/**
	 * Check if the input key is a integer
	 * @param event {@code keyEvent}
	 * @author Quentin Capdepon
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
	 * @author Quentin Capdepon
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
	 * @author Quentin Capdepon
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
		if (!this.cityName.getSelectionModel().isEmpty()) {
			
			// Align center
			Map.latitude = 0.0;
			Map.longitude = 0.0;

			// Initialize
			Map.scale = 1.0;
			
			this.addStateBar("Search for " + this.cityName.getSelectionModel().getSelectedItem());
			
			// Load streets etc ... In the way to all the user to make a path research
			try {

				Boolean theCityExist = this.fromName.getSelectionModel().isEmpty();
				
				// If the city exist
				if (theCityExist != false) {
					
					City city = MainViewController.listCitySorted.get(this.cityName.getSelectionModel().getSelectedIndex());

//					// Fetch, Load and Render the map for this city
//					this.renderCityMap(city);
					
					CompletableFuture.runAsync(() -> {
						try {
							// Fetch, Load and Render the map for this city
							this.renderCityMap(city);
						} catch (Exception e) {
							System.out.println(e);
						}
					});
					
					// Build the query for getting all the streets of a city
					String streetQuery = QueriesBuilding.buildFetchStreetsQuery(city);
					
					// Download the streets
					QueriesLoading.downloadStreets(city, streetQuery);
					
				    this.addStateBar("Streets of " + city.name + " downloaded");
					
					// Load them inside comboBox's
					QueriesLoading.loadStreets(city);
					
				    this.addStateBar("Streets of " + city.name + " loaded");
					
					if (!this.cityName.getSelectionModel().isEmpty()) {
						
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
					
				} else {
					City c = MainViewController.listCitySorted.get(this.cityName.getSelectionModel().getSelectedIndex());
					this.addStateBar(c.name + " doesn't exist !");
				}
				
			}
			catch (CannotReachServerException e) {
				this.addStateBar("Server cannot be reached !");
			}
			catch (NullPointerException e) {
				this.addStateBar("No city found !");
			}
		}
		else {
			System.out.println("Empty field");
		}
	}
	
	/**
	 * Fetch, Load and Render the map for this city
	 * @param city {@code String} City to render
	 * @throws CannotReachServerException Throw this exception when the server cannot be reached
	 * @throws Exception If the coordinates wasn't found
	 * @author Yanis Labrak
	 */
	private void renderCityMap(City city) {
		
		try {
			
			// Sanitize the city name
			city.name = URLEncoder.encode(city.name
			.replaceAll("\\.", "\\_")
			.replaceAll("\\/", "\\_"), "UTF-8");
			
			// Fetch everything we need to display the map		
			this.fetchAllContentCity(city);
			
			// Parse the JSON file as Java Objects
			// TODO: 7,25300 seconds - To optimize
			Map.loadCityAsObject(URLEncoder.encode(city.name, "UTF-8").toLowerCase());
			
			// Render all the objects of the canvas
			this.renderMap();
		
		} catch (Exception | CannotReachServerException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Render all the objects of the canvas
	 * @author Yanis Labrak
	 */
	private void renderMap() {
		
		// Clear the canvas before draw
		this.canvasMap.getGraphicsContext2D().clearRect(0, 0, this.canvasMap.getWidth(), this.canvasMap.getHeight());
		this.canvasNodes.getGraphicsContext2D().clearRect(0, 0, this.canvasMap.getWidth(), this.canvasMap.getHeight());
		
		Map.width = this.canvasMap.getWidth();
		Map.height = this.canvasMap.getHeight();
		
		// Draw all ways
		Draw.drawWays(this.gc);
	}
	
	/**
	 * Draw all {@code Node}'s in a specific area
	 * @param event {@code MouseEvent}
	 * @author Yanis Labrak
	 */
	@FXML
	void showNodesArround(MouseEvent event) {
		
		Bbox bbox = new Bbox(
			event.getX() - 10,
			event.getX() + 10,
			event.getY() - 10,
			event.getY() + 10
		);
		
		// Clear canvas
		this.canvasNodes.getGraphicsContext2D().clearRect(0, 0, this.canvasMap.getWidth(), this.canvasMap.getHeight());
		
		// Draw Nodes
		Draw.drawNodes(this.gcNodes, bbox);
	}
	
	/**
	 * Get all the info's about the closest {@code Node} of the mouse when right click
	 * @param event {@code MouseEvent}
	 * @author Yanis Labrak
	 */
	@FXML
	void getNodeInformations(MouseEvent event) {
		
		// Get THE closest Node
		Node closest = this.getClosestNodes(event);
		System.out.println(closest);
		
		// Print infos
		
		final ContextMenu contextMenu = new ContextMenu();
		
		MenuItem cut = new MenuItem("Id: " + closest.id);
		MenuItem copy = new MenuItem("Lat: " + closest.lat);
		MenuItem paste = new MenuItem("Lon: " + closest.lon);
		
		contextMenu.getItems().addAll(cut, copy, paste);
		
		cut.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
		        System.out.println("Cut...");				
			}
		});
		
		for (Long key : Map.mapContent.keySet()) {
		    
			GeoData g = Map.mapContent.get(key);
			
			if (g instanceof Road && ((Road) g).getNodes().contains(closest.id) && g.name != null) {
				
				System.out.println("Road:" + g.name);
				
				contextMenu.getItems().add(
					new MenuItem(g.name)
				);
			}
		}
		
        contextMenu.show(this.canvasPane, event.getScreenX(), event.getScreenY());
	}
	

	/**
	 * Get the closest {@code Node} of the mouse when right click
	 * @param event {@code MouseEvent}
	 * @author Yanis Labrak
	 */
	@FXML
	Node getClosestNodes(MouseEvent event) {
		
		Bbox bbox = new Bbox(
			event.getX() - 10,
			event.getX() + 10,
			event.getY() - 10,
			event.getY() + 10
		);

		Node closest = null;
		
		// Get all nodes around
		for (Long key : Map.nodes.keySet()) {
		    
		    Node node = Map.nodes.get(key);
		    
    		// Coordinate after processing
    		List<Double> coordinates = Node.toPixel(node.lat, node.lon);
    		
    		Double x = coordinates.get(0);
    		Double y = Map.height - coordinates.get(1);
	    		
		    if (x < bbox.topRight && x > bbox.topLeft && y > bbox.bottomLeft && y < bbox.bottomRight) {
		    	
		    	if (closest == null) {
		    		closest = node;
				}
		    	else {
		    		
		    		Double distanceMouseNode = MainViewController.Distance(event.getX(), event.getY(), node.lon, node.lat);
		    		Double distanceMouseClosest = MainViewController.Distance(event.getX(), event.getY(), closest.lon, closest.lat);

					// Mouse.distance(node) < Mouse.distance(closest)
					// closest = node
		    		if (distanceMouseNode < distanceMouseClosest) {
		    			closest = node;						
					}
				}	
			}			
		}
		
		return closest;
	}

    /**
     * The distance between the two points
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return {@code Double} The distance
     */
    static public double Distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.sqrt(y2 - y1) + Math.sqrt(x2 - x1));
    }
	
	/**
	 * When a key is pressed inside {@code cityName}
	 * @param event {@code KeyEvent} The key pressed
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @throws Exception Throw a exception when the city doesn't exists
	 * @author Zheng Zhiao
	 */
	@FXML
	public void KeyPressCity(KeyEvent event) throws Exception, CannotReachServerException {
		
		this.checkInputCity();
		
		try {
			
			// If it's a moving key continue
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER) {
				event.consume();
				return;
			}
			
			// If the field isn't empty
			if (!this.cityName.getEditor().getText().isEmpty()) {

					this.disableAllUnderCity();
					
					// Clear the current list of city
					MainViewController.listCitySorted.clear();
					
					if (this.cityName.getEditor().getText().length() >= 3) {
						
						Boolean isCoordinate = this.cityName.getEditor().getText().matches(".*\\d.*");
						String nominatimUrl = null; 
						
						// If the field contain digits make a reverse city research
						if (isCoordinate) {
							
							// http://photon.komoot.de/reverse?lon=4.8059012&lat=43.9492493
							
							String[] coordinates = this.cityName.getEditor().getText().split(", ");

							String lon = coordinates[1].replaceAll(",",".");
							String lat = coordinates[0].replaceAll("\\s+","").replaceAll(",",".");
			
							nominatimUrl = "http://photon.komoot.de/reverse?lon=" + lon + "&lat=" + lat;
							
						} else {						
							nominatimUrl = "http://photon.komoot.de/api/?q=" + URLEncoder.encode("'" + this.cityName.getEditor().getText() + "'", "UTF-8");
						}
						
						JSONObject loadedQuery = QueriesLoading.getQueryResult(nominatimUrl);							
						JSONArray items = (JSONArray) loadedQuery.get("features");
						Iterator<JSONObject> i = items.iterator();
						
				        while (i.hasNext()) {
				        	
				        	JSONObject item = (JSONObject) i.next();
				        	
				        	JSONObject properties = (JSONObject) item.get("properties");
				        	
				        	JSONObject geometry = (JSONObject) item.get("geometry");
							JSONArray coordinates = (JSONArray) geometry.get("coordinates");
				        	
							Long osm_id = (Long) properties.get("osm_id");
				            Double lat = (Double) coordinates.get(1);
				            Double lon = (Double) coordinates.get(0);
				            String country = (String) properties.get("country");
				            String name = (String) properties.get("name");
				            String state = (String) properties.get("state");
				            String osm_value = (String) properties.get("osm_value");
				            
				            if (isCoordinate || osm_value.equals("city") || osm_value.equals("village")) {	
					        	City c = new City(osm_id.toString(), lat, lon, name, state, country);
								MainViewController.listCitySorted.add(c);				
							}
				        }
	
						this.cityName.setItems(MainViewController.listCitySorted);
						this.cityName.show();
					}
					
					this.fromName.show();
					this.cityButton.setDisable(false);
					this.resetBtn.setDisable(false);
			}
			else {
				
				this.cityButton.setDisable(true);
				
				this.disableAllUnderCity();
			}
			
		} catch (CannotReachServerException e) {
			this.addStateBar("Server cannot be reached !");
		}
	}
	
	/**
	 * Disable all the fields under the city field
	 */
	void disableAllUnderCity() {
		
		this.fromName.hide();
		this.toName.hide();
		
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
	
	/**
	 * Zoom in the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void zoomIn(ActionEvent event) {
		
		if (Map.scale * Settings.ZOOMING_SCALE <= Settings.MAX_SCALE) {		
			
			Map.scale *= Settings.ZOOMING_SCALE;
			
			this.slider.setValue(Map.scale);
			
			this.renderMap();			
		}
	}
	
	/**
	 * Zoom out the map
	 * @param event {@code MouseEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	private void zoomInSlider(MouseEvent event) {		
		Map.scale = Math.pow(2, this.slider.getValue());
		this.renderMap();
		
	}
	
	/**
	 * Zoom out the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void zoomOut(ActionEvent event) {
		
		if (Map.scale / Settings.ZOOMING_SCALE > Settings.MIN_SCALE) {
			
			Map.scale /= Settings.ZOOMING_SCALE;
			
			this.slider.setValue(Map.scale);
			
			this.renderMap();
		}
	}
	
	/**
	 * Zoom in the map with mouse
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void canvasScrolling(ScrollEvent event) {
		
        double deltaY = event.getDeltaY();
        
        if (deltaY > 0 ) {
        	this.zoomIn(new ActionEvent());
		}
        else if (deltaY < 0) {
        	this.zoomOut(new ActionEvent());
		}
	}
	
	/**
	 * Move to the left in the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void left(ActionEvent event) {
		Map.longitude += Settings.HORI_MOVE_DIST;
		this.renderMap();
	}
	
	/**
	 * Move to the right in the map
	 * @param event {@code ActionEvent}
	 * @author @author Mohammed Benyamna
	 */
	@FXML
	public void right(ActionEvent event) {
		Map.longitude -= Settings.HORI_MOVE_DIST;
		this.renderMap();
	}
	
	/**
	 * Move to the top in the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void up(ActionEvent event) {
		Map.latitude -= Settings.VERT_MOVE_DIST;
		this.renderMap();
	}
	
	/**
	 * Move to the bottom in the map
	 * @param event {@code ActionEvent}
	 * @author Mohammed Benyamna
	 */
	@FXML
	public void down(ActionEvent event) {
		Map.latitude += Settings.VERT_MOVE_DIST;
		this.renderMap();
	}
	
	/**
	 * Hide the left panel
	 * @param event {@code ActionEvent}
	 * @author Quentin Capdepon
	 */
	@FXML
	public void hideLeft(ActionEvent event)
	{
		this.splitPane.setDividerPositions(0.0);
	}
	
	/**
	 * Show the left panel
	 * @param event {@code ActionEvent}
	 * @author Quentin Capdepon
	 */
	@FXML
	public void showLeft(ActionEvent event)
	{
		this.splitPane.setDividerPositions(0.29);
	}

    /**
     * Handle the drag event on the canvas
     * @param event {@code MouseEvent}
     */
    @FXML
    void drag(MouseEvent event) {    	
    	if (event.getButton() == MouseButton.PRIMARY) {
        	Map.xDelta = event.getSceneX();
        	Map.yDelta = event.getSceneY();
			System.out.println("LEFT");	
		}
    	else if (event.getButton() == MouseButton.SECONDARY)
        {
    		System.out.println("right click");
            this.getNodeInformations(event);
        }
    }

    /**
     * Handle the drop event on the canvas
     * @param event {@code MouseEvent}
     */
    @FXML
    void drop(MouseEvent event) {
    	
		if (event.getButton() == MouseButton.PRIMARY) {
			
			Map.xDelta = event.getSceneX() - Map.xDelta;
	    	Map.yDelta = event.getSceneY() - Map.yDelta;
	    	
	    	Map.longitude += Map.xDelta * 3;
	    	Map.latitude -=  Map.yDelta / 1000000000d;
	    	
			this.renderMap();	
			System.out.println("LEFT DROP");	
			
		}		
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

			// If it's a moving key continue
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER) {
				event.consume();
			}
			// If the last pressed key is TAB
			else if (event.getCode() == KeyCode.CONTROL) {
				
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
				} else {
					this.toName.hide();
					this.fromName.show();
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
			
			// If it's a moving key continue
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER) {
				event.consume();
			}
			// If the last pressed key is TAB
			else if (event.getCode() == KeyCode.CONTROL) {
				
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
				} else {
					this.fromName.hide();
					this.toName.show();
				}
				
			}
			
		}
		else {
			MainViewController.listStreetNameSortedTo.setAll(MainViewController.listStreetName);
		}
	}
}
