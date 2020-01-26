package fr.univavignon.ceri.deskmap.models.line;

import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.models.Node;
import fr.univavignon.ceri.deskmap.models.region.Highway;

/**
 * @author Mohamed BEN YAMNA
 */
public class Road extends Line implements Highway {

	/**
	 * Does it is a junction
	 */
	private String junction;

	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param name {@code String} Name of the road
	 * @param thickness {@code Integer} Thickness of the road line
	 */
	public Road(String id, String name, Integer thickness) {
		super(id, name, thickness, "#ffffff");
	}
	
	/**
	 * Constructor
	 * @param id {@code String} Identifier
	 * @param thickness {@code Integer} Thickness of the road line
	 */
	public Road(String id, Integer thickness) {
		super(id, "", thickness, "#ffffff");
	}
	
	/**
	 * Constructor
	 * @param id {@code Long} Identifier
	 * @param thickness {@code Integer} Thickness of the road line
	 */
	public Road(Long id, Integer thickness) {
		super(id, "", thickness, "#ffffff");
	}
	
	/**
	 * Constructor identifier only
	 * @param id {@code Long} Identifier
	 */
	public Road(Long id) {
		super(id, "", 1, "#ffffff");
	}
	
	/**
	 * Constructor identifier only
	 * @param id {@code String} Identifier
	 */
	public Road(String id) {
		super(id, "", 1, "#ffffff");
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Return the center of the {@code Road}
	 * @return {@code Long} The {@code Node} identifier
	 */
	public Node getMiddle() {
		int index = this.nodes.size() <= 2 ? 0 : this.nodes.size() / 2; 
		return Map.nodes.get(this.nodes.get(index));
	}

	/**
	 * Return the angle of the {@code Road}
	 * @return {@code Integer} The angle
	 */
	public Double getAngle() {
		
		Long startId = this.nodes.get(0);
		Long endId = this.nodes.get(this.nodes.size() - 1);
		
		Node start = Map.nodes.get(startId);
		Node end = Map.nodes.get(endId);
		
		Double ex = start.lat;
		Double ey = start.lon;
		
		Double cx = end.lat;
		Double cy = end.lon;

		Double dy = ey - cy;
		Double dx = ex - cx;
		
		Double theta = Math.atan2(dy, dx);
		theta *= 180 / Math.PI;
		
		if (theta < 0) theta = 360 + theta;
		
		return theta;
	}

	/**
	 * Setter for junction
	 * @param junction {@code String}
	 */
	public void setJunction(String junction) {
		this.junction = junction;
	}

	/**
	 * Getter junction
	 * @return {@code String} The junction
	 */
	public String getJunction() {
		return this.getJunction();
	}

}
