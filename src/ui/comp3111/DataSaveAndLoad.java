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


public class DataSaveAndLoad {
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
		} 
		catch (IOException e) {
		}		
	}
	
	public void loadData(File file) {
		try{
			FileInputStream fis = new FileInputStream(file.getPath());
            ObjectInputStream ois = new ObjectInputStream(fis);
            Main.allDataSet = (ArrayList<DataTable>) ois.readObject();
            ois.close();
		}		
		catch (Exception e){
		}
		
	}
}
