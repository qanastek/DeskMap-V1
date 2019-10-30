/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import java.util.List;

import fr.univavignon.ceri.deskmap.region.School;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Yanis Labrak
 */
public class Draw {
	
	/**
	 * @param gc {@code GraphicsContext}
	 */
	public static void drawNodes(GraphicsContext gc) {
		
		// Draw all ways
		Draw.drawWays(gc);
		
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
		
//		MainViewController.canvasMap

	}
	
	/**
	 * @param gc {@code GraphicsContext}
	 */
	public static void drawWays(GraphicsContext gc) {
				
		for (Long key : Map.mapContent.keySet()) {
			
//			System.out.println("key : " + key);
//		    System.out.println("value : " + Map.mapContent.get(key));
		    
		    Object prop = Map.mapContent.get(key);
		    
		    if (prop instanceof School) {
		    	List<Long> nodes = ((School) prop).getNodes();
		    	
		    	// For each node id
		    	for (Long nodeId : nodes) {
		    		
		    		// Get the correspondent node
		    		Node node = Map.nodes.get(nodeId);
		    		
		    		gc.setFill(Color.GREEN);
		    		gc.setStroke(Color.BLUE);
		    		
		    		// Coordinate after processing
		    		List<Double> coordinates = Node.toPixel(node.lat, node.lon);
		    		
		    		Double x = coordinates.get(0);
		    		Double y = coordinates.get(1);
		    		
		    		gc.fillOval(x, y, 5, 5);
		    		
//		    		System.out.println(node);
				}
			}
			
		}
		
	}

}
