package Algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import Algorithm.Dijkstra.PathTable.Path;
import Graph.Edge;
import Graph.Node;

public class Dijkstra {
	final static int INF = Integer.MAX_VALUE;

	PathTable table;
	ArrayList<Node> adjList;

//	int adjMatrix[][];

	class PathTable implements Comparator<Path> {
		ArrayList<Path> table;
		PriorityQueue<Path> queue;

		public PathTable() {
			super();
			table = new ArrayList<Path>();
			queue = new PriorityQueue<>(this);
		}

		@Override
		public int compare(Path o1, Path o2) {
			// TODO Auto-generated method stub
			return o1.cost - o2.cost;
		}

		class Path extends Node {
			Boolean isKnown;

			public Path(String name, Node link) {
				super(name, INF, link);
				this.isKnown = false;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj instanceof Path)
					return ((Path)obj).id.equals(this.id);
				else
					return false;
			}

		}

		Path get(String vertexName) {
			for (Path p: table) {
				if (p.id.equals(vertexName))
					return p;
			}

			return null;
		}

		void updateCost(Node via) {
			Node next = via.link;
			int costToVia = get(via.id).cost;

			while (next != null) {
				int currCost = get(next.id).cost;
				int anotherCost = costToVia + next.cost;

				if( anotherCost < currCost ) {
					Path update = get(next.id);
					update.cost = anotherCost;
					update.link = via;
					System.out.println("update Cost " + currCost + " -> " + anotherCost + " via " + via.id);
					queue.add(update);
				}

				next = next.link;
			}
		}

		void addVertex(String vertexName) {
			table.add(new Path(vertexName, null));
		}

		void setStartNode(String nodeName) {
			Path start = get(nodeName);
			start.cost = 0;
			
			queue.clear();
			queue.add(start);
//			for (Path p : table) {
//				if (p.isKnown == false)
//					queue.add(p);
//			}
		}

		Path selectNode() {
//			queue.clear();
//			for (Path p : table) {
//				if (p.isKnown == false)
//					queue.add(p);
//			}

			Path selected = null;
			while(!queue.isEmpty()) {
				selected = queue.poll();
				
				if(get(selected.id).isKnown == false) {
					break;
				}
			}
			
			if (selected != null) {
				selected.isKnown = true;
				System.out.println("Pick~!" + selected.id);
			}

			return selected;
		}
	}


	Node getNode(String name) {
		for (Node node : adjList) {
			if (node.id.equals(name)) {
				return node;
			}
		}
		return null;
	}

	void init(ArrayList<Edge> edges) {
		System.out.println("Init~!");

		table = new PathTable();
		adjList = new ArrayList<Node>();

		for (Edge e : edges) {
			insertEdge(e);
		}

		for (Node n : adjList) {
			table.addVertex(n.id);
		}

	}

	void insertEdge(Edge e) {
		Node node = null;

		for ( Node n : adjList) {
			if (e.from.equals(n.id)) {
				node = n;
			}
		}

		if (node == null) {
			node = new Node(e.from, 0, null);
			adjList.add(node);
		}

		while(node.link != null)
			node = node.link;  //insert to tail

		node.link = new Node(e.to, e.cost, null);
	}


	void displayPath() {
		System.out.println(String.format("name \t cost \t via\n"));
//		System.out.println(table.table);
		for (Path p : table.table) {
			System.out.println(p.id + "\t" + p.cost + "\t" + p.link + "\t" + p.isKnown);
		}
	}

	void calcShortestPath(String start) {
		table.setStartNode(start);
		Path selected = table.selectNode();

		while(selected != null) {
			Node nodeInAdjList = getNode(selected.id);
			table.updateCost(nodeInAdjList);
			selected = table.selectNode();
		}

	}

	public static void main(String[] args) {
		ArrayList<Edge> list = new ArrayList<Edge>();
		list.add(new Edge(0, 1, 4));
		list.add(new Edge(0, 2, 9));
		list.add(new Edge(0, 4, 2));
		list.add(new Edge(1, 0, 4));
		list.add(new Edge(1, 5, 7));
		list.add(new Edge(2, 0, 9));
		list.add(new Edge(2, 4, 3));
		list.add(new Edge(2, 5, 4));
		list.add(new Edge(2, 6, 3));
		list.add(new Edge(3, 5, 8));
		list.add(new Edge(4, 0, 2));
		list.add(new Edge(4, 2, 3));
		list.add(new Edge(4, 6, 8));
		list.add(new Edge(5, 1, 7));
		list.add(new Edge(5, 2, 4));
		list.add(new Edge(5, 3, 8));
		list.add(new Edge(5, 7, 2));
		list.add(new Edge(6, 2, 3));
		list.add(new Edge(6, 4, 8));
		list.add(new Edge(6, 7, 4));
		list.add(new Edge(7, 5, 2));
		list.add(new Edge(7, 6, 4));

		Dijkstra dij = new Dijkstra();

		dij.init(list);
		dij.calcShortestPath("0");
		dij.displayPath();
	}
}
