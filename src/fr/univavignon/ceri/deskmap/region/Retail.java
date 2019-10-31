/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Yanis Labrak
 *
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
