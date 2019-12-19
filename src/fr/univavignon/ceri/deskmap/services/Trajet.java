package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.models.Bbox;
import fr.univavignon.ceri.deskmap.models.Node;
import javafx.geometry.Point2D;
import fr.univavignon.ceri.deskmap.services.AStar;

/**
 * @author Labrak Yanis
 * @author ZIHAO ZHENG
 */
public class Trajet {
	
	/**
	 * @return Distance between two nodes
	 * @author ZHENG Zihao
	 */
	public static Double calculeDistance(Point2D from, Point2D to) {
		// It is about 111.12 kilometers per degree
		
		return 111.12 * Math.sqrt(Math.pow((from.getX() - to.getX()), 2) + Math.pow((from.getY() - to.getY()), 2));
	}
	
	/**
	 * Get the center point between the departure and arrival
	 * @return 
	 */
	public static Point2D centreAdresse(Point2D from, Point2D to) {
		// Longitude and latitude of the middle point
		Double x = Math.abs(from.getX() + to.getX()) / 2;
		Double y = Math.abs(from.getY() + to.getY()) / 2;
		
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
	
	public static List<Node> getNeighbors(Node n){
		List<Node> neighbors = new ArrayList<Node>();
		
		
		
		
//		this.afficheNode(n);
//		this.afficheNearNode();
		//Research the neighbour of the node
		return neighbors;
	}
	
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

//		Set<Node> nodes;
//				
//		Point2D from = new Point2D(43.94746, 4.81214);
//		Point2D to = new Point2D(43.94631, 4.81151);
//		
//		Double distance = Trajet.calculeDistance(from, to);
//		Point2D centre = Trajet.centreAdresse(from, to);
//		Bbox b = Trajet.generateBbox(centre);
//		
//		System.out.println(distance);
//		System.out.println(centre);
//		System.out.println(b);
		List<Node> tmp;
		Long debutNode = new Long(309628735);
		Long finNode = new Long(309628731);
		
		System.out.println(Map.nodes.get(debutNode).lat);
//		AStar a = new AStar(debutNode, Map.nodes.get(debutNode).lat, Map.nodes.get(debutNode).lon);
//		tmp = a.findPathTo(Map.nodes.get(finNode).lat, Map.nodes.get(finNode).lon);
//		System.out.println(tmp);
	}
}