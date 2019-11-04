package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class SchoolAmenity extends Region {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the school
	 * @param color {@code String} Background color
	 */
	public SchoolAmenity(String id, String name, String color) {
		super(id, name, Color.SCHOOL);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the school
	 * @param color {@code String} Background color
	 */
	public SchoolAmenity(Long id, String name, String color) {
		super(id, name, Color.SCHOOL);
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the school
	 */
	public SchoolAmenity(String id, String name) {
		super(id, name, Color.SCHOOL);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the school
	 */
	public SchoolAmenity(Long id, String name) {
		super(id, name, Color.SCHOOL);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public SchoolAmenity(Long id) {
		super(id);
	}

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public SchoolAmenity(String id) {
		super(id);
	}

}
