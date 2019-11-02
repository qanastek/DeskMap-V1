/**
 * 
 */
package fr.univavignon.ceri.deskmap;

/**
 * @author Yanis Labrak
 *
 */
public final class Settings {	
	
	/**
	 * The default city draw when the software start
	 */
	public static final String DEFAULT_CITY = "Avignon";

	/**
	 * Max scale of the canvas
	 */
	public static final int MIN_SCALE = 1;
	
	/**
	 * Max scale of the canvas
	 */
	public static final int MAX_SCALE = 40;
	
	/**
	 * Ratio of the canvas
	 */
	public static final int CANVAS_RATIO = 1;
	
	/**
	 * Thickness of the Motorway roads
	 */
	public static int LEVEL_1_ROAD_THICKNESS = 10;
	
	/**
	 * Thickness of the Trunk roads
	 */
	public static int LEVEL_2_ROAD_THICKNESS = 8;
	
	/**
	 * Thickness of the Primary, Secondary and Tertiary roads
	 */
	public static int LEVEL_3_ROAD_THICKNESS = 6;
	
	/**
	 * Thickness of the road for the Residential, Living Street, Pedestrian and all others roads
	 */
	public static int LEVEL_4_ROAD_THICKNESS = 4;
	
	/**
	 * Horizontal moving distance
	 */
	public static final Double HORI_MOVE_DIST = 350.0;
	
	/**
	 * Zooming ratio
	 */
	public static final Double ZOOM_RATIO = 1.25;
	
	/**
	 * Vertical moving distance
	 */
	public static final Double VERT_MOVE_DIST = 0.19 / 1000000;

}
