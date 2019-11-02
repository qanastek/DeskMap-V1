package fr.univavignon.ceri.deskmap.line;

import fr.univavignon.ceri.deskmap.Color;
import fr.univavignon.ceri.deskmap.Settings;
import fr.univavignon.ceri.deskmap.region.Highway;

/**
 * @author Yanis Labrak
 */
public class Trunk extends Line implements Highway, Level2 {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the data
	 */
	public Trunk(String id, String name) {
		super(id, name, Settings.LEVEL_2_ROAD_THICKNESS, Color.TRUNK);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the data
	 */
	public Trunk(Long id, String name) {
		super(id, name, Settings.LEVEL_2_ROAD_THICKNESS, Color.TRUNK);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Trunk(Long id) {
		super(id, "", Settings.LEVEL_2_ROAD_THICKNESS, Color.TRUNK);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Trunk(String id) {
		super(id, "", Settings.LEVEL_2_ROAD_THICKNESS, Color.TRUNK);
	}

}
