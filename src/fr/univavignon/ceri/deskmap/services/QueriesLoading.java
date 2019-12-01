package fr.univavignon.ceri.deskmap.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DeskMapExceptions.CannotReachServerException;
import fr.univavignon.ceri.deskmap.controllers.MainViewController;
import fr.univavignon.ceri.deskmap.models.Street;
import fr.univavignon.ceri.deskmap.models.geopoint.City;

/**
 * All the functions which is used for loading and downloading the queries results
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
			System.out.println("Query: " + query);
			System.out.println("Output: " + outputName);
			
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
	 * Get the query result directly
	 * @param query {@code String} Query to send to the OSM API
	 * @throws CannotReachServerException Exception thrown when the server cannot be reached
	 * @return {@code JSONObject} The full object which contain all the results
	 * @throws IOException Thrown when the file is missing
	 * @throws MalformedURLException Thrown when the {@code URL} isn't complete
	 * @throws ParseException Thrown when the {@code JSONObject} cannot be parse
	 * @author Yanis Labrak
	 */
	public static JSONObject getQueryResult(String query) throws CannotReachServerException, MalformedURLException, IOException, ParseException {
		
	    InputStream is = new URL(query).openStream();
	     
	    try {
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    	
			StringBuilder sb = new StringBuilder();
			
			int cp;			
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(sb.toString());
			
			return json;
	      
	    } finally {
	      is.close();
	    }
	    
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

		System.out.println("//////////////////////: " + city);

		String STREET_FILE = city.name
		.replaceAll("\\.", "\\_")
		.replaceAll("\\/", "\\_")
		.replaceAll("\\s+","")
		.toLowerCase() + ".csv";
		
		System.out.println(STREET_FILE);
		
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
			BufferedReader buffer = new BufferedReader(new FileReader(city.name.replaceAll("\\s+","").toLowerCase() + ".csv"));
					
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
