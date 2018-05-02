package ui.comp3111;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import core.comp3111.GeneralChart;
import core.comp3111.LineChartObj;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

public class ShowAnimatedLineChart {
	private LineChartObj chart;
	private NumberAxis xAxis = new NumberAxis();
	private NumberAxis yAxis = new NumberAxis();
	private NumberAxis xAxisAnimation;
	private NumberAxis yAxisAnimation;
	private XYChart.Series<Number, Number> series;
	private XYChart.Series<Number, Number> animationSeries;
	private int maxDataPoints;

    private ConcurrentLinkedQueue<Number> dataQX = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Number> dataQY = new ConcurrentLinkedQueue<>();
	private LineChart<Number,Number> animationLineChart;
	private ExecutorService executor;

	public void showAnimation(GeneralChart chart){
		this.chart = (LineChartObj) chart;
		LineChart<Number,Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);	
		maxDataPoints = ((LineChartObj) chart).getXAxisColumn().getSize()/4;

		series = new Series<Number, Number>();
		lineChart.setTitle(chart.getTitle());
		xAxis.setLabel(((LineChartObj) chart).getXAxisName());
		yAxis.setLabel(((LineChartObj) chart).getYAxisName());
		series.setName(((LineChartObj) chart).getXAxisName() + " versus " + ((LineChartObj) chart).getYAxisName());
		for(int i = 0; i < ((LineChartObj) chart).getXAxisColumn().getSize(); i++) {
			series.getData().add(new Data<Number, Number>((Number)((LineChartObj) chart).getXAxisColumn().getData()[i], 
					(Number)((LineChartObj) chart).getYAxisColumn().getData()[i]));
			}
		lineChart.getData().add(series);					
		
		Stage dialog = new Stage();
		Scene dialogScene = new Scene(lineChart, 800, 600);
		dialog.setTitle("Line Chart");
		
		dialog.setScene(dialogScene);
		init(dialog);
		dialog.show();
        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable run) {
                Thread thread = new Thread(run);
                thread.setDaemon(true);
                return thread;
                }
        });

        AddToQueue addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        prepareTimeline();
	}
	
	private void init(Stage primaryStage) {
		xAxisAnimation = new NumberAxis();
		xAxisAnimation.setForceZeroInRange(false);
		xAxisAnimation.setAutoRanging(false);
		yAxisAnimation = new NumberAxis();
		yAxisAnimation.setForceZeroInRange(false);
		
		xAxisAnimation.setLabel(chart.getXAxisName());
		yAxisAnimation.setLabel(chart.getYAxisName());
		
		animationLineChart = new LineChart<Number, Number>(xAxisAnimation, yAxisAnimation) {
			@Override
	        	protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {}
			};
		
		animationLineChart.setAnimated(false);
		animationLineChart.setTitle("Animation : " + chart.getTitle());
		
		animationSeries = new XYChart.Series<>();
		animationSeries.setName(chart.getXAxisName() + " versus " + chart.getYAxisName());
		
		animationLineChart.getData().add(animationSeries);
	
		primaryStage.setScene(new Scene(animationLineChart));
	}
		
	private class AddToQueue implements Runnable {
	    public void run() {
	        try {
	        	animationSeries.getData().clear();
	            for(int i = 1; i < chart.getXAxisColumn().getSize(); i++) {
	            	dataQX.add((Number)chart.getXAxisColumn().getData()[i]);
	            	dataQY.add((Number)chart.getYAxisColumn().getData()[i]);
	            	Thread.sleep(150);
	            }
	            executor.execute(this);
	        } catch (InterruptedException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	private void prepareTimeline() { 
		new AnimationTimer() {
	        @Override
	        public void handle(long now) {
	            addDataToSeries();
	        }
	    }.start();
	}
	
	private void addDataToSeries() {
	    for (int i = 0; i < chart.getXAxisColumn().getSize(); i++) {
	        if (dataQX.isEmpty()) break;
	        animationSeries.getData().add(new XYChart.Data<>(dataQX.remove(), dataQY.remove()));
	    }
	    if (animationSeries.getData().size() > maxDataPoints) {
	    	animationSeries.getData().remove(0, animationSeries.getData().size() - maxDataPoints);
	    }
	    xAxisAnimation.setLowerBound(chart.getXAxisColumn().getMin());
	    xAxisAnimation.setUpperBound(chart.getXAxisColumn().getMax());
	}
	
		
}
