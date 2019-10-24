package fr.univavignon.ceri.deskmap;

/**
 * A class which represent the structure of an OSM Node
 * @author Yanis Labrak
 */
public class Node {
	
	/**
	 * {@code Long} Identifier for the node
	 */
	public Long id;
	
	/**
	 * {@code Double} Earth latitude coordinate
	 */
	public Double lat;
	
	/**
	 * {@code Double} Earth longitude coordinate
	 */
	public Double lon;
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 * @author Yanis Labrak
	 */
	public Node(String id, Double lat, Double lon) {
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
	}
}
