package graphs;

public class UnionFind {
	private int[] labels;

	public UnionFind(int N) {
		create(N);
	}

	public void create(int N) {
		labels = new int[N];
		for (int i = 0; i < N; i += 1) {
			labels[i] = i;
		}
	}

	public int find(int x) {

		// find real label of x
		int id = x;
		while (labels[id] != id)
			id = labels[id];

		// compress label
		int curr = x;
		while (labels[curr] != id) {
			int next = labels[curr];
			labels[curr] = id;
			curr = next;
		}

		return labels[x];
	}

	public void union(int x, int y) {
		int n = labels.length;
		int sx = find(x);
		int sy = find(y);
		labels[sx] = sy; // set id
		find(sx);
	}
}