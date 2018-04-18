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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
	private DataSaveAndLoad dataSaveAndLoad = new DataSaveAndLoad();
	private DataFilter dataFilter = new DataFilter();

	public static ArrayList<Chart> storedChart = new ArrayList<Chart>();

	private PlotLineChart plotlinechart;
	
	// Attributes: Scene and Stage

	private static final int SCENE_NUM = 6;

	private static final int SCENE_MAIN_SCREEN = 0;
	private static final int SCENE_IMPORT_EXPORT = 1;
	private static final int SCENE_MUTIPLE_CHRAT = 2;
	private static final int SCENE_SAVE_LOAD = 3;
	private static final int SCENE_FILTER_DATA = 4;
	private static final int SCENE_PLOT_LINE_CHART = 5;
	/*
	 * TODO PieChart (will be using similar method as Line Chart)
	 * private static final int SCENE_PLOT_PIE_CHART = 6;
	 */
	private static final String[] SCENE_TITLES = { "COMP3111 - [Sun of the bench]", "Data Import & Export",  "HandleMultiDataAndChart",  "Save And Load", "Data Filtering","Plot Line Chart"};

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
	private Button btImportExport_BackToMenu;
	private Map<VBox, DataTable> map = new LinkedHashMap<VBox, DataTable>();
	
	
	//Screen 3: paneHandleMultiDataAndChart
	private ListView<VBox> listViewDataSetObj;
	private ListView<VBox> listViewChartObj;
	private ObservableList<VBox> listViewDataSet = FXCollections.observableArrayList();
	private ObservableList<VBox> listViewChart = FXCollections.observableArrayList();
	private Button btShowChart;
	private Button btBackToMenu2;
	private Button btPlotChart;
	private Map<VBox, Chart> chartMap = new LinkedHashMap<VBox, Chart>();
	private Map<VBox, DataTable> dataTableMap = new LinkedHashMap<VBox, DataTable>();

	//Screen 4: paneSaveAndLoad
	private Button loadButton;
	private Button saveButton;
	private Button btBackToMenu3;
	
	//Screen 5: paneSaveAndLoad
	private ObservableList<VBox> dataFilterDataSet = FXCollections.observableArrayList();
	private ObservableList<HBox> dataColumnDataSet = FXCollections.observableArrayList();
	private ListView<VBox> dataFilterData;
	private ListView<HBox> dataColumnList;
	private final ToggleGroup groupRandom = new ToggleGroup();
	private final ToggleGroup groupText = new ToggleGroup();
	private RadioButton replaceRandom;
	private RadioButton createNewRandom;
	private RadioButton replaceText;
	private RadioButton createNewText;
	private Button btRandomPaneConfirm;
	private Button btTextPaneConfirm;
	private Button btDataFilter_BackToMenu;
	private Map<VBox, DataTable> mapDataFilter = new LinkedHashMap<VBox, DataTable>();
	private Map<CheckBox, DataColumn> dataFilterColumns = new LinkedHashMap<CheckBox, DataColumn>();

	//Screen 6: paneHandlePlotLineChart
	private Button btPlotLine;
	private Button btReturn;
	
	/*
	 * TODO PieChart (will be using similar method as Line Chart)
	//Screen 7: paneHandlePlotPieChart
	private Button btPlotPie;
	private Button btReturn_alt;
	 *
	 */
	public static int numOfConlict = 0;
	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 400, 500);
		scenes[SCENE_IMPORT_EXPORT] = new Scene(paneImportExportScreen(), 800, 600);
		scenes[SCENE_MUTIPLE_CHRAT] = new Scene(paneHandleMultiDataAndChart(), 800, 600);
		scenes[SCENE_SAVE_LOAD] = new Scene(paneSaveAndLoad(), 400, 500);
		scenes[SCENE_FILTER_DATA] = new Scene(paneDataFiltering(), 800, 600);
		scenes[SCENE_PLOT_LINE_CHART] = new Scene(paneHandlePlotLineChart(), 800, 600);
		/*
		 * TODO PieChart (will be using similar method as Line Chart)
		 * scenes[SCENE_PLOT_PIE_CHART] = new Scene(paneHandlePlotPieChart(), 800, 600);
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
		initSaveAndLoad();
		initDataFiltering();
		initHandlePlotLineChart();
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
	
	private void initImportExportScreenHandlers() {
		
		btExportData.setDisable(true);
		
		btImportData.setOnAction(e -> {
			dataImportExport.importData(stage);
			if(allDataSet.size() > 0) {
				btExportData.setDisable(false);
			}
			updateListView();
		});
			
		btExportData.setOnAction(e -> {
			dataImportExport.exportData(stage, dataSet, map);
		});
			
		btImportExport_BackToMenu.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
		
	}
	
	private void initHandleMultiDataAndChart() {		
		//TODO 
		listViewDataSetObj.getSelectionModel().selectedItemProperty().addListener(e->{
			btPlotChart.setOnAction(o->{
				DataTable selectedDataTable = dataTableMap.get(listViewDataSetObj.getSelectionModel().getSelectedItem());
				plotlinechart = new PlotLineChart(selectedDataTable);
				putSceneOnStage(SCENE_PLOT_LINE_CHART);
			});
		});
		
		listViewChartObj.getSelectionModel().selectedItemProperty().addListener(e->{
			btShowChart.setOnAction(o->{
				
			});
		});
		
		btBackToMenu2.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
	}
	
	private void initSaveAndLoad() {
		loadButton.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Load .Comp3111");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Comp3111 files", "*.comp3111"));

			File file = fileChooser.showOpenDialog(stage);
			if(file != null)
				dataSaveAndLoad.loadData(file);
			updateListView();
		});
		
		saveButton.setOnAction(e->{
	        FileChooser fs = new FileChooser();
	        fs.setTitle("Save .Comp3111");

	        fs.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Comp3111", ".comp3111"));
			File file = fs.showSaveDialog(stage);
			dataSaveAndLoad.saveData(file);
		});
		
		btBackToMenu3.setOnAction(e->{
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
		
	}
	
	private void initDataFiltering() {	
		
		dataFilterData.getSelectionModel().selectedItemProperty().addListener(e->{
			DataTable selectedDataTable = mapDataFilter.get(dataFilterData.getSelectionModel().getSelectedItem());
			
			if( selectedDataTable != null) {
				btRandomPaneConfirm.setOnAction(e2 -> {
					if(replaceRandom.isSelected()) {
						dataFilter.RandomSplit("Replace", selectedDataTable.getFileName());
					} else if (createNewRandom.isSelected()) {
						dataFilter.RandomSplit("Create New", selectedDataTable.getFileName());
					}
					updateListView();
				});
			
				updateDataColumnListView(selectedDataTable);
			}
		});
		
		btDataFilter_BackToMenu.setOnAction(e->{
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});		
	}
	
	private void initHandlePlotLineChart() {		
		
		btPlotLine.setOnAction(e->{

		});
		
		btReturn.setOnAction(e->{
			putSceneOnStage(SCENE_MUTIPLE_CHRAT);
		});		
	}
	
	/*
	 * 
	 * TODO PieChart (will be using similar method as Line Chart)
	 * private void initHandlePlotPieChart() {		
		
		btPlotLine.setOnAction(e->{

		});
		
		btReturn.setOnAction(e->{
			putSceneOnStage(SCENE_MUTIPLE_CHRAT);
		});		
	}
	 * 
	 */

	/**
	 * Creates the main screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	private Pane paneMainScreen() {

		lbMainScreenTitle = new Label("COMP3111");
		btImportExport = new Button("Import & Export");
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
		btImportExport_BackToMenu = new Button("Back To Menu");
        
		VBox container = new VBox(20);
		dataSet.setItems(viewDataSet);
		dataSet.setPrefSize(50, 150);
	
		
		container.getChildren().addAll(label, dataSet, btImportData, btExportData, btImportExport_BackToMenu);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		
		btImportData.getStyleClass().add("menu-button");
		btExportData.getStyleClass().add("menu-button");
		btImportExport_BackToMenu.getStyleClass().add("menu-button");
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

	public Pane paneSaveAndLoad() {
		loadButton = new Button("Load");
		saveButton = new Button("Save");
		btBackToMenu3 = new Button("Back to menu");
		
		dataSaveAndLoad = new DataSaveAndLoad();
		
		Separator separator = new Separator();
		separator.setPadding(new Insets(20, 20, 20, 20));

		Separator separator2 = new Separator();
		separator2.setPadding(new Insets(20, 20, 20, 20));
		
		VBox vBox = new VBox();
		
		vBox.getChildren().addAll(loadButton, separator, saveButton, separator2, btBackToMenu3);
		vBox.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(vBox);
		
		return pane;
	}
	
	public Pane paneDataFiltering() {
		replaceRandom = new RadioButton(" Replace Current DataSet ");
		replaceRandom.setToggleGroup(groupRandom);
		createNewRandom = new RadioButton(" Create New DataSet ");
		createNewRandom.setToggleGroup(groupRandom);
		btRandomPaneConfirm = new Button("Confirm");
		
		replaceText = new RadioButton(" Replace Current DataSet ");
		replaceText.setToggleGroup(groupText);
		createNewText = new RadioButton(" Create New DataSet ");
		createNewText.setToggleGroup(groupText);
		btTextPaneConfirm = new Button("Confirm");
		
		btDataFilter_BackToMenu = new Button("Back to menu");
		dataFilterData = new ListView<VBox>();
		dataFilterData.setItems(dataFilterDataSet);
		dataColumnList = new ListView<HBox>();
		dataColumnList.setItems(dataColumnDataSet);
		
		
		Label title = new Label("Data Filtering");
		title.setPadding(new Insets(0, 0, 5, 0));
		
		VBox titleVBox = new VBox();
		titleVBox.getChildren().addAll(title, new Separator());
		titleVBox.setAlignment(Pos.CENTER);
		titleVBox.setPadding(new Insets(20, 20, 20, 20));
		
		VBox data = new VBox();
		Label dataTitle = new Label(" Current Datasets :");
		dataTitle.setPadding(new Insets(0, 0, 5, 0));
		data.getChildren().addAll(dataTitle, dataFilterData);
		data.setAlignment(Pos.CENTER);
		
		HBox exit = new HBox();
		exit.getChildren().add(btDataFilter_BackToMenu);
		exit.setPadding(new Insets(10, 0, 0, 0));
		exit.setAlignment(Pos.CENTER);
		
		StackPane root = new StackPane();
		
		VBox randomVbox = new VBox();
		randomVbox.getChildren().addAll(new Label("Please Select a SINGLE Dataset and choose following : "), replaceRandom, createNewRandom, btRandomPaneConfirm);
		replaceRandom.setPadding(new Insets(20, 0, 0, 0));
		createNewRandom.setPadding(new Insets(20, 0, 50, 0));
		randomVbox.setAlignment(Pos.CENTER);

		
		Tab randomTab = new Tab();
		randomTab.setText("Random Filter");
		randomTab.setContent(randomVbox);
		randomTab.closableProperty().setValue(false);
		
		VBox columnBox = new VBox();
		Label colTitle = new Label(" The Columns of Current Dataset : ");
		colTitle.setPadding(new Insets(10, 0, 10, 0));
		columnBox.getChildren().addAll(colTitle, dataColumnList);
		columnBox.setPadding(new Insets(0, 25, 20, 10));
		
		
		VBox textVBox = new VBox();
		textVBox.getChildren().addAll(new Label("Please choose following : "), replaceText, createNewText, btTextPaneConfirm);
		replaceText.setPadding(new Insets(20, 0, 0, 0));
		createNewText.setPadding(new Insets(20, 0, 50, 0));
		textVBox.setAlignment(Pos.CENTER);
		
		HBox textHBox = new HBox();
		textHBox.getChildren().addAll(columnBox, textVBox);
		
		Tab textTab = new Tab();
		textTab.setText("Text Filter");
		textTab.setContent(textHBox);
		textTab.closableProperty().setValue(false);
		
		TabPane tabPane = new TabPane();
		tabPane.getSelectionModel().select(1);
        tabPane.getTabs().addAll(textTab, randomTab);
        
        tabPane.setTabMinWidth(228);
        tabPane.setTabMinHeight(30);
        
        
        root.getChildren().add(tabPane);
        root.setPadding(new Insets(0, 0, 0, 10));

		BorderPane pane = new BorderPane();
		pane.setTop(titleVBox);
		pane.setLeft(data);
		pane.setBottom(exit);
		pane.setCenter(root);
		pane.setPadding(new Insets(20, 20, 20, 20));
		
		
		btTextPaneConfirm.getStyleClass().add("menu-button");
		btRandomPaneConfirm.getStyleClass().add("menu-button");
		btDataFilter_BackToMenu.getStyleClass().add("menu-button");
		tabPane.getStyleClass().add("tab-pane");
		root.getStyleClass().add("tab-header-background");
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
		/*
		 * TODO
		 * Still learning to use Combo box Class (use Combo box is the Requirement of the Project)
		 */
		topContainer.getChildren().add(Hints);
		topContainer.setAlignment(Pos.CENTER);
		comboBox.getChildren().addAll(selectX, selectY);
		comboBox.setAlignment(Pos.CENTER);
		bottomContainer.getChildren().addAll(btPlotLine, btReturn);
		bottomContainer.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setTop(topContainer);
		pane.setCenter(comboBox);
		pane.setBottom(bottomContainer);
		pane.getStyleClass().add("screen-background");		
		return pane;
	}
	
	/*
	 * TODO PieChart (will be using similar method as Line Chart)
	private Pane paneHandlePlotPieChart() {
		Label Hints = new Label("Select ONE Text and ONE Numeric Column");
		Label lbXaxis = new Label("Selected X-axis");
		Label lbYaxis = new Label("Selected Y-axis");
		
		btPlotPie = new Button("Plot");
		btReturn_alt = new Button("Return");
		
		HBox topContainer = new HBox(20);
		HBox comboBox = new HBox(20);
		VBox selectX = new VBox(20);
		VBox selectY = new VBox(20);
		HBox bottomContainer = new HBox(20);
		
		TODO
		Still learning to use Combo box Class (use Combo box is the Requirement of the Project)	
			
		topContainer.getChildren().add(Hints);
		comboBox.getChildren().addAll(selectX, selectY);
		bottomContainer.getChildren().addAll(btPlotPie, btReturn_alt);
		
		BorderPane pane = new BorderPane();
		pane.setTop(topContainer);
		pane.setCenter(comboBox);
		pane.setBottom(bottomContainer);
		pane.getStyleClass().add("screen-background");		
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
	
	public void updateListView() {

		viewDataSet.clear();
		listViewDataSet.clear();
		dataFilterDataSet.clear();
		
		map.clear();
		mapDataFilter.clear();
		

		for(int i = 0; i < allDataSet.size(); i++) {
			
			VBox dataVBox = new VBox();
			VBox dataVBoxHandle = new VBox();
			VBox dataFilterVBox = new VBox();
			
            map.put(dataVBox, allDataSet.get(i));

            mapDataFilter.put(dataFilterVBox,allDataSet.get(i));

            dataTableMap.put(dataVBoxHandle, allDataSet.get(i));
			
            dataVBox.getChildren().addAll(new Label("DataSet " + (i + 1) +
            		" : " + allDataSet.get(i).getFileName() +  ""));
            viewDataSet.add(dataVBox);

            dataVBoxHandle.getChildren().addAll(new Label("DataSet " + (i + 1) +
            		" : " + allDataSet.get(i).getFileName() +  ""));
            listViewDataSet.add(dataVBoxHandle);
            
            dataFilterVBox.getChildren().addAll(new Label("DataSet " + (i + 1) +
            		" : " + allDataSet.get(i).getFileName() +  ""));
            dataFilterDataSet.add(dataFilterVBox);
		}
	}
	

	private void updateDataColumnListView(DataTable data) {
		dataColumnDataSet.clear();
		
		for(int i = 0; i < data.getNumCol(); i++) {
			HBox dataHBox = new HBox();
			CheckBox checkBox = new CheckBox();
		
			dataFilterColumns.put(checkBox, data.getColByNum(i));
			
			dataHBox.getChildren().addAll(checkBox,new Label(data.getColName(i)));
			dataColumnDataSet.add(dataHBox);
		}
	}
	
	public static int isValidFileName(String name) {
		Boolean conflict = false;
		for(DataTable data : allDataSet) {
			if(data.getFileName().equals(name)) {
				conflict = true;
				break;
			} 
		}
		if(conflict) {
				return 1 + checkNameHelper(name + "_1");
			}
		 else {
			return 0;
		}
	}
	
	public static int checkNameHelper(String name) {
		Boolean conflict = false;
		for(DataTable data : allDataSet) {
			if(data.getFileName().equals(name)) {
				conflict = true;
				break;
			} 
		}
		
		if(conflict) {
			String[] check = name.split("_");
			int num = Integer.parseInt(check[check.length - 1]) + 1;
			String next = "";
			for(int i = 0; i < check.length - 1; i++) {
				if(check.length == 1) {
					next += (check[i]);
				} else if (check.length > 1) {
					next += (check[i] + "_");
				}
			}
			
			System.out.println(next  + Integer.toString(num));
			return 1 + checkNameHelper(next  + Integer.toString(num));
				
		} else {
			return 0;
		}
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
