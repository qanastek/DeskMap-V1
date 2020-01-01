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
import java.net.URLEncoder;
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
import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.controllers.MainViewController;
import fr.univavignon.ceri.deskmap.models.GeoData;
import fr.univavignon.ceri.deskmap.models.Street;
import fr.univavignon.ceri.deskmap.models.geopoint.City;
import fr.univavignon.ceri.deskmap.models.line.Road;
import fr.univavignon.ceri.deskmap.models.region.Landuse;
import fr.univavignon.ceri.deskmap.models.region.Region;

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
	 * Load the streets of the city into the variables of the {@code comboBox}'s
	 * @author Yanis Labrak
	 */
	public static void loadStreets() {
		
		// Clear the current list of streets
		MainViewController.listStreetName.clear();

		System.out.println("Way before name: " + Map.mapContent.keySet().size());
		
		// Load all the street inside listStreetName
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Road) {		
		    	
		    	Road r = ((Road) prop);

//	    		System.out.println("Before name");
		    	if (r.name != null && !r.name.isEmpty() && !r.name.equals("")) {
		    		
		    		boolean present = false;
		    		
		    		for (Road rd : MainViewController.listStreetName) {
		    			if (rd.name.equals(r.name)) {
							present = true;
						}
					}
		    		
		    		if (present == false) {
			    		MainViewController.listStreetName.add(r);
					}
				}		    	
			}	
		}
			
		MainViewController.listStreetNameSortedFrom.clear();
		MainViewController.listStreetNameSortedFrom.addAll(MainViewController.listStreetName);
		
		MainViewController.listStreetNameSortedTo.clear();
		MainViewController.listStreetNameSortedTo.addAll(MainViewController.listStreetName);
		
		System.out.println("Qts fromName: ");
		System.out.println(MainViewController.listStreetNameSortedFrom.size());
		
		System.out.println("Qts toName: ");
		System.out.println(MainViewController.listStreetNameSortedTo.size());
	}
	
}
