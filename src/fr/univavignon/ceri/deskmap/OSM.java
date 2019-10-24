package fr.univavignon.ceri.deskmap;

/**
 * A class which contain every single command necessary for the built of an Overpass API query.
 * @author Yanis Labrak
 */
public class OSM {
	
	/**
	 * The query URL
	 */
	public String query;
	
	/**
	 * <b>Constructor</b>
	 * <br>
	 * Initialize the query to an empty {@code String}
	 * @author Yanis Labrak
	 */
	public OSM() {
		this.query = "";
	}
	
	/**
	 * Define the output fields, format and column separator
	 * @param format {@code String}
	 * @param parameters {@code String}
	 * @param header {@code Boolean}
	 * @param delimiter {@code String}
	 * @author Yanis Labrak
	 */
	public void output(String format, String parameters, Boolean header, String delimiter) {
		
		this.query += "[out:" + format;
		
		switch (format) {
			case "csv":			
				this.query += "(" + parameters.toString() +  ";" + header.toString() + ";\"" + delimiter + "\")";			
				break;
			case "json":
				break;
		}
		
		this.query += "];";
	}
	
	/**
	 * Set the area where we search
	 * @param place {@code String} Zone dans laquelle les noeuds devront ce trouvé
	 * @author Yanis Labrak
	 */
	public void area(String place) {
		this.query += "(area[name=\"" + place + "\"];)->.SA;(";
	}
	
	/**
	 * Add a kind of node to fetch
	 * @param key {@code String} Key
	 * @param value {@code String} Value
	 * @author Yanis Labrak
	 */
	public void node(String key, String value) {
		this.query += "node[\"" + key + "\"=\"" + value + "\"](area.SA);";
	}
	
	/**
	 * <u>Example:</u> {@code way("amenity", "post_box");}
	 * @param key {@code String} Key
	 * @param value {@code String} Value
	 * @author Yanis Labrak
	 */
	public void way(String key, String value) {
		this.query += "way[\"" + key + "\"=\"" + value + "\"](area.SA);";		
	}
	
	/**
	 * <u>Example:</u> {@code way("highway");}
	 * @param key {@code String} Key
	 * @author Yanis Labrak
	 */
	public void way(String key) {
		this.query += "way[\"" + key + "\"](area.SA);";
	}
	
	/**
	 * End of the query
	 * @author Yanis Labrak
	 */
	public void out() {
		this.query += ");out;";
	}
	
	/**
	 * Testing main
	 * @param args {@code String[]}
	 * @author Yanis Labrak
	 */
	public static void main(String[] args) {
		OSM o = new OSM();
		
		o.output("csv", "::id, name", false, "|");
		o.area("France");
		o.node("place", "city");
//		o.way("amenity", "post_box");
//		o.way("highway");
		o.out();
		
		System.out.println(o.query);
	}

}
