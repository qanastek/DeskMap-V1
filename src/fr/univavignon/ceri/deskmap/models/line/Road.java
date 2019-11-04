package fr.univavignon.ceri.deskmap.line;

import fr.univavignon.ceri.deskmap.region.Highway;

/**
 * @author Mohamed BEN YAMNA
 */
public class Road extends Line implements Highway {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the road
	 * @param thickness {@code Integer} Thickness of the road line
	 */
	public Road(String id, String name, Integer thickness) {
		super(id, name, thickness, "#ffffff");
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param thickness {@code Integer} Thickness of the road line
	 */
	public Road(String id, Integer thickness) {
		super(id, "", thickness, "#ffffff");
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param thickness {@code Integer} Thickness of the road line
	 */
	public Road(Long id, Integer thickness) {
		super(id, "", thickness, "#ffffff");
	}
	
	/**
	 * Constructor identifier only
	 * @param id {@code Long} Identifier
	 */
	public Road(Long id) {
		super(id, "", 1, "#ffffff");
	}
	
	/**
	 * Constructor identifier only
	 * @param id {@code String} Identifier
	 */
	public Road(String id) {
		super(id, "", 1, "#ffffff");
	}

}
