package core.comp3111;

import java.util.Random;

/**
 * A helper class to illustrate how to generate data and store it into a
 * DataTable object
 * 
 * @author cspeter
 *
 */
public class SampleDataGenerator {

	/**
	 * A sample data generation. It illustrates how to use the DataTable class
	 * implemented in the base code
	 * 
	 * @return DataTable object
	 */
	public static DataTable generateSampleLineData() {

		DataTable t = new DataTable();

		// Sample: An array of integer
		Number[] xvalues = new Integer[] { 1, 2, 3, 4, 5 };
		DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);

		// Sample: Can also mixed Number types
		Number[] yvalues = new Number[] { 30.0, 25, (short) 16, 8.0, (byte) 22 };
		DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

		// Sample: A array of String
		String[] labels = new String[] { "P1", "P2", "P3", "P4", "P5" };
		DataColumn labelsCol = new DataColumn(DataType.TYPE_STRING, labels);

		try {

			t.addCol("X", xvaluesCol);
			t.addCol("Y", yvaluesCol);
			t.addCol("label", labelsCol);

		} catch (DataTableException e) {
			e.printStackTrace();

		}

		return t;
	}

	/**
	 * A sample data generation. It illustrates how to use the DataTable class
	 * implemented in the base code
	 * 
	 * @return DataTable object
	 */
	public static DataTable generateSampleLineDataV2() {
		DataTable t = new DataTable();

		final int num = 100;
		Number[] xvalues = new Integer[num]; // Integer is a subclass of Number
		Number[] yvalues = new Integer[num];
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			xvalues[i] = i; // int: 0..num-1
			yvalues[i] = r.nextInt(500) + 100; // Random int: 100...600
		}

		DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);
		DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);
		
		System.out.println(xvaluesCol.getTypeName());
		try {

			t.addCol("X", xvaluesCol);
			t.addCol("Y", yvaluesCol);

		} catch (DataTableException e) {
			e.printStackTrace();

		}

		return t;
	}
}
