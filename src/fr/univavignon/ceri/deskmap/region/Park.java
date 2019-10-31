/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Yanis Labrak
 *
 */
public class Park extends Region implements Leisure {
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Park(String id) {
		super(id, "", Color.PARK);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Park(Long id) {
		super(id, "", Color.PARK);
	}
	
	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the {@code Park}
	 */
	public Park(String id, String name) {
		super(id, name, Color.PARK);
	}

}
