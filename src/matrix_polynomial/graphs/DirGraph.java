package graphs;

public class DirGraph {
	private int n; // number of vertices
	private int m; // number of edges
	private int[] out_degree; // out_degrees[i]: the out-degree of vertex i
	private int[][] out_edges; // out_edges[i][j]: the j-th out_edge of vertex i
	private int[] in_degree; // in_degrees[i]: the in-degree of vertex i
	private int[][] in_edges; // in_edges[i][j]: the j-th out_edge of vertex i

	DirGraph(int n, int m, int[] edge_from_array, int[] edge_to_array) {
		this.n = n;
		this.m = m;

		out_degree = new int[n];
		in_degree = new int[n];

		for (int i = 0; i < n; i++) {
			out_degree[i] = 0;
			in_degree[i] = 0;
		}

		for (int i = 0; i < m; i++) {
			out_degree[edge_from_array[i]]++;
			in_degree[edge_to_array[i]]++;
		}

		out_edges = new int[n][];
		in_edges = new int[n][];

		for (int i = 0; i < n; i++) {
			if (out_degree[i] != 0) {
				out_edges[i] = new int[out_degree[i]];
				out_degree[i] = 0;
			} else {
				out_edges[i] = null;
			}

			if (in_degree[i] != 0) {
				in_edges[i] = new int[in_degree[i]];
				in_degree[i] = 0;
			} else {
				in_edges[i] = null;
			}

		}

		for (int i = 0; i < m; i++) {
			out_edges[edge_from_array[i]][out_degree[edge_from_array[i]]++] = edge_to_array[i];
			in_edges[edge_to_array[i]][in_degree[edge_to_array[i]]++] = edge_from_array[i];
		}

		
	}
}
