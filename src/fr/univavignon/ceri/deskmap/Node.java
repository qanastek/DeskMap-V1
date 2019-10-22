package fr.univavignon.ceri.deskmap;

/**
 * @author Yanis Labrak
 *
 */
public class Node {
	public Long id;
	public Double lat;
	public Double lon;
	
	/**
	 * @param id
	 * @param lat
	 * @param lon
	 * @author Yanis Labrak
	 */
	public Node(String id, Double lat, Double lon) {
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
	}
}
