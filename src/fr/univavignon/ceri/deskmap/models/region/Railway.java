package fr.univavignon.ceri.deskmap.models.region;

import fr.univavignon.ceri.deskmap.config.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Railway extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Railway(String id) {
		super(id, "", Color.RAILWAY);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Railway(Long id) {
		super(id, "", Color.RAILWAY);
	}
}
