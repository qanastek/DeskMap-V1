package fr.univavignon.ceri.deskmap.models.region;

import fr.univavignon.ceri.deskmap.config.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Grass extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Grass(String id) {
		super(id, "", Color.GRASS);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Grass(Long id) {
		super(id, "", Color.GRASS);
	}

}
