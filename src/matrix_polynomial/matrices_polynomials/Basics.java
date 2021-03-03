package matrices_polynomials;

public class Basics {

	public static void main(String[] args) {
		double[][] Aa = { { 0.0, -1.0, 2.0 }, { 4.0, -3.0, 3.0 } };
		
		int[][] CC = { { 1, 2 }, { 3, 4 } };
		double[][] Cc = int_to_double(CC);
		double[][] Dd = { { 1.0, 3.0, 2.0 }, { 3.0, -4.0, 7.0 }, { 2.0, 7.0, 3.0 } }; // symmetric
		double[][] Ee = { { 0.0, -3.0, 2.0 }, { 3.0, 0.0, 7.0 }, { -2.0, -7.0, 0.0 } }; // asymetric
		double[][] Ll = { { 2.0, 0.0, 0.0 }, { 5.0, 7.0, 0.0 }, { 4.0, 3.0, -1.0 } };
		double[][] Rr = { { 2.0, 5.0, 1.0 }, { 0.0, 7.0, 4.0 }, { 0.0, 0.0, -1.0 } };


		System.out.println(identity(3).difference(new Matrix(Dd)));
		
		System.out.println("changed");
		/*
		 * double[][] entries; long startTime, stopTime;
		 * 
		 * startTime = System.nanoTime(); System.out.println(n + ": Determinant =" +
		 * determinant(entries)); stopTime = System.nanoTime();
		 * 
		 * System.out.println("Time to compute: " + (stopTime - startTime) /
		 * 1000000000.0 + "\n"); }
		 */

	}

	public static Quad_Matrix identity(int dim) {
		double[][] elems = new double[dim][dim];
		for (int i = 0; i < dim; i++) {
			elems[i][i] = 1;
		}
		return new Quad_Matrix(elems);
	}
	
	public static double[][] int_to_double(int[][] M) {
		if (M == null) {
			return null;
		}

		int dim = M.length;
		double[][] MM = new double[dim][];
		for (int i = 0; i < dim; i++) {
			if (M[i] != null) {
				MM[i] = new double[M[i].length];
				for (int j = 0; j < M[i].length; j++) {
					MM[i][j] = (double) M[i][j];
				}
			}
		}
		return MM;
	}

}
