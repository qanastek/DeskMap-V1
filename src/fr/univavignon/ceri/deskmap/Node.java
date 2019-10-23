package fr.univavignon.ceri.deskmap;

/**
 * A class which represent the structure of an OSM Node
 * @author Yanis Labrak
 */
public class Node {
	
	/**
	 * Identifier of the node {@code Long}
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
	 * @param id {@code String} Indentifier
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
