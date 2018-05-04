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

/**
 * ShowAnimatedLineChart - A Helper class. This class will be used by show the animation. It
 * stores the javafx Object and pop-up the animation line chart
 * 
 * @author tmtam
 *
 */
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
	/**
	 *  Show Animation Line Chart Method - to pop-up and new stage and play the animated line chart
	 * 
	 * 	@param chart
	 * 			- the dataColumn would used to create the Chart Animation 	 
	 * 
	 */
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

	/**
	 *  init Method - init the pop-up stage
	 * 
	 */
	private void init(Stage primaryStage) {
		xAxisAnimation = new NumberAxis();
		xAxisAnimation.setForceZeroInRange(false);
		
		yAxisAnimation = new NumberAxis();
		yAxisAnimation.setForceZeroInRange(false);
		yAxisAnimation.setAutoRanging(false);
		
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
		
	/**
	 * AddToQueue - A helper class to start the threading for make animation
	 * 
	 * @author cpkoaajack
	 *
	 */
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

	/**
	 *  prepareTimeline Method - to start AnimationTimer
	 * 
	 */
	private void prepareTimeline() { 
		new AnimationTimer() {
	        @Override
	        public void handle(long now) {
	            addDataToSeries();
	        }
	    }.start();
	}

	/**
	 *  update line chart data Method - to add the new data to the animation
	 * 
	 */
	private void addDataToSeries() {
	    for (int i = 0; i < chart.getXAxisColumn().getSize(); i++) {
	        if (dataQX.isEmpty()) break;
	        animationSeries.getData().add(new XYChart.Data<>(dataQX.remove(), dataQY.remove()));
	    }
	    if (animationSeries.getData().size() > maxDataPoints) {
	    	animationSeries.getData().remove(0, animationSeries.getData().size() - maxDataPoints);
	    }
	    yAxisAnimation.setLowerBound(chart.getYAxisColumn().getMin());
	    yAxisAnimation.setUpperBound(chart.getYAxisColumn().getMax());
	}
	
		
}
