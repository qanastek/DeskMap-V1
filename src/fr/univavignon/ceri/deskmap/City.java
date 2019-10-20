package fr.univavignon.ceri.deskmap;

public class City {
	
	public Long id;
	public Double lat;
	public Double lon;
	public String name;
	
	public City(String id, Double lat, Double lon, String name) {
		this.id = Long.parseLong(id);
		this.lat = lat;
		this.lon = lon;
		this.name = name;
	}
	@Override
	public String toString() {
		return "[" + this.id + "," + this.lat + "," + this.lon + "," + this.name + "]";
	}

}
