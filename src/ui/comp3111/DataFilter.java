package ui.comp3111;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class DataFilter {

	private String firstDataSet = "";
	private String secondDataSet = "";
	private Boolean cancelAlert;
	
	
	public void RandomSplit(String type, String name) {
		DataTable data1;
		DataTable data2;
		
		int randomNum = 0;
		int order = 0;

		cancelAlert = false;
		
		/*Searching DataTable by Name*/
		for(int i = 0; i < Main.allDataSet.size(); i++) {
			if(name == Main.allDataSet.get(i).getFileName()) {
				break;
			}
			order++;
		}
		
		DataTable selected = Main.allDataSet.get(order);
		
		/*Determine the validity of DataTable, if size is <= 1, cannot be split into two*/
		if(selected.getNumCol() <= 1) {
			Main.alertUser("Invalid Dataset size", "Invalid Dataset size", "Unable To Split Into Two DataSets");
			return;
		}
		
		
		/*Ask user to input the DataName, which cannot be Empty and Duplicate*/
		while(true) {
			alertEnterName();
			
			if (firstDataSet.equals(secondDataSet) && !firstDataSet.isEmpty()) {
				firstDataSet = "";
				secondDataSet = "";
				Main.alertUser("Duplicate DataSet Name", "DataSet Names are overlapped", "");
			}
			else if(!firstDataSet.isEmpty() && !secondDataSet.isEmpty()) {
				break;
			} 
			else if (cancelAlert == true) {
				return;
			}
			else {
				Main.alertUser("Empty DataSet Name", "DataSet Name cannot be Empty", "");
			}
		}
		
        /*If there exits the same file name, make the different*/
		if(Main.isValidFileName(firstDataSet) > 0) {
        	data1 = new DataTable(firstDataSet + "_" + Main.isValidFileName(firstDataSet));
        } else {
        	data1 = new DataTable(firstDataSet);
        }
        
        
        if(Main.isValidFileName(secondDataSet) > 0) {
        	data2 = new DataTable(secondDataSet + "_" + Main.isValidFileName(secondDataSet));
        } else {
        	data2 = new DataTable(secondDataSet);
        }
        
    	firstDataSet = "";
		secondDataSet = "";	
		
		/* Start splitting randomly*/
		randomNum = Math.abs((int)System.currentTimeMillis() % (selected.getNumCol() - 1));
		if(randomNum == 0) {
			randomNum++;
		}

		for(int i = 0; i < selected.getNumCol(); i++) {
			try {
				if( i < randomNum ) {
					data1.addCol(selected.getColName(i), selected.getColByNum(i));
					} 
				else {	
					data2.addCol(selected.getColName(i), selected.getColByNum(i));
					}
			}catch(DataTableException e) {
				Main.alertUser("Error", "DataTable Exception", e.getLocalizedMessage());
			} 
		}
			
		if(type == "Replace") {
			System.out.println("Random : Replace Current");
			Main.allDataSet.set(order, data1);
			Main.allDataSet.add(data2);
		} 
		else if (type == "Create New") {
			System.out.println("Random : Create New");
			Main.allDataSet.add(data1);
			Main.allDataSet.add(data2);
		} 
		else {
			System.out.println(" Random Split Method not found. ");
		}
	}
	
	
	/*
	 * Create a pop up window for user to input the name of DataSets
	 * 
	 */
	private void alertEnterName() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("New DataSets");
		dialog.setHeaderText("Please Enter the name of DataSets");

		ButtonType OK = new ButtonType("OK", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(OK, cancel);
	
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField first = new TextField();
		first.setPromptText("First DataSet : ");
		TextField second = new TextField();
		second.setPromptText("Second DataSet : ");
		
		grid.add(new Label("First DataSet :"), 0, 0);
		grid.add(first, 1, 0);
		grid.add(new Label("Second DataSet :"), 0, 1);
		grid.add(second, 1, 1);


		dialog.getDialogPane().setContent(grid);

		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == OK) {
		        return new Pair<>(first.getText(), second.getText());
		    } else if (dialogButton == cancel) {
		    	cancelAlert = true;
		    	return null;
		    }
		    return null;
		});
		
		Optional<Pair<String, String>> result = dialog.showAndWait();
		if( !result.isPresent() ) {
			return;
		}
		
		firstDataSet = result.get().getKey();
		secondDataSet = result.get().getValue();
	}
}
