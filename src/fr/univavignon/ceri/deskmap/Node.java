package fr.univavignon.ceri.deskmap;

import java.lang.Math;
/**
 * @author Yanis Labrak
 *
 */
public class Node {
	public Long id;
	public Double lat;
	public Double lon;
	
	/**
	 * @param id
	 * @param lat
	 * @param lon
	 * @author Yanis Labrak
	 */
	public Node(String id, Double lat, Double lon) {
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
	}
	
	public Node(Double lat, Double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	static double atanh(double x)
	{
	return ( 0.5*Math.log((x + 1.0) / (1.0 - x) ));
	}
	
	
	/**
	 * @see <a href="https://georezo.net/forum/viewtopic.php?id=94465">Conversion WGS84 vers RGF93 en PHP</a>
	 * Interpreté par BEN-YAMNA Mohamed
	 */
	
	public static Node Node_projection(double latitude, double longitude) { //FROM WGS-84 TO RGF93
	    double c= 11754255.426096; //constante de la projection
	    double e= 0.0818191910428158; //première exentricité de l'ellipsoïde
	    double n= 0.725607765053267; //exposant de la projection
	    double xs= 700000; //coordonnées en projection du pole
	    double ys= 12655612.049876; //coordonnées en projection du pole
		
	    double lat_rad= latitude/180*3.14159265359; //latitude en rad
	    double lat_iso= Node.atanh(Math.sin(lat_rad))-e*Node.atanh(e*Math.sin(lat_rad)); //latitude isométrique

	    double x= ((c*Math.exp(-n*(lat_iso)))*Math.sin(n*(longitude-3)/180*3.14159265359)+xs);
	    double y= (ys-(c*Math.exp(-n*(lat_iso)))*Math.cos(n*(longitude-3)/180*3.14159265359));

		return new Node(x,y);
	}



	@Override
	public String toString() {
		return "Node [id=" + id + ", lat=" + lat + ", lon=" + lon + "]";
	}
	
	
	
}
