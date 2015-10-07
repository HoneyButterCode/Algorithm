package Graph;

public class Node {
	public String id;
	public int cost;
	
	public Node link;

	public Node(String id, int cost, Node link) {
		super();
		this.id = id;
		this.cost = cost;
		this.link = link;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("[%s]", id);
	}
}
