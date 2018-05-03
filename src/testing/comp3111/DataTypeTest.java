package testing.comp3111;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.comp3111.DataType;

public class DataTypeTest {
	@Test
	public void testDatTypeTest() {
		DataType dataType = new DataType();
		assertEquals(dataType.TYPE_NUMBER, "java.lang.Number");
		assertEquals(dataType.TYPE_OBJECT, "java.lang.Object");
		assertEquals(dataType.TYPE_STRING, "java.lang.String");

	}

}
