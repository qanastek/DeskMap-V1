package fr.univavignon.ceri.deskmap.models.line;

import fr.univavignon.ceri.deskmap.config.Color;
import fr.univavignon.ceri.deskmap.models.region.Highway;

/**
 * @author Mohamed BEN YAMNA
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
