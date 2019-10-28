/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yanis Labrak
 */
public class Map {

	/**
	 * Scale of the map
	 */
	private Float scale;
	
	/**
	 * Latitude position 
	 */
	private Float latitude;
	
	/**
	 * Longitude position 
	 */
	private Float longitude;
	
	/**
	 * Height 
	 */
	private Float height;

	/**
	 * Width 
	 */
	private Float width;
	
	/**
	 * All the GeoData which will be displayed
	 */
	private List<GeoData> mapContent = new ArrayList<GeoData>();
	
	/**
	 * Constructor
	 */
	public Map() {
		this.scale = (float) 0.0;
		this.latitude = (float) 0.0;
		this.longitude = (float) 0.0;
		this.height = (float) 0.0;
		this.width = (float) 0.0;
	}

	/**
	 * @return the scale
	 */
	public Float getScale() {
		return this.scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(Float scale) {
		this.scale = scale;
	}

	/**
	 * @return the latitude
	 */
	public Float getLatitude() {
		return this.latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Float getLongitude() {
		return this.longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the height
	 */
	public Float getHeight() {
		return this.height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Float height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public Float getWidth() {
		return this.width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Float width) {
		this.width = width;
	}
	
}
