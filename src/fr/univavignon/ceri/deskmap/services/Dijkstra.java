package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univavignon.ceri.deskmap.models.Node;

public class Dijkstra {
	private static Node start = null;
	private static Node end = null;
	
	private final List<Node> nodes;
	private final List<Node> edges;
	
	private Set<Node> settledNodes;
	private Set<Node> unSettledNodes;
	 
	
	public Dijkstra(Node from, Node to) {

		this.nodes = new ArrayList<Node>();
		this.edges = new ArrayList<Node>();
		
		start = from;
		end = to;
	}
	
}
