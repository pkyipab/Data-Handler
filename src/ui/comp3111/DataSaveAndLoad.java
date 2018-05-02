package ui.comp3111;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import core.comp3111.DataTable;

/**
 * DataSaveAndLoad - A helper class to handle the data saving and loading
 * 
 * @author tmtam
 *
 */

public class DataSaveAndLoad {
	/**
	 *  Save Data Method - save allDataSet in file .comp3111
	 * 
	 * 	@param file
	 * 			- the save file direction 	 
	 * 
	 */
	public void saveData(File file) {
		try{
			FileOutputStream  fos = new FileOutputStream(file.getPath());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            System.out.println(file.getPath());
            oos.writeObject(Main.allDataSet);
            oos.flush();
            oos.close();
		}		
		catch (FileNotFoundException e){
			Main.alertUser("Save Error", "FileNotFoundException", "Return");
		} 
		catch (IOException e) {
			Main.alertUser("Save Error", "IOException", "Return");
		}		
	}

	/**
	 *  load Data Method - read a .comp3111 and write it to allDataSet
	 * 
	 * 	@param file
	 * 			- the input file 	 
	 * 
	 */
	public void loadData(File file) {
		try{
			FileInputStream fis = new FileInputStream(file.getPath());
            ObjectInputStream ois = new ObjectInputStream(fis);
            Main.allDataSet = (ArrayList<DataTable>) ois.readObject();
            ois.close();
		}		
		catch (Exception e){
			Main.alertUser("Load Error", "Exception", "Return");
		}		
	}
}
