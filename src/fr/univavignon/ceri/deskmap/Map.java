/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Yanis Labrak
 */
public class Map {

	/**
	 * Scale of the map
	 */
	private Float scale;
	
	/**
	 * Latitude position 
	 */
	private Float latitude;
	
	/**
	 * Longitude position 
	 */
	private Float longitude;
	
	/**
	 * Height 
	 */
	private Float height;

	/**
	 * Width 
	 */
	private Float width;
	
	/**
	 * All the GeoData to load
	 */
	private List<GeoData> mapContent = new ArrayList<GeoData>();

	/**
	 * All the nodes to load
	 */
	private HashMap<Long, Node> nodes = new HashMap<Long, Node>();
	
	/**
	 * @param data {@code GeoData} The data to add
	 */
	public void addMapContent(GeoData data) {
		this.mapContent.add(data);
	}
	
	/**
	 * Add a {@code Node} inside the {@code HashMap}
	 * @param node The {@code Node} to add
	 */
	public void addNode(Node node) {
		this.nodes.put(node.id, node);
	}
	
	/**
	 * Get the {@code Node} which have the same {@code Id}
	 * @param id {@code String} Identifier of the {@code Node}
	 * @return return the {@code Node} for which the identifier match
	 */
	public Node getNode(String id) {
		return this.nodes.get(Long.parseLong(id));
	}
	
	/**
	 * @return the mapContent
	 */
	public List<GeoData> getMapContent() {
		return this.mapContent;
	}

	/**
	 * @param mapContent the mapContent to set
	 */
	public void setMapContent(List<GeoData> mapContent) {
		this.mapContent = mapContent;
	}

	/**
	 * @return the nodes
	 */
	public HashMap<Long, Node> getNodes() {
		return this.nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(HashMap<Long, Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Constructor
	 */
	public Map() {
		this.scale = (float) 0.0;
		this.latitude = (float) 0.0;
		this.longitude = (float) 0.0;
		this.height = (float) 0.0;
		this.width = (float) 0.0;
	}

	/**
	 * @return the scale
	 */
	public Float getScale() {
		return this.scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(Float scale) {
		this.scale = scale;
	}

	/**
	 * @return the latitude
	 */
	public Float getLatitude() {
		return this.latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Float getLongitude() {
		return this.longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the height
	 */
	public Float getHeight() {
		return this.height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Float height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public Float getWidth() {
		return this.width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Float width) {
		this.width = width;
	}
	
}
