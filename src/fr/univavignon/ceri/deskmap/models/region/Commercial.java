package fr.univavignon.ceri.deskmap.models.region;

import fr.univavignon.ceri.deskmap.config.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Commercial extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Commercial(String id) {
		super(id, "", Color.COMMERCIAL);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Commercial(Long id) {
		super(id, "", Color.COMMERCIAL);
	}

}
