package fr.univavignon.ceri.deskmap.models;

/**
 * A class which represent the structure of an OSM Street entity
 * @author Yanis Labrak
 */
public class Street {
	
	/**
	 * Identifier of the street node {@code Long}
	 */
	public Long id;
	
	/**
	 * Name of the street {@code String}
	 */
	public String name;
	
	/**
	 * @param id {@code String} Identifier
	 * @param name {@code String} name
	 * @author Yanis Labrak
	 */
	public Street(String id, String name) {
		this.id = Long.parseLong(id);
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}