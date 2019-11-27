package fr.univavignon.ceri.deskmap.models.line;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.deskmap.models.GeoData;

/**
 * @author Mohamed BEN YAMNA
 */
public class Line extends GeoData {

	/**
	 * Thickness of the line
	 */
	protected Integer thickness;
	
	/**
	 * Color of it
	 */
	protected String color;
	
	/**
	 * List of the identifiers for each nodes which make the line
	 */
	protected List<Long> nodes = new ArrayList<Long>();
	
	/**
	 * Add a {@code Node} reference in this {@code Line}
	 * @param id {@code String} Identifier of the {@code Node}
	 */
	public void addNode(String id) {
		this.nodes.add(Long.parseLong(id));
	}
	
	/**
	 * Get the thickness of the line
	 * @return the thickness
	 */
	public Integer getThickness() {
		return this.thickness;
	}

	/**
	 * Set the thickness of the line
	 * @param thickness the thickness to set
	 */
	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}

	/**
	 * Get the color of the line
	 * @return the color
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * Set the color of the line
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the nodes
	 */
	public List<Long> getNodes() {
		return this.nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<Long> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the data
	 * @param thickness {@code Integer} Thickness of the line on the canvas
	 * @param color {@code String} Color of the line on the canvas
	 */
	public Line(String id, String name, Integer thickness, String color) {
		super(id,name);
		this.thickness = thickness;
		this.color = color;
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the data
	 * @param thickness {@code Integer} Thickness of the line on the canvas
	 * @param color {@code String} Color of the line on the canvas
	 */
	public Line(Long id, String name, Integer thickness, String color) {
		super(id,name);
		this.thickness = thickness;
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "[Name" + this.name + ", Thickness: " + this.thickness + ",Color: " + this.color + "]";
	}

}
