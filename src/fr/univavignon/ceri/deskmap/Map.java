/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import fr.univavignon.ceri.deskmap.region.Building;
import fr.univavignon.ceri.deskmap.region.School;

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
	private HashMap<Long,GeoData> mapContent = new HashMap<Long,GeoData>();

	/**
	 * All the nodes to load
	 */
	private HashMap<Long, Node> nodes = new HashMap<Long, Node>();
	
	/**
	 * @param data {@code GeoData} The data to add
	 */
	public void addMapContent(GeoData data) {
		this.mapContent.put(data.id,data);
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
	public HashMap<Long,GeoData> getMapContent() {
		return this.mapContent;
	}

	/**
	 * @param mapContent the mapContent to set
	 */
	public void setMapContent(HashMap<Long,GeoData> mapContent) {
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
	
	/**
	 * Load all the Node
	 * @param city {@code String} Name of the city
	 */
	public static void loadNodes(String city) {
		 
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(city.toLowerCase() + "Map.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONObject main = (JSONObject) obj;
            JSONArray elements = (JSONArray) main.get("elements");
            
            Iterator<JSONObject> iterator = elements.iterator();
            
            Integer nodeCpt = 0;

            // TODO: continuous
			while (iterator.hasNext()) {	
				
				JSONObject item = iterator.next();

				String type = (String) item.get("type");
				
				if (type.toLowerCase().equals("node")) {
					nodeCpt++;
					
					Node node = new Node(
						(Long) item.get("id"),
						(Double) item.get("lat"),
						(Double) item.get("lon")
					);
					
					MainViewController.map.addNode(node);
				}	
				
		    }
			
			System.out.println("Nodes: " + nodeCpt);
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * Load all the Node
	 * @param city {@code String} Name of the city
	 */
	public static void loadWays(String city) {
		
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		
		try (FileReader reader = new FileReader(city.toLowerCase() + "Map.json"))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			
			JSONObject main = (JSONObject) obj;
			JSONArray elements = (JSONArray) main.get("elements");
			
			Iterator<JSONObject> iterator = elements.iterator();
			
			Integer wayCpt = 0;
			Integer buildingCpt = 0;
			Integer schoolCpt = 0;
			
			// TODO: continuous
			while (iterator.hasNext()) {	
				
				JSONObject item = iterator.next();
				
				String type = (String) item.get("type");
				
				if (type.toLowerCase().equals("way")) {
					wayCpt++;
					
					JSONObject tags = (JSONObject) item.get("tags");
					
					if (tags != null ) {
						
						// If it's a Building
						if ((String) tags.get("building") != null) {
							
							switch ((String) tags.get("building")) {
							
								case "yes":
																		
									Building building = new Building((Long) item.get("id"));
									
									// Read the 'nodes' array
									JSONArray nodes = (JSONArray) item.get("nodes");
									
									Iterator it = nodes.iterator();
									
									// for each node identifier of this array
									while (it.hasNext()) {
										
										// Read it
										String nodeId = it.next().toString();
										
										// Add the id to the nodes list of the Building
										building.addNode(nodeId);
									}
									
									// Add the Building
									MainViewController.map.addMapContent(building);
									
									buildingCpt++;
									
									break;
									
								case "school":
									
									School school;
									
									// If the school doesn't have explicit name
									if (item.get("name") != null)
									{
										school = new School(
											(Long) item.get("id"),
											(String) item.get("name")
										);
									} else
									{
										school = new School(
											(Long) item.get("id")
										);
									}
									
									// Read the 'nodes' array
									JSONArray nodesSchool = (JSONArray) item.get("nodes");
									
									Iterator itSchool = nodesSchool.iterator();
									
									// for each node identifier of this array
									while (itSchool.hasNext()) {
										
										// Read it
										String nodeId = itSchool.next().toString();
										
										// Add the id to the nodes list of the School
										school.addNode(nodeId);
									}
									
									// Add the Building
									MainViewController.map.addMapContent(school);
									
									schoolCpt++;
									
									break;
									
								default:
									// Region
									break;
							}
							
						}
						// If it's a Amenity
						else if ((String) tags.get("amenity") != null) {
							
							switch ((String) tags.get("amenity")) {
								case "school":
									// Region
									break;
							}
							
						}
						// If it's a Leisure
						else if ((String) tags.get("leisure") != null) {
							
							switch ((String) tags.get("leisure")) {
							
								case "sports_centre":
									// Region
									break;
									
								case "park":
									// Region
									break;
									
								case "golf_course":
									// Region
									break;
							}
							
						}
						// If it's a Landuse
						else if ((String) tags.get("landuse") != null) {
							
							switch ((String) tags.get("landuse")) {
							
								case "residential":
									// Region
									break;
									
								case "industrial":
									// Region
									break;
									
								case "commercial":
									// Region
									break;
									
								case "retail":
									// Region
									break;
									
								case "railway":
									// Region
									break;
									
								case "cemetery":
									// Region
									break;
									
								case "forest":
									// Region
									break;
									
								case "grass":
									// Region
									break;									
							}
							
						}
						// If it's a Highway
						else if ((String) tags.get("highway") != null) {
							
							switch ((String) tags.get("highway")) {
							
								case "primary":
									// thickness 5
									break;
									
								case "secondary":
									// thickness 4
									break;
									
								case "trunk":
									// thickness 3
									break;
									
								case "residential":
								case "living_street":
								case "pedestrian":
								case "motorway":
									// thickness 2
									break;
									
								default:
									// Simple road
									break;
							}
							
						}
						// If it's a Natural
						else if ((String) tags.get("natural") != null) {
							
							switch ((String) tags.get("natural")) {
							
								case "water":
									// Region water
									break;
							}
							
						}
						
					}
					else {
						// Si pas de tags alors route						
					}
					
				}
				
			}
			
			System.out.println("Ways: " + wayCpt);
			System.out.println("Buildings: " + buildingCpt);
			System.out.println("Schools: " + schoolCpt);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Load all the Node
	 * @param city {@code String} Name of the city
	 */
	public static void loadRelations(String city) {
		
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		
		try (FileReader reader = new FileReader(city.toLowerCase() + "Map.json"))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			
			JSONObject main = (JSONObject) obj;
			JSONArray elements = (JSONArray) main.get("elements");
			
			Iterator<JSONObject> iterator = elements.iterator();
			
			Integer relaCpt = 0;
			
			// TODO: continuous
			while (iterator.hasNext()) {	
				
				JSONObject item = iterator.next();
				
				String type = (String) item.get("type");
				
				// Loading order:
				// Nodes
				// Way
				// Relation
				
				if (type.toLowerCase().equals("relation")) {
					relaCpt++;
					
					// Faut géré les multipolygon
					// Pour ce faire: if have members, its a multipolygon
				}
				
			}
			
			System.out.println("Relations: " + relaCpt);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}
	
}
