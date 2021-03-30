package math_GUI;

import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import matrices_polynomials.Matrix;

public class FX_GUI extends Application {
	private TextField matrix_A_dim_text;
	private TextField matrix_A_text;
	private TextField matrix_B_dim_text;
	private TextField matrix_B_text;
	private Label matrix_A_label;
	private Label matrix_B_label;
	private Matrix A;
	private Matrix B;

	private Label result_label;

	private ToggleGroup group;
	private RadioButton mult;
	private RadioButton add;
	private RadioButton subt;

	private Button display_btn;


	private void initUI(Stage stage) {

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

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					binary_function();
				}
			}
		});

		display_btn = new Button("Display");
		display_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				disp_btn_action();
			}
		});

		// Grid layout pane
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.addColumn(0, matrix_A_desc, matrix_A_dim_text, matrix_A_text, matrix_A_label);
		grid.addColumn(1, matrix_B_desc, matrix_B_dim_text, matrix_B_text, matrix_B_label);
		grid.addRow(4, display_btn);
		grid.addRow(5, new Label("Operations:"), mult, add, subt);
		grid.add(result_label, 2, 3);

		// canvas pane
		Pane canvas_pane = new Pane();
		Canvas canvas = new Canvas(500, 300);
		GraphicsContext context = canvas.getGraphicsContext2D();
		drawLines(context);
		canvas_pane.getChildren().add(canvas);

		// organize different layout panes
		VBox root = new VBox();
		root.getChildren().addAll(grid, canvas_pane);

		// create scene
		Scene scene = new Scene(root); // new scene with grid_layout

		// add css file
		String class_path = FX_GUI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String new_path = class_path.replaceAll("bin", "src/matrix_polynomial/math_GUI");
		File f = new File(new_path + "style.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		// set stage
		stage.setScene(scene);
		stage.setTitle("Matrix Calculator");
		stage.show();
	}
	
	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	private void drawLines(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(30.5, 30.5);
		gc.lineTo(150.5, 30.5);
		gc.lineTo(150.5, 150.5);
		gc.lineTo(30.5, 30.5);
		gc.stroke();
	}

	private void binary_function() {
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

	private void disp_btn_action() {
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

	private void create_matrices() {
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
		a_dim_scanner.close();
		b_dim_scanner.close();
		a_scanner.close();
		b_scanner.close();

	}

	public static void main(String[] args) {
		launch();
	}
}
