package fr.univavignon.ceri.deskmap;

public class Street {
	
	public Long id;
	public String name;
	
	public Street(String id, String name) {
		this.id = Long.parseLong(id);
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
//		return "[" + this.id + "," + this.name + "]";
	}

}