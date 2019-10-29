/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 */
public class Forest extends Region {

	/**
	 * Constructor with name
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the forest
	 */
	public Forest(String id, String name) {
		super(id, name, "#c7e8c6");
	}
	
	/**
	 * Constructor without name
	 * @param id {@code String} Identifier
	 */
	public Forest(String id) {
		super(id, "", "#c7e8c6");
	}

}
