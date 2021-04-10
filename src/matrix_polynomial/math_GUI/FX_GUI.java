package math_GUI;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import matrices_polynomials.Matrix;
import matrices_polynomials.Vector;

public class FX_GUI extends Application {
	// Calculator Elements
	private TextField matrix_A_text;
	private TextField matrix_B_text;
	private Label matrix_A_label;
	private Label matrix_B_label;
	private Label result_label;
	private ToggleGroup group;
	private RadioButton mult;
	private RadioButton add;
	private RadioButton subt;
	private Button display_btn;
	private Button clear_btn;
	private Button compute_btn;

	private Matrix A;
	private Matrix B;
	private Matrix R;

	// panes
	private HBox root;
	private GridPane grid;
	private Pane canvas_pane;
	private CartesianPane cartPane;

	private void initUI(Stage stage) {

		Label matrix_A_desc = new Label("Matrix A");
		matrix_A_text = new TextField("1,2,3;4,5,6;");

		Label matrix_B_desc = new Label("Matrix B");
//		matrix_B_dim_text = new TextField("2 1");
		matrix_B_text = new TextField("1,2;3,4;5,6;");

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

		clear_btn = new Button("Clear");
		clear_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				clear_btn_action();
			}
		});

		compute_btn = new Button("Compute");
		compute_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				binary_function();
			}
		});

		// Grid layout pane
		grid = new GridPane();
		grid.setMinSize(500, 500);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.addRow(0, matrix_A_desc, matrix_B_desc);
		grid.addRow(1, matrix_A_text, matrix_B_text);
		grid.addRow(2, matrix_A_label, matrix_B_label, result_label);
		grid.addRow(3, display_btn, compute_btn, clear_btn);
		grid.addRow(4, new Label("Operations:"), mult, add, subt);

		// canvas pane
		canvas_pane = new StackPane();
		canvas_pane.setId("canvas_pane");
		drawOnCanvas();

		// organize different layout panes
		root = new HBox(grid, canvas_pane);
		root.setSpacing(10);

		// create scene
		Scene scene = new Scene(root);

		// add css file
		String class_path = FX_GUI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String new_path = class_path.replaceAll("bin", "src/matrix_polynomial/math_GUI");
		File f = new File(new_path + "style.css");
//		scene.getStylesheets().clear();
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

	private void drawOnCanvas() {
		int chartWidth = 500;
		int chartHeight = 500;
		double minX = -8;
		double maxX = 8;
		double xTickUnit = 1;
		double minY = -8;
		double maxY = 8;
		double yTickUnit = 1;

		cartPane = new CartesianPane(chartWidth, chartHeight, minX, maxX, xTickUnit, minY, maxY, yTickUnit);
//		Vector v = new Vector(new double[] { -4, 5 });
//		cartPane.drawVector(v, Color.CORNFLOWERBLUE);
		canvas_pane.getChildren().add(cartPane);

	}

	private void binary_function() {
		disp_btn_action();
		if (A != null && B != null) {
			if (mult.isSelected()) {
				R = A.getProduct(B);
			} else if (add.isSelected()) {
				R = A.getSum(B);
			} else if (subt.isSelected()) {
				R = A.getDifference(B);
			} else {
				result_label.setText("Choose (valid) \noperation");
				return;
			}
			if (R != null) {
				result_label.setText(R.toString());
				if (R.is_vector()) {
					cartPane.drawVector(new Vector(R.getElements()), Color.LAWNGREEN);
				}
			} else {
				result_label.setText("Dimensions are \nnot fitting");
			}
		} else {
			result_label.setText("One of the matrices is null");
		}

	}

	private void create_matrices() {

		// read matrix A
		try {
			String text_A = matrix_A_text.getText();

			String rowSep = ";";
			Pattern rowPattern = Pattern.compile(rowSep);
			String[] rows = rowPattern.split(text_A);

			double[][] matrix = new double[rows.length][];
			int i = 0;
			for (String element : rows) {

				String elemsSep = ",";
				Pattern elemsPattern = Pattern.compile(elemsSep);
				String[] elems = elemsPattern.split(element);

				matrix[i] = new double[elems.length];
				int j = 0;
				for (String e : elems) {
					matrix[i][j] = str_to_double(e);
					j++;
				}
				i++;
			}
			A = new Matrix(matrix);
		} catch (Exception ex) {
			matrix_A_label.setText("Error: Check dimension \n or entries of matrix A");
			A = null;
		}

		// Read matrix B
		try {
			String text_B = matrix_B_text.getText();

			String rowSep = ";";
			Pattern rowPattern = Pattern.compile(rowSep);
			String[] rows = rowPattern.split(text_B);

			double[][] matrix = new double[rows.length][];
			int i = 0;
			for (String element : rows) {

				String elemsSep = ",";
				Pattern elemsPattern = Pattern.compile(elemsSep);
				String[] elems = elemsPattern.split(element);

				matrix[i] = new double[elems.length];
				int j = 0;
				for (String e : elems) {
					matrix[i][j] = str_to_double(e);
					j++;
				}
				i++;
			}
			B = new Matrix(matrix);
		} catch (Exception ex) {
			matrix_B_label.setText("Error: Check dimension \n or entries of matrix B");
			B = null;
		}

	}

	private static double str_to_double(String str) {
		Scanner scan = new Scanner(str);
		double val;
		if (scan.hasNextDouble()) {
			val = scan.nextDouble();
		} else {
			val = Double.MIN_VALUE;
		}
		scan.close();
		return val;
	}

	private void disp_btn_action() {
		create_matrices();
		if (A != null && B != null) {
			matrix_A_label.setText(A.toString());
			matrix_B_label.setText(B.toString());

			if (A.is_vector()) {
				cartPane.drawVector(new Vector(A.getElements()), Color.CORNFLOWERBLUE);
			}

			if (B.is_vector()) {
				cartPane.drawVector(new Vector(B.getElements()), Color.HOTPINK);
			}

		} else if (A == null) {
			matrix_A_label.setText("Error: Check dimension \nor entries of matrix A");
			matrix_B_label.setText(B.toString());
		} else if (B == null) {
			matrix_A_label.setText(A.toString());
			matrix_B_label.setText("Error: Check dimension \nor entries of matrix B");
		}

	}

	private void clear_btn_action() {
		cartPane.clearCanvas();
	}

	public static void main(String[] args) {
		launch();
	}
}
