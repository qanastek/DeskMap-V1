package fr.univavignon.ceri.deskmap.models.region;

import fr.univavignon.ceri.deskmap.config.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class FarmLand extends Region implements Landuse {

	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the forest
	 */
	public FarmLand(String id, String name) {
		super(id, name, Color.FARM_LAND);
	}

	/**
	 * Constructor with name
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the forest
	 */
	public FarmLand(Long id, String name) {
		super(id, name, Color.FARM_LAND);
	}

	/**
	 * Constructor with name
	 * @param id {@code Long} Identifier
	 */
	public FarmLand(Long id) {
		super(id, "", Color.FARM_LAND);
	}

	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 */
	public FarmLand(String id) {
		super(id, "", Color.FARM_LAND);
	}

}
