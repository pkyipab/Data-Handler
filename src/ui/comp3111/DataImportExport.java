package ui.comp3111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/* TODO 
 * 
 * 
 *  1) Add pop-up window for user to choose fill in mean / zero / median
 * 	
 * 
 * 
 * 
 * 
 *  2) Mix Integer and Double Type -> NUMBER
 * 
 * 
 * 
 * 
 */



public class DataImportExport {
	
	public void importData(Stage s, ObservableList<VBox> viewDataSet, ObservableList<VBox> dataSetHandle, ObservableList<VBox> dataFilterDataSet, Map<VBox, DataTable> map) {
			FileChooser fc;
			BufferedReader br = null;
			String line = "";
			ArrayList<String[]> row = new ArrayList<String[]>();
			
			DataTable dataTable = new DataTable();

			VBox dataVBox = new VBox();
			VBox dataVBoxHandle = new VBox();
			VBox dataFilterVBox = new VBox();

			
			 try {
				 	fc = new FileChooser();
				 	fc.setTitle("Load CSV file");
				 	fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv*"));
				 	
				 	File file = fc.showOpenDialog(s);
				 	
					if(file != null) {
					 		
			            br = new BufferedReader(new FileReader(file));
			         
			            
			           //Split the file row by row
			            String[] title = br.readLine().split(",");
			            row.add(title);
			            
			            while ((line = br.readLine()) != null) {
			                String[] dataRow = line.split(",");
			                if(dataRow.length > row.get(0).length) {
			                	throw new DataTableException(" Missing The Column Title ! ");
			                } 
			                row.add(dataRow);
			            }
			            
			            //Transfer from ArrayList into DataColumn
			            for(int colNum = 0; colNum < row.get(0).length; colNum ++) {
			            	ArrayList<String> strData = new ArrayList<String>();
			            	ArrayList<Integer> intData;
			            	ArrayList<Double> doubleData;
			            	DataColumn dataCol;
			            	String type = "";
			            	
			            	for(int rowNum = 1; rowNum < row.size(); rowNum ++) {
			            		if(row.get(rowNum).length <= colNum) {
			            			strData.add("");
			            		} else {
			            			strData.add(row.get(rowNum)[colNum]);
			            		}
			            	}
			            	
			            	type = dataChecking(strData);
			            	
			            	switch(type) {
			            		case "Integer": 
			            			intData = strToInt(strData);
			            			dataCol = new DataColumn(DataType.TYPE_NUMBER, intData.toArray());
			            			dataTable.addCol(row.get(0)[colNum], dataCol);
			            			break;
			            			
			            		case "Double": 
			            			doubleData = strToDouble(strData);
			            			dataCol = new DataColumn(DataType.TYPE_NUMBER, doubleData.toArray());
			            			dataTable.addCol(row.get(0)[colNum], dataCol);
			            			break;
			            			
			            		default:
			            			dataCol = new DataColumn(DataType.TYPE_STRING, strData.toArray());
			            			dataTable.addCol(row.get(0)[colNum], dataCol);
			            			break;
			            	}
			            	
			            }
			           
			            Main.allDataSet.add(dataTable);

			            map.put(dataVBox, dataTable);
			            
			            dataVBox.getChildren().addAll(new Label("DataSet " + (Main.allDataSet.size()) +  " : " + file.getName() +  ""));
			            viewDataSet.add(dataVBox);

			            dataVBoxHandle.getChildren().addAll(new Label("DataSet " + (Main.allDataSet.size()) +  " : " + file.getName() +  ""));
			            dataSetHandle.add(dataVBoxHandle);
			            
			            dataFilterVBox.getChildren().addAll(new Label("DataSet " + (Main.allDataSet.size()) +  " : " + file.getName() +  ""));
			            dataFilterDataSet.add(dataFilterVBox);
			            
			            System.out.println("[ Import Success ]");
					}
		        }  catch (IOException ex) {
		            ex.printStackTrace();
		        } catch (DataTableException e1) {
					Main.alertUser("Error", "DataTable Exception", e1.getLocalizedMessage());
				} finally {
		            if (br != null) {
		                try {
		                    br.close();
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		            }
		       }
	}
	
	public void exportData(Stage stage, ListView<VBox> dataSet, Map<VBox, DataTable> map) {
		
		if(!dataSet.getSelectionModel().isEmpty()) {
			
			DataTable selectedDataTable = map.get(dataSet.getSelectionModel().getSelectedItem());
			FileChooser fileChooser = new FileChooser();
			  
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            
            //Show save file dialog
            File file = fileChooser.showSaveDialog(stage);
            
            if(selectedDataTable != null && file != null){
            	try {
                    FileWriter fileWriter = new FileWriter(file);
                    
                    for(int sizeOfTable = 0; sizeOfTable < selectedDataTable.getNumCol(); sizeOfTable ++) {
                    
                    	if(sizeOfTable == selectedDataTable.getNumCol() - 1) {
                    		fileWriter.write("" + selectedDataTable.getColName(sizeOfTable));
                    	} else {
                    	fileWriter.write("" + selectedDataTable.getColName(sizeOfTable));
                    	fileWriter.write(",");
                    	}
                    
                    }
                    
                    fileWriter.write("\n");
                    
                    for(int rowNum = 0; rowNum < selectedDataTable.getNumRow(); rowNum++) {
                    	for(int colNum = 0; colNum < selectedDataTable.getNumCol(); colNum++) {
                    		
                    		if(colNum == selectedDataTable.getNumCol() - 1) {
                    			fileWriter.write(selectedDataTable.getColByNum(colNum).getData()[rowNum] + "");
                    		} else {
                    		fileWriter.write(selectedDataTable.getColByNum(colNum).getData()[rowNum] + "");
                    		fileWriter.write(",");
                    		}
                    	}
                    	fileWriter.write("\n");
                    	
                    }
                    
                    fileWriter.flush();
                    fileWriter.close();
                    System.out.println("[ Export Success ]");
                } catch (IOException ex) {
                    ex.printStackTrace();;
                } 
            } 
		} 
	}
	
	private ArrayList<Double> strToDouble(ArrayList<String> arrList) {
		ArrayList<Double> doubleList = new ArrayList<Double>();
		
		for(int i = 0; i < arrList.size(); i++) {
			if(arrList.get(i).isEmpty()) {
				doubleList.add(0.0);
			} else {
				doubleList.add(Double.parseDouble(arrList.get(i)));
			}
		}
		
		return doubleList;
	}
	
	private ArrayList<Integer> strToInt(ArrayList<String> arrList) {
		
		ArrayList<Integer> intList = new ArrayList<Integer>();
	
		for(int i = 0; i < arrList.size(); i++) {
			if(arrList.get(i).isEmpty()) {
				intList.add(0);
			} else {
				intList.add(Integer.parseInt(arrList.get(i)));
			}
		}
		
		return intList;
	}
	
	private String dataChecking(ArrayList<String> arrList) throws DataTableException{
		String type = "null";
		
		for(int i = 0; i < arrList.size(); i++) {
			if(!arrList.get(i).isEmpty() && type == "null") {
				type = typeChecking(arrList.get(i));
			}
			else if (!arrList.get(i).isEmpty() && type != "null") {
				if(typeChecking(arrList.get(i)) != type) {
					throw new DataTableException(" Unmatch Datatype !");
				}
			}
		}
		
		return type;
	}
	
	private String typeChecking(String str) {
		
		if(str.isEmpty()) {
			return "empty";
		}
		else if(isDouble(str) && isInteger(str)) {
			return "Integer";
		} else if(isDouble(str) && !isInteger(str)) {
			return "Double";
		} else {
			return "String";
		}
		
	}
	
	private boolean isInteger(String input)
	{
	   try
	   {
	      Integer.parseInt( input );
	      return true;
	   }
	   catch( Exception e )
	   {
	      return false;
	   }
	}
	
	private boolean isDouble(String input) {
		   try
		   {
		      Double.parseDouble( input );
		      return true;
		   }
		   catch( Exception e )
		   {
		      return false;
		   }
	}
	
}
