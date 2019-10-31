/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 *
 */
public class Commercial extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Commercial(String id) {
		super(id, "", "#f2d9d8");
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Commercial(Long id) {
		super(id, "", "#f2d9d8");
	}

}
