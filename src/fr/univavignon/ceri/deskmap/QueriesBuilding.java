/**
 * 
 */
package fr.univavignon.ceri.deskmap;

import java.io.UnsupportedEncodingException;

import fr.univavignon.ceri.deskmap.geopoint.City;

/**
 * @author Yanis Labrak
 *
 */
public abstract class QueriesBuilding {

	/**
	 * Build a Overpass Query in the way to fetch all the objects necessary to display the map
	 * @param bbox The Bounding box in which we want the data
	 * @return The OSM query
	 * @throws UnsupportedEncodingException Thrown when the encoding process failed
	 */
	public static String fullMapQuery(String bbox) throws UnsupportedEncodingException {
		// TODO: Full query
		OSM queryOverpass = new OSM();
		
		queryOverpass.output("json", "", false, "");
		queryOverpass.start();
		
		queryOverpass.nodeMulti("landuse", "residential|industrial|commercial|retail|railway|cemetery|forest|grass|farmland|greenhouse_horticulture|farmyard|quarry", bbox);
		queryOverpass.way("landuse","residential",bbox);
		queryOverpass.way("landuse","industrial",bbox);
		queryOverpass.way("landuse","commercial",bbox);
		queryOverpass.way("landuse","retail",bbox);
		queryOverpass.way("landuse","railway",bbox);
		queryOverpass.way("landuse","cemetery",bbox);
		queryOverpass.way("landuse","forest",bbox);
		queryOverpass.way("landuse","grass",bbox);
		queryOverpass.way("landuse","greenhouse_horticulture",bbox);
		queryOverpass.way("landuse","farmland",bbox);
		queryOverpass.way("landuse","farmyard",bbox);
		queryOverpass.way("landuse","quarry",bbox);
		queryOverpass.relation("landuse",bbox);

		queryOverpass.node("amenity", "school", bbox);
		queryOverpass.way("amenity","school",bbox);
		queryOverpass.relation("amenity",bbox);
		
		queryOverpass.node("waterway", "riverbank", bbox);
		queryOverpass.way("waterway","riverbank",bbox);
		queryOverpass.relation("waterway",bbox);

		queryOverpass.nodeMulti("leisure", "sports_centre|park|golf_course", bbox);
		queryOverpass.way("leisure","sports_centre",bbox);
		queryOverpass.way("leisure","park",bbox);
		queryOverpass.way("leisure","golf_course",bbox);
		queryOverpass.relation("leisure",bbox);

		queryOverpass.nodeMulti("highway", "primary|secondary|tertiary|trunk|residential|living_street|pedestrian|motorway|unclassified", bbox);
		queryOverpass.way("highway","primary",bbox);
		queryOverpass.way("highway","secondary",bbox);
		queryOverpass.way("highway","tertiary",bbox);
		queryOverpass.way("highway","trunk",bbox);
		queryOverpass.way("highway","residential",bbox);
		queryOverpass.way("highway","living_street",bbox);
		queryOverpass.way("highway","pedestrian",bbox);
		queryOverpass.way("highway","motorway",bbox);
		queryOverpass.way("highway","unclassified",bbox);
		queryOverpass.relation("highway",bbox);

		queryOverpass.node("building","yes",bbox);
		queryOverpass.way("building","yes",bbox);
		queryOverpass.way("building","school",bbox);
		queryOverpass.relation("building",bbox);
		
		queryOverpass.nodeMulti("natural","water|scrub|wood",bbox);
		queryOverpass.way("natural","water",bbox);
		queryOverpass.way("natural","scrub",bbox);
		queryOverpass.way("natural","wood",bbox);
		queryOverpass.relation("natural",bbox);
		
		queryOverpass.outBodySkel();
		
		String query = queryOverpass.query;
		
		System.out.println("Query full map created: " + query);
		return query;
	}
	

	/**
	 * Send the HTTP GET request to the Overpass API
	 * Get all the city from a specific country
	 * @param country {@code String} Country name
	 * @throws Exception {@code no informations}
	 * @return {@code String} The query
	 * @author Yanis Labrak
	 */
	public static String buildFetchCitiesQuery(String country) throws Exception {

		OSM queryOverpass = new OSM();
		
		queryOverpass.output("csv", "::id,::lat,::lon,name", false, "|");
		queryOverpass.area(country);
		queryOverpass.start();
		queryOverpass.node("place", "city");
		queryOverpass.node("place", "town");
		queryOverpass.way("place");
		queryOverpass.out();
		
		String query = queryOverpass.toString();
		
		return query;
	}
		
	/**
	 * Fetch all the cities from the API inside a file
	 * @param city {@code City} From where we will get all the streets
	 * @throws Exception Throw a exception if the file cannot be create
	 * @return {@code String} The query
	 * @author Yanis Labrak
	 */
	public static String buildFetchStreetsQuery(City city) throws Exception {
		
		OSM queryOverpass = new OSM();
		
		queryOverpass.output("csv", "::id,name", false, "|");
		queryOverpass.area(city.name);
		queryOverpass.start();
		queryOverpass.node("highway", "primary");
		queryOverpass.node("highway", "secondary");
		queryOverpass.node("highway", "tertiary");
		queryOverpass.node("highway", "residential");
		queryOverpass.node("highway", "unclassified");
		queryOverpass.way("highway");
		queryOverpass.out();
		
		String query = queryOverpass.toString();
		
		return query;
	}

}
