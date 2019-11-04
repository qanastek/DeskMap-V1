package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Water extends Region implements Natural, Waterway {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the water spot
	 */
	public Water(String id, String name) {
		super(id, name, Color.WATER);
	}
	
	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Water(String id) {
		super(id, "", Color.WATER);
	}

	/**
	 * Constructor without name
	 * @param id {@code Long} Identifier
	 */
	public Water(Long id) {
		super(id, "", Color.WATER);
	}

}
