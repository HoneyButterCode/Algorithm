package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;

import com.sun.javafx.binding.StringFormatter;

import Graph.Edge;
import Graph.Node;


public class Dijkstra_adjMatrix {
	int[][] adjMatrix;
	final int INF = Integer.MAX_VALUE;
	ArrayList<Path> result;
	ArrayList<String> nodeList;

	class Path {
		String id;
		int cost;
		boolean isKnown;
		Path via = null;

		public Path(String id) {
			this(id, INF, false, null);
		}

		public Path(String id, int cost, boolean isKnown, Path via) {
			super();
			this.id = id;
			this.cost = cost;
			this.isKnown = isKnown;
			this.via = via;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.format("[%s\t%d\t%s\t%s]", id, cost, isKnown, (via!=null) ? via.id:"null");
		}
	}

	void init(ArrayList<Edge> edges, ArrayList<String> nodeNames) {
		System.out.println("Init~!");

		nodeList = nodeNames;
		result = new ArrayList<Path>(nodeList.size());
		adjMatrix = new int[nodeList.size()][nodeList.size()];

		for (String id : nodeNames)
			result.add(new Path(id));

		System.out.println(result);

		for (int[] arr : adjMatrix)
			Arrays.fill(arr, INF);

		for (Edge e : edges) {
			insertEdge(e);
		}
	}

	void displayPath() {
		for (Path p: result) {
			System.out.println(p);
		}
	}

	void display() {
		for (int[] arr : adjMatrix) {
			for (int cost : arr)
				System.out.print(String.format("%5d", (cost == INF) ? -1:cost));
			System.out.println("");
		}
	}

	void insertEdge(Edge e) {
		Node node = null;
		int idxFrom = nodeList.indexOf(e.from);
		int idxTo = nodeList.indexOf(e.to);

		adjMatrix[idxFrom][idxTo] = e.cost;
	}

	Path pickShortestOne() {
		Path selected = null;
		int minValue = INF;

		for (Path p : result) {
			if(p.isKnown == false && p.cost < minValue) {
				minValue = p.cost;
				selected = p;
			}
		}

		if (selected != null) {
			selected.isKnown = true;
		}

		return selected;
	}

	void calcShortestPath(String start) {

		//init AdjMatrix
		int startIdx = nodeList.indexOf(start);
		result.get(startIdx).cost = 0;

		//pick Shortest cost vertex
		Path selected = pickShortestOne();

		while (selected != null) {
			//update via cost
			int selIdx = nodeList.indexOf(selected.id);
			int costToSelect = result.get(selIdx).cost;
			for(int i = 0; i < adjMatrix.length; i++) {
				int currCost = result.get(i).cost;
				int cost = costToSelect + adjMatrix[selIdx][i];
				if (adjMatrix[selIdx][i] < INF && cost < currCost) {
					result.get(i).cost = cost;
					result.get(i).via = selected;
				}
			}

			selected = pickShortestOne();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dijkstra_adjMatrix dijkstra = new Dijkstra_adjMatrix();

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

		ArrayList<String> nodeNames = new ArrayList<String>( Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7") );

		dijkstra.init(list, nodeNames);
		dijkstra.displayPath();
		dijkstra.calcShortestPath("0");
		dijkstra.displayPath();
	}

}
