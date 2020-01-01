/**
 * 
 */
package fr.univavignon.ceri.deskmap.models;

/**
 * @author Yanis Labrak
 *
 */
public class Bbox {
	
	/**
	 * {@code Double} The lowest latitude
	 */
	public Double topLeft;
	
	/**
	 * {@code Double} The highest latitude
	 */
	public Double topRight;
	
	/**
	 * {@code Double} The lowest longitude
	 */
	public Double bottomLeft;
	
	/**
	 * {@code Double} The highest longitude
	 */
	public Double bottomRight;

	/**
	 * Constructor
	 * @param topLeft {@code Double}
	 * @param topRight {@code Double}
	 * @param bottomLeft {@code Double}
	 * @param bottomRight {@code Double}
	 */
	public Bbox(Double topLeft, Double topRight, Double bottomLeft, Double bottomRight) {
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
	}

	@Override
	public String toString() {
		return this.topLeft + "," + this.bottomLeft + "," + this.topRight + "," + this.bottomRight;
	}

}
