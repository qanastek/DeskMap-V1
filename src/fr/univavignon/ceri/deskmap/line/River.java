/**
 * 
 */
package fr.univavignon.ceri.deskmap.line;

import fr.univavignon.ceri.deskmap.Color;
import fr.univavignon.ceri.deskmap.region.Highway;

/**
 * @author Yanis Labrak
 *
 */
public class River extends Line implements Highway {

	/**
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the river
	 * @param thickness {@code Integer} Thickness of the river line
	 */
	public River(String id, String name, Integer thickness) {
		super(id, name, thickness, Color.WATER);
	}

}
