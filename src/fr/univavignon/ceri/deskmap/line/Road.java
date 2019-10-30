/**
 * 
 */
package fr.univavignon.ceri.deskmap.line;

/**
 * @author Yanis Labrak
 */
public class Road extends Line {

	/**
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the road
	 * @param thickness {@code Integer} Thickness of the road line
	 */
	public Road(String id, String name, Integer thickness) {
		super(id, name, thickness, "#ffffff");
	}

}