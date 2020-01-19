package fr.univavignon.ceri.deskmap.models;

import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.models.line.Road;

/**
 * A class which represent the structure of an OSM Node for the path
 * @author Zihao Zheng
 * @author Mohamed Ben Yamna
 */
public class NodePath extends Node {
	
	/**
	 * Distance from the last {@code Node} to this one
	 */
	public Double distance = 0.0;
	
	/**
	 * Parent of {@code Node}
	 */
	public NodePath parent = null;
	
	/**
	 * Street name
	 */
	public String street = "";

	/**
	 * Constructor
	 * @param n {@code Node} Base {@code Node}
	 * @author Zihao Zheng
	 * @author Mohamed Ben Yamna
	 */
	public NodePath(Node n) {
		super(n.id, n.lat, n.lon);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 * @param distance {@code Double} Distance from the last {@code Node} to this one
	 * @param parent {@code NodePath} Parent
	 * @author Zihao Zheng
	 * @author Mohamed Ben Yamna
	 */
	public NodePath(Long id, Double lat, Double lon, Double distance, NodePath parent) {
		super(id, lat, lon);
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.distance = distance;
		this.parent = parent;
	}

	/**
	 * Set the street name of the {@code NodePath}
	 */
	public void setStreet() {
		
		// Get the street name of the node
		for (GeoData g : Map.mapContent.values()) {
			
			// If its a Road
			if (g instanceof Road) {
				
				Road r = ((Road) g);
				
				// If this Road contain the Node
				if (r.getNodes().contains(this.id)) {
					
					// Get this name
					this.street = r.name;
					break;
				}
			}
		}
		
	}

	/**
	 * Get the distance in meters
	 * @return {@code Double} The distance
	 * @author Zihao Zheng
	 * @author Mohamed Ben Yamna
	 * @author Yanis Labrak
	 */
	public Double getDistanceInMeters() {
		return Math.floor((this.distance * 90000) * 100) / 100;
	}
}