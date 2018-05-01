package core.comp3111;

import java.io.Serializable;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;
//Animation
import javafx.animation.AnimationTimer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class LineChartObj extends GeneralChart implements Serializable{
	private DataColumn xAxisColumn;
	private DataColumn yAxisColumn;
	private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    XYChart.Series<Number, Number> series;
    //Animation
    private int MAX_DATA_POINTS = 10;
    private int xSeriesData = 0;
    private XYChart.Series<Number, Number> animationSeries;
    private ExecutorService executor;
    private ConcurrentLinkedQueue<Number> dataQ = new ConcurrentLinkedQueue<>();
    private LineChart<Number,Number> animationLineChart;
    private NumberAxis xAxisAnimation;
    private NumberAxis yAxisAnimation;
    
	public LineChartObj(DataColumn xData, DataColumn yData, DataTable dt, String xAxisName, String yAxisName) {
		this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + " [Type = Line] [X-Axis: " + xAxisName + ", Y-Axis: " + yAxisName + "]";
		this.xAxisColumn = xData;
		this.yAxisColumn = yData;
		LineChart<Number,Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		series = new Series<Number, Number>();
		lineChart.setTitle(xAxisName + " versus " + yAxisName);
		xAxis.setLabel(xAxisName);
		yAxis.setLabel(yAxisName);
		series.setName(xAxisName + " versus " + yAxisName);
		for(int i = 0; i < xData.getSize(); i++) {
			series.getData().add(new Data<Number, Number>((Number)xAxisColumn.getData()[i], (Number)yAxisColumn.getData()[i]));
			System.out.println("Added " + ( i + 1 ) + " row data. [ X-Axis index = " + (Number)xAxisColumn.getData()[i] + " ] [ Y-Axis index = " + (Number)yAxisColumn.getData()[i] + " ]");
		}
		lineChart.getData().add(series);
		this.chart = lineChart;
		System.out.println("[ NEW Line Chart : " + this.chartName + " created ]");
	}

	private void init(Stage primaryStage) {

		xAxisAnimation = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
		xAxisAnimation.setForceZeroInRange(false);
		xAxisAnimation.setAutoRanging(false);
		xAxisAnimation.setTickLabelsVisible(false);
		xAxisAnimation.setTickMarkVisible(false);
		xAxisAnimation.setMinorTickVisible(false);

		yAxisAnimation = new NumberAxis();

		animationLineChart = new LineChart<Number, Number>(xAxisAnimation, yAxisAnimation) {
        @Override
        protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {}
        };

        animationLineChart.setAnimated(false);
        animationLineChart.setTitle(this.chartName);
        animationLineChart.setHorizontalGridLinesVisible(true);
        animationSeries = new XYChart.Series<>();
        animationSeries.setName("Series 1");

        animationLineChart.getData().add(animationSeries);

        primaryStage.setScene(new Scene(animationLineChart));
    }
	
	@Override
	public void animationStart() {
		Stage stage = new Stage();
		stage.setHeight(600);
        stage.setWidth(800);
        stage.setTitle("Animated Line Chart");
        init(stage);
        stage.show();

        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });

        AddToQueue addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        prepareTimeline();
    }
	
	private class AddToQueue implements Runnable {
        public void run() {
            try {
                for(int i = 1; i < xAxisColumn.getSize(); i++) {
                	dataQ.add((Number)xAxisColumn.getData()[i]);
                	Thread.sleep(100);
                }
                executor.execute(this);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
	
	private void prepareTimeline() { new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }
	
	private void addDataToSeries() {
        for (int i = 0; i < 20; i++) {
            if (dataQ.isEmpty()) break;
            animationSeries.getData().add(new XYChart.Data<>(xSeriesData++, dataQ.remove()));
        }
        if (animationSeries.getData().size() > MAX_DATA_POINTS) {
        	animationSeries.getData().remove(0, animationSeries.getData().size() - MAX_DATA_POINTS);
        }
        xAxisAnimation.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        xAxisAnimation.setUpperBound(xSeriesData - 1);
    }
}
