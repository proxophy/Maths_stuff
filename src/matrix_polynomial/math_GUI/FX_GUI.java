package math_GUI;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import matrices_polynomials.Matrix;

public class FX_GUI extends Application {
	TextField matrix_A_dim_text;
	TextField matrix_A_text;
	TextField matrix_B_dim_text;
	TextField matrix_B_text;
	Label matrix_A_label;
	Label matrix_B_label;
	Matrix A;
	Matrix B;

	Label result_label;

	ToggleGroup group;
	RadioButton mult;
	RadioButton add;
	RadioButton subt;

	Button opt_btn;
	Button display_btn;

	public void init() {

	}

	@Override
	public void start(Stage stage) throws IOException {

		Label matrix_A_desc = new Label("Matrix A");
		matrix_A_dim_text = new TextField("2 2");
		matrix_A_text = new TextField("1 1 \n1 1");

		Label matrix_B_desc = new Label("Matrix B");
		matrix_B_dim_text = new TextField("2 2");
		matrix_B_text = new TextField("2 2 \n2 2");

		matrix_A_label = new Label("A will be displayed here");
		matrix_B_label = new Label("B will be displayed here");
		result_label = new Label("");
		matrix_A_label.setMinHeight(50);

		group = new ToggleGroup();
		mult = new RadioButton("Multiply");
		add = new RadioButton("Add");
		subt = new RadioButton("Subtract");
		mult.setToggleGroup(group);
		add.setToggleGroup(group);
		subt.setToggleGroup(group);

		opt_btn = new Button("Choose");
		opt_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				binary_function();
			}
		});

		display_btn = new Button("Display");
		display_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				disp_btn_action();
			}
		});

		GridPane root = new GridPane(); // layout

		root.addColumn(0, matrix_A_desc, matrix_A_dim_text, matrix_A_text, matrix_A_label);
		root.addColumn(1, matrix_B_desc, matrix_B_dim_text, matrix_B_text, matrix_B_label);
		root.addRow(4, display_btn);
		root.addRow(5, new Label("Operations:"), mult, add, subt, opt_btn);
		root.add(result_label, 2, 3);
		root.setHgap(10);
		root.setVgap(10);

		Scene scene = new Scene(root); // new scene with root
		String class_path = FX_GUI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String new_path =  class_path.replaceAll("bin", "src/matrix_polynomial/math_GUI" );
		System.out.println(class_path) ;
		System.out.println(new_path) ;
		File f = new File(new_path + "style.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		stage.setScene(scene);
		stage.setTitle("Matrix Calculator");
		stage.show();
	}

	public void stop() {

	}

	public void binary_function() {
		disp_btn_action();
		if (A != null && B != null) {
			Matrix result = null;
			if (mult.isSelected()) {
				result = A.product(B);
			} else if (add.isSelected()) {
				result = A.sum(B);
			} else if (subt.isSelected()) {
				result = A.difference(B);
			} else {
				result_label.setText("Choose (valid) operation");
				return;
			}
			if (result != null) {
				result_label.setText(result.toString());
			} else {
				result_label.setText("Dimensions are not fitting");
			}
		} else {
			result_label.setText("One of the matrices is null");
		}

	}

	public void disp_btn_action() {
		create_matrices();
		if (A != null && B != null) {
			matrix_A_label.setText(A.toString());
			matrix_B_label.setText(B.toString());
		} else if (A == null) {
			matrix_A_label.setText("Error: Check dimension \nor entries of matrix A");
			matrix_B_label.setText(B.toString());
		} else if (B == null) {
			matrix_A_label.setText(A.toString());
			matrix_B_label.setText("Error: Check dimension \nor entries of matrix B");
		}

	}

	public void create_matrices() {
		Scanner a_dim_scanner = new Scanner(matrix_A_dim_text.getText());
		Scanner b_dim_scanner = new Scanner(matrix_B_dim_text.getText());
		Scanner a_scanner = new Scanner(matrix_A_text.getText());
		Scanner b_scanner = new Scanner(matrix_B_text.getText());

		// read matrix A
		int[] dimA = new int[2];
		double[][] elementsA = null;
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

		} catch (Exception ex) {
			matrix_A_label.setText("Error: Check dimension \n or entries of matrix A");
			A = null;
		}

		// Read matrix B
		int[] dimB = new int[2];
		double[][] elementsB = null;
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

		} catch (Exception ex) {
			matrix_B_label.setText("Error: Check dimension \n or entries of matrix B");
			B = null;
		}

	}

	public static void main(String[] args) {
		launch();
	}
}

// old code
//mult_btn = new Button("Multiply");
//mult_btn.setOnAction(new EventHandler<ActionEvent>() {
//	@Override
//	public void handle(ActionEvent arg0) {
//		mult_btn_action();
//	}
//});
//
//add_btn = new Button("Add");
//add_btn.setOnAction(new EventHandler<ActionEvent>() {
//	@Override
//	public void handle(ActionEvent arg0) {
//		add_btn_action();
//	}
//});
