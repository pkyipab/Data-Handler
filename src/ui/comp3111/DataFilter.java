package ui.comp3111;


import java.util.ArrayList;
import java.util.Optional;


import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;


/**
 * DataFilter - used to transform into new data sets
 * 
 * @author pkyipab
 *
 */
public class DataFilter {

	private String firstDataSet = "";
	private String secondDataSet = "";
	private Boolean cancelAlert;
	
	
	/**
	 * Random Split. Method that randomly split one DataColumn object into two DataColumns
	 * 
	 * @param type
	 *            - type is used to determine user action, either "Replace" or "Create New"
	 *            
	 * @param name
	 *            - pass the name of DataTable object that used for searching that DataTable object
	 */
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
			alertEnterName("Random");
			
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
			Main.allDataSet.set(order, data1);
			Main.allDataSet.add(data2);
		} 
		else if (type == "Create New") {
			Main.allDataSet.add(data1);
			Main.allDataSet.add(data2);
		} 
	}
	
	/**
	 * Numeric Split. Method that allows user to compare their data with specific number and
	 * 						create a new DataTable Object									
	 * 	
	 * @param type
	 *            - type is used to determine user action, either "Replace" or "Create New"
	 *            
	 * @param dataTable
	 *            - pass the dataTable object that user selected
	 * 
	 * @param dataCol
	 *            - pass the dataColumn object that user selected
	 *
	 * @param sign
	 *            - the compare operator that user selected
	 *            
	 * @param compareNum
	 *            - the number that users want to compare with
	 */
	public void NumericSplit(String type, DataTable dataTable,DataColumn dataCol, String sign, double compareNum) {
		ArrayList<Object[]> dataRow = new ArrayList<Object[]>();
		DataTable dataTableNew;
		String[] colType = new String[dataTable.getNumCol()];
		String[] colName = new String[dataTable.getNumCol()];			
		int order = 0;
		
        /*If there exits the same file name, make the different*/
		if(Main.isValidFileName(firstDataSet) > 0) {
			this.firstDataSet = (firstDataSet + "_" + Main.isValidFileName(firstDataSet));
        } 
		
		for(int i = 0; i < dataTable.getNumCol(); i++) {
			colType[i] = dataTable.getColByNum(i).getTypeName();
			colName[i] = dataTable.getColName(i);
		}
		
		cancelAlert = false;
		
		/*Searching DataTable by Name*/
		for(int i = 0; i < Main.allDataSet.size(); i++) {
			if(dataTable.getFileName().equals(Main.allDataSet.get(i).getFileName())) {
				break;
			}
			order++;
		}
		
		for(int i = 0; i < dataCol.getSize(); i++) {
			
			switch(sign) {
				case "Less than" :
					if(Double.valueOf((dataCol.getData()[i].toString())) < compareNum) {
						dataRow.add(addRow(dataTable, i));
					}
					break;
				case "Greater than" :
					if(Double.valueOf((dataCol.getData()[i].toString())) > compareNum) {
						dataRow.add(addRow(dataTable, i));
					}
					break;
				case "Less than or equals to" :
					if(Double.valueOf((dataCol.getData()[i].toString())) <= compareNum) {
						dataRow.add(addRow(dataTable, i));
					}
					break;
				case "Greater than or equals to" :
					if(Double.valueOf((dataCol.getData()[i].toString())) >= compareNum) {
						dataRow.add(addRow(dataTable, i));
					}
					break;
				case "Not equals to" :
					if(Double.valueOf((dataCol.getData()[i].toString())) != compareNum) {
						dataRow.add(addRow(dataTable, i));
					}
					break;
				case "Equals to" :
					if(Double.valueOf((dataCol.getData()[i].toString())) == compareNum) {
						dataRow.add(addRow(dataTable, i));
					}
					break;
			}
		}
		
		if(dataRow.isEmpty()) {
			Main.alertUser("Fatal Error", "Will Cause An Empty DataTable", "Sorry, You are not able to create an empty data table");
			return;
		} 
		
		/*Ask user to input the DataName, which cannot be Empty and Duplicate*/
		while(true) {
			alertEnterName("Numeric");
			
			if(!firstDataSet.isEmpty()) {
				break;
			} 
			else if (cancelAlert == true) {
				return;
			}
			else {
				Main.alertUser("Empty DataSet Name", "DataSet Name cannot be Empty", "");
			}
		}

		dataTableNew = rowToTable(dataRow, colType, colName, firstDataSet);
		
	  	firstDataSet = "";
		
		if(type == "Replace") {
			Main.allDataSet.set(order, dataTableNew);
		} 
		else if (type == "Create New") {
			Main.allDataSet.add(dataTableNew);
		} 
	};
	
	
	/**
	 * Add the data that lies in same row into a Array
	 * 
	 * @param dataTable
	 *            - pass the DataTable into method for checking
	 *            
	 * @param index
	 *            - indicate the position of row 
	 * 
	 * @return Object[] reference
	 */
	private Object[] addRow(DataTable dataTable, int index) {
		Object[] row = new Object[dataTable.getNumCol()];
		
		for(int col = 0; col < dataTable.getNumCol(); col++) {
			row[col] = dataTable.getColByNum(col).getData()[index];
		}
		
		return row;
	}
	
	
	/**
	 * Translate number of rows into a DataTable object.								
	 * 	
	 * @param dataList
	 *            - pass in ArrayList that stores all rows data
	 *            
	 * @param typeName
	 *            - an array that store all columns type
	 * 
	 * @param colName
	 *            - an array that store all columns name
	 *
	 * @param tableName
	 *            - the name of DataTable object
	 *            
	 * @return DataColumn reference or null
	 *            
	 */
	private DataTable rowToTable(ArrayList<Object[]> dataList, String[] typeName, String[] colName, String tableName) {	
		
		DataTable dataTable = new DataTable(tableName);
		
			for(int col = 0; col < dataList.get(0).length; col ++) {
				Object[] data = new Object[dataList.size()];
				DataColumn dataCol;

				for(int row = 0; row < dataList.size(); row++) {
					data[row] = dataList.get(row)[col];
				}
				
				dataCol = new DataColumn(typeName[col], data);
				try {
					dataTable.addCol(colName[col], dataCol);
				} catch (DataTableException e) {
					e.printStackTrace();
				}
			}		
			return dataTable;
	}
	
	/**
	 * Create a pop up window for user to input the name of DataSets
	 * 
	 *  @param type
	 *           - determine the method is called by randomSplit or numericSplit method
	 * 
	 */
	private void alertEnterName(String type) {
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
		if(type.equals("Random")) {
			second.setPromptText("Second DataSet : ");
		}
		grid.add(new Label("First DataSet :"), 0, 0);
		grid.add(first, 1, 0);
		if(type.equals("Random")) {
			grid.add(new Label("Second DataSet :"), 0, 1);
			grid.add(second, 1, 1);
		}



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
		if(type.equals("Random")) {
		secondDataSet = result.get().getValue();
		}
	}
	
}
