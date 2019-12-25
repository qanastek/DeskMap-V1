package fr.univavignon.ceri.deskmap.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.univavignon.ceri.deskmap.Map;
import fr.univavignon.ceri.deskmap.models.Bbox;
import fr.univavignon.ceri.deskmap.models.Node;
import fr.univavignon.ceri.deskmap.models.Way;
import fr.univavignon.ceri.deskmap.models.line.Line;
import fr.univavignon.ceri.deskmap.models.line.Path;
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
	 * Draw all the {@code Nodes} on the {@code Canvas}
	 * @param gc {@code GraphicsContext}
	 * @author Yanis Labrak
	 */
	public static void drawNodes(GraphicsContext gc, Bbox bbox) {
		
		for (Long key : Map.nodes.keySet()) {
		    
		    Node node = Map.nodes.get(key);
		    
    		// Coordinate after processing
    		List<Double> coordinates = Node.toPixel(node.lat, node.lon);
    		
    		Double x = coordinates.get(0);
    		Double y = Map.height - coordinates.get(1);
	    		
		    if (x < bbox.topRight && x > bbox.topLeft && y > bbox.bottomLeft && y < bbox.bottomRight) {

			    gc.setFill(Color.BLACK);
	    		gc.setStroke(Color.BLACK);
	    		gc.fillOval(x, y, Map.scale/5, Map.scale/5);
	    		
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
	
	/**
	 * Display the path on the map
	 * @param nodes
	 */
	public static void drawPath(GraphicsContext gc) {
		
//		int j = 0;
//		
//		// Add content to the path
//		for (Long key : Map.nodes.keySet()) {
//			
//			if (j == 6) { break; }
//			
//		    Node node = Map.nodes.get(key);
//		    Map.path.add(node);
//		    j++;
//		}

		Long n001 = 1674101738L;
		Node n01 = Map.nodes.get(n001);
		System.out.println(n01);

		Long n002 = 657442L;
		Node n02 = Map.nodes.get(n002);
		System.out.println(n02);

		Long n003 = 1674101978L;
		Node n03 = Map.nodes.get(n003);
		System.out.println(n03);

		Long n004 = 1674102010L;
		Node n04 = Map.nodes.get(n004);
		System.out.println(n04);
		
	    Map.path.add(n01);
	    Map.path.add(n02);
	    Map.path.add(n03);
	    Map.path.add(n04);
	    
	    System.out.println(Map.path);
		
		if (Map.path.size() < 2) {
			System.err.println("Path too small !");
		} else {
			
			// Draw each segment of the path
			for (int i = 0; i < Map.path.size() - 1; i++) {
				
				Line segment = new Path();
				
				Long n0 = Map.path.get(i).id;
				Long n1 = Map.path.get(i+1).id;
				
				segment.addNode(n0);
				segment.addNode(n1);
				
				Draw.drawPropLine(gc, segment);
			}			
		}
	}

}
