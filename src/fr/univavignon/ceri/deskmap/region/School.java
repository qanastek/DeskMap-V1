/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 *
 */
public class School extends Region {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the school
	 */
	public School(String id, String name) {
		super(id, name, "#ffffe5");
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the school
	 */
	public School(Long id, String name) {
		super(id, name, "#ffffe5");
	}	
	
	/**
	 * Constructor no name
	 * @param id {@code String} Identifier
	 */
	public School(String id) {
		super(id, "", "#ffffe5");
	}
	
	/**
	 * Constructor no name
	 * @param id {@code String} Identifier
	 */
	public School(Long id) {
		super(id, "", "#ffffe5");
	}

}
