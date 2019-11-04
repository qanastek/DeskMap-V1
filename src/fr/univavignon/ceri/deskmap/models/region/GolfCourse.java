package fr.univavignon.ceri.deskmap.models.region;

import fr.univavignon.ceri.deskmap.config.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class GolfCourse extends Region implements Leisure {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Golf course name
	 */
	public GolfCourse(String id, String name) {
		super(id, name, Color.GOLF_COURSE);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public GolfCourse(String id) {
		super(id, "", Color.GOLF_COURSE);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public GolfCourse(Long id) {
		super(id, "", Color.GOLF_COURSE);
	}

}
