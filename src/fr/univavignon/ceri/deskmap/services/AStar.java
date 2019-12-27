package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.controllers.MainViewController;
import fr.univavignon.ceri.deskmap.models.GeoData;
import fr.univavignon.ceri.deskmap.models.Node;
import fr.univavignon.ceri.deskmap.models.NodePath;
import fr.univavignon.ceri.deskmap.models.line.Road;
import fr.univavignon.ceri.deskmap.models.region.Highway;
import javafx.scene.paint.Color;

/**
 * The A star algorithm
 * @author Zheng Zihao
 * @author Labrak Yanis
 * @author Ben Yamna Mohamed
 */
public class AStar {

	public static List<NodePath> path;
	private List<Node> close;
	private List<NodePath> banned;

	private NodePath now;
	private Node departure = Map.nodes.get(Long.parseLong("926350446"));
	private Node arrival = Map.nodes.get(Long.parseLong("937057575"));

	public AStar() {

		this.now = new NodePath(this.departure);

		AStar.path = new ArrayList<NodePath>();
		this.close = new ArrayList<Node>();
		this.banned = new ArrayList<NodePath>();
	}
	
	public void displayFromTo() {
		this.displayPOI(this.departure); 
		this.displayPOI(this.arrival); 
	}
	
	public void displayPOI(Node node) {
		
		// Coordinate after processing
		List<Double> coordinates = Node.toPixel(node.lat, node.lon);
		
		Double x = coordinates.get(0);
		Double y = Map.height - coordinates.get(1);

	    MainViewController.gc.setFill(Color.RED);
	    MainViewController.gc.setStroke(Color.RED);
//		MainViewController.gc.fillOval(x, y, Map.scale/5, Map.scale/5);
		MainViewController.gc.fillOval(x, y, 100,100);
	    
	}

	/**
	 * Working well
	 * 
	 * @return
	 */
	public ArrayList<Node> getNeithboors() {

		this.close.clear();
		ArrayList<Node> around = new ArrayList<Node>();

		for (Long key : Map.mapContent.keySet()) {

			GeoData g = Map.mapContent.get(key);

			if (g instanceof Road && ((Road) g).getNodes().contains(now.id) && g.name != null) {

				int index = ((Road) g).getNodes().indexOf(now.id);
				int max = ((Road) g).getNodes().size() - 1;
				int before = index - 1 >= 0 ? index - 1 : 0;
				int after = index + 1 <= max ? index + 1 : max;

				Node b = Map.nodes.get(((Road) g).getNodes().get(before));
				Node a = Map.nodes.get(((Road) g).getNodes().get(after));

				around.add(b);
				around.add(a);
			}
		}

		return around;
	}

	/**
	 * @param xend destination lat
	 * @param yend destination lon
	 * @return path of node
	 */
	public List<NodePath> findPath() {
		
		System.out.println("/*START*/");

		AStar.path.add(this.now);

		this.close = this.getNeithboors();
		boolean ban;

		// If have parent
		if (this.now.parent != null) {
			
			Iterator<Node> it = this.close.iterator();
			
			while (it.hasNext()) {
				
				Node n = it.next();
				ban = false;
				
				// Delete all banned
				for (NodePath nodePath : this.banned) {
					if (nodePath.id == this.now.id) {
						ban = true;
						break;
					}
				}
				
				System.out.println(ban ? "true ban" : "false ban");
				
				if (ban) {
					it.remove();
				}
				// Delete current Node
				else if (n.id == this.now.parent.id || n.id == this.now.id) {
					System.out.println("/// CLOSE START");
					System.out.println(this.close);
					System.out.println("/// CLOSE REMOVE");
					it.remove();
					System.out.println(this.close);
					System.out.println("/// CLOSE END");
				}
			}
		}

		// If we have neighbors
		if (this.close.size() > 0) {

			this.now = this.getCloser();
			System.out.println("NowAfter");
			System.out.println(this.now);

			// If we reach the end
			if (this.now.id == this.arrival.id) {
				System.out.println("FINDED");
				AStar.path.add(this.now);				
				Draw.drawPath(MainViewController.gc);
				return AStar.path;
			}
			
//			this.displayFromTo();
			
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			// Not found but not blocked
			this.findPath();

		} else {

			// If blocked
			if (this.now.id == this.departure.id) {
				System.err.println("No path found !");
				return null;
			}
			
			System.out.println("----- Ban S -----");
			System.out.println(this.banned);
			System.out.println("----- Ban E -----");

			this.now = this.now.parent;
			this.banned.add(this.now);
			AStar.path.add(0, this.now);
			Draw.drawPath(MainViewController.gc);
			this.findPath();

		}
		
		return null;

	}

	private NodePath getCloser() {

		Double bestDistance = Double.MAX_VALUE;

		NodePath best = null;

		Double d1;
		Double d2;
		Double distance;

		for (Node node : this.close) {

			d1 = this.distance(node, this.now);
			d2 = this.distance(node, this.arrival);
			distance = d1 + d2;

			if (distance < bestDistance) {
				
				best = new NodePath(node);
				best.distance = d1;
				best.parent = this.now;
				
				bestDistance = distance;
			}
		}
		System.out.println("----------------");
		System.out.println("Best");
		System.out.println(best);
		System.out.println("Now");
		System.out.println(this.now);
		System.out.println("Now Parent");
		System.out.println(this.now.parent);
		System.out.println("----------------");
		
		return best;
	}

	/**
	 * @param id id node
	 */
	public double distance(Node n, Node n1) {
		return Math.sqrt(Math.pow((n1.lat - n.lat), 2) + Math.pow((n1.lon - n.lon), 2));
	}

	/**
	 * @param id id node
	 * @return ditance g
	 */
	public double calculerG(Long id) {
		// It is about 111.12 kilometers per degree
		double g = 111.12 * Math.sqrt(Math.pow((this.now.lat - Map.nodes.get(id).lat), 2)
				+ Math.pow((this.now.lon - Map.nodes.get(id).lon), 2));
		return g;
	}
	/*
	
			 * public double calculerH(Long id) { double h = 111.12 *
			 * Math.sqrt(Math.pow((this.xend - Map.nodes.get(id).lat), 2) +
			 * Math.pow((this.yend - Map.nodes.get(id).lon), 2));
			 * 
			 * return h;
			 * 
			 * }
			 */
}