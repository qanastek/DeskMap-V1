package fr.univavignon.ceri.deskmap.models;

public class Edge {
	private final String id;
    private final Node source;
    private final Node destination;
    private final int weight;

    public Edge(String id, Node source, Node destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * @return id of way
     */
    public String getId() {
        return this.id;
    }
    public Node getDestination() {
        return this.destination;
    }

    public Node getSource() {
        return this.source;
    }
    public int getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return this.source + " " + this.destination;
    }
}
