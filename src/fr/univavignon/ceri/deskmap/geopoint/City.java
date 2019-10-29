package fr.univavignon.ceri.deskmap.geopoint;

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
	 * Testing main
	 * @param args Nothing
	 */
	public static void main(String[] args) {
		System.out.println("City: ");
		
		City c1 = new City("4848848", -10.0, 20.0, "Brussel");
		System.out.println(c1.toString());
	}
}
