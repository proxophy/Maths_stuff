// Undirected Graph

package graphs;

import java.util.LinkedList;

public class Graph extends DirGraph{
	private int n; // number of vertices
	private int m; // number of edges
	private boolean weighted;
	private double[][] weights = new double[n][n];
	private boolean[][] adj_matrix = new boolean[n][n];
	private LinkedList<LinkedList<Integer>> adj_list = new LinkedList<LinkedList<Integer>>();

	private boolean connected;

//	public Graph(int vertices, boolean weighted) {
//		this.n = vertices;
//		this.weighted = weighted;
//	}

	@Override
	public boolean add_edge(int u, int v) {
		assert u >= 0 && v >= 0 && u < n && v < n;
		if (adj_matrix[u][v])
			return false; // edge already exists

		adj_list.get(u).add(v);
		adj_list.get(v).add(u);
		adj_matrix[u][v] = true;
		adj_matrix[v][u] = true;
		m++;

		return true;
	}

	@Override
	public boolean add_weighted_edge(int u, int v, int weight) {
		assert u >= 0 && v >= 0 && u < n && v < n;
		if (adj_matrix[u][v])
			return false; // edge already exists

		adj_list.get(u).add(v);
		adj_list.get(v).add(u);
		adj_matrix[u][v] = true;
		adj_matrix[v][u] = true;
		weights[u][v] = weight;
		weights[v][u] = weight;
		m++;

		return true;
	}

	@Override
	public boolean remove_edge(int u, int v) {
		assert u >= 0 && v >= 0 && u < n && v < n;
		if (!adj_matrix[u][v])
			return false; // edge doesn't exist

		int i = 0;
		for (int w : adj_list.get(u)) {
			if (w == v)
				adj_list.get(u).remove(i); // remove v
			i++;
		}

		i = 0;
		for (int w : adj_list.get(v)) {
			if (w == u)
				adj_list.get(v).remove(i); // remove u
			i++;
		}

		adj_matrix[u][v] = false;
		adj_matrix[v][u] = false;
		weights[u][v] = 0;
		weights[v][u] = 0;

		return true;
	}

	public boolean isConnected() {
		return connected;
	}
	
	/*
	 * TODO: Reachable
	 * 
	 * TODO Directed: DFS, BFS: Reachable isTopologicalsort orderedtriple
	 * Eulerianpath and eulercycle
	 */

}
