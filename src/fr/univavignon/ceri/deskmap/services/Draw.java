package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.models.GeoData;
import fr.univavignon.ceri.deskmap.models.Node;
import fr.univavignon.ceri.deskmap.models.Way;
import fr.univavignon.ceri.deskmap.models.line.Line;
import fr.univavignon.ceri.deskmap.models.line.River;
import fr.univavignon.ceri.deskmap.models.line.Road;
import fr.univavignon.ceri.deskmap.models.region.Amenity;
import fr.univavignon.ceri.deskmap.models.region.Healthcare;
import fr.univavignon.ceri.deskmap.models.region.Landuse;
import fr.univavignon.ceri.deskmap.models.region.Leisure;
import fr.univavignon.ceri.deskmap.models.region.Natural;
import fr.univavignon.ceri.deskmap.models.region.Pedestrian;
import fr.univavignon.ceri.deskmap.models.region.Region;
import fr.univavignon.ceri.deskmap.models.region.SchoolAmenity;
import fr.univavignon.ceri.deskmap.models.region.Structure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Yanis Labrak
 */
public class Draw {
	
	/**
	 * Draw all the ways on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @author Yanis Labrak
	 */
	public static void drawWays(GraphicsContext gc) {
		
		// Draw Landuse
		Draw.drawLayout1(gc);		
		
		// Draw Natural and Leisure
		Draw.drawLayout2(gc);
		
		// Draw Highways
		Draw.drawLayout4(gc);
		
		// Pedestrian
		Draw.drawLayout5(gc);
		
		// Draw Amenity and Building
		Draw.drawLayout3(gc);
		
		// Draw the Relations
//		Draw.drawRelations(gc);
		
	}
	
	/**
	 * drawRoadName
	 * @param gc {@code GraphicsContext}
	 * @author Capdepon
	 */
	public static void drawRoadName(GraphicsContext gc) {
		
		for (Long key : Map.mapContent.keySet()) {
		    
		    GeoData item = Map.mapContent.get(key);
		    
		    if (item instanceof Road) {
		    	
		    	Road r = (Road) item;
		    	
		    	if (r.getType().equals("primary")) {
		    		
		    		List<Long> allNodes = r.getNodes();
		    		
		    		System.out.println("ici");
		    		
		    		System.out.println(r.getJunction());
		    		
		    		if (allNodes.size() >= 3 && (r.getJunction() == null || r.getJunction().isEmpty())) {	
		    			
		    		System.out.println("Pas crash");
		    		
			    		Long idMiddleNode = allNodes.get((allNodes.size() / 2));
			    		Node MiddleNode = Map.nodes.get(idMiddleNode);
			    		
			    		gc.setLineWidth(2.0);
					    gc.setFill(Color.WHITE);
					    gc.setStroke(Color.WHITE);
					    
					    List<Double> coordinates = Node.toPixel(MiddleNode.lat, MiddleNode.lon);
					    Double x = coordinates.get(0);
					    Double y = Map.height - coordinates.get(1);
					    
					    gc.fillText(r.getName(), x, y);
			            gc.strokeText(r.getName(), x, y);
			            
					    gc.setFill(Color.BLACK);
					    gc.setStroke(Color.BLACK);
			            
			            gc.fillOval(x, y, 5, 5);
		    			
					}
					
				}
				
			}
			
		}
	}
	
	/**
	 * Convert a {@code List<Double>} to a {@code double[]}
	 * @param doubles {@code List<Double>} List of double which will be converted
	 * @return {@code double[]} Return a {@code Array} of {@code Double}
	 * @author Yanis Labrak
	 */
	public static double[] convertDoubles(List<Double> doubles)
	{
	    double[] ret = new double[doubles.size()];
	    
	    Iterator<Double> iterator = doubles.iterator();
	    
	    int i = 0;
	    
	    while(iterator.hasNext())
	    {
	        ret[i] = iterator.next();
	        i++;
	    }
	    
	    return ret;
	}
	
	/**
	 * Draw the Region in argument on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @param prop {@code Region} The {@code Region} to draw
	 * @author Yanis Labrak
	 */
	public static void drawPropRegion(GraphicsContext gc, Region prop) {
		List<Long> nodes = prop.getNodes();
		
		gc.setFill(Color.web(prop.getColor()));
		gc.setStroke(Color.web(prop.getColor()));
    	
		List<Double> x = new ArrayList<Double>();
		List<Double> y = new ArrayList<Double>();
		
    	// For each node id
    	for (Long nodeId : nodes) {
    		
    		// Get the correspondent node
    		Node node = Map.nodes.get(nodeId);
    		
    		// Coordinate after processing
    		List<Double> coordinates = Node.toPixel(node.lat, node.lon);

    		x.add(coordinates.get(0));
    		y.add(Map.height - coordinates.get(1));
		}
    	
    	// Draw the building
    	gc.fillPolygon(Draw.convertDoubles(x), Draw.convertDoubles(y), x.size());
	}
	
	/**
	 * Draw the Line in argument on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @param prop {@code Line} The {@code Line} to draw
	 * @author Yanis Labrak
	 */
	public static void drawPropLine(GraphicsContext gc, Line prop) {
		List<Long> nodes = prop.getNodes();
		
		gc.setFill(Color.web(prop.getColor()));
		gc.setStroke(Color.web(prop.getColor()));
		
		gc.setLineWidth(prop.getThickness());
		
		List<List<Double>> allNodes = new ArrayList<List<Double>>();
		
		// Load each node in a List
		for (Long nodeId : nodes) {
			
			// Get the correspondent node
			Node node = Map.nodes.get(nodeId);
			
			// Coordinate after processing
			List<Double> coordinates = Node.toPixel(node.lat, node.lon);
			
			List<Double> nodeAdd = new ArrayList<Double>();
			nodeAdd.add(coordinates.get(0));			    		
			nodeAdd.add(Map.height- coordinates.get(1));
			
			allNodes.add(nodeAdd);
		}
		
		// Draw the segment of the Road
		for (int i = 0; i < allNodes.size() - 1; i++) {
			gc.strokeLine(
				allNodes.get(i).get(0),
				allNodes.get(i).get(1),
				allNodes.get(i + 1).get(0),
				allNodes.get(i + 1).get(1)
			);
		}
	}
	
	/**
	 * Draw the 1st layout on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @author Yanis Labrak
	 */
	public static void drawLayout1(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {		    	
		    	if (prop instanceof Landuse) {		    		
			    	Draw.drawPropRegion(gc, (Region) prop);
				}		    	
			}			
		}
	}
	
	/**
	 * Draw the 2nd layout on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @author Yanis Labrak
	 */
	public static void drawLayout2(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {		    	
		    	if (prop instanceof Natural || prop instanceof Leisure || prop instanceof SchoolAmenity || prop instanceof Healthcare) {		    		
		    		Draw.drawPropRegion(gc, (Region) prop);
				}		    	
			}			
		}
	}
	
	/**
	 * Draw the 3rd layout on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @author Yanis Labrak
	 */
	public static void drawLayout3(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {		    	
		    	if (prop instanceof Amenity || prop instanceof Structure && !(prop instanceof SchoolAmenity) && !(prop instanceof Healthcare)) {		    		
		    		Draw.drawPropRegion(gc, (Region) prop);
				}		    	
			}			
		}
	}
		
	/**
	 * Draw the 4th layout on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @author Yanis Labrak
	 */
	public static void drawLayout4(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
			
			Object prop = Map.mapContent.get(key);
			
			if(prop instanceof Line) {				
				if (prop instanceof River || prop instanceof Road) {
					Draw.drawPropLine(gc, (Line) prop);					
				}
			}			
		}
	}
	
	/**
	 * Draw the 5th layout on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @author Yanis Labrak
	 */
	public static void drawLayout5(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {		    	
		    	if (prop instanceof Pedestrian) {		    		
		    		Draw.drawPropRegion(gc, (Region) prop);
				}		    	
			}			
		}
	}
	
	/**
	 * Draw all the relations on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas 
	 * @author Yanis Labrak
	 */
	public static void drawRelations(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
			
			Object prop = Map.mapContent.get(key);
			
			if (prop instanceof Way) {
				
				List<Long> nodes = ((Way) prop).getNodes();
				
				if (((Way) prop).getColor() != null) {
					gc.setFill(Color.web(((Way) prop).getColor()));
					gc.setStroke(Color.web(((Way) prop).getColor()));					
				} else {
					gc.setFill(Color.BLACK);
					gc.setStroke(Color.BLACK);					
				}			
				
				
				List<Double> x = new ArrayList<Double>();
				List<Double> y = new ArrayList<Double>();
				
				// For each node id
				for (Long nodeId : nodes) {
					
					// Get the correspondent node
					Node node = Map.nodes.get(nodeId);
					
					// Coordinate after processing
					List<Double> coordinates = Node.toPixel(node.lat, node.lon);
					
					x.add(coordinates.get(0));
					y.add(coordinates.get(1));
				}
				
				// Draw the building
				gc.fillPolygon(Draw.convertDoubles(x), Draw.convertDoubles(y), x.size());				
			}			
		}
	}

}
