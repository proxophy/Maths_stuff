package math_GUI;

import javax.swing.*;

import matrices_polynomials.Matrix;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class mv_GUI implements ActionListener {
	private JFrame frame = new JFrame();
	private JPanel panel;

	private JLabel matrix_A_label;
	private JTextField matrix_A_dim_text;
	private JTextField matrix_A_text;
	private JLabel matrix_B_label;
	private JTextField matrix_B_dim_text;
	private JTextField matrix_B_text;
	private JLabel result_label;
	private JButton button = new JButton();

	public mv_GUI() {

		// the panel with the button and text
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0, 1));

		// Matrix A Label
		matrix_A_label = new JLabel("Matrix A");
		matrix_A_label.setBounds(10, 20, 80, 25);
		// Matrix A Dim Textfield
		matrix_A_dim_text = new JTextField("2 2");
		matrix_A_dim_text.setBounds(40, 20, 165, 35);
		// Matrix A Textfield
		matrix_A_text = new JTextField("1 1 1 1");
		matrix_A_text.setBounds(100, 20, 65, 35);

		// Matrix B Label
		matrix_B_label = new JLabel("Matrix B");
		matrix_B_label.setBounds(10, 20, 80, 25);
		// Matrix B Dim Textfield
		matrix_B_dim_text = new JTextField("2 2");
		matrix_B_dim_text.setBounds(40, 20, 65, 35);
		// Matrix B Textfield
		matrix_B_text = new JTextField("2 2 2 2");
		matrix_B_text.setBounds(100, 20, 165, 35);

		// result text label
		result_label = new JLabel("Result will be displayed here");
		result_label.setBounds(100, 100, 165, 100 );
		JPanel resultPanel = new JPanel();
		
		// the clickable button
		button = new JButton("Click Me");
		button.addActionListener(this);
		button.setBounds(100, 20, 165, 35);

		panel.add(matrix_A_label);
		panel.add(matrix_A_dim_text);
		panel.add(matrix_A_text);
		panel.add(matrix_B_label);
		panel.add(matrix_B_dim_text);
		panel.add(matrix_B_text);
		panel.add(button);
		//panel.add(result_label);
		resultPanel.add(result_label);
		resultPanel.setBackground(Color.PINK);

		// set up the frame and display it;
		frame.add(panel, BorderLayout.CENTER);
		frame.add(resultPanel, BorderLayout.SOUTH);
		frame.pack();
//		frame.setSize(500, 500);
		frame.setTitle("GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	// process the button clicks
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			Scanner matrix_a_dim_scanner = new Scanner(matrix_A_dim_text.getText());
			Scanner matrix_b_dim_scanner = new Scanner(matrix_B_dim_text.getText());
			Scanner matrix_a_scanner = new Scanner(matrix_A_text.getText());
			Scanner matrix_b_scanner = new Scanner(matrix_B_text.getText());
			
			//read matrix A
			int[] dimA = new int[2];
			double[][] elementsA = null;
			Matrix A;
			String resultA;
			try {
				dimA[0] =  matrix_a_dim_scanner.nextInt();
				dimA[1] =  matrix_a_dim_scanner.nextInt();
				elementsA = new double[dimA[0]][dimA[1]];
				for (int i = 0; i < dimA[0]; i++) {
					for (int j = 0; j < dimA[1]; j++) {
						elementsA[i][j] =  matrix_a_scanner.nextDouble();
					}
				}
				A = new Matrix(elementsA);
				resultA = A.toString().replaceAll("\n", "<br>");
				
			} catch (Exception ex) {
				result_label.setText("Matrix A: Exception occured " + Arrays.toString(elementsA));
				return;
			} 
			
			int[] dimB = new int[2];
			double[][] elementsB = null;
			Matrix B;
			String resultB;
			try {
				dimB[0] =  matrix_b_dim_scanner.nextInt();
				dimB[1] =  matrix_b_dim_scanner.nextInt();
				elementsB = new double[dimB[0]][dimB[1]];
				
				for (int i = 0; i < dimB[0]; i++) {
					for (int j = 0; j < dimB[1]; j++) {
						elementsB[i][j] =  matrix_b_scanner.nextDouble();
					}
				}
				B= new Matrix(elementsB);
				resultB = B.toString().replaceAll("\n", "<br>");
				
			} catch (Exception ex) {
				result_label.setText("Matrix B: Exception occured " + Arrays.toString(elementsB));
				return;
			}
			
			Matrix AB = A.product(B);
			String resultAB =  AB.toString().replaceAll("\n", "<br>");
			
			String result = String.format("<html>%s</html>", resultAB);
			result_label.setText(result);
		}

	}

	// create one Frame
	public static void main(String[] args) {
		new mv_GUI();
	}
}
