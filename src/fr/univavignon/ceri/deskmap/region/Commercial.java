/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Yanis Labrak
 *
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
