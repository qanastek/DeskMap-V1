package fr.univavignon.ceri.deskmap;

import java.util.Arrays;
import java.util.List;

/**
 * A class which represent the structure of an OSM Node
 * @author Yanis Labrak
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
	 * @author Yanis Labrak
	 */
	public Node(String id, Double lat, Double lon) {
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
	}

	/**
	 * @param id {@code String} Identifier
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 * @author Yanis Labrak
	 */
	public Node(Long id, Double lat, Double lon) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
//		this.Node_projection();
	}
	
	@Override
	public String toString() {
		return "[Id: " + this.id + ", Lat: " + this.lat + ", Lon: " + this.lon + "]";
	}
	
	/**
	 * Hyperbolic Tangent of
	 * @param x {@code Double} Value
	 * @return {@code Double} Return the hyperbolic Tangent of value
	 */
	static double atanh(double x)
	{
		return ( 0.5*Math.log((x + 1.0) / (1.0 - x) ));
	}
	
	/**
	 * Transform the geographical coordinates into canvas coordinates
	 * @param x {@code Double} x
	 * @param y {@code Double} y
	 * @return Return the {@code x} and {@code y} of the {@code Node} converted into pixels 
	 * @author Philippe Beraud - Writer of the article on what I base my algorithm
	 * @author Yanis Labrak - Adaptation of the formula
	 * @see <a href="https://blogs.msdn.microsoft.com/ogdifrance/2011/07/13/de-la-go-et-des-maths/?fbclid=IwAR3efsf9pp87SdKcxNy71T79GPfu7wcxwE-2JhpUWKYOhxW91f38fa_CynY">blogs.msdn.microsoft.com</a>
	 */
	public static List<Double> toPixel(Double x, Double y) {
		
		// In meters
		Double latMeters = y / 111110;
		// Longitude in meters
		Double lonMeters = 111110 * Math.cos(latMeters);

		Double bboxWidth = Map.bottomRight * lonMeters - Map.bottomLeft * lonMeters;
		Double bboxHeight = Map.topRight * latMeters - Map.topLeft * latMeters;
		
		Double ratioWidthPixel = Map.width / bboxWidth;
		Double ratioHeightPixel = Map.height / bboxHeight;
		
		// Set the map scale
		Map.scaleMeter.set((int) (50 * ratioWidthPixel));
		
		// Lat
		Double posVertical = x * latMeters -  Map.topLeft *latMeters;
		// Lon
		Double posHorizontal = y * lonMeters -  Map.bottomLeft * lonMeters;
		
		// Lat
		x = posVertical * ratioHeightPixel;
		// Lon
		y = posHorizontal * ratioWidthPixel;
		
		return Arrays.asList(x,y);
	}
}
