package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.univavignon.ceri.deskmap.models.Node;
import javafx.geometry.Point2D;


/**
 * @author ZIHAO ZHENG
 *
 */
public class Trajet {
	
	public Double latFrom = 43.94728;
	public Double lonFrom = 4.80997;
	
	public Double latTo = 43.94626;
	public Double lonTo = 4.81048;
	
	private Set<Node> nodes;
	
	/**
	 * @return distance entre deux node
	 * @author ZHENG Zihao
	 */
	private Double calculeDistance() {
		Double distance = 0.0;
		
		distance = Math.sqrt(Math.pow((this.latFrom-this.latTo), 2)+Math.pow((this.lonFrom-this.lonTo), 2));
		
		return distance;
	}
	
	/**
	 * @return center point between the departure and arrival
	 */
	private Point2D centreAdresse() {
		Double latTem = 0.0;
		Double lonTem = 0.0;
		
		latTem = Math.abs(this.latFrom-this.latTo);
		lonTem = Math.abs(this.lonFrom-this.lonTo);
		
		Point2D point = new Point2D(latTem, lonTem);
		
		return point;
	}
	
	private List<Node> getNeighbors(Node n){
		List<Node> neighbors = new ArrayList<Node>();
		
		
		this.afficheNode(n);
		this.afficheNearNode();
		//Research the neighbour of the node
		return neighbors;
	}
	
	private Node getMinimNode(Set<Node> n) {
		Node minimumNode = null;
		
		//Return minimum distance of the node
		return minimumNode;
	}
	
	private void afficheNode(Node n) {
		
	}
	
	private void afficheNearNode() {
		
	}
}
