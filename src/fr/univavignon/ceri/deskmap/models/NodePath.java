package fr.univavignon.ceri.deskmap.models;

import java.util.Arrays;
import java.util.List;

import fr.univavignon.ceri.deskmap.Map;

/**
 * A class which represent the structure of an OSM Node
 * @author Mohamed BEN YAMNA
 */
public class NodePath extends Node {
	
	/**
	 * Distance before
	 */
	public Double distance=0.0;
	
	/**
	 * parent of nodes
	 */
	public NodePath parent = null;

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 * @param distance 
	 * @param parent 
	 */
	public NodePath(Node n) {
		super(n.id, n.lat, n.lon);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 * @param distance 
	 * @param parent 
	 */
	public NodePath(Long id, Double lat, Double lon, Double distance, NodePath parent) {
		super(id, lat, lon);
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.distance = distance;
		this.parent = parent;
	}
}