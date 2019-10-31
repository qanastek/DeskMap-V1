/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.univavignon.ceri.deskmap.line.Line;
import fr.univavignon.ceri.deskmap.line.River;
import fr.univavignon.ceri.deskmap.line.Road;
import fr.univavignon.ceri.deskmap.region.Amenity;
import fr.univavignon.ceri.deskmap.region.Building;
import fr.univavignon.ceri.deskmap.region.Cemetery;
import fr.univavignon.ceri.deskmap.region.Forest;
import fr.univavignon.ceri.deskmap.region.Grass;
import fr.univavignon.ceri.deskmap.region.Landuse;
import fr.univavignon.ceri.deskmap.region.Leisure;
import fr.univavignon.ceri.deskmap.region.Natural;
import fr.univavignon.ceri.deskmap.region.Pedestrian;
import fr.univavignon.ceri.deskmap.region.Region;
import fr.univavignon.ceri.deskmap.region.School;
import fr.univavignon.ceri.deskmap.region.SchoolAmenity;
import fr.univavignon.ceri.deskmap.region.Structure;
import fr.univavignon.ceri.deskmap.region.Water;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * @author Yanis Labrak
 */
public class Draw {
	
	/**
	 * @param gc {@code GraphicsContext}
	 */
	public static void drawNodes(GraphicsContext gc) {
		
		for (Long key : Map.nodes.keySet()) {
			
//			System.out.println("key : " + key);
//		    System.out.println("value : " + Map.mapContent.get(key));
		    
		    Node node = Map.nodes.get(key);
		    
		    gc.setFill(Color.GREEN);
    		gc.setStroke(Color.BLUE);
    		
    		// Coordinate after processing
    		List<Double> coordinates = Node.toPixel(node.lat, node.lon);
    		
    		Double x = coordinates.get(0);
    		Double y = coordinates.get(1);
    		
    		gc.fillOval(x, y, 1, 1);
			
		}
	}
	
	/**
	 * Convert a {@code List<Double>} to a {@code double[]}
	 * @param doubles {@code List<Double>} List of double which will be converted
	 * @return {@code double[]} Return a {@code Array} of {@code Double}
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
	 * Draw all the ways on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
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
	 * Draw the Region in parameter on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @param prop {@code Region} The {@code Region} to draw
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
    		y.add(coordinates.get(1));
		}
    	
    	// Draw the building
    	gc.fillPolygon(Draw.convertDoubles(x), Draw.convertDoubles(y), x.size());
	}
	
	/**
	 * Draw the Line in parameter on the {@code Canvas}
	 * @param gc {@code GraphicsContext} The canvas
	 * @param prop {@code Line} The {@code Line} to draw
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
			nodeAdd.add(coordinates.get(1));
			
			allNodes.add(nodeAdd);
		}
		
		// Draw the Road
		// From the current Node to the next one
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
	 * Draw the 1st layout on the canvas
	 * @param gc {@code GraphicsContext} The canvas
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
	 * Draw the 2nd layout on the canvas
	 * @param gc {@code GraphicsContext} The canvas
	 */
	public static void drawLayout2(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {		    	
		    	if (prop instanceof Natural || prop instanceof Leisure || prop instanceof SchoolAmenity) {		    		
		    		Draw.drawPropRegion(gc, (Region) prop);
				}		    	
			}			
		}
	}
	
	/**
	 * Draw the 3rd layout on the canvas
	 * @param gc {@code GraphicsContext} The canvas
	 */
	public static void drawLayout3(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {		    	
		    	if (prop instanceof Amenity || prop instanceof Structure && !(prop instanceof SchoolAmenity)) {		    		
		    		Draw.drawPropRegion(gc, (Region) prop);
				}		    	
			}			
		}
	}
		
	/**
	 * Draw the 4th layout on the canvas
	 * @param gc {@code GraphicsContext} The canvas
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
	 * Draw the 5th layout on the canvas
	 * @param gc {@code GraphicsContext} The canvas
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
	 * 
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
