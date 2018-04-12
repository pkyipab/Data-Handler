package ui.comp3111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
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
	static ArrayList<DataTable> allDataSet = new ArrayList<DataTable>();
	private DataImportExport dataImportExport = new DataImportExport();
	
	// Attributes: Scene and Stage
	private static final int SCENE_NUM = 2;
	private static final int SCENE_MAIN_SCREEN = 0;
	private static final int SCENE_IMPORT_EXPORT = 1;
	private static final int SCENE_MUTIPLE_CHRAT = 2;
	private static final int SCENE_SAVE_LOAD = 3;
	private static final int SCENE_FILTER_DATA = 4;
	private static final String[] SCENE_TITLES = { "COMP3111 - [Sun of the bench]", "Data Import & Export" };
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
	
	

	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 400, 500);
		scenes[SCENE_IMPORT_EXPORT] = new Scene(paneImportExportScreen(), 800, 600);
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
	}
	
	private void initImportExportScreenHandlers() {
			
		btImportData.setOnAction(e -> {
			dataImportExport.importData(stage, viewDataSet);
		});
			
		btExportData.setOnAction(e -> {
			dataImportExport.exportData(stage, dataSet);
		});
			
		btBackToMenu.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
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
		container.getChildren().addAll(lbMainScreenTitle, btImportExport, this.btMutipleChart, this.btSavingLoading, this.btDataFiltering);
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
	
		
		container.getChildren().addAll(label, dataSet, btImportData, this.btExportData, this.btBackToMenu);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		pane.getStyleClass().add("screen-background");

		return pane;
	}
	
	
	
	
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
}
