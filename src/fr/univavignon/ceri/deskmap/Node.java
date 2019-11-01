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
	 * Convert from WGS-84 format to RGF93
	 * @author loic.buscoz <i>Author</i>
	 * @author BEN-YAMNA Mohamed <i>Adaptation</i>
	 * @param lat {@code Double} Latitude
	 * @param lon {@code Double} Longitude
	 * @return {@code Double[]} x and y after projection
	 * @see <a href="https://georezo.net/forum/viewtopic.php?id=94465">Conversion WGS84 vers RGF93 en PHP</a>
	 */
	public static Double[] Node_projection(Double lat, Double lon) {
		
	    double c = 11754255.426096;
	    double e = 0.0818191910428158;
	    double n = 0.725607765053267;
	    double xs = 700000;
	    double ys = 12655612.049876;
		
	    double lat_rad = lat/180*3.14159265359;
	    double lat_iso = Node.atanh(Math.sin(lat_rad))-e*Node.atanh(e*Math.sin(lat_rad));

	    double x = ((c*Math.exp(-n*(lat_iso)))*Math.sin(n*(lon-3)/180*3.14159265359)+xs);
	    double y = (ys-(c*Math.exp(-n*(lat_iso)))*Math.cos(n*(lon-3)/180*3.14159265359));
	    
	    return new Double[] {x,y};
	}
	
	/**
	 * Tranform the geographical coordinates into canvas coordinates
	 * @param x {@code Double} x
	 * @param y {@code Double} y
	 * @return Return the {@code x} and {@code y} of the {@code Node} converted into pixels 
	 */
	public static List<Double> toPixel(Double x, Double y) {
		
//		Double[] projection = Node.Node_projection(x, y);
//		
//		x = projection[0];
//		y = projection[1];
//		
//	    System.out.println(x);
//	    System.out.println(y);
		
		// TODO: continuous		
		Double bboxWidth = Map.bottomRight - Map.bottomLeft;
		Double bboxHeight = Map.topRight - Map.topLeft;
		
		Double ratioWidthPixel = Map.width / bboxWidth;
		Double ratioHeightPixel = Map.height / bboxHeight;

		// Lat
		Double posVertical = x -  Map.topLeft;
		// Lon
		Double posHorizontal = y -  Map.bottomLeft;
		
		// Lat
		x = posVertical * ratioHeightPixel;
		
		// Lon
		y = posHorizontal * ratioWidthPixel;
		
		
//		System.out.println("----------------");
		
		// 48.8+ = vertical
		// 2.3+ = horizontal
		
//		System.out.println(Map.topLeft + "," + Map.bottomLeft + "," + Map.topRight + "," + Map.bottomRight);
//		
//		System.out.println("BBOX Width: " + bboxWidth);
//		System.out.println("BBOX Height: " + bboxHeight);
//		
//		System.out.println("ratioWidthPixel: " + ratioWidthPixel);
//		System.out.println("ratioHeightPixel: " + ratioHeightPixel);
//		
//		System.out.println("positionLon: " + posHorizontal);
//		System.out.println("positionLat: " + posVertical);
//		
//		System.out.println(x + " -- " + y);
		
		return Arrays.asList(x,y);
	}
}
