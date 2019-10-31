package fr.univavignon.ceri.deskmap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import fr.univavignon.ceri.deskmap.line.Line;
import fr.univavignon.ceri.deskmap.line.Road;
import fr.univavignon.ceri.deskmap.region.Building;
import fr.univavignon.ceri.deskmap.region.Cemetery;
import fr.univavignon.ceri.deskmap.region.Commercial;
import fr.univavignon.ceri.deskmap.region.Forest;
import fr.univavignon.ceri.deskmap.region.GolfCourse;
import fr.univavignon.ceri.deskmap.region.Grass;
import fr.univavignon.ceri.deskmap.region.Industrial;
import fr.univavignon.ceri.deskmap.region.Park;
import fr.univavignon.ceri.deskmap.region.Pedestrian;
import fr.univavignon.ceri.deskmap.region.Railway;
import fr.univavignon.ceri.deskmap.region.Region;
import fr.univavignon.ceri.deskmap.region.Residential;
import fr.univavignon.ceri.deskmap.region.Retail;
import fr.univavignon.ceri.deskmap.region.School;
import fr.univavignon.ceri.deskmap.region.SchoolAmenity;
import fr.univavignon.ceri.deskmap.region.SportsCentre;
import fr.univavignon.ceri.deskmap.region.Water;
import fr.univavignon.ceri.deskmap.region.Wood;

/**
 * @author Yanis Labrak
 */
public class Map {

	/**
	 * Scale of the map
	 */
	public static Float scale;
	
	/**
	 * Latitude position top
	 */
	public static Double topLeft;

	/**
	 * Latitude position bottom
	 */
	public static Double bottomLeft;
	
	/**
	 * Longitude position left
	 */
	public static Double topRight;
	
	/**
	 * Longitude position right
	 */
	public static Double bottomRight;
	
	/**
	 * Height 
	 */
	public static Double height;

	/**
	 * Width 
	 */
	public static Double width;
	
	/**
	 * All the GeoData to load
	 */
	public static HashMap<Long,GeoData> mapContent = new HashMap<Long,GeoData>();

	/**
	 * All the nodes to load
	 */
	public static HashMap<Long, Node> nodes = new HashMap<Long, Node>();
	
	/**
	 * @param data {@code GeoData} The data to add
	 */
	public void addMapContent(GeoData data) {
		Map.mapContent.put(data.id,data);
	}
	
	/**
	 * Add a {@code Node} inside the {@code HashMap}
	 * @param node The {@code Node} to add
	 */
	public void addNode(Node node) {
		Map.nodes.put(node.id, node);
	}
	
	/**
	 * Get the {@code Node} which have the same {@code Id}
	 * @param id {@code String} Identifier of the {@code Node}
	 * @return return the {@code Node} for which the identifier match
	 */
	public Node getNode(String id) {
		return Map.nodes.get(Long.parseLong(id));
	}
	
	/**
	 * @return the mapContent
	 */
	public HashMap<Long,GeoData> getMapContent() {
		return Map.mapContent;
	}

	/**
	 * @param mapContent the mapContent to set
	 */
	public void setMapContent(HashMap<Long,GeoData> mapContent) {
		Map.mapContent = mapContent;
	}

	/**
	 * @return the nodes
	 */
	public HashMap<Long, Node> getNodes() {
		return Map.nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(HashMap<Long, Node> nodes) {
		Map.nodes = nodes;
	}

	/**
	 * Constructor
	 */
	public Map() {
		Map.scale = (float) 1.0;
		Map.topLeft = 0.0;
		Map.bottomLeft = 0.0;
		Map.topRight = 0.0;
		Map.bottomRight = 0.0;
		Map.height = 0.0;
		Map.width = 0.0;
	}

	/**
	 * @return the scale
	 */
	public Float getScale() {
		return Map.scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(Float scale) {
		Map.scale = scale;
	}
	
	/**
	 * @return the height
	 */
	public Double getHeight() {
		return Map.height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Double height) {
		Map.height = height;
	}

	/**
	 * @return the width
	 */
	public Double getWidth() {
		return Map.width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Double width) {
		Map.width = width;
	}	

	/**
	 * Parse the JSON file and make Object from It
	 * @param city {@code String} Name of the city
	 * @throws org.json.simple.parser.ParseException If the file wasn't find
	 */
	public static void loadCityAsObject(String city) throws org.json.simple.parser.ParseException {
		
		// Load all the nodes
		Map.loadNodes(city);
		
		// Load all the ways
		Map.loadWays(city);	

		// Load all the relations
		Map.loadRelations(city);	
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
		
		// Draw Landuse, Natural, Leisure, Amenity, Highways and Building
		Map.loadLayout1(city);
		
	}
	
	/**
	 * 
	 */
	public static void loadRegion(Region entity, JSONObject item) {
		JSONArray nodes;
		Iterator<?> it;
		
		// Read the 'nodes' array
		nodes = (JSONArray) item.get("nodes");
		
		it = nodes.iterator();
		
		// for each node identifier of this array
		while (it.hasNext()) {
			
			// Read it
			String nodeId = it.next().toString();
			
			// Add the id to the nodes list of the water
			entity.addNode(nodeId);
		}
		
		// Add the Building
		MainViewController.map.addMapContent(entity);
	}
	
	/**
	 * Draw Landuse
	 * @param city {@code String} Name of the city
	 */
	public static void loadLayout1(String city) {
		
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		
		try (FileReader reader = new FileReader(city.toLowerCase() + "Map.json"))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			
			JSONObject main = (JSONObject) obj;
			JSONArray elements = (JSONArray) main.get("elements");
			
			Iterator<JSONObject> iterator = elements.iterator();
			
			// For each JSON elements
			while (iterator.hasNext()) {	
				
				JSONObject item = iterator.next();
				
				// Type: Node, Way or Relation
				String type = (String) item.get("type");
				
				// If its a way
				if (type.toLowerCase().equals("way")) {
					
					// Fetch tags
					JSONObject tags = (JSONObject) item.get("tags");
					
					Region entity;
					
					// If its not a isolated Way
					if (tags != null ) {
						
						if ((String) tags.get("landuse") != null) {
							
							switch ((String) tags.get("landuse")) {
							
								case "residential":
									entity = new Residential((Long) item.get("id"));
									Map.loadRegion(entity, item);
									break;
									
								case "industrial":									
									entity = new Industrial((Long) item.get("id"));
									Map.loadRegion(entity, item);
									break;
									
								case "commercial":									
									entity = new Commercial((Long) item.get("id"));
									Map.loadRegion(entity, item);
									break;
									
								case "retail":									
									entity = new Retail((Long) item.get("id"));
									Map.loadRegion(entity, item);
									break;
									
								case "railway":									
									entity = new Railway((Long) item.get("id"));
									Map.loadRegion(entity, item);								
									break;
									
								case "cemetery":
									entity = new Cemetery((Long) item.get("id"));
									Map.loadRegion(entity, item);
									break;
									
								case "forest":
									entity = new Forest((Long) item.get("id"));
									Map.loadRegion(entity, item);								
									break;
									
								case "grass":
									entity = new Grass((Long) item.get("id"));
									Map.loadRegion(entity, item);
									break;									
							}							
						}
						// If it's a Leisure
						else if ((String) tags.get("leisure") != null) {
								
							switch ((String) tags.get("leisure")) {
							
								case "sports_centre":	
									entity = new SportsCentre((Long) item.get("id"));
									Map.loadRegion(entity, item);										
									break;
									
								case "park":										
									entity = new Park((Long) item.get("id"));
									Map.loadRegion(entity, item);										
									break;
									
								case "golf_course":	
									entity = new GolfCourse((Long) item.get("id"));
									Map.loadRegion(entity, item);
									break;
							}
							
						}
						// If it's a Natural
						else if ((String) tags.get("natural") != null) {
							
							switch ((String) tags.get("natural")) {
							
								case "water":										
									entity = new Water((Long) item.get("id"));
									Map.loadRegion(entity, item);
									break;
							}							
						}
						// Buildings
						else if ((String) tags.get("building") != null) {
							
							switch ((String) tags.get("building")) {
							
								case "yes":								
									entity = new Building((Long) item.get("id"));
									Map.loadRegion(entity, item);								
									break;
									
								case "school":									
									entity = new School(
										(Long) item.get("id"),
										(String) item.get("name")
									);
									
									Map.loadRegion(entity, item);								
									break;
							}
							
						}
						// If it's a Amenity
						else if ((String) tags.get("amenity") != null) {
							
							switch ((String) tags.get("amenity")) {
							
								case "school":
									entity = new SchoolAmenity(
										(Long) item.get("id"),
										(String) item.get("name")
									);
									
									Map.loadRegion(entity, item);
									break;
							}
							
						}
						// If it's a Highway
						else if ((String) tags.get("highway") != null) {
							
							Iterator<?> it;
							JSONArray nodes;
							Line entityRoad;
							
							entityRoad = new Road((Long) item.get("id"));
							
							// Read the 'nodes' array
							nodes = (JSONArray) item.get("nodes");
							
							it = nodes.iterator();
							
							// for each node identifier of this array
							while (it.hasNext()) {
								
								// Read it
								String nodeId = it.next().toString();
								
								// Add the id to the nodes list of the GolfCourse
								entityRoad.addNode(nodeId);
							}
							
							switch ((String) tags.get("highway")) {
							
								case "motorway":
									entityRoad.setColor(Color.MOTORWAY);
									entityRoad.setThickness(Settings.LEVEL_1_ROAD_THICKNESS);
									break;

								// Second biggest roads of the country after the motorways (Equivalent to the autobahn)
								case "trunk":
									entityRoad.setColor(Color.TRUNK);
									entityRoad.setThickness(Settings.LEVEL_2_ROAD_THICKNESS);
									break;
									
								case "primary":
									entityRoad.setColor(Color.PRIMARY);
									entityRoad.setThickness(Settings.LEVEL_3_ROAD_THICKNESS);
									break;
									
								case "secondary":
									entityRoad.setColor(Color.SECONDARY);
									entityRoad.setThickness(Settings.LEVEL_3_ROAD_THICKNESS);
									break;
									
								case "tertiary":
									entityRoad.setColor(Color.TERTIARY);
									entityRoad.setThickness(Settings.LEVEL_3_ROAD_THICKNESS);
									break;
									
								case "residential":
								case "living_street":
								case "pedestrian":
								default:
									entityRoad.setColor(Color.ROAD);
									entityRoad.setThickness(Settings.LEVEL_4_ROAD_THICKNESS);
									break;
							}							

							// Add the Building
							MainViewController.map.addMapContent(entityRoad);
							
						}	
					} else {
						// If no tags found, its a Way
						entity = new Way((Long) item.get("id"));
						Map.loadRegion(entity, item);	
					}
				}				
			}
			
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
			
			// For each JSON object
			while (iterator.hasNext()) {
				
				JSONObject item = iterator.next();
				
				// Get type
				String type = (String) item.get("type");
				
				// If its a relation
				if (type.toLowerCase().equals("relation")) {
					
					// Members iterator
					Iterator<JSONObject> it;
					
					// Read the array of ways
					JSONArray members = (JSONArray) item.get("members");
					
					// Get tags
					JSONObject tags = (JSONObject) item.get("tags");	
					
					Way baseWay;

					// If we have tags
					if (tags != null ) {

						// Get the name
						String name = (String) tags.get("name");		
						
						it = members.iterator();
						
						int i = 0;
						
						// for each ways of this array
						while (it.hasNext()) {
							
							// Get the way identifier
							String wayId = it.next().get("ref").toString();
							
							// Set the name of the place on the way
//							Map.mapContent.get(Long.parseLong(wayId)).setName(name);							
							
							// If the corresponding way exist
							if (Map.mapContent.get(Long.parseLong(wayId)) != null && Map.mapContent.get(Long.parseLong(wayId)) instanceof Way) {
																
								baseWay = (Way) Map.mapContent.get(Long.parseLong(wayId));
								
								// Create a new Way
								Region newWay = null;
								
								// TODO
								// Depending of the type
								if (tags.get("landuse") != null) {
									
									switch ((String) tags.get("landuse")) {
									
										case "residential":		
											newWay = new Residential(
												Long.parseLong(wayId) + i++
											);										
											break;
											
										case "industrial":		
											newWay = new Industrial(
												Long.parseLong(wayId) + i++
											);										
											break;
											
										case "commercial":	
											newWay = new Commercial(
												Long.parseLong(wayId) + i++
											);										
											break;
											
										case "retail":	
											newWay = new Retail(
												Long.parseLong(wayId) + i++
											);										
											break;
											
										case "railway":	
											newWay = new Railway(
												Long.parseLong(wayId) + i++
											);										
											break;
											
										case "cemetery":
											newWay = new Cemetery(
												Long.parseLong(wayId) + i++
											);
											break;
											
										case "forest":
											newWay = new Forest(
												Long.parseLong(wayId) + i++
											);										
											break;
											
										case "grass":
											newWay = new Grass(
												Long.parseLong(wayId) + i++
											);											
											break;
									}									
								}
								else if (tags.get("building") != null) {
									switch ((String) tags.get("building")) {
										default:						
											newWay = new Building(
												Long.parseLong(wayId) + i++
											);											
											break;
									}
								}
								else if (tags.get("amenity") != null) {
									switch ((String) tags.get("amenity")) {
										case "school":		
											newWay = new School(
												Long.parseLong(wayId) + i++
											);										
											break;
									}
								}
								else if (tags.get("highway") != null) {
									
									
									if (tags.get("highway").equals("pedestrian")) {
										newWay = new Pedestrian(
											Long.parseLong(wayId) + i++
										);
										newWay.setColor(Color.PEDESTRIAN);
									}
									else {
										newWay = new fr.univavignon.ceri.deskmap.region.Road(
											Long.parseLong(wayId) + i++
										);
										
										switch ((String) tags.get("highway")) {
										
											case "motorway":
												newWay.setColor(Color.MOTORWAY);
												break;
		
											case "trunk":
												newWay.setColor(Color.TRUNK);
												break;
												
											case "primary":
												newWay.setColor(Color.PRIMARY);
												break;
												
											case "secondary":
												newWay.setColor(Color.SECONDARY);
												break;
												
											case "tertiary":
												newWay.setColor(Color.TERTIARY);
												break;
												
											case "residential":
											case "living_street":
											default:
												newWay.setColor(Color.ROAD);
												break;
										}
									}
								}
								else if (tags.get("leisure") != null) {
									switch ((String) tags.get("leisure")) {
									
										case "sports_centre":
											newWay = new SportsCentre(
												Long.parseLong(wayId) + i++
											);	
											newWay.setColor(Color.SPORT_CENTRE);
											break;
	
										case "park":
											newWay = new Park(
												Long.parseLong(wayId) + i++
											);	
											newWay.setColor(Color.PARK);
											break;
											
										case "golf_course":
											newWay = new GolfCourse(
												Long.parseLong(wayId) + i++
											);	
											newWay.setColor(Color.GOLF_COURSE);
											break;
									}
								}
								else if (tags.get("natural") != null) {
									switch ((String) tags.get("natural")) {
									
										case "water":
											newWay = new Water(
												Long.parseLong(wayId) + i++
											);	
											newWay.setColor(Color.WATER);
											break;
	
										case "wood":
											newWay = new Wood(
												Long.parseLong(wayId) + i++
											);	
											newWay.setColor(Color.WOOD);
											break;
									}
								}
								
								// If one of the above class
								if (newWay != null) {
									
									// Load each nodes inside the new Way
									for (Long nodeId : baseWay.getNodes()) {										
										newWay.addNode(nodeId);										
									}

									// Add the way
									Map.mapContent.put(newWay.id + i, newWay);
								}
								
							}
						}	
						
					}					
				}				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}
	
}
