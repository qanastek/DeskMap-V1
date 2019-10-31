/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 */
public class Wood extends Region {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the Wood
	 * @param color {@code String} Color of the Wood
	 */
	public Wood(String id, String name, String color) {
		super(id, name, color);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the Wood
	 * @param color {@code String} Color of the Wood
	 */
	public Wood(Long id, String name, String color) {
		super(id, name, color);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Wood(Long id) {
		super(id);
	}

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Wood(String id) {
		super(id);
	}

}
