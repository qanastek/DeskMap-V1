/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Yanis Labrak
 *
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
