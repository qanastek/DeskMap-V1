/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 *
 */
public class Industrial extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Industrial(String id) {
		super(id, "", "#ff00df");
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Industrial(Long id) {
		super(id, "", "#ff00df");
	}

}
