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
	 * 
	 */
	public Double topLeft;
	
	/**
	 * 
	 */
	public Double topRight;
	
	/**
	 * 
	 */
	public Double bottomLeft;
	
	/**
	 * 
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
