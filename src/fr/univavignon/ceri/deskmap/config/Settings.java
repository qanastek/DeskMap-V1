/**
 * 
 */
package fr.univavignon.ceri.deskmap;

/**
 * This class represent all the settings of the software
 * @author Yanis Labrak
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
	 * Zooming scale
	 */
	public static final Double ZOOMING_SCALE = 1.25;
	
	/**
	 * Ratio of the canvas
	 */
	public static final int CANVAS_RATIO = 1;
	
	/**
	 * Thickness of the Motorway roads
	 */
	public static final int LEVEL_1_ROAD_THICKNESS = 4;
	
	/**
	 * Thickness of the Trunk roads
	 */
	public static final int LEVEL_2_ROAD_THICKNESS = 3;
	
	/**
	 * Thickness of the Primary, Secondary and Tertiary roads
	 */
	public static final int LEVEL_3_ROAD_THICKNESS = 2;
	
	/**
	 * Thickness of the road for the Residential, Living Street, Pedestrian and all others roads
	 */
	public static final int LEVEL_4_ROAD_THICKNESS = 1;
	
	/**
	 * Horizontal moving distance
	 */
	public static final Double HORI_MOVE_DIST = 350.0;
	
	/**
	 * Vertical moving distance
	 */
	public static final Double VERT_MOVE_DIST = 0.19 / 1000000;

}
