package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.models.GeoData;
import fr.univavignon.ceri.deskmap.models.Node;
import fr.univavignon.ceri.deskmap.models.line.Road;
import fr.univavignon.ceri.deskmap.models.region.Highway;


/**
 * @author zheng zihao
 *
 */
public class AStar {
	/**
	 * open list
	 */
	private final List<Node> open;
	private final List<Node> close;
	
	private final List<Node> path;
	private Node now;
	
	/**
	 * node start longitude
	 */
	private final Double xstart;
	
    /**
     * node start latitude
     */
    private final Double ystart;
    
    /**
     * node end longitude and latitude
     */
    private Double xend, yend;
    
    /**
     * @param debut id debut node
     * @param xstart longitude
     * @param ystart latitude
     * @param diag	diagram
     */
    AStar(Long debut,Double xstart, Double ystart) {
        this.open = new ArrayList<>();
        this.close = new ArrayList<>();
        this.path = new ArrayList<>();
        this.now = new Node(debut,xstart, ystart, 0, 0,null);
        this.xstart = xstart;
        this.ystart = ystart;
    }
	
	/**
	 * @param xend destination lat
	 * @param yend destination lon
	 * @return path of node
	 */
	public List<Node> findPathTo(Double xend, Double yend){
		this.xend = xend;
		this.yend = yend;
		
		this.close.add(this.now);
		addNeigborsToOpenList();
		while(this.now.lat != this.xend || this.now.lon != this.xend) {
			if (this.open.isEmpty()) {
				return null;
			}
			this.now = this.open.get(0);
			this.open.remove(0);
			this.close.add(this.now);
			addNeigborsToOpenList();
		}
		
		this.path.add(0, this.now);
		while (this.now.lat != this.xstart || this.now.lon != this.ystart) {
			this.now = this.now.parent;
			
			this.path.add(0, this.now);
		}
		
		return this.path;
	}
	
	/**
	 * get all neigbors of one node
	 */
	private void addNeigborsToOpenList() {
		ArrayList<Long> allNodesArround = new ArrayList<Long>();
		
		for (GeoData obj : Map.mapContent.values()) {
			if (obj instanceof Road) {
				
				int index = ((Road) obj).getNodes().indexOf(this.now.id);
				int max = ((Road) obj).getNodes().size();
				int before;
				int after;
				
				if (index != -1) {
					before = index - 1 >= 0 ? index - 1 : 0;
					after = index + 1 <= max ? index + 1 : max;
					
					allNodesArround.add(((Road) obj).getNodes().get(before));
					allNodesArround.add(((Road) obj).getNodes().get(after));
				}
				
			}	
		}
		double g,h;
		for (Long nodes : allNodesArround) {
			g = calculerG(nodes);
			h = calculerH(nodes);
			Node tmp = Map.nodes.get(nodes);
			this.open.add(new Node(nodes,tmp.lat,tmp.lon,this.now.g+g,h,this.now));
		}
	
//		Collections.sort(this.open);
		
	}
	
	
	/**
	 * @param id id node
	 * @return ditance g
	 */
	public double calculerG(Long id) {
		// It is about 111.12 kilometers per degree
		double g = 111.12 * Math.sqrt(Math.pow((this.now.lat - Map.nodes.get(id).lat), 2) + Math.pow((this.now.lon - Map.nodes.get(id).lon), 2));
		return g;
	}
	
	/**
	 * @param id node
	 * @return distance h
	 */
	public double calculerH(Long id) {
		double h = 111.12 * Math.sqrt(Math.pow((this.xend - Map.nodes.get(id).lat), 2) + Math.pow((this.yend - Map.nodes.get(id).lon), 2));

		return h;
		
	}
}