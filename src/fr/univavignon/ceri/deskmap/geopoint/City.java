package fr.univavignon.ceri.deskmap.geopoint;

import java.io.BufferedReader;
import java.io.FileReader;

import fr.univavignon.ceri.deskmap.MainViewController;
import fr.univavignon.ceri.deskmap.Node;

/**
 * A class which represent the structure of an OSM City entity
 * @author Yanis Labrak
 */
public class City extends GeoPoint {
	
	/**
	 * Main constructor
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 * @param name {@code String} Name of the city
	 * @author Yanis Labrak
	 */
	public City(String id, Double lat, Double lon, String name) {
		super(id, name,new Node(Long.parseLong(id),lat,lon));
	}
	
	/**
	 * Copy constructor
	 * @param city {@code City} Another City object
	 * @author Yanis Labrak
	 */
	public City(City city) {
		super(city.id, city.name,city.point);
	}
	
	/**
	 * Check if the city is a real one.
	 * @param cityName {@code String} Name of the city
	 * @return {@code City} if found else {@code null}
	 * @author Yanis Labrak
	 */
	public static City isInListCity(String cityName) {
		
		// For each cities
		for (City city : MainViewController.listCity) {
					
			if (!city.name.isEmpty() && city.name.toLowerCase().equals(cityName.toLowerCase())) {
				System.out.println("Inside: " + city.name.toLowerCase());
				return city;
			}
		}
		return null;
		
	}
	
	/**
	 * Return the coordinates of a city
	 * @param city {@code String} Name of the city
	 * @return {@code String} Coordinates
	 */
	public static String getCityCoordinates(String city) {
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
	 * Testing main
	 * @param args Nothing
	 */
	public static void main(String[] args) {
		System.out.println("City: ");
		
		City c1 = new City("4848848", -10.0, 20.0, "Brussel");
		System.out.println(c1.toString());
	}
}
