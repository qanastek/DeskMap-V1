package fr.univavignon.ceri.deskmap.models.region;

import fr.univavignon.ceri.deskmap.config.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Retail extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Retail(String id) {
		super(id, "", Color.RETAIL);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Retail(Long id) {
		super(id, "", Color.RETAIL);
	}

}
