package fr.univavignon.ceri.deskmap;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which represent the structure of an OSM Building
 * @author Yanis Labrak
 */
public class Building {

	/**
	 * Building's name
	 */
	public String name;
	
	/**
	 * The {@code List} of {@code node} which compose the building
	 */
	public List<Node> nodes;
	
	/**
	 * Constructor
	 * @param name Name of the building
	 */
	public Building(String name) {
		this.name = name;
		this.nodes = new ArrayList<Node>();
	}
}
