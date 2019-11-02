package fr.univavignon.ceri.deskmap.line;

import fr.univavignon.ceri.deskmap.Color;
import fr.univavignon.ceri.deskmap.Settings;
import fr.univavignon.ceri.deskmap.region.Highway;

/**
 * @author Yanis Labrak
 */
public class Motorway extends Line implements Highway, Level1 {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the data
	 */
	public Motorway(String id, String name) {
		super(id, name, Settings.LEVEL_1_ROAD_THICKNESS, Color.MOTORWAY);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the data
	 */
	public Motorway(Long id, String name) {
		super(id, name, Settings.LEVEL_1_ROAD_THICKNESS, Color.MOTORWAY);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Motorway(Long id) {
		super(id, "", Settings.LEVEL_1_ROAD_THICKNESS, Color.MOTORWAY);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Motorway(String id) {
		super(id, "", Settings.LEVEL_1_ROAD_THICKNESS, Color.MOTORWAY);
	}

}
