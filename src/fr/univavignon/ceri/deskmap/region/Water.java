package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 */
public class Water extends Region {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the water spot
	 */
	public Water(String id, String name) {
		super(id, name, "#aadaff");
	}
	
	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Water(String id) {
		super(id, "", "#aadaff");
	}

}
