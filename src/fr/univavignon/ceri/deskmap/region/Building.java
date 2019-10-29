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
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the building
	 */
	public Building(String id, String name) {
		super(id, name, "#d9d0c9");
	}
	
	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Building(String id) {
		super(id, "", "#d9d0c9");
	}
	
	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Building(Long id) {
		super(id, "", "#d9d0c9");
	}

}
