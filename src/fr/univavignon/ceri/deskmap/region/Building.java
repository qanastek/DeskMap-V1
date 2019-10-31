/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * A class which represent the structure of an OSM Building
 * @author Yanis Labrak
 */
public class Building extends Region implements Structure {

	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the building
	 */
	public Building(String id, String name) {
		super(id, name, Color.BUILDING);
	}
	
	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Building(String id) {
		super(id, "", Color.BUILDING);
	}
	
	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Building(Long id) {
		super(id, "", Color.BUILDING);
	}

}
