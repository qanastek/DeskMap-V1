package fr.univavignon.ceri.deskmap;

/**
 * A class which represent the structure of an OSM City entity
 * @author Yanis Labrak
 */
public class City {
	
	/**
	 * Identifier of the city node {@code Long}
	 */
	public Long id;
	
	/**
	 * Earth latitude coordinate {@code Double}
	 */
	public Double lat;
	
	/**
	 * Earth longitude coordinate {@code Double}
	 */
	public Double lon;
	
	/**
	 * Name of the city {@code String}
	 */
	public String name;
	
	/**
	 * Main constructor
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 * @param name {@code String} Name
	 * @author Yanis Labrak
	 */
	public City(String id, Double lat, Double lon, String name) {
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
		this.name = name;
	}
	
	/**
	 * Copy constructor
	 * @param city {@code City} Another City object
	 * @author Yanis Labrak
	 */
	public City(City city) {
		this.id = city.id;
		this.lat = city.lat;
		this.lon = city.lon;
		this.name = city.name;		
	}
	
	@Override
	public String toString() {
		return "[" + this.id + "," + this.lat + "," + this.lon + "," + this.name + "]";
	}
}
