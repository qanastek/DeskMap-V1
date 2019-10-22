package fr.univavignon.ceri.deskmap;

public class Node {
	public Long id;
	public Double lat;
	public Double lon;
	
	public Node(String id, Double lat, Double lon) {
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
	}
}
