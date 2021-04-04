package matrices_polynomials;

public class Quad_Matrix extends Matrix {
	private int dim;

	public static void main(String[] args) {
		double[][] Bb = { { 1.0, 3.0, 2.0 }, { 1.0, -4.0, 2.0 }, { 3.0, 0.0, 3.0 } };
		Quad_Matrix B = new Quad_Matrix(Bb);

		double[][] Cc = { { 2.0, 0.0, 0.0 }, { 0.0, 0.5, 0.0 }, { 0.0, 0.0, 3.0 } };
		Quad_Matrix C = new Quad_Matrix(Cc);
		System.out.println(B.inverse());
	}

	public Quad_Matrix(double[][] elements) {
		super(elements);
		assert this.is_quadratic() : "Matrix is not quadratic";
		dim = elems.length;
	}

	private double determinant() {
		assert this.is_valid() : "The input array is not a valid matrix";
		double det = 0.0;

		if (elems.length == 2) {
			return elems[0][0] * elems[1][1] - elems[0][1] * elems[1][0];
		} else if (elems.length == 1) {
			return elems[0][0];
		} else {

			for (int i = 0; i < elems.length; i++) {
				Quad_Matrix sub_matrix = sub_matrix(0, i);
				det += Math.pow(-1, i) * elems[0][i] * sub_matrix.determinant();
			}
		}
		return det;
	}

	public double getDeterminant() {
		return determinant();
	}

	@Override
	public Quad_Matrix sub_matrix(int y, int x) {
		assert this.is_valid() : "The input array is not a valid matrix";
		Matrix old = super.sub_matrix(y, x);
		return new Quad_Matrix(old.elems);
	}

	private boolean right_triangular() {
		assert this.is_valid() : "The input array is not a valid matrix";
		for (int i = 1; i < elems.length; i++) {
			for (int j = 0; j < i; j++) {
				if (elems[i][j] != 0) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean is_right_triangular() {
		return right_triangular();
	}

	private boolean left_triangular() {
		assert this.is_valid() : "The input array is not a valid matrix";
		for (int i = 1; i < elems.length; i++) {
			for (int j = 0; j < i; j++) {
				if (elems[j][i] != 0) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean is_left_triangular() {
		return left_triangular();
	}

	private boolean symmetric() {
		assert this.is_valid() : "The input array is not a valid matrix";
		for (int i = 0; i < elems.length; i++) {
			for (int j = i; j < elems.length; j++) {
				if (elems[i][j] != elems[j][i]) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean is_symmetric() {
		return symmetric();
	}

	private boolean asymmetric() {
		assert this.is_valid() : "The input array is not a valid matrix";

		for (int i = 0; i < elems.length; i++) {
			if (elems[i][i] != 0) {
				return false;
			}
			for (int j = i + 1; j < elems.length; j++) {
				if (elems[i][j] != -elems[j][i]) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean is_asymmetric() {
		return asymmetric();
	}

	// To-Do
	private Matrix inverse() {
		if (dim > 10) {
			throw new IllegalArgumentException("can't compute inverse for n>10");
		} else if (this.determinant() == 0.0) {
			System.out.println("det == 0");
			return null;
		}

		double[][] aug_mat = new double[dim][2 * dim];

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < 2 * dim; j++) {
				if (j < dim) {
					aug_mat[i][j] = elems[i][j];
				} else if (j == i + n) {
					aug_mat[i][j] = 1.0;
				}
			}
		}

		return new Matrix(aug_mat);
	}

	public Matrix getInverse() {
		return inverse();
	}
}
