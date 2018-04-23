package testing.comp3111;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;


public class DataTableTest {

	DataColumn testDataColumn;
	@BeforeEach
	void init() {
		testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
	}
	
	@Test
	void testGetFileName() {
		DataTable dataTable = new DataTable("Test");
		assertEquals("Test", dataTable.getFileName());
	}
	
	@Test
	void testGetNumRow_Empty() {
		DataTable dataTable = new DataTable("");
		assertEquals(0, dataTable.getNumRow());
	}
	
	@Test
	void testGetNumRow_NonEmpty() throws DataTableException {
		DataTable dataTable = new DataTable("");
		dataTable.addCol("testColumn", new DataColumn());
		
		assertEquals(0, dataTable.getNumRow());
	}
	
	@Test
	void testGetNumCol_NonEmpty() throws DataTableException{
		DataTable dataTable = new DataTable("");
		dataTable.addCol("testNumberColumn", testDataColumn);
		
		int numCol = dataTable.getNumCol();
		
		assertEquals(1, numCol);
	}
	
	@Test 
	void testGetCol_NonExistent() throws DataTableException{
		DataTable dataTable = new DataTable("");
		dataTable.addCol("testNumberColumn", testDataColumn);
		
		DataColumn dataColumn = dataTable.getCol("testStringColumn");
		
		assertEquals(null, dataColumn);
	}
	
	@Test
	void testAddCol_AlreadyExists() throws DataTableException{
		DataTable dataTable = new DataTable("");
		dataTable.addCol("testNumberColumn", testDataColumn);
		
		assertThrows(DataTableException.class, ()-> dataTable.addCol("testNumberColumn", new DataColumn()));
	}

	@Test
	void testAddCol_NumRow() throws DataTableException{
		DataTable dataTable = new DataTable("");
		DataColumn testDataColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});

		dataTable.addCol("testNumberColumn", testDataColumn);
		dataTable.addCol("testNumberRow", testDataColumn_2);

		int numCol = dataTable.getNumCol();

		
		assertEquals(2, numCol);
	}
	@Test
	void testAddCol_WrongNumRow() throws DataTableException{
		DataTable dataTable = new DataTable("");
		DataColumn testDataColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3, 4});

		dataTable.addCol("testNumberColumn", testDataColumn);
		
		assertThrows(DataTableException.class, ()-> dataTable.addCol("testWrongNumberRow", testDataColumn_2));		
	}
	
	@Test
	void testRemoveCol_ExistColName() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		dataTable.addCol("TestColName", testDataColumn);
		
		dataTable.removeCol("TestColName");
		
		assertEquals(0, dataTable.getNumRow());
	}

	@Test
	void testRemoveCol_WorngColName() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		dataTable.addCol("TestColName", testDataColumn);
				
		assertThrows(DataTableException.class, ()->dataTable.removeCol("TestColName_Worng"));		
	}
	
	@Test
	void testGetColByNum_Null() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		dataTable.addCol("TestGetColNum", testDataColumn);
		assertEquals(null, dataTable.getColByNum(2));
	}

	@Test
	void testGetColByNum_() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		dataTable.addCol("TestGetColNum", testDataColumn);
		assertEquals(null, dataTable.getColByNum(2));
	}
	
	@Test 
	void TestGetColByNum_GetCorrectNum() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		dataTable.addCol("TestGetColNum", testDataColumn);
		DataColumn testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
		DataColumn testDataColumn3 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
		dataTable.addCol("TestGetColNum2", testDataColumn2);
		dataTable.addCol("TestGetColNum3", testDataColumn3);
		assertEquals(testDataColumn3, dataTable.getColByNum(2));
	}

	@Test
	void testGetColName_Null() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		dataTable.addCol("TestGetColNum", testDataColumn);
		assertEquals(null, dataTable.getColName(1));
	}
	
	@Test 
	void TestGetColName_GetCorrectOrder() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		dataTable.addCol("TestGetColNum", testDataColumn);
		DataColumn testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
		DataColumn testDataColumn3 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
		dataTable.addCol("TestGetColNum2", testDataColumn2);
		dataTable.addCol("TestGetColNum3", testDataColumn3);
		assertEquals("TestGetColNum3", dataTable.getColName(2));
	}
}
