/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.univavignon.ceri.deskmap.region.Building;
import fr.univavignon.ceri.deskmap.region.School;
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
		
//		gc.setFill(Color.GREEN);
//        gc.setStroke(Color.BLUE);
//        gc.setLineWidth(5);
//        gc.strokeLine(40, 10, 10, 40);
//        gc.fillOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                       new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                         new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                          new double[]{210, 210, 240, 240}, 4);

	}
	
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
	 * @param gc {@code GraphicsContext}
	 */
	public static void drawWays(GraphicsContext gc) {
				
		for (Long key : Map.mapContent.keySet()) {
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof Building) {
		    	List<Long> nodes = ((Building) prop).getNodes();
		    	
//	    		gc.setFill(Color.web(((Building) prop).getColor()));
//	    		gc.setStroke(Color.web(((Building) prop).getColor()));
	    		
	    		gc.setFill(Color.BLACK);
	    		gc.setStroke(Color.BLACK);
	    		
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
