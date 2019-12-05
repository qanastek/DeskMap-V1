package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.deskmap.models.Node;

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
	private int[][] maze;
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
    private final boolean diag;
    
    /**
     * @param maze distance
     * @param xstart longitude
     * @param ystart latitude
     * @param diag	diagram
     */
    AStar(int[][] maze, Double xstart, Double ystart, boolean diag) {
        this.open = new ArrayList<>();
        this.close = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node("",xstart, ystart, 0, 0, null);
        this.xstart = xstart;
        this.ystart = ystart;
        this.diag = diag;
    }
	
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
	
	private void addNeigborsToOpenList() {
		
	}
}
