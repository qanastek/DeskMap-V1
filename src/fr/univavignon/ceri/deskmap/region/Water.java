package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 */
public class Water extends Region {

	/**
	 * Constructor
	 * @param name {@code String} Name of the water spot
	 */
	public Water(String name) {
		super(name, "#aadaff");
	}
	
	/**
	 * Constructor without name
	 */
	public Water() {
		super("", "#aadaff");
	}

}
