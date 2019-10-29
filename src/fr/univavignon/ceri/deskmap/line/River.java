/**
 * 
 */
package fr.univavignon.ceri.deskmap.line;

/**
 * @author Yanis Labrak
 *
 */
public class River extends Line {

	/**
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the river
	 * @param thickness {@code Integer} Thickness of the river line
	 */
	public River(String id, String name, Integer thickness) {
		super(id, name, thickness, "#aadaff");
	}

}
