package graphs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Graph {
	private int n; // number of vertices
	private int m; // number of edges
	private int[] degrees; // degrees[i]: the degree of vertex i
	private int[][] edges; // edges[i][j]: the endpoint of the j-th edge of vertex i
	private int[][] weights; // weights[i][j]: the weight of the j-th edge of vertex i
//	private Edge[] g_edges;

	public Graph(int n, int m, int[][] edge_array) {

		this.n = n;
		this.m = m;
		degrees = new int[n];

		for (int i = 0; i < n; i++) {
			degrees[i] = 0;
		}

		for (int i = 0; i < m; i++) {
			degrees[edge_array[i][0]]++;
			degrees[edge_array[i][1]]++;
		//	g_edges[i] = new Edge(edge_array[i][0], edge_array[i][1], edge_array[i][1]);
		}

		edges = new int[n][];
		weights = new int[n][];

		for (int i = 0; i < n; i++) {
			if (degrees[i] != 0) {
				edges[i] = new int[degrees[i]];
				weights[i] = new int[degrees[i]];
				degrees[i] = 0;
			} else {
				edges[i] = null;
				weights[i] = null;
			}
		}

		for (int i = 0; i < m; i++) {
			edges[edge_array[i][0]][degrees[edge_array[i][0]]] = edge_array[i][1];
			edges[edge_array[i][1]][degrees[edge_array[i][1]]] = edge_array[i][0];
			weights[edge_array[i][0]][degrees[edge_array[i][0]]] = edge_array[i][2];
			weights[edge_array[i][1]][degrees[edge_array[i][1]]] = edge_array[i][2];
			degrees[edge_array[i][0]]++;
			degrees[edge_array[i][1]]++;
		}
	}
	
	// TODO Kruskal
	/*
	public long kruskal() {

		long cost = 0;
		//
		// Sort the edges
		//
		Arrays.sort(g_edges, new Comparator<Edge>() {
			public int compare(Edge o1, Edge o2) {
				return o1.w - o2.w;
			}
		});

		UnionFind ufind = new UnionFind(V);
		int size = 0; // size of graph G resulting in span tree
		int i = 0;
		while (size < V && i < E) {
			Edge cur = g_edges[i];
			if (ufind.find(cur.u) != ufind.find(cur.v)) {
				cost += cur.w;
				ufind.union(cur.u, cur.v);
				size++;
			}
			i++;
		}

		return cost;

	} */

	public boolean isBipartite() {
		boolean[] dfs_done = new boolean[n];
		int dfs_count = 0;

		// Do DFS n times
		for (int i = 0; i < n; i++) {
			int curr = i;
			if (dfs_count == n) {
				break;
			} else if (dfs_done[curr] || degrees[curr] == 0) {
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

				if (degrees[last] != 0) {
					for (int j = 0; j < degrees[last]; j++) {
						int w = edges[last][j];
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

	public LinkedList<Integer> DFS_search(){
	    LinkedList<Integer> DFS_order = new LinkedList<Integer>();
	    boolean [] visited = new boolean[n];
	    
	    // Process each node from 0 to n-1
	    for(int i=0;i<n;i++){
	      if(!visited[i]){
	        LinkedList<Integer> stack = new LinkedList<Integer>();
	        stack.add(i);
	            
	        while(!stack.isEmpty()){
	          int last = stack.getLast();
	          boolean finished  = false;
	          boolean added = false;
	        
	          if (degrees[last] != 0){
	            for (int j = 0; j < degrees[last]; j++){
	              int w = edges[last][j];
	              // vertex is added to the  stack
	              if (!added && !visited[w]){
	                added = true;
	                visited[w] = true;
	                stack.add(w);
	                DFS_order.add(w);
	              }
	            }
	            finished = !added;
	          } else {
	            finished = true;
	          }
	        
	          if (finished){
	            stack.removeLast();
	          }
	        }   
	      }
	    }
	    return DFS_order;
	    }
}

class Edge {
	public int u; // vertex u of the edge
	public int v; // vertex v of the edge
	public int w; // the weight of the edge

	public Edge(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}
}