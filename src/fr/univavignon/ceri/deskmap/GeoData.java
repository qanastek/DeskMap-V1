/**
 * 
 */
package fr.univavignon.ceri.deskmap;

/**
 * A GeoData is something which will be displayed like a {@code Region}, a point of interest or {@code Line} (road, building, etc ...)
 * @author Yanis Labrak
 */
public abstract class GeoData {
	
	/**
	 * The name of the data
	 */
	protected String name;
	
	/**
	 * Constructor
	 * @param name Name of the data
	 */
	public GeoData(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	protected String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

}
