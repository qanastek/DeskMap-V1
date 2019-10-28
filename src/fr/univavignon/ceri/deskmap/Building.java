package fr.univavignon.ceri.deskmap;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which represent the structure of an OSM Building
 * @author Yanis Labrak
 */
public class Building {
	
	/**
	 * Building's identifier
	 */
	public Long id;

	/**
	 * Building's name
	 */
	public String name;
	
	/**
	 * The {@code List} of {@code node} which compose the building
	 */
	public List<Node> nodes;
	
	/**
	 * Constructor with name
	 * @param id Building identifier
	 * @param name Name of the building
	 */
	public Building(String id, String name) {
		this.id = Long.parseLong(id);
		this.name = name;
		this.nodes = new ArrayList<Node>();
	}
	
	/**
	 * Constructor without name
	 * @param id Building identifier
	 */
	public Building(String id) {
		this.id = Long.parseLong(id);
		this.name = "";
		this.nodes = new ArrayList<Node>();
	}
}
