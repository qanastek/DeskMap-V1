/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import fr.univavignon.ceri.deskmap.region.Region;

/**
 * @author Yanis Labrak
 *
 */
public class Way extends Region {

	/**
	 * @param id
	 * @param name
	 * @param color
	 */
	public Way(String id, String name, String color) {
		super(id, name, color);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param name
	 * @param color
	 */
	public Way(Long id, String name, String color) {
		super(id, name, color);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 */
	public Way(Long id) {
		super(id, "", "#000000");
	}

}
