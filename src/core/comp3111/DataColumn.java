package core.comp3111;

public class DataColumn {

	public DataColumn() {
		data = null;
		typeName = "";
	}

	public DataColumn(String typeName, Object[] values) {
		set(typeName, values);
	}

	public void set(String typeName, Object[] values) {
		this.typeName = typeName;
		data = values;
	}

	public Object[] getData() {
		return data;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getSize() {
		if (data == null)
			return 0;
		return data.length;
	}

	private Object[] data;
	private String typeName;

}
