package math_GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import matrices_polynomials.Matrix;

public class FX_GUI extends Application {
	Label matrix_A_label;
	TextField matrix_A_dim_text;
	TextField matrix_A_text;
	Label matrix_B_label;
	TextField matrix_B_dim_text;
	TextField matrix_B_text;
	Label result_label;
	Button btn1;
	Button btn2;
	
	Matrix A;
	Matrix B;

	public void init() {

	}

	@Override
	public void start(Stage stage) {

		matrix_A_label = new Label("Matrix A");
		matrix_A_dim_text = new TextField("2 2");
		matrix_A_text = new TextField("1 1 \n1 1");

		matrix_B_label = new Label("Matrix B");
		matrix_B_dim_text = new TextField("2 2");
		matrix_B_text = new TextField("2 2 \n2 2");

		result_label = new Label("Result will be displayed here");

		btn1 = new Button("Multiply");
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				bt1_action();
			}
		});
		
		btn2 = new Button("Multiply");
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				bt2_action();
			}
		});

		GridPane root = new GridPane(); // layout

		root.addColumn(0, matrix_A_label, matrix_A_dim_text, matrix_A_text);
		root.addColumn(1, matrix_B_label, matrix_B_dim_text, matrix_B_text);
		root.add(btn1, 2, 1);
		root.add(result_label, 2, 3);

		Scene scene = new Scene(root, 600, 400); // new scene with root

		stage.setScene(scene);
		stage.setTitle("First JavaFX Application");
		stage.show();
	}

	public void stop() {

	}

	public void bt1_action() {
		System.out.println(matrix_A_text.getText());

		Scanner a_dim_scanner = new Scanner(matrix_A_dim_text.getText());
		Scanner b_dim_scanner = new Scanner(matrix_B_dim_text.getText());
		Scanner a_scanner = new Scanner(matrix_A_text.getText());
		Scanner b_scanner = new Scanner(matrix_B_text.getText());

		// read matrix A
		int[] dimA = new int[2];
		double[][] elementsA = null;
		Matrix A;
		String resultA;
		try {
			dimA[0] = a_dim_scanner.nextInt();
			dimA[1] = a_dim_scanner.nextInt();
			elementsA = new double[dimA[0]][dimA[1]];
			for (int i = 0; i < dimA[0]; i++) {
				for (int j = 0; j < dimA[1]; j++) {
					elementsA[i][j] = a_scanner.nextDouble();
				}
			}
			A = new Matrix(elementsA);
			resultA = A.toString();

		} catch (Exception ex) {
			result_label.setText("Matrix A: Exception occured " + Arrays.toString(elementsA));
			return;
		}

		int[] dimB = new int[2];
		double[][] elementsB = null;
		Matrix B;
		String resultB;
		try {
			dimB[0] = b_dim_scanner.nextInt();
			dimB[1] = b_dim_scanner.nextInt();
			elementsB = new double[dimB[0]][dimB[1]];

			for (int i = 0; i < dimB[0]; i++) {
				for (int j = 0; j < dimB[1]; j++) {
					elementsB[i][j] = b_scanner.nextDouble();
				}
			}
			B = new Matrix(elementsB);
			resultB = B.toString();

		} catch (Exception ex) {
			result_label.setText("Matrix B: Exception occured " + Arrays.toString(elementsB));
			return;
		}

		Matrix AB = A.product(B);
		result_label.setText(AB.toString());

	}

	public void bt2_action() {
		
	}
	
	
	public static void main(String[] args) {
		launch();
	}
}