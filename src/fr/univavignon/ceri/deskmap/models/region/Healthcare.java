package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Healthcare extends Region {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the hospital
	 */
	public Healthcare(String id, String name) {
		super(id, "", Color.HEALTHCARE);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name {@code String} Name of the hospital
	 */
	public Healthcare(Long id, String name) {
		super(id, "", Color.HEALTHCARE);
	}

	/**
	 * Constructor without name
	 * @param id {@code Long} Identifier
	 */
	public Healthcare(Long id) {
		super(id, "", Color.HEALTHCARE);
	}

	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Healthcare(String id) {
		super(id, "", Color.HEALTHCARE);
	}

}
