/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Yanis Labrak
 *
 */
public class School extends Region implements Structure {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the school
	 */
	public School(String id, String name) {
		super(id, name, Color.SCHOOL);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the school
	 */
	public School(Long id, String name) {
		super(id, name, Color.SCHOOL);
	}	
	
	/**
	 * Constructor no name
	 * @param id {@code String} Identifier
	 */
	public School(String id) {
		super(id, "", Color.SCHOOL);
	}
	
	/**
	 * Constructor no name
	 * @param id {@code String} Identifier
	 */
	public School(Long id) {
		super(id, "", Color.SCHOOL);
	}

}
