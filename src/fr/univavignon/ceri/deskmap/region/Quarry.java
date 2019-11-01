/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Yanis Labrak
 *
 */
public class Quarry extends Region implements Landuse {

	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the Quarry
	 */
	public Quarry(String id, String name) {
		super(id, "", Color.QUARRY);
	}

	/**
	 * Constructor with name
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the Quarry
	 */
	public Quarry(Long id, String name) {
		super(id, "", Color.QUARRY);
	}

	/**
	 * Constructor with name
	 * @param id {@code Long} Identifier
	 */
	public Quarry(Long id) {
		super(id, "", Color.QUARRY);
	}

	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 */
	public Quarry(String id) {
		super(id, "", Color.QUARRY);
	}

}
