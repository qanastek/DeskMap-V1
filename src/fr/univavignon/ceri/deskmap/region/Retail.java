/**
 * 
 */
package fr.univavignon.ceri.deskmap.region;

/**
 * @author Yanis Labrak
 *
 */
public class Retail extends Region implements Landuse {

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 */
	public Retail(String id) {
		super(id, "", "#ffd5d0");
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 */
	public Retail(Long id) {
		super(id, "", "#ffd5d0");
	}

}
