/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

/**
 * A class which represent the structure of an OSM Building
 * @author Yanis Labrak
 */
public class Building extends Region {

	/**
	 * Constructor with name
	 * @param name {@code String} Name of the building
	 */
	public Building(String name) {
		super(name, "#d9d0c9");
	}
	
	/**
	 * Constructor without name
	 */
	public Building() {
		super("", "#d9d0c9");
	}

}
