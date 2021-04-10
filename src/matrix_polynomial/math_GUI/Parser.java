package math_GUI;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser {

	public static void main(String[] args) {
		String exp = "1,2;3,4;5,6;";

		String rowSep = ";";
		Pattern rowPattern = Pattern.compile(rowSep);
		String[] rows = rowPattern.split(exp);

		System.out.println("split.length = " + rows.length);

		double[][] matrix = new double[rows.length][];
		int i = 0;
		for (String element : rows) {
			System.out.println("element = " + element);
			
			String elemsSep = ",";
			Pattern elemsPattern = Pattern.compile(elemsSep);
			String[] elems = elemsPattern.split(element);
			
			matrix[i] = new double[elems.length];
			int j = 0;
			for (String e : elems) {
				System.out.print(e + " ");
				matrix[i][j] = str_to_double(e);
				
				j++;
			}
			System.out.println();
			System.out.println(Arrays.toString(matrix[i])+ " ");
			i++;
		}
	}
	
	private static double str_to_double(String str) {
		Scanner scan = new Scanner(str); 
		double val;
		if (scan.hasNextDouble()) {
			val =  scan.nextDouble();
		} else {
			val = Double.MIN_VALUE;
		}
		scan.close();
		return val;
	}
		
//		String patternString = "sep";
//		Pattern pattern = Pattern.compile(patternString);
//
//		String[] split = pattern.split(text);
//
//		System.out.println("split.length = " + split.length);
//
//		for (String element : split) {
//			System.out.println("element =" + element+ "-");
//		}
	

}
