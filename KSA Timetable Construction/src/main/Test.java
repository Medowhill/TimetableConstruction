package main;

import pool.Graph;

public class Test {

	public static void main(String[] args) {

		testTime();
		// testClique();
		// testValue();

	}

	static void testTime() {
		int N = 400;
		int M = 1000;
		int D = 3;

		long time = 0;
		int edge = 0;

		for (int j = 0; j < M; j++) {
			Graph g = new Graph(N);
			for (int i = 0; i < N * (N + 1) / 2 / D; i++) {
				int x = (int) (Math.random() * N);
				int y = (int) (Math.random() * N);
				g.addEdge(x, y);
			}

			long start = System.currentTimeMillis();
			int[] maxclique = g.maxClique(true);
			long end = System.currentTimeMillis();
			time += end - start;
			edge += g.getEdgeNum();
		}

		System.out.println(time / M);
		System.out.println(edge / M);
	}

	static void testClique() {
		Graph g = new Graph(9);
		g.addEdge(1, 3);
		g.addEdge(1, 5);
		g.addEdge(1, 6);
		g.addEdge(1, 8);
		g.addEdge(2, 5);
		g.addEdge(2, 6);
		g.addEdge(2, 7);
		g.addEdge(3, 6);
		g.addEdge(3, 7);
		g.addEdge(3, 8);
		g.addEdge(4, 6);
		g.addEdge(4, 7);
		g.addEdge(4, 8);
		g.addEdge(5, 7);
		g.addEdge(5, 8);

		// int[] clique = g.ordering();
		int[] clique = g.maxClique(true);

		for (int i = 0; i < clique.length; i++)
			System.out.println(clique[i]);
	}

	static void testValue() {

		int N = 300;

		for (int j = 0; j < 100; j++) {
			Graph g = new Graph(N);
			for (int i = 0; i < N * (N + 1) / 2 / 10; i++) {
				int x = (int) (Math.random() * N);
				int y = (int) (Math.random() * N);
				g.addEdge(x, y);
			}
			System.out.println(g.maxClique_old() == g.maxClique(true).length);
		}

	}
}
