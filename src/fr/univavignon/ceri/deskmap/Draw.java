/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.univavignon.ceri.deskmap.region.Amenity;
import fr.univavignon.ceri.deskmap.region.Building;
import fr.univavignon.ceri.deskmap.region.Cemetery;
import fr.univavignon.ceri.deskmap.region.Forest;
import fr.univavignon.ceri.deskmap.region.Grass;
import fr.univavignon.ceri.deskmap.region.Landuse;
import fr.univavignon.ceri.deskmap.region.Leisure;
import fr.univavignon.ceri.deskmap.region.Natural;
import fr.univavignon.ceri.deskmap.region.Region;
import fr.univavignon.ceri.deskmap.region.School;
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
				
		Draw.drawLayout1(gc);
		Draw.drawLayout2(gc);
		Draw.drawLayout3(gc);
		
	}
	
	/**
	 * 
	 */
	public static void drawLayout1(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {
		    	
		    	if (prop instanceof Landuse) {
		    		
			    	List<Long> nodes = ((Region) prop).getNodes();
		    		
		    		gc.setFill(Color.web(((Region) prop).getColor()));
		    		gc.setStroke(Color.web(((Region) prop).getColor()));
			    	
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
	
	/**
	 * 
	 */
	public static void drawLayout2(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {
		    	
		    	if (prop instanceof Natural || prop instanceof Leisure) {
		    		
			    	List<Long> nodes = ((Region) prop).getNodes();
		    		
		    		gc.setFill(Color.web(((Region) prop).getColor()));
		    		gc.setStroke(Color.web(((Region) prop).getColor()));
			    	
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
	
	/**
	 * 
	 */
	public static void drawLayout3(GraphicsContext gc) {
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Region) {
		    	
		    	if (prop instanceof Amenity || prop instanceof Structure) {
		    		
			    	List<Long> nodes = ((Region) prop).getNodes();
		    		
		    		gc.setFill(Color.web(((Region) prop).getColor()));
		    		gc.setStroke(Color.web(((Region) prop).getColor()));
			    	
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

}
