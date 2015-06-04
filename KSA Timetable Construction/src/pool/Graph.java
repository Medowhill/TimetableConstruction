package pool;

import java.util.ArrayList;

public class Graph {

	private int edgeNum = 0;

	private int max;
	private int[] c;
	private int[] max_clique;
	private ArrayList<int[]> maxCliques;

	private final boolean[][] edges;

	public Graph(int vertices) {
		edges = new boolean[vertices][vertices];
		c = new int[vertices];
		max_clique = new int[vertices];
		maxCliques = new ArrayList<>();
	}

	public boolean addEdge(int i, int j) {
		if (i < 0 || i >= edges.length)
			return false;
		if (j < 0 || j >= edges.length)
			return false;
		if (i == j)
			return false;
		if (edges[i][j])
			return false;
		edges[i][j] = true;
		edges[j][i] = true;
		edgeNum++;
		return true;
	}

	private void clique(int[] vertices, int size) {

		if (vertices.length == 0) {
			if (size > max) {
				max = size;
				int[] tmp = new int[max];
				for (int i = 0; i < max; i++)
					tmp[i] = max_clique[i];
				maxCliques.clear();
				maxCliques.add(tmp);
			} else if (size == max) {
				int[] tmp = new int[max];
				for (int i = 0; i < max; i++)
					tmp[i] = max_clique[i];
				maxCliques.add(tmp);
			}
			return;
		}

		while (vertices.length != 0) {
			if (size + vertices.length < max)
				return;

			int min = vertices[0];

			if (size + c[min] < max)
				return;

			vertices = remove(vertices, min);

			max_clique[size] = min;

			clique(adjacent(vertices, min), size + 1);
		}
	}

	public ArrayList<int[]> maxClique(boolean ordering) {
		max = 0;

		int[] arr;
		if (ordering)
			arr = ordering();
		else {
			arr = new int[edges.length];
			for (int i = 0; i < arr.length; i++)
				arr[i] = i;
		}

		for (int i = edges.length - 1; i >= 0; i--) {

			int k = 0;
			int[] tmpVertices = new int[edges.length - i];
			for (int j = 1; j < tmpVertices.length; j++) {
				if (edges[arr[i + j]][arr[i]]) {
					tmpVertices[k] = arr[i + j];
					k++;
				}
			}

			int[] vertices = new int[k];
			for (int j = 0; j < vertices.length; j++)
				vertices[j] = tmpVertices[j];

			max_clique[0] = arr[i];

			clique(vertices, 1);

			c[arr[i]] = max;
		}

		return maxCliques;
	}

	private int[] ordering() {

		// long start = System.currentTimeMillis();

		int[] orderedVertices = new int[edges.length];
		int count = orderedVertices.length - 1;

		int[] vertices = new int[edges.length];
		for (int i = 0; i < vertices.length; i++)
			vertices[i] = i;

		while (vertices.length > 0) {
			int[] vertices_ = new int[vertices.length];
			for (int i = 0; i < vertices.length; i++)
				vertices_[i] = vertices[i];

			int[] degrees = new int[edges.length];
			for (int i = 0; i < vertices.length; i++) {
				for (int j = i + 1; j < vertices.length; j++) {
					if (edges[vertices[i]][vertices[j]]) {
						degrees[vertices[i]]++;
						degrees[vertices[j]]++;
					}
				}
			}

			while (vertices_.length > 0) {
				int vertex = findMaxDegreeVertex(vertices_, degrees);
				orderedVertices[count] = vertex;
				count--;
				vertices_ = disjoint(vertices_, vertex);
				vertices = remove(vertices, vertex);
			}
		}

		// System.out.println(System.currentTimeMillis() - start);

		return orderedVertices;

	}

	private int[] remove(int[] arr, int x) {
		int k = 0;
		int[] narr = new int[arr.length - 1];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != x) {
				narr[k] = arr[i];
				k++;
			}
		}

		return narr;
	}

	private int[] adjacent(int[] arr, int x) {
		int[] tmpArr = new int[arr.length];
		int k = 0;
		for (int i = 0; i < arr.length; i++) {
			if (edges[arr[i]][x]) {
				tmpArr[k] = arr[i];
				k++;
			}
		}

		int[] narr = new int[k];
		for (int i = 0; i < k; i++)
			narr[i] = tmpArr[i];

		return narr;
	}

	private int[] disjoint(int[] arr, int x) {
		int[] tmpArr = new int[arr.length];
		int k = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != x && !edges[arr[i]][x]) {
				tmpArr[k] = arr[i];
				k++;
			}
		}

		int[] narr = new int[k];
		for (int i = 0; i < k; i++)
			narr[i] = tmpArr[i];

		return narr;
	}

	private int findMaxDegreeVertex(int[] vertices, int[] degrees) {

		int max = degrees[vertices[0]];
		int index = 0;
		for (int i = 1; i < vertices.length; i++) {
			if (degrees[vertices[i]] > max) {
				max = degrees[vertices[i]];
				index = i;
			}
		}

		return vertices[index];
	}

	public int getEdgeNum() {
		return edgeNum;
	}

}
