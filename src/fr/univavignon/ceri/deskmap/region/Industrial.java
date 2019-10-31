/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Yanis Labrak
 *
 */
public class Industrial extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Industrial(String id) {
		super(id, "", Color.INDUSTRIAL);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Industrial(Long id) {
		super(id, "", Color.INDUSTRIAL);
	}

}
