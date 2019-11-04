package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Forest extends Region implements Landuse {

	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the forest
	 */
	public Forest(String id, String name) {
		super(id, name, Color.FOREST);
	}
	
	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Forest(String id) {
		super(id, "", Color.FOREST);
	}
	
	/**
	 * Constructor without name
	 * @param id {@code Long} Identifier
	 */
	public Forest(Long id) {
		super(id, "", Color.FOREST);
	}

}
