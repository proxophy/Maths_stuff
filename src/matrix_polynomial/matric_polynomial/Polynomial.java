package matric_polynomial;

import java.util.Arrays;
import java.util.HashSet;

public class Polynomial {
	private int deg = 0;
	private double[] coeffs = new double[deg + 1];

	public static void main(String[] args) {
		double[] test = new double[] { 3.0, 2.0 };
		Polynomial f = new Polynomial(new double[] { 3.0, 2.0 });
		Polynomial g = new Polynomial(new double[] { 5.0, 4.0 });
		Polynomial h = new Polynomial(new double[] { 2.0, 57.0, 6.0, 100.0 });
		// System.out.println(f.equals(new Polynomial(test)));
		// System.out.println(f.real_roots());
		// System.out.println(h.evaluate(1));
		Polynomial i = new Polynomial(new double[] { 6.0, 0.0, 1.0 });
		Polynomial j = new Polynomial(new double[] { -64.0, 0.0, 4.0 });
		Polynomial k = new Polynomial(new double[] { 6.0, 8.0, 2.0 });
		Polynomial l = new Polynomial(new double[] {0.0, 0.0, 0.0, 0.0, 1.0});
		//System.out.println(i.tangent(0));
		System.out.println(h);
		System.out.println(l);
		// System.out.println(j.real_roots());
		// System.out.println(k.real_roots());
	}

	public Polynomial(double[] coeffs) {
		if (coeffs != null) {
			this.coeffs = coeffs;
			if (coeffs != null && coeffs.length > 0) {
				this.deg = coeffs.length - 1;
			}
		}
	}

	public double evaluate(double x) {
		double val = 0;
		for (int i = 0; i <= deg; i++) {
			val += coeffs[i] * Math.pow(x, i);
		}
		return val;
	}

	// returns sum of this polynomial and another
	public Polynomial pol_add(Polynomial B) {
		if (B == null)
			return this;

		int new_deg = Math.max(this.deg, B.deg);
		double[] new_coeff = new double[new_deg + 1];

		for (int i = 0; i <= new_deg; i++) {
			if (this.deg < i) {
				new_coeff[i] = B.coeffs[i];
			} else if (B.deg < i) {
				new_coeff[i] = this.coeffs[i];
			} else {
				new_coeff[i] = this.coeffs[i] + B.coeffs[i];
			}
		}
		return new Polynomial(new_coeff);
	}

	// adds to this polynomial another polynomial
	public void addto(Polynomial B) {
		Polynomial new_poly = this.pol_add(B);
		if (new_poly != null) {
			this.coeffs = new_poly.coeffs;
			this.deg = new_poly.deg;
		}

	}

	// return product of polynomial
	public Polynomial pol_mult(Polynomial B) {
		if (B == null)
			return this;
		int new_deg = this.deg + B.deg;
		double[] new_coeffs = new double[new_deg + 1];

		for (int i = 0; i <= this.deg; i++) {
			for (int j = 0; j <= B.deg; j++) {
				new_coeffs[i + j] += this.coeffs[i] * B.coeffs[j];
			}
		}

		return new Polynomial(new_coeffs);
	}

	// multiplies this polynomial by another one
	public void multwith(Polynomial B) {
		Polynomial new_poly = this.pol_mult(B);
		if (new_poly != null) {
			this.coeffs = new_poly.coeffs;
			this.deg = new_poly.deg;
		}

	}

	// returns the derivative
	public Polynomial derivative() {
		double[][] ce_2d = new double[][] { this.coeffs };
		Matrix pol_matrix = (new Matrix(ce_2d)).transposed_matrix(); // Vector of polynomial
		Matrix der_matrix = der_matrix().product(pol_matrix); // Matrix of derivative
		double[] der_coeff = der_matrix.transposed_matrix().elems[0];
		return new Polynomial(der_coeff);
	}

	// Matrix for derivating polynomial
	public Matrix der_matrix() {
		double[][] elems = new double[deg][deg + 1];
		for (int i = 1; i <= deg; i++) {
			elems[i - 1][i] = i;
		}
		return new Matrix(elems);
	}

	public Polynomial tangent(double x) {
		if (deg <= 1) {
			return this;
		}

		double[] tang = new double[2];

		double gradient = derivative().evaluate(x); // determine gradient of the function at this point
		double y = this.evaluate(x);
		double b = -(y / gradient * x);

		tang[0] = b;
		tang[1] = gradient;

		return new Polynomial(tang);
	}

	//TODO
	public HashSet<Double> real_roots() {
		HashSet<Double> roots = new HashSet<Double>();
		if (deg == 0) {
			return null;
		} else if (deg == 1) {
			if (coeffs[0] != 0) {
				roots.add(-coeffs[0] / coeffs[1]);
			}
		} else if (deg == 2) {
			double a = coeffs[2];
			double b = coeffs[1];
			double c = coeffs[0];
			if (a == 0) {
				if (b != 0) {
					roots.add(-c / b);
				}
				return roots;
			}
			double det = b * b - 4 * a * c;
			if (det < 0) {
				return roots;
			}
			double x1 = (-b + Math.sqrt(det)) / (2 * a);
			double x2 = (-b - Math.sqrt(det)) / (2 * a);
			if (x1 == x2) {
				roots.add(x1);
			} else {
				roots.add(x1);
				roots.add(x2);
			}

		} else {
			// TODO
		}

		return roots;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Polynomial)) {
			return false;
		}

		Polynomial c = (Polynomial) o;
		return Arrays.equals(this.coeffs, c.coeffs);
	}

	@Override
	public String toString() {
		String str ="";
		boolean first = false;
		
		if (!(coeffs[0] == 0 && deg > 0)) {
			str += coeffs[0];
			first = true;
		}

		for (int i = 1; i <= deg; i++) {
			if (first) {
				str += " + ";
			}
			if (i == 1 && coeffs[i] != 0.0) {
				str +=  coeffs[i] + "*x";
			}  else if (coeffs[i] != 0.0) {
				str +=  coeffs[i] + "*x^" + i;
			}
			if (!first && coeffs[i] != 0) {
				first =true;
			}
		}

		return str;
	}
}
