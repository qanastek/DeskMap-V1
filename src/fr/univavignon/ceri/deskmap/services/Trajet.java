package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.univavignon.ceri.deskmap.models.Bbox;
import fr.univavignon.ceri.deskmap.models.Node;
import javafx.geometry.Point2D;


/**
 * @author Labrak Yanis
 * @author ZIHAO ZHENG
 */
public class Trajet {
	
	/**
	 * @return Distance between two nodes
	 * @param from {@code Point2D}
	 * @param to {@code Point2D}
	 * @author ZHENG Zihao
	 */
	public static Double calculeDistance(Point2D from, Point2D to) {		
		return Math.sqrt(Math.pow((from.getX() - to.getX()), 2) + Math.pow((from.getY() - to.getY()), 2));
	}
	
	/**
	 * Get the center point between the departure and arrival
	 * @param from {@code Point2D} The departure
	 * @param to {@code Point2D} The arrival
	 * @return {@code Point2D} The center point
	 */
	public static Point2D centreAdresse(Point2D from, Point2D to) {
		
		Double x = Math.abs(from.getX() - to.getX());
		Double y = Math.abs(from.getY() - to.getY());
		
		return new Point2D(x, y);
	}
	
	/**
	 * Generate a BBOX thanks to the centered point
	 * @param center {@code Point2D}
	 * @return {@code Bbox}
	 * 
	 */
	public static Bbox generateBbox(Point2D center) {		
		return OSM.bboxCalc(center.getX(), center.getY());

	}
	
	/**
	 * @param n {@code Node}
	 * @return {@code List<Node>}
	 */
	public static List<Node> getNeighbors(Node n){
		
		List<Node> neighbors = new ArrayList<Node>();
		
		
//		this.afficheNode(n);
//		this.afficheNearNode();
		// Research the neighbors of the node
		return neighbors;
	}
	
	/**
	 * @param n {@code Node}
	 * @return {@code Node}
	 */
	public static Node getMinimNode(Set<Node> n) {
		Node minimumNode = null;
		
		// Return minimum distance of the node
		return minimumNode;
	}
	
	public static void afficheNode(Node n) {
		
	}
	
	public static void afficheNearNode() {
		
	}
	
	public static void main(String[] args) {	

		Set<Node> nodes;
				
		Point2D from = new Point2D(43.94728, 4.80997);
		Point2D to = new Point2D(43.94626, 4.81048);
		
		Double distance = Trajet.calculeDistance(from, to);
		Point2D centre = Trajet.centreAdresse(from, to);
		Bbox b = Trajet.generateBbox(centre);
		
		System.out.println(distance);
		System.out.println(centre);
		System.out.println(b);
	}
}
