package fr.univavignon.ceri.deskmap.models;

import java.util.Arrays;
import java.util.List;

import fr.univavignon.ceri.deskmap.Map;

/**
 * A class which represent the structure of an OSM Node
 * @author Mohamed BEN YAMNA
 */
public class Node {
	
	/**
	 * {@code Long} Identifier for the node
	 */
	public Long id;
	
	/**
	 * {@code Double} Earth latitude coordinate
	 */
	public Double lat;
	
	/**
	 * {@code Double} Earth longitude coordinate
	 */
	public Double lon;
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 */
	public Node(String id, Double lat, Double lon) {
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
	}
	
	/**
	 * Constructor
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 */
	public Node(Double lat, Double lon) {
		this.id = null;
		this.lat = lat;
		this.lon = lon;
	}

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 */
	public Node(Long id, Double lat, Double lon) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
	}
	
	/**
	 * Transform the geographical coordinates into canvas coordinates
	 * @param lat {@code Double} x
	 * @param lon {@code Double} y
	 * @return Return the {@code x} and {@code y} of the {@code Node} converted into pixels 
	 * @author Philippe Beraud - Writer of the article on what I base my algorithm
	 * @author Mohamed BEN YAMNA - Adaptation of the formula
	 * @see <a href="https://blogs.msdn.microsoft.com/ogdifrance/2011/07/13/de-la-go-et-des-maths/?fbclid=IwAR3efsf9pp87SdKcxNy71T79GPfu7wcxwE-2JhpUWKYOhxW91f38fa_CynY">blogs.msdn.microsoft.com</a>
	 */
	public static List<Double> toPixel(Double lat, Double lon) {
		
		// Get the value of a degres of latitude into meters
		Double latMeters = lon / 111110;
		
		// Get the value of a degres of longitude into meters
		Double lonMeters = 111110 * Math.cos(latMeters);

		// Get the width in meters
		Double bboxWidth = Map.right * lonMeters - Map.left * lonMeters;
		// Get the height in meters
		Double bboxHeight = Map.top * latMeters - Map.bottom * latMeters;
		
		// Get the width in pixel
		Double ratioWidthPixel = Map.width / bboxWidth * Map.scale;
		// Get the height in pixel
		Double ratioHeightPixel = Map.height / bboxHeight * Map.scale;
		
		// Set the map scale value on the screen
		Map.scaleMeter.set((int) (50 * ratioWidthPixel));
		
		// Latitude
		Double posVertical = (lat - Map.bottom) * latMeters;
		// Longitude
		Double posHorizontal = (lon - Map.left) * lonMeters;
		
		// Move the node of X in latitude
		posVertical += Map.latitude;
		// Move the node of X in longitude
		posHorizontal += Map.longitude;
		
		// Latitude in pixel for the canvas
		lat = posVertical * ratioHeightPixel;
		// Longitude in pixel for the canvas
		lon = posHorizontal * ratioWidthPixel;
		
		return Arrays.asList(lon, lat);
	}
		
	@Override
	public String toString() {
		return "[Id: " + this.id + ", Lat: " + this.lat + ", Lon: " + this.lon + "]";
	}

	/**
	 * Check If the {@code Node} is inside the {@code Bbox} area.
	 * @param bbox {@code Bbox} The area were the {@code Node} need to be
	 * @return
	 * {@code True} If inside the {@code Bbox}
	 * <br>
	 * {@code False} If not inside the {@code bbox}
	 */
	public boolean in(Bbox bbox) {
		
		if (bbox.topLeft < this.lon && this.lon < bbox.topRight) {
			if (bbox.bottomLeft < this.lat && this.lat < bbox.topLeft) {
				System.out.println("Dedant");
				return true;
			}
		}
		return false;
	}
}