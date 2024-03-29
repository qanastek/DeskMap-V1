package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.controllers.MainViewController;
import fr.univavignon.ceri.deskmap.models.GeoData;
import fr.univavignon.ceri.deskmap.models.Node;
import fr.univavignon.ceri.deskmap.models.NodePath;
import fr.univavignon.ceri.deskmap.models.line.Road;
import javafx.scene.paint.Color;

/**
 * The A star algorithm
 * @author Ben Yamna Mohamed
 * @author Labrak Yanis
 * @author Zheng Zihao
 */
public class AStar {
	
	/**
	 * The departure of the path
	 */
	private Node departure;
	
	/**
	 * The arrival of the path
	 */
	private Node arrival;

	/**
	 * The current position
	 */
	private NodePath now;

	/**
	 * The path
	 */
	public static List<NodePath> path;
	
	/**
	 * The list of all the closest {@code Node}'s
	 */
	private List<Node> close;
	
	/**
	 * All the banned {@code NodePath} were we cannot go anymore
	 */
	private List<NodePath> banned;

	/**
	 * Constructor
	 * @author Zihao Zheng
	 * @author Mohamed Ben Yamna
	 */
	public AStar() {
		
		this.departure = Map.nodes.get(Long.parseLong("2006280968"));
		this.arrival = Map.nodes.get(Long.parseLong("32320483"));

		this.now = new NodePath(this.departure);

		AStar.path = new ArrayList<NodePath>();
		this.close = new ArrayList<Node>();
		this.banned = new ArrayList<NodePath>();
	}
	
	/**
	 * Constructor
	 * @param from {@code Node} The departure {@code Node}
	 * @param to {@code Node} The arrival {@code Node}
	 * @author Zihao Zheng
	 * @author Mohamed Ben Yamna
	 */
	public AStar(Node from, Node to) {
		
		this.departure = from;
		this.arrival = to;

		this.now = new NodePath(this.departure);
		
		AStar.path = new ArrayList<NodePath>();
		this.close = new ArrayList<Node>();
		this.banned = new ArrayList<NodePath>();
	}
	
	/**
	 * Display the departure and the arrival of the path
	 * @author Yanis Labrak
	 */
	public void displayFromTo() {
		this.displayPOI(this.departure); 
		this.displayPOI(this.arrival); 
	}
	
	/**
	 * Display on the map a point of interest
	 * @param node {@code Node} The node to display
	 * @author Yanis Labrak
	 */
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
	 * Get all the neighbors were we can go on
	 * @return {@code ArrayList<Node>} Were we can go on
	 * @author Mohamed Ben Yamna
	 */
	public ArrayList<Node> getNeithboors() {

		if (this.close != null && this.close.size() > 0) {
//			this.close.clear();
			this.close = new ArrayList<Node>();
		}
		
		ArrayList<Node> around = new ArrayList<Node>();

		for (Long key : Map.mapContent.keySet()) {

			GeoData g = Map.mapContent.get(key);

			if (this.now != null && g instanceof Road && ((Road) g).getNodes().contains(this.now.id) && g.name != null) {

				int index = ((Road) g).getNodes().indexOf(this.now.id) >= 0 ? ((Road) g).getNodes().indexOf(this.now.id) : 0;
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
	 * Process and display in real time on the {@code Canvas} the path.
	 * @return path of node
	 * @author Zihao Zheng
	 */
	public List<NodePath> oldFindPath() {
		
		if (MainViewController.status == false) {
			System.out.println("Process stoped !");
			return null;
		}

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
				
				if (ban) {
					it.remove();
				}
				// Delete current Node
				else if (n.id == this.now.parent.id || n.id == this.now.id) {
					it.remove();
				}
			}
		}

		// If we have neighbors
		if (this.close.size() > 0) {

			this.now = this.getCloser();

			// If we reach the end
			if (this.now.id == this.arrival.id) {

				MainViewController.addMapPath("Path found !");
				System.out.println("Path found !");
				
				AStar.path.add(this.now);				
				Draw.drawPath(MainViewController.gc);
				
//				System.out.println(AStar.path);
				
				return AStar.path;
			}
			
			this.findPath();

		} else {

			// If blocked
			if (this.now.id == this.departure.id) {
				
				MainViewController.addMapPath("No path found !");
				
				System.out.println("Path no found !");
				
				// Clear the current path
				AStar.path.clear();
				MainViewController.clearMapPathTextArea();
				
				return AStar.path;
			}

			this.now = this.now.parent;
			this.banned.add(this.now);
			
			AStar.path.add(0, this.now);
//			Draw.drawPath(MainViewController.gc);
			this.findPath();

		}
		
		return AStar.path;

	}
	
	/**
	 * Process and display in real time on the {@code Canvas} the path.
	 * @return path of node
	 * @author Zihao Zheng
	 */
	public List<NodePath> findPath() {
		
		while (MainViewController.status != false) {

//			Platform.runLater(()->{
//				MainViewController.renderMap();
//			});
			
//			System.out.println(AStar.path.size());
//			System.out.println(AStar.path);
//			System.out.println("Now: " + this.now);
//			System.out.println("Close: " + this.close);
//			System.out.println("Size: " + this.close.size());
			AStar.path.add(this.now);
			
			this.close = this.getNeithboors();
			boolean ban;
			
			// If have parent
			if (this.now.parent != null) {
				
				Iterator<Node> it = this.close.iterator();
				
				while (it.hasNext()) {
					
					Node n = it.next();
					ban = false;
					
					// Check if already banned
					for (NodePath nodePath : this.banned) {
						if (nodePath.id == this.now.id) {
							ban = true;
							break;
						}
					}
					
					if (ban) {
						it.remove();
					}
					// Delete current Node
					else if (n.id == this.now.parent.id || n.id == this.now.id) {
						it.remove();
					}
					else {
						
						// If the node has been already visited
						for (NodePath nodeVisited : AStar.path) {
							if (n.id == nodeVisited.id) {
//								System.out.println("--------------------------DELETE");
								it.remove();
								break;
							}							
						}						
					}
				}
			}
			
			// If we have neighbors
			if (this.close.size() > 0) {
				
				this.now = this.getCloser();
				
				// If we reach the end
				if (this.now.id == this.arrival.id) {
					
					MainViewController.addMapPath("Path found !");
					System.out.println("Path found !");
					
					AStar.path.add(this.now);				
					Draw.drawPath(MainViewController.gc);
					
//					System.out.println(AStar.path);
					
					return AStar.path;
				}

				continue;
				
			}
			
			// If blocked without nodes around
			if (this.now.id == this.departure.id) {
				
				// Clear the current path
				AStar.path.clear();
				MainViewController.clearMapPathTextArea();
				
				MainViewController.addMapPath("No path found !");
				System.out.println("Path no found !");
				
				// Cela retourne null
				return AStar.path;
			}
			
			// Else go back
//			System.out.println("Now before parent:" + this.now);
			this.banned.add(this.now);
			this.now = this.now.parent;
//			System.out.println("Now after parent:" + this.now);
			
			AStar.path.add(0, this.now);
			continue;
		}
		
		if (MainViewController.status == false) {
			System.out.println("Process stoped !");
			return null;
		}
		
		return AStar.path;
		
	}

	/**
	 * Get the closest {@code NodePath} around the current position
	 * @return {@code NodePath} The closest node around the current position
	 * @author Mohamed Ben Yamna
	 */
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

			// If the distance is better
			if (distance < bestDistance) {
				
				best = new NodePath(node);
				best.distance = d1;
				best.parent = this.now;
				bestDistance = distance;
				best.setStreet();
			}
		}
		
		return best;
	}

	/**
	 * Calculate the distance between two {@code Node}'s
	 * @param n {@code Node} Departure
	 * @param n1 {@code Node} Arrival
	 * @return {@code Double} The distance
	 * @author Mohamed Ben Yamna
	 */
	public double distance(Node n, Node n1) {
		return Math.sqrt(Math.pow((n1.lat - n.lat), 2) + Math.pow((n1.lon - n.lon), 2));
	}

	/**
	 * Display the informations about the length of the path and all the segments of It too.
	 * @author Capdepon Quentin
	 */
	public static void getPathInformations() {
		
		MainViewController.clearMapPathTextArea();
		
		Double totalDistance = 0.0;
		
		// For each segment
		for (int i = 1; i < AStar.path.size() - 1; i++) {
			
			// Add to the total
			totalDistance += AStar.path.get(i).getDistanceInMeters();
			
			// Display the segment informations
			MainViewController.addMapPath(
				AStar.path.get(i).street + " -> " +
				AStar.path.get(i+1).street + " : " + 
				AStar.path.get(i+1).getDistanceInMeters().toString() + " m"
			);
		}
		
		totalDistance = Math.floor(totalDistance * 100) / 100;
		
		// Print the total length of the path
		MainViewController.addMapPath("Total length of the path: " + totalDistance.toString() + "m");		
	}
}