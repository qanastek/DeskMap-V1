/**
 * 
 */
package fr.univavignon.ceri.deskmap;

/**
 * @author Yanis Labrak
 */
public class Forest extends Region {

	/**
	 * Constructor with name
	 * @param name {@code String} Name of the forest
	 */
	public Forest(String name) {
		super(name, "#c7e8c6");
	}
	
	/**
	 * Constructor without name
	 */
	public Forest() {
		super("", "#c7e8c6");
	}

}
