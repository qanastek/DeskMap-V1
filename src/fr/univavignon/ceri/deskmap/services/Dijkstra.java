package fr.univavignon.ceri.deskmap.services;

import fr.univavignon.ceri.deskmap.models.Node;

public class Dijkstra {
	private static Node start = null;
	private static Node end = null;
	
	public Dijkstra(Node from, Node to) {

		start = from;
		end = to;
	}
}
