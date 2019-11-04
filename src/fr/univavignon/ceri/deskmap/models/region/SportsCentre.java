package fr.univavignon.ceri.deskmap.region;

import fr.univavignon.ceri.deskmap.Color;

/**
 * @author Mohamed BEN YAMNA
 */
public class SportsCentre extends Region implements Leisure {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the sport centre
	 */
	public SportsCentre(String id, String name) {
		super(id, name, Color.SPORT_CENTRE);
	}

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public SportsCentre(String id) {
		super(id, "", Color.SPORT_CENTRE);
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public SportsCentre(Long id) {
		super(id, "", Color.SPORT_CENTRE);
	}

}
