/**
 * 
 */
package fr.univavignon.ceri.deskmap.geopoint;

import fr.univavignon.ceri.deskmap.GeoData;
import fr.univavignon.ceri.deskmap.Node;

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
	 * @param id {@code String} Identifier
	 * @param name Name of the place
	 * @param point The only {@code Node} of the place
	 */
	public GeoPoint(String id, String name, Node point) {
		super(Long.parseLong(id),name);
		this.point = point;
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param name Name of the place
	 * @param point The only {@code Node} of the place
	 */
	public GeoPoint(Long id, String name, Node point) {
		super(id,name);
		this.point = point;
	}
	
	@Override
	public String toString() {
		return "[Id: " + this.point.id + ",Lat: " + this.point.lat + ",Lon: " + this.point.lon + ",Name: " + this.name + "]";
	}
}