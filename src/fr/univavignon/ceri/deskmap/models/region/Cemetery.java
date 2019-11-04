package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class Cemetery extends Region implements Structure {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the {@code Cemetery}
	 */
	public Cemetery(String id, String name) {
		super(id, name, Color.CEMETERY);
	}

	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Cemetery(Long id) {
		super(id, "", Color.CEMETERY);
	}

}
