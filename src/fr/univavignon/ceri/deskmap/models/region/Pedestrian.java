package fr.univavignon.ceri.deskmap.models.region;

/**
 * @author Mohamed BEN YAMNA
 */
public class Pedestrian extends Road {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the road
	 * @param color {@code String} Color of the road
	 */
	public Pedestrian(String id, String name, String color) {
		super(id, name, color);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the road
	 * @param color {@code String} Color of the road
	 */
	public Pedestrian(Long id, String name, String color) {
		super(id, name, color);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Pedestrian(Long id) {
		super(id);
	}

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Pedestrian(String id) {
		super(id);
	}

}
