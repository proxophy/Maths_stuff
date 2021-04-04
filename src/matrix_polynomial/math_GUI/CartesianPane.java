//Copied at https://stackoverflow.com/questions/24005247/draw-cartesian-plane-graphi-with-canvas-in-javafx

package math_GUI;

import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CartesianPane extends Pane {
	private NumberAxis xAxis;
	private NumberAxis yAxis;

	private double chartWidth;
	private double chartHeight;
	private double minX;
	private double maxX;
	private double xTickUnit;
	private double xUnitW;
	private double minY;
	private double maxY;
	private double yTickUnit;
	private double yUnitW;

	public CartesianPane(double chartWidth, double chartHeight, double minX, double maxX, double xTickUnit, double minY,
			double maxY, double yTickUnit) {
		this.chartWidth = chartWidth;
		this.chartHeight = chartHeight;
		this.minX = minX;
		this.maxX = maxX;
		this.xTickUnit = xTickUnit;
		this.xUnitW = this.chartWidth / (maxX - minX);
		this.minY = minY;
		this.maxY = maxY;
		this.yTickUnit = yTickUnit;
		this.yUnitW = this.chartHeight/ (maxY - minY);

		Axes axes = new Axes();
		
		this.getChildren().setAll(axes);
		draw();
	}

	private void draw() {
		Canvas canvas = new Canvas(chartWidth, chartHeight);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setLineWidth(5.0);
		gc.setStroke(Color.RED);
		gc.strokeLine(chartWidth/2.0, chartHeight/2.0, chartWidth/2.0  + 3 * xUnitW , chartHeight/2.0  - yUnitW*7.0);
	
		
		this.getChildren().add(canvas);
	}
	
	
	
	class Axes extends Pane {
		public Axes() {
			this.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
			this.setPrefSize(chartWidth, chartHeight);
			this.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

			xAxis = new NumberAxis(minX, maxX, xTickUnit);
			xAxis.setSide(Side.BOTTOM);
			xAxis.setMinorTickVisible(false);
			xAxis.setPrefWidth(chartWidth);
			xAxis.setLayoutY(chartHeight / 2);

			yAxis = new NumberAxis(minY, maxY, yTickUnit);
			yAxis.setSide(Side.LEFT);
			yAxis.setMinorTickVisible(false);
			yAxis.setPrefHeight(chartHeight);
//			yAxis.setLayoutX(chartWidth / 2.0);
			yAxis.layoutXProperty().bind(Bindings.subtract((chartWidth / 2) +1, yAxis.widthProperty()));

			getChildren().setAll(xAxis, yAxis);
		}

		public NumberAxis getXAxis() {
			return xAxis;
		}

		public NumberAxis getYAxis() {
			return yAxis;
		}

	}

}
