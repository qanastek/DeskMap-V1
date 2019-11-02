package fr.univavignon.ceri.deskmap.line;

import fr.univavignon.ceri.deskmap.Color;
import fr.univavignon.ceri.deskmap.Settings;
import fr.univavignon.ceri.deskmap.region.Highway;

/**
 * @author Yanis Labrak
 */
public class Pedestrian extends Line implements Highway, Level4 {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the data
	 */
	public Pedestrian(String id, String name) {
		super(id, name, Settings.LEVEL_4_ROAD_THICKNESS, Color.PEDESTRIAN);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the data
	 */
	public Pedestrian(Long id, String name) {
		super(id, name, Settings.LEVEL_4_ROAD_THICKNESS, Color.PEDESTRIAN);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Pedestrian(Long id) {
		super(id, "", Settings.LEVEL_4_ROAD_THICKNESS, Color.PEDESTRIAN);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Pedestrian(String id) {
		super(id, "", Settings.LEVEL_4_ROAD_THICKNESS, Color.PEDESTRIAN);
	}

}
