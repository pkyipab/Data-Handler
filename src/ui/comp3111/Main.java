package ui.comp3111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.SampleDataGenerator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




/**
 * The Main class of this GUI application
 * 
 * @author cspeter
 *
 */
public class Main extends Application {

	// Attribute: DataTable
	// In this sample application, a single data table is provided
	// You need to extend it to handle multiple data tables
	// Hint: Use java.util.List interface and its implementation classes (e.g.
	// java.util.ArrayList)
	public static ArrayList<DataTable> allDataSet = new ArrayList<DataTable>();
	private DataImportExport dataImportExport = new DataImportExport();
	
	// Attributes: Scene and Stage
	private static final int SCENE_NUM = 6;
	private static final int SCENE_MAIN_SCREEN = 0;
	private static final int SCENE_IMPORT_EXPORT = 1;
	private static final int SCENE_MUTIPLE_CHRAT = 2;
	private static final int SCENE_SAVE_LOAD = 3;
	private static final int SCENE_FILTER_DATA = 4;
	private static final int SCENE_PLOT_LINE_CHART = 5;
	/*
	 * TODO PieChart
	 * private static final int SCENE_PLOT_PIE_CHART = 6;
	 */
	private static final String[] SCENE_TITLES = { "COMP3111 - [Sun of the bench]", "Data Import & Export",  "HandleMultiDataAndChart", "", "", "Plot Line Chart"};
    private Scene[] scenes = null;
    private Stage stage = null;
    
	// To keep this application more structural,
	// The following UI components are used to keep references after invoking
	// createScene()

	// Screen 1: paneMainScreen
	private Button btImportExport, btMutipleChart, btSavingLoading, btDataFiltering;
	private Label lbMainScreenTitle;
	
	//Screen 2: paneImportExportScreen (**Need**)
    private ObservableList<VBox> viewDataSet = FXCollections.observableArrayList();
    private ListView<VBox> dataSet = new ListView<VBox>();
	private Button btImportData;
	private Button btExportData;
	private Button btBackToMenu;
	private Map<VBox, DataTable> map = new LinkedHashMap<VBox, DataTable>();;
	
	
	//Screen 3: paneHandleMultiDataAndChart
	private ListView<VBox> listViewDataSetObj;
	private ListView<VBox> listViewChartObj;
	private ObservableList<VBox> listViewDataSet = FXCollections.observableArrayList();
	private ObservableList<VBox> listViewChart = FXCollections.observableArrayList();
	private Button btShowChart;
	private Button btBackToMenu2;
	private Button btPlotChart;
	
	//Screen 5: paneHandlePlotLineChart
	private Button btPlotLine;
	private Button btReturn;

	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 400, 500);
		scenes[SCENE_IMPORT_EXPORT] = new Scene(paneImportExportScreen(), 800, 600);
		scenes[SCENE_MUTIPLE_CHRAT] = new Scene(paneHandleMultiDataAndChart(), 800, 600);
		scenes[SCENE_PLOT_LINE_CHART] = new Scene(paneHandlePlotLineChart(), 800, 600);
		/*
		 * TODO PieChart
		 * scenes[SCENE_PLOT_PIE_CHART] = new Scene(paneHandlePlotChart(), 800, 600);
		 */
		for (Scene s : scenes) {
			if (s != null)
				// Assumption: all scenes share the same stylesheet
				s.getStylesheets().add("Main.css");
		}
	}

	/**
	 * This method will be invoked after createScenes(). In this stage, all UI
	 * components will be created with a non-NULL references for the UI components
	 * that requires interaction (e.g. button click, or others).
	 */
	private void initEventHandlers() {
		initMainScreenHandlers();
		initImportExportScreenHandlers();
		initHandleMultiDataAndChart();
		initHandlePlotLineChart();
	
	}
	
	private void initImportExportScreenHandlers() {
		
		btExportData.setDisable(true);
		
		btImportData.setOnAction(e -> {
			dataImportExport.importData(stage, viewDataSet, listViewDataSet, map);
			if(allDataSet.size() > 0) {
				btExportData.setDisable(false);
			}
		});
			
		btExportData.setOnAction(e -> {
			dataImportExport.exportData(stage, dataSet, map);
		});
			
		btBackToMenu.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
		
	}
	
	private void initHandleMultiDataAndChart() {		
		//TODO 
		listViewDataSetObj.getSelectionModel().selectedItemProperty().addListener(e->{
			
		});
		
		listViewChartObj.getSelectionModel().selectedItemProperty().addListener(e->{
			
		});
		
		btShowChart.setOnAction(e->{
			
		});
		
		btPlotChart.setOnAction(e->{
			putSceneOnStage(SCENE_PLOT_LINE_CHART);
		});		

		btBackToMenu2.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
	}

	private void initHandlePlotLineChart() {		
		
		btPlotLine.setOnAction(e->{
			//TODO call the Chart Class function to create a new chart
		});
		
		btReturn.setOnAction(e->{
			putSceneOnStage(SCENE_MUTIPLE_CHRAT);
		});		
	}
	
	/**
	 * Initialize event handlers of the main screen
	 */
	private void initMainScreenHandlers() {
		
		btImportExport.setOnAction(e -> {
			putSceneOnStage(SCENE_IMPORT_EXPORT);
		});
		
		btMutipleChart.setOnAction(e -> {
			putSceneOnStage(SCENE_MUTIPLE_CHRAT);
		});
		
		btSavingLoading.setOnAction(e -> {
			putSceneOnStage(SCENE_SAVE_LOAD);
		});
		
		btDataFiltering.setOnAction(e -> {
			putSceneOnStage(SCENE_FILTER_DATA);
		});

	}

	/**
	 * Creates the main screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	private Pane paneMainScreen() {

		lbMainScreenTitle = new Label("COMP3111");
		btImportExport = new Button("Import & Export (by victor)");
		btMutipleChart = new Button("Mutiple Datasets & Charts");
		btSavingLoading = new Button("Save / Load");
		btDataFiltering = new Button("Filter Data");
		
		VBox container = new VBox(20);
		container.getChildren().addAll(lbMainScreenTitle, btImportExport, btMutipleChart, btSavingLoading, btDataFiltering);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply style to the GUI components
		btImportExport.getStyleClass().add("menu-button");
		btMutipleChart.getStyleClass().add("menu-button");
		btSavingLoading.getStyleClass().add("menu-button");
		btDataFiltering.getStyleClass().add("menu-button");
		lbMainScreenTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");

		return pane;
	}
	
	/**
	 * Creates the Import & Export screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	private Pane paneImportExportScreen() {
		Label label = new Label("Current DataSet :");
		btImportData = new Button("Import Data");
		btExportData = new Button("Export Data");
		btBackToMenu = new Button("Back To Menu");
        
		VBox container = new VBox(20);
		dataSet.setItems(viewDataSet);
		dataSet.setPrefSize(50, 150);
	
		
		container.getChildren().addAll(label, dataSet, btImportData, btExportData, btBackToMenu);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		pane.getStyleClass().add("screen-background");

		return pane;
	}
	
	private Pane paneHandleMultiDataAndChart() {
		Label lbDataSet = new Label("Data set");
		Label lbChart = new Label("Chart");
		btShowChart = new Button("Show Chart");
		btBackToMenu2 = new Button("Back to Menu");
		btPlotChart = new Button("Plot Chart");

		listViewDataSetObj = new ListView<VBox>();
		listViewDataSetObj.setItems(listViewDataSet);
		listViewChartObj = new ListView<VBox>();
		listViewChartObj.setItems(listViewChart);
		
		HBox container = new HBox(20);
		HBox bottomContainer = new HBox(20);

		VBox dataSetContainer = new VBox(10);
		VBox chartContainer = new VBox(10);
		
		
		dataSetContainer.getChildren().addAll(lbDataSet, listViewDataSetObj, btPlotChart);
		chartContainer.getChildren().addAll(lbChart, listViewChartObj, btShowChart);
		bottomContainer.getChildren().add(btBackToMenu2);
		bottomContainer.setAlignment(Pos.CENTER);
		
		container.getChildren().addAll(dataSetContainer, chartContainer);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		pane.setBottom(bottomContainer);
		
		pane.getStyleClass().add("screen-background");		

		return pane;
	}
	
	private Pane paneHandlePlotLineChart() {
		Label Hints = new Label("ONLY Numeric Columns will DISPLAYED");
		Label lbXaxis = new Label("Selected X-axis");
		Label lbYaxis = new Label("Selected Y-axis");
		
		btPlotLine = new Button("Plot");
		btReturn = new Button("Return");
		
		HBox topContainer = new HBox(20);
		HBox comboBox = new HBox(20);
		VBox selectX = new VBox(20);
		VBox selectY = new VBox(20);
		HBox bottomContainer = new HBox(20);
		
		topContainer.getChildren().add(Hints);
		comboBox.getChildren().addAll(selectX, selectY);
		bottomContainer.getChildren().addAll(btPlotLine, btReturn);
		
		BorderPane pane = new BorderPane();
		pane.setTop(topContainer);
		pane.setCenter(comboBox);
		pane.setBottom(bottomContainer);
		pane.getStyleClass().add("screen-background");		
		return pane;
	}
	
	/*
	 * TODO PieChart
	private Pane paneHandlePlotPieChart() {
			
		BorderPane pane = new BorderPane();
		return pane;
	}
	*/
	
	/**
	 * This method is used to pick anyone of the scene on the stage. It handles the
	 * hide and show order. In this application, only one active scene should be
	 * displayed on stage.
	 * 
	 * @param sceneID
	 *            - The sceneID defined above (see SCENE_XXX)
	 */
	private void putSceneOnStage(int sceneID) {

		// ensure the sceneID is valid
		if (sceneID < 0 || sceneID >= SCENE_NUM)
			return;

		stage.hide();
		stage.setTitle(SCENE_TITLES[sceneID]);
		stage.setScene(scenes[sceneID]);
		stage.setResizable(true);
		stage.show();
	}

	/**
	 * All JavaFx GUI application needs to override the start method You can treat
	 * it as the main method (i.e. the entry point) of the GUI application
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

			stage = primaryStage; // keep a stage reference as an attribute
			initScenes(); // initialize the scenes
			initEventHandlers(); // link up the event handlers
			putSceneOnStage(SCENE_MAIN_SCREEN); // show the main screen

		} catch (Exception e) {

			e.printStackTrace(); // exception handling: print the error message on the console
		}
	}

	/**
	 * main method - only use if running via command line
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 *  Alert Method - only use when Exception caught
	 * 
	 * 	@param title
	 * 			- The pop up dialog Title
	 * 	@param 
	 * 
	 */
	
	public static void alertUser(String title, String errorType,String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(errorType);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
