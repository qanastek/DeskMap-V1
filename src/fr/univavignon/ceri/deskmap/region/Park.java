/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 *
 */
public class Park extends Region {
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Park(String id) {
		super(id, "", "#c8facc");
	}
	
	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the {@code Park}
	 */
	public Park(String id, String name) {
		super(id, name, "#c8facc");
	}

}
