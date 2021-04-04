package matrices_polynomials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Vector extends Matrix implements Comparable<Vector> {
	private int dim;
	private double[] elems_1d;
	
	public static void main(String[] args) {
		Vector v = new Vector(new double[][] { { 6.0 }, { 2.5 } });
		Vector q = new Vector(new double[][] { { 6.0 }, { 3 } });
		Vector[] vectors_list = new Vector[] {v,q};
		//vectors_list.sort();
//		ArrayList<Vector> vectors = new ArrayList<Vector>();
//		vectors.add(q);
//		vectors.add(v);
//		System.out.println(vectors);
//		Collections.sort(vectors);
//		System.out.println(vectors);
		
		Vector w = new Vector(new double[] {2, 3});
		
	}

	public Vector(double[][] elements) {
		super(elements);
		assert this.is_vector();
		this.dim = this.elems.length;
		elems_1d = new double[this.m];
		for (int i = 0; i < elements.length; i++) {
			elems_1d[i] = elements[i][0];
		}
	}

	public Vector(double[] elements) {
		
		super(convertElemsArray(elements));
		this.elems_1d = elements;
		this.dim = this.elems.length;
	}

	private static double[][] convertElemsArray(double[] elements) {
		double[][] new_elems = new double[elements.length][1];
		for (int i = 0; i < elements.length; i++) {
			new_elems[i][0] = elements[i];
		}
		return new_elems;
	}
	
	private double dot_product(Vector w) {

		if (w.m != this.m) {
			throw new IllegalArgumentException();
		}
		Matrix vt = this.getTransposed();
		return vt.getProduct(w).elems[0][0];

	}

	private double two_norm() {
		return Math.sqrt(dot_product(this));
	}
	
	public double getTwoNorm(){
		return two_norm();
	}
	
	public double getDotProduct (Vector w) {
		return dot_product(w);
	}

	public int getDim() {
		return dim;
	}
	
	public double[] get_elems_1d() {
		return elems_1d;
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
