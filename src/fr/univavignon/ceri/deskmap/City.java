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
	
	public City(City city) {
		this.id = city.id;
		this.lat = city.lat;
		this.lon = city.lon;
		this.name = city.name;		
	}
	
	@Override
	public String toString() {
		return "[" + this.id + "," + this.lat + "," + this.lon + "," + this.name + "]";
	}
}
