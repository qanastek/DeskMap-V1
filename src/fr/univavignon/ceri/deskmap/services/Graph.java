package fr.univavignon.ceri.deskmap.services;
import java.util.List;

import fr.univavignon.ceri.deskmap.models.Edge;
import fr.univavignon.ceri.deskmap.models.Node;

public class Graph {
	private final List<Node> nodes;
	private final List<Edge> edges;
	
	public Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}
	
	public List<Node> getNodes(){
		return this.nodes;
	}
	
	public List<Edge> getEdges(){
		return this.edges;
	}
}
