package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Residential extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Residential(String id) {
		super(id, "", Color.RESIDENTIAL);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Residential(Long id) {
		super(id, "", Color.RESIDENTIAL);
	}

}
