package fr.univavignon.ceri.deskmap;

import java.awt.Point;
import java.awt.geom.Point2D;

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
		this.Node_projection();
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
	 * @see <a href="https://georezo.net/forum/viewtopic.php?id=94465">Conversion WGS84 vers RGF93 en PHP</a>
	 */
	public void Node_projection() {
		
	    double c = 11754255.426096;
	    double e = 0.0818191910428158;
	    double n = 0.725607765053267;
	    double xs = 700000;
	    double ys = 12655612.049876;
		
	    double lat_rad = this.lat/180*3.14159265359;
	    double lat_iso = Node.atanh(Math.sin(lat_rad))-e*Node.atanh(e*Math.sin(lat_rad));

	    double x = ((c*Math.exp(-n*(lat_iso)))*Math.sin(n*(this.lon-3)/180*3.14159265359)+xs);
	    double y = (ys-(c*Math.exp(-n*(lat_iso)))*Math.cos(n*(this.lon-3)/180*3.14159265359));
	    
	    System.out.println(x);
	    System.out.println(y);
	    
	    this.lat = x;
	    this.lon = y;
	}
	
	public static void main(String[] args) {
		Node n = new Node("484848484", 48.8566969, 2.3514616);
		System.out.println(n.toString());
	}
}
