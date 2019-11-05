package fr.univavignon.ceri.deskmap.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DeskMapExceptions.CannotReachServerException;
import fr.univavignon.ceri.deskmap.controllers.MainViewController;
import fr.univavignon.ceri.deskmap.models.Street;
import fr.univavignon.ceri.deskmap.models.geopoint.City;
import javafx.collections.FXCollections;

/**
 * @author Yanis Labrak
 */
public abstract class QueriesLoading {
	
	/**
	 * Load cities from cache or API
	 * @param query {@code String} Query to send to the OSM API
	 * @param outputName {@code String} name of the output file
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @author Yanis Labrak
	 */
	public static void laodQueryInFile(String query, String outputName) throws CannotReachServerException {
		
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
			
			System.out.println("Caching done !");
		    
		} catch (Exception e) {
			throw new CannotReachServerException();
		}
	}	

	/**
	 * Download the cities of a country
	 * @param query {@code String} The query
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @author Yanis Labrak
	 */
	public static void downloadCities(String query) throws CannotReachServerException {		
		final String CITIES_FILE = "cities.csv";
		
		System.out.println("Query created: " + query);
		
		File f = new File(CITIES_FILE);
		
		if (!f.exists()) {
			System.out.println("File not found !");
			QueriesLoading.laodQueryInFile(query, CITIES_FILE);	
		}
	}
	
	/**
	 * Load the cities of the country
	 * @throws Exception Throw an exception if we cannot read the cities
	 * @author Yanis Labrak
	 */
	public static void loadCities() throws Exception {
		// Load the cities from the file		
		MainViewController.listCity = FXCollections.observableArrayList(QueriesLoading.parseCities());
		MainViewController.listCitySorted = FXCollections.observableArrayList();
		
	}
	
	/**
	 * Parse the cities.csv file and return a list of cities
	 * @throws Exception Throw an exception when the file cannot be read
	 * @return {@code List<City>} A list fill up with the cities
	 * @author Yanis Labrak
	 */
	public static List<City> parseCities() throws Exception {
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
	 * Download all the street of the city
	 * @param city {@code City} The city for which we want to download all streets
	 * @param query {@code String} The OSM query for fetching the data we want
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @author Yanis Labrak
	 */
	public static void downloadStreets(City city, String query) throws CannotReachServerException {
		final String STREET_FILE = city.name + ".csv";
		
		File f = new File(STREET_FILE);
		
		if (!f.exists()) {
			QueriesLoading.laodQueryInFile(query, STREET_FILE);
		}
	}
	
	/**
	 * Load the streets of the city into the variables of the {@code comboBox}'s
	 * @param city {@code City} The city for which we want to load all streets
	 * @throws Exception Throw an exception if we cannot read the streets
	 * @author Yanis Labrak
	 */
	public static void loadStreets(City city) throws Exception {
		
		// Clear list and load the cities from the file	
		MainViewController.listStreetName.clear();
		MainViewController.listStreetName.addAll(QueriesLoading.parseStreets(city));
		
		MainViewController.listStreetNameSortedFrom.clear();
		MainViewController.listStreetNameSortedFrom.addAll(MainViewController.listStreetName);
		
		MainViewController.listStreetNameSortedTo.clear();
		MainViewController.listStreetNameSortedTo.addAll(MainViewController.listStreetName);
	}
	
	/**
	 * Load streets from cache or API
	 * @param city {@code City} City object from where we want the streets
	 * @return The list fill up with the streets
	 * @throws Exception Throw a exception if the file which contain the streets isn't find
	 * @author Yanis Labrak
	 */
	public static List<Street> parseStreets(City city) throws Exception {		
		
		HashMap<String, Street> records = new HashMap<String, Street>();
		
		try {
			
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
		        	Street isAlreadyPresent = records.get(values[1]);

			        // If all the fields isn't null
			        if (values.length == 2 && !values[0].isEmpty() && !values[1].isEmpty() && isAlreadyPresent == null) {
				        	
				        	Street street = new Street(
				        		values[0],
				        		values[1]
					        );
					        
//					        records.add(street);
				        	records.put(values[1], street);
					}
			        
				} catch (Exception e) {
				}
		        
		    }
		    
		    // Close the stream
		    buffer.close();
		    
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		// Output list
		List<Street> rslt = new ArrayList<Street>();
		
		// Transform to List
		for (String item : records.keySet()) {
			rslt.add(records.get(item));
		}
		
		// return all the streets of the city
		return rslt;
	}
	
}
