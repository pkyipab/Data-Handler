package testing.comp3111;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.LineChartObj;


public class LineChartObjTest {
	
	@Test
	public void testGetMethod() throws DataTableException {
		DataColumn testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
		DataColumn testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
		DataTable testDataTable = new DataTable("DataTable Test");
		testDataTable.addCol("Test x", testDataColumn);
		testDataTable.addCol("Test y", testDataColumn2);
		LineChartObj testLineChartObj = new LineChartObj(testDataColumn, testDataColumn2, testDataTable, "Test x", "Test y", true);
	
		assertEquals("Test x", testLineChartObj.getXAxisName());
		assertEquals("Test y", testLineChartObj.getYAxisName());
		assertEquals(testDataColumn, testLineChartObj.getXAxisColumn());
		assertEquals(testDataColumn2, testLineChartObj.getYAxisColumn());
		assert(testLineChartObj.isAnimated());
		testLineChartObj = new LineChartObj(testDataColumn, testDataColumn2, testDataTable, "Test x", "Test y", false);
		assertFalse(testLineChartObj.isAnimated());


	}
	
	
}
