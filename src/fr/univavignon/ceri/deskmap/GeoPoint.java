/**
 * 
 */
package fr.univavignon.ceri.deskmap;

/**
 * Point of interest like a {@code City} or a {@code Mountain}
 * @author Yanis Labrak
 */
public abstract class GeoPoint extends GeoData {
	
	/**
	 * Node which represent the location
	 */
	protected Node point;

	/**
	 * Constructor
	 * @param name Name of the place
	 * @param point The only {@code Node} of the place
	 */
	public GeoPoint(String name, Node point) {
		super(name);
		this.point = point;
	}
	
	@Override
	public String toString() {
		return "[Id: " + this.point.id + ",Lat: " + this.point.lat + ",Lon: " + this.point.lon + ",Name: " + this.name + "]";
	}
}
