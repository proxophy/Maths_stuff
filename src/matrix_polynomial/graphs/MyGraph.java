// Undirected Graph

package graphs;

import java.util.LinkedList;

public class MyGraph {
	private int n; // number of vertices
	private int m; // number of edges
	private boolean weighted;
	private double[][] weights = new double[n][n];
	private boolean[][] adj_matrix = new boolean[n][n];
	private LinkedList<LinkedList<Integer>> adj_list = new LinkedList<LinkedList<Integer>>();

	private boolean connected;

	public MyGraph(int vertices, boolean weighted) {
		this.n = vertices;
		this.weighted = weighted;
	}

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

	public void edge_array_add(int[][] edge_array) {
		for (int i = 0; i < edge_array.length; i++) {
			if (edge_array[i].length == 3 && weighted) {
				add_weighted_edge(edge_array[i][0], edge_array[i][1], edge_array[i][2]);
			} else if (edge_array[i].length == 2 && !weighted) {
				add_edge(edge_array[i][0], edge_array[i][1]);
			}
		}
	}

	public LinkedList<Integer> DFS_order() {
		LinkedList<Integer> DFS_order = new LinkedList<Integer>();
		boolean[] visited = new boolean[n];

		// Process each node from 0 to n-1
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				if (i > 0)
					connected = false;

				LinkedList<Integer> stack = new LinkedList<Integer>();
				stack.add(i);

				while (!stack.isEmpty()) {
					int last = stack.getLast();
					boolean finished = false;
					boolean added = false;

					if (adj_list.get(last).size() != 0) {
						for (int j = 0; j < adj_list.get(last).size(); j++) {
							int w = adj_list.get(last).get(j);
							// vertex is added to the stack
							if (!added && !visited[w]) {
								added = true;
								visited[w] = true;
								stack.add(w);
								DFS_order.add(w);
								break;
							}
						}
						finished = !added;
					} else {
						finished = true;
					}

					if (finished) {
						stack.removeLast();
					}
				}
			}
		}
		return DFS_order;
	}

	public LinkedList<Integer> BFS_order() {
		LinkedList<Integer> BFS_order = new LinkedList<Integer>();
		boolean[] visited = new boolean[n];

		// Process each node from 0 to n-1
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				LinkedList<Integer> queue = new LinkedList<Integer>();
				queue.add(i);

				while (!queue.isEmpty()) {
					int last = queue.getFirst();
					boolean finished = false;
					boolean added = false;

					if (adj_list.get(last).size() != 0) {
						for (int j = 0; j < adj_list.get(last).size(); j++) {
							int w = adj_list.get(last).get(j);
							// vertex is added to the stack
							if (!added && !visited[w]) {
								added = true;
								visited[w] = true;
								queue.add(w);
								BFS_order.add(w);
								break;
							}
						}
						finished = !added;
					} else {
						finished = true;
					}

					if (finished) {
						queue.removeFirst();
					}
				}
			}
		}
		return BFS_order;
	}

	public boolean isBipartite() {
		boolean[] dfs_done = new boolean[n];
		int dfs_count = 0;

		// Do DFS n times
		for (int i = 0; i < n; i++) {
			int curr = i;
			if (dfs_count == n) {
				break;
			} else if (dfs_done[curr] || adj_list.get(curr).size() == 0) {
				continue;
			}

			boolean[] visited = new boolean[n];
			int[] pre = new int[n];
			int[] dfs = new int[n];
			visited[curr] = true;
			pre[curr] = curr;

			LinkedList<Integer> stack = new LinkedList<Integer>();
			stack.addLast(curr);
			dfs_count++;

			int count = 0;
			while (!stack.isEmpty() && count <= m) {
				count++;

				int last = stack.getLast();
				boolean finished = false;
				boolean added = false;

				if (adj_list.get(last).size() != 0) {
					for (int j = 0; j < adj_list.get(last).size(); j++) {
						int w = adj_list.get(last).get(j);
						// cycles with odd length found
						if (visited[w] && (pre[w] != last) && ((dfs[last] - dfs[w]) % 2 == 0)) {
							return false;
						}
						// vertex is added to the stack
						else if (!added && !visited[w]) {
							added = true;

							visited[w] = true;
							pre[w] = last;
							dfs[w] = count;

							stack.addLast(w);
							dfs_count++;
							dfs_done[w] = true;
						}

					}
					finished = !added;

				} else {
					finished = true;
				}

				if (finished) {
					stack.removeLast();
				}

			}
			dfs_done[curr] = true;

		}

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
