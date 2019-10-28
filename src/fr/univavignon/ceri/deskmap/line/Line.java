package fr.univavignon.ceri.deskmap.line;

import fr.univavignon.ceri.deskmap.GeoData;

/**
 * @author Yanis Labrak
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
	 * Constructor
	 * @param name {@code String} Name of the data
	 * @param thickness {@code Integer} Thickness of the line on the canvas
	 * @param color {@code String} Color of the line on the canvas
	 */
	public Line(String name, Integer thickness, String color) {
		super(name);
		this.thickness = thickness;
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "[Name" + this.name + ", Thickness: " + this.thickness + ",Color: " + this.color + "]";
	}

}
