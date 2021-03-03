package maths_stuff;


public class Vector extends Matrix {
	public static void main(String[] args) {
		Vector v = new Vector(new double[][] { { 6.0 }, { 2.5 } });
		Vector w = new Vector(new double[][] { { 3.0 }, { 12.0 }, { 4.0 } });
		System.out.println("hmm " + v.two_norm());
		System.out.println("hmm " + w.two_norm());
	}

	public Vector(double[][] elements) {
		super(elements);
		assert this.is_vector();
	}

	public double dot_product(Vector w) {
		
		if (w.m != this.m) {
			throw new IllegalArgumentException();
		}
		Matrix vt = this.transposed_matrix();
		return vt.product(w).elems[0][0];

	}
	
	public double two_norm() {
		return Math.sqrt(dot_product(this));
	}
}
