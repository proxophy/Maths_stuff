package matric_polynomial;

import java.util.Arrays;

public class Matrix {
	protected double[][] elems;
	protected int m;
	protected int n;

	public static void main(String[] args) {
		double[][] test = new double[][] { { 6.0 }, { 2.5 } };
		Vector v = new Vector(new double[][] { { 6.0 }, { 2.5 } });
		Matrix w = (new Vector(new double[][] { { 6.0 }, { 2.5 } })).transposed_matrix();
		System.out.println(v.equals(new Vector(test)));
	}

	public Matrix(double[][] elements) {
		this.elems = elements;
		assert this.is_valid_matrix() : "The input array is not a valid matrix";
		m = elems.length;
		n = elems[0].length;
	}

	// return sum of this matrix and the other matrix B
	public Matrix sum(Matrix B) {
		assert this.is_valid_matrix() : "The input array is not a valid matrix";
		assert B.is_valid_matrix() : "The input array is not a valid matrix";
		if (this.m != B.m || this.n != B.n)
			return null;

		double[][] sum = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				sum[i][j] = this.elems[i][j] + B.elems[i][j];
			}
		}

		return new Matrix(sum);
	}

	public void add(Matrix B) {
		if (B != null) {
			Matrix new_m = sum(B);
			this.elems = new_m.elems;
			this.m = new_m.m;
			this.n = new_m.n;
		}

	}

	public Matrix difference(Matrix B) {
		assert this.is_valid_matrix() : "The input array is not a valid matrix";
		assert B.is_valid_matrix() : "The input array is not a valid matrix";
		if (this.m != B.m || this.n != B.n)
			return null;

		double[][] sum = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				sum[i][j] = this.elems[i][j] - B.elems[i][j];
			}
		}

		return new Matrix(sum);
	}

	public void subtract(Matrix B) {
		if (B != null) {
			Matrix new_m = difference(B);
			this.elems = new_m.elems;
			this.m = new_m.m;
			this.n = new_m.n;
		}
	}

	public Matrix product(Matrix B) {

		assert this.is_valid_matrix() : "The input array is not a valid matrix";
		assert B.is_valid_matrix() : "The input array is not a valid matrix";

		double[][] B_elems = B.elems;
		int o, p;

		o = B_elems.length;
		p = B_elems[0].length;

		// check dimensions
		if (n != o) {
			System.out.println("wrong dimensions");
			return null;
		}

		double[][] product = new double[m][p];
		double curr = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < p; j++) {
				curr = 0;
				for (int k = 0; k < n; k++) {
					curr += elems[i][k] * B_elems[k][j];
				}
				product[i][j] = curr;
			}
		}

		return new Matrix(product);
	}

	public void multipy(Matrix B) {
		if (B != null) {
			Matrix new_m = product(B);
			this.elems = new_m.elems;
			this.m = new_m.m;
			this.n = new_m.n;
		}

	}

	public Matrix transposed_matrix() {
		assert this.is_valid_matrix() : "The input array is not a valid matrix";

		double[][] MT = new double[n][m];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				MT[j][i] = elems[i][j];
			}
		}
		return new Matrix(MT);
	}

	public void transpose() {
		Matrix new_m = transposed_matrix();
		this.elems = new_m.elems;
		this.m = new_m.m;
		this.n = new_m.n;

	}

	public Matrix sub_matrix(int y, int x) {
		assert this.is_valid_matrix() : "The input array is not a valid matrix";

		double[][] sub = new double[m - 1][n - 1];

		int ix = 0;
		int iy = 0;
		for (int i = 0; i < m; i++) {
			if (i == y) {
				continue;
			}

			for (int j = 0; j < n; j++) {
				if (j == x) {
					continue;
				}
				sub[iy][ix] = elems[i][j];
				ix++;
			}
			ix = 0;
			iy++;
		}

		return new Matrix(sub);
	}

	public boolean is_valid_matrix() {
		if (elems == null || elems[0] == null || elems[0].length == 0) {
			return false;
		}

		int dim = elems[0].length;
		for (int i = 1; i < elems.length; i++) {
			if (elems[i] == null || elems[i].length != dim) {
				return false;
			}
		}

		return true;
	}

	public boolean is_quadratic() {
		assert this.is_valid_matrix() : "The input array is not a valid matrix";
		return m == n;
	}

	public boolean is_vector() {
		assert this.is_valid_matrix() : "The input array is not a valid matrix";
		return n == 1;
	}

	@Override
	public String toString() {

		String str = "[";
		String sep = ",\n ";

		for (int i = 0; i < m; i++) {
			str += Arrays.toString(elems[i]);

			if (i < (m - 1)) {
				str += sep;
			}
		}

		return str + "]";

	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Matrix)) {
			return false;
		}

		Matrix c = (Matrix) o;
		return Arrays.deepEquals(this.elems, c.elems);
	}

	/*
	 * public double determinant() { return 0; }
	 */

}
