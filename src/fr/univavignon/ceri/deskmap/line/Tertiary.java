package fr.univavignon.ceri.deskmap.line;

import fr.univavignon.ceri.deskmap.Color;
import fr.univavignon.ceri.deskmap.Settings;
import fr.univavignon.ceri.deskmap.region.Highway;

/**
 * @author Yanis Labrak
 */
public class Tertiary extends Line implements Highway, Level3 {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the data
	 */
	public Tertiary(String id, String name) {
		super(id, name, Settings.LEVEL_3_ROAD_THICKNESS, Color.TERTIARY);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the data
	 */
	public Tertiary(Long id, String name) {
		super(id, name, Settings.LEVEL_3_ROAD_THICKNESS, Color.TERTIARY);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Tertiary(Long id) {
		super(id, "", Settings.LEVEL_3_ROAD_THICKNESS, Color.TERTIARY);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Tertiary(String id) {
		super(id, "", Settings.LEVEL_3_ROAD_THICKNESS, Color.TERTIARY);
	}

}
