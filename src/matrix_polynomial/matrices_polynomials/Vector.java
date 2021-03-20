package matrices_polynomials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Vector extends Matrix implements Comparable<Vector> {
	public static void main(String[] args) {
		Vector v = new Vector(new double[][] { { 6.0 }, { 2.5 } });
		Vector q = new Vector(new double[][] { { 6.0 }, { 3 } });
		Vector[] vectors_list = new Vector[] {v,q};
		//vectors_list.sort();
		ArrayList<Vector> vectors = new ArrayList<Vector>();
		vectors.add(q);
		vectors.add(v);
		System.out.println(vectors);
		Collections.sort(vectors);
		System.out.println(vectors);
		
		Vector w = new Vector(new double[][] { { 3.0 }, { 12.0 }, { 4.0 } });
		System.out.println("hmm " + v.two_norm());
		System.out.println("hmm " + w.two_norm());
	}

	public Vector(double[][] elements) {
		super(elements);
		assert this.is_vector();
	}

	public Vector(double[] elements) {
		super(new double[elements.length][]);
		for (int i = 0; i < elements.length; i++) {
			elems[i][0] = elements[i];
		}

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

	@Override
	public int compareTo(Vector arg0) {
		
		if (this.two_norm() > arg0.two_norm()) {
			//this vector is bigger than the argument vector
			return 1;
		} else if (this.two_norm() == arg0.two_norm()) {
			return 0;
		} else {
			//this vector is smaller than the argument vector
			return -1;
		}
	}
}
