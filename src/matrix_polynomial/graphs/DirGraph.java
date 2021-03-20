// Author: Isabel Haas
// Methods

package graphs;

import java.util.LinkedList;

public class DirGraph {
	private int n; // number of vertices
	private int m; // number of edges
	private int[] out_degrees; // out_degrees[i]: the out-degree of vertex i
	private int[][] out_edges; // out_edges[i][j]: the j-th out_edge of vertex i
	private int[] in_degrees; // in_degrees[i]: the in-degree of vertex i
	private int[][] in_edges; // in_edges[i][j]: the j-th out_edge of vertex i

	public DirGraph(int n, int m, int[] edge_from_array, int[] edge_to_array) {
		this.n = n;
		this.m = m;

		out_degrees = new int[n];
		in_degrees = new int[n];

		for (int i = 0; i < n; i++) {
			out_degrees[i] = 0;
			in_degrees[i] = 0;
		}

		for (int i = 0; i < m; i++) {
			out_degrees[edge_from_array[i]]++;
			in_degrees[edge_to_array[i]]++;
		}

		out_edges = new int[n][];
		in_edges = new int[n][];

		for (int i = 0; i < n; i++) {
			if (out_degrees[i] != 0) {
				out_edges[i] = new int[out_degrees[i]];
				out_degrees[i] = 0;
			} else {
				out_edges[i] = null;
			}

			if (in_degrees[i] != 0) {
				in_edges[i] = new int[in_degrees[i]];
				in_degrees[i] = 0;
			} else {
				in_edges[i] = null;
			}

		}

		for (int i = 0; i < m; i++) {
			out_edges[edge_from_array[i]][out_degrees[edge_from_array[i]]++] = edge_to_array[i];
			in_edges[edge_to_array[i]][in_degrees[edge_to_array[i]]++] = edge_from_array[i];
		}

	}

	public boolean Reachable(int u, int v) {
		if (u >= n || v >= n) {
			return false;
		} else if (u == v) {
			return true;
		} else {
			boolean[] reachable = new boolean[n];

			// BFS
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(u);
			int s = u;
			while (queue.size() != 0) {
				queue.remove(0);

				if (!reachable[s]) {
					reachable[s] = true;
					if (reachable[v]) {
						return true;
					}
					if (out_edges[s] != null) {
						for (int i = 0; i < out_degrees[s]; i++) {
							queue.add(out_edges[s][i]);
							if (out_edges[s][i] == v) {
								return true;
							}
						}
					}
				}
				if (queue.size() != 0) {
					s = queue.getFirst();
				}
			}

		}
		return false;
	}

	public boolean IsTopologicalSort(int sorted_array[]) {
		int[] topsort_index = new int[n];

		if (sorted_array.length != n) {
			return false;
		}
		int curr;
		for (int i = 0; i < n; i++) {
			curr = sorted_array[i];
			topsort_index[curr] = i;
		}

		for (int i = 0; i < n; i++) {
			if (out_edges[i] != null) {

				if (out_degrees[i] >= n - topsort_index[i]) {
					return false;
				}
				for (int j = 0; j < out_edges[i].length; j++) {
					curr = out_edges[i][j];

					if (topsort_index[i] >= topsort_index[curr]) {
						return false;
					}
				}
			}

		}

		return true;
	}

	public boolean OrderedTriple(int u, int v, int w) {
		boolean uw = false;
		boolean uv = false;
		boolean vw = false;

		if (out_edges[u] == null || out_edges[v] == null) {
			return false;
		} else if (u >= n || v >= n || w >= n) {
			return false;
		} else {
			for (int i = 0; i < out_edges[u].length; i++) {
				if (out_edges[u][i] == w) {
					uw = true;
				} else if (out_edges[u][i] == v) {
					uv = true;
				}
			}
			for (int j = 0; j < out_edges[v].length; j++) {
				if (out_edges[v][j] == w) {
					vw = true;
				}
			}
		}

		return uw && uv && vw;
	}

	public boolean hasEulerianPath() {
		int an = -1;
		int bn = -1;

		// check if path is possible and find beginning and end
		for (int i = 0; i < n; i++) {
			if (in_degrees[i] == out_degrees[i]) {
				continue;
			} else if (in_degrees[i] + 1 == out_degrees[i]) {
				if (an == -1) {
					an = i;
				} else {
					return false;
				}
			} else if (in_degrees[i] == out_degrees[i] + 1) {
				if (bn == -1) {
					bn = i;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		if (an == -1 || bn == -1) {
			if (hasEulerianCycle()) {
				return true;
			}
			return false;
		}
		// add edge from bn to an;
		out_degrees[bn]++;
		int[] new_out_bn = new int[out_degrees[bn]];
		if (out_degrees[bn] > 1) {
			for (int i = 0; i < out_degrees[bn] - 1; i++) {
				new_out_bn[i] = out_edges[bn][i];
			}
		}

		new_out_bn[out_degrees[bn] - 1] = an;
		out_edges[bn] = new_out_bn;

		in_degrees[an]++;
		int[] new_in_an = new int[in_degrees[an]];
		if (in_degrees[an] > 1) {
			for (int i = 0; i < in_degrees[an] - 1; i++) {
				new_in_an[i] = in_edges[an][i];
			}
		}
		new_in_an[in_degrees[an] - 1] = bn;
		in_edges[an] = new_in_an;
		m++;

		// Euler Path
		LinkedList<Integer> stack = new LinkedList<Integer>();
		int counter = 0;
		int curr = 0;
		boolean[][] marked = new boolean[n][];

		for (int i = 0; i < n; i++) {
			marked[i] = new boolean[out_degrees[i]];
		}

		stack.addLast(0);

		while (!stack.isEmpty() && counter < m) {

			curr = stack.getLast();
			// find unmarked edge
			if (out_degrees[curr] != 0) {
				boolean finished = true;

				for (int i = 0; i < out_degrees[curr]; i++) {
					if (marked[curr][i] == false) {
						finished = false;
						marked[curr][i] = true;
						stack.addLast(out_edges[curr][i]);
						counter++;
						break;
					}
				}

				if (finished) {
					stack.removeLast();
				}

			} else {
				stack.pollLast();
			}
		}

		if (counter == m) {
			return true;
		} else {
			return false;
		}

	}

	public boolean hasEulerianCycle() {

		LinkedList<Integer> stack = new LinkedList<Integer>();
		int counter = 0;
		int curr = 0;
		boolean[][] marked = new boolean[n][];

		for (int i = 0; i < n; i++) {
			marked[i] = new boolean[out_degrees[i]];
		}

		stack.addLast(0);

		while (!stack.isEmpty() && counter < m) {

			curr = stack.getLast();
			// find unmarked edge
			if (out_degrees[curr] != 0) {
				boolean finished = true;

				for (int i = 0; i < out_degrees[curr]; i++) {
					if (marked[curr][i] == false) {
						finished = false;
						marked[curr][i] = true;
						stack.addLast(out_edges[curr][i]);
						counter++;
						break;
					}
				}

				if (finished) {
					stack.removeLast();
				}

			} else {
				stack.pollLast();
			}
		}

		if (counter == m) {
			return true;
		} else {
			return false;
		}

	}
}

