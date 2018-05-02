package testing.comp3111;

import org.junit.jupiter.api.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataType;

/**
 * A sample DataColumn test case written using JUnit. It achieves 100% test
 * coverage on the DataColumn class
 * 
 * @author cspeter
 *
 */
class DataColumnTest {

	@Test
	void testCoverageEmptyDataColumnConstructor() {

		DataColumn dc = new DataColumn();
		assert (dc.getSize() == 0);

	}

	@Test
	void testCoverageNonEmptyDataColumnConstructor() {

		Number[] arr = new Integer[] { 1, 2, 3, 4, 5 };
		DataColumn dc = new DataColumn(DataType.TYPE_NUMBER, arr);
		assert (dc.getSize() == 5);

	}

	@Test
	void testCoverageGetDataAndType() {

		DataColumn dc = new DataColumn();
		assert (dc.getTypeName().equals(""));
		assert (dc.getData() == null);

	}
	
	@Test
	void testCoverageFillin() {
		
		Number[] arr = new Double[] { 1.1, 2.2, 3.0, null, 5.5 };
		DataColumn dc = new DataColumn(DataType.TYPE_NUMBER, arr);
		dc.fillin(7.7);
		
		assert ((double)dc.getData()[3] == 7.7);
	}
	
	@Test
	void testCoverageGetMeanAndMedian() {
		Integer[] arr = new Integer[] { 1, 2, 3, null, 5 };
		DataColumn dc = new DataColumn(DataType.TYPE_NUMBER, arr);
		
		Integer[] arr2 = new Integer[] { 1, 2, 3, 2, 5 };
		DataColumn dc2 = new DataColumn(DataType.TYPE_NUMBER, arr2);
		
		assert(dc.getMean() == 2.75);
		assert(dc.getMedian() == 2.5);
		assert(dc2.getMedian() == 2);
	}
	
	@Test
	void testCoverageTestEmptySpace() {
		Number[] arr = new Double[] { 1.1, 2.2, 3.0, null, 5.5 };
		DataColumn dc = new DataColumn(DataType.TYPE_NUMBER, arr);
		
		Integer[] arr2 = new Integer[] { 1, 2, 3, 2, 5 };
		DataColumn dc2 = new DataColumn(DataType.TYPE_NUMBER, arr2);
		
		assert(dc.hasNumericEmpty() == true);
		assert(dc2.hasNumericEmpty() == false);
	}
	
	@Test
	void testCoverageMaxAndMin() {
		Number[] arr = new Double[] { 2.2, 1.1, 3.0, 4.4, 5.5 };
		DataColumn dc = new DataColumn(DataType.TYPE_NUMBER, arr);
		
		assert(dc.getMax() == 5.5);
		assert(dc.getMin() == 1.1);
	}

}
