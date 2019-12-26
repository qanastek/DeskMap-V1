package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.models.GeoData;
import fr.univavignon.ceri.deskmap.models.Node;
import fr.univavignon.ceri.deskmap.models.NodePath;
import fr.univavignon.ceri.deskmap.models.line.Road;
import fr.univavignon.ceri.deskmap.models.region.Highway;

/**
 * @author zheng zihao
 *
 */
public class AStar {

	private List<NodePath> path;
	private List<Node> close;
	private List<NodePath> banned;

	private NodePath now;
	private Node departure = Map.nodes.get(Long.parseLong("4774738526"));
	private Node arrival = Map.nodes.get(Long.parseLong("937548040"));

	/**
	 * node start longitude
	 */
	// private final Double xstart;

	/**
	 * node start latitude
	 */
	// private final Double ystart;

	/**
	 * node end longitude and latitude
	 */
	// private Double xend, yend;

	/**
	 * @param debut  id debut node
	 * @param xstart longitude
	 * @param ystart latitude
	 * @param diag   diagram
	 */
	public AStar() {

		this.now = new NodePath(this.departure);

		this.path = new ArrayList<NodePath>();
		this.close = new ArrayList<Node>();
		this.banned = new ArrayList<NodePath>();

		// this.open = new ArrayList<>();
		// this.xstart = xstart;
		// this.ystart = ystart;
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

		this.path.add(this.now);

		this.close = this.getNeithboors();
		boolean ban;

		// If have parent
		if (this.now.parent != null) {

//			System.out.println(this.close);
			
			// Delete now
			for (int i = 0; i < this.close.size(); i++) {
				
				ban = false;
				Node n = this.close.get(i);
				
				for (NodePath nodePath : banned) {
					if (nodePath.id == this.now.id) {
						ban = true;
					}
				}
				
				
				System.out.println(ban ? "true":"false");
				
				if (n.id == this.now.id || n.id == this.now.parent.id || ban) {
					this.close.remove(i);
					System.out.println("Deleted");
				}
				
			}
		}

		// If we have neighbours
		if (this.close.size() > 0) {

			this.now = this.getCloser();

			// If we reach the end
			if (this.now.id == this.arrival.id) {
				return this.path;
			}
			
			// Not found but not blocked
			this.findPath();

		} else {

			// If blocked
			if (this.now.id == this.departure.id) {
				System.err.println("No path found !");
				return null;
			}
			
			this.banned.add(this.now);
			this.now = this.now.parent;
			this.path.add(0, this.now);

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
				
				System.out.println("Node");
				System.out.println(node);
				
				best = new NodePath(node);
				best.distance = d1;
				best.parent = this.now;
				bestDistance = distance;
			}
		}

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
	
	public static void main(String[] args) {
		AStar a = new AStar();
		System.out.println(a.findPath());
	}
}