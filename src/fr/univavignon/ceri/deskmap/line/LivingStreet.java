package fr.univavignon.ceri.deskmap.line;

import fr.univavignon.ceri.deskmap.Color;
import fr.univavignon.ceri.deskmap.Settings;
import fr.univavignon.ceri.deskmap.region.Highway;

/**
 * @author Yanis Labrak
 */
public class LivingStreet extends Line implements Highway, Level4 {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the data
	 */
	public LivingStreet(String id, String name) {
		super(id, name, Settings.LEVEL_4_ROAD_THICKNESS, Color.ROAD);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the data
	 */
	public LivingStreet(Long id, String name) {
		super(id, name, Settings.LEVEL_4_ROAD_THICKNESS, Color.ROAD);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public LivingStreet(Long id) {
		super(id, "", Settings.LEVEL_4_ROAD_THICKNESS, Color.ROAD);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public LivingStreet(String id) {
		super(id, "", Settings.LEVEL_4_ROAD_THICKNESS, Color.ROAD);
	}

}
