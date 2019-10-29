/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.deskmap.GeoData;
import fr.univavignon.ceri.deskmap.Node;

/**
 * @author Yanis Labrak
 */
public abstract class Region extends GeoData {
	
	/**
	 * Color of the region when drawn
	 */
	protected String color;
	
	/**
	 * All the nodes which make the Region
	 */
	protected List<Long> nodes = new ArrayList<Long>();
	
	/**
	 * Constructor
	 * @param name {@code String} Name of the region
	 * @param color {@code String} Color assigned to this {@code Region}
	 */
	public Region(String name, String color) {
		super(name);
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Get all the nodes of this Region
	 * @return the nodes
	 */
	public List<Long> getNodes() {
		return this.nodes;
	}
	
	/**
	 * Add a node to the node list
	 * @param node The node to add
	 */
	public void addNode(Node node) {
		this.nodes.add(node.id);
	}
	
	@Override
	public String toString() {
		return "[Name: " + this.name + ", Color: " + this.color + "]";
	}

}
