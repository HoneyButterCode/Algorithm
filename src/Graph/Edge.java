package Graph;

public class Edge {
	public String from;
	public String to;
	public int cost;
	
	public Edge(String from, String to, int cost) {
		super();
		this.from = from;
		this.to = to;
		this.cost = cost;
	}
	
	public Edge(Integer from, Integer to, int cost) {
		super();
		this.from = from.toString();
		this.to = to.toString();
		this.cost = cost;
	}
}
