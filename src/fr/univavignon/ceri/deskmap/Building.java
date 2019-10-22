package fr.univavignon.ceri.deskmap;

import java.util.ArrayList;
import java.util.List;

public class Building {

	public String name;
	public List<Node> nodes;
	
	public Building(String name) {
		this.name = name;
		this.nodes = new ArrayList<Node>();
	}
}
