package fr.univavignon.ceri.deskmap.services;

import fr.univavignon.ceri.deskmap.models.Node;

/**
 * @author Zheng Zihao
 */
public class Dijkstra {
	
	/**
	 * Departure coordinates
	 */
	public static Node START = null;
	
	/**
	 * Arrival coordinates
	 */
	public static Node END = null;
	
	/**
	 * @param from {@code Node}
	 * @param to {@code Node}
	 * @author Zheng Zihao
	 */
	public Dijkstra(Node from, Node to) {

		Dijkstra.START = from;
		Dijkstra.END = to;
	}
}
