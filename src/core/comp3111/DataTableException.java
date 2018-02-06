package core.comp3111;

/**
 * An implementation of DataTableException. It simply prints out an error
 * message with prefix "DataTableException"
 * 
 * Note: In Java, some classes are recommended to provide serial ID. It is out
 * of the scope of this course, so I suppress the warning message using the
 * syntax below
 * 
 * @author cspeter
 *
 */
@SuppressWarnings("serial")
public class DataTableException extends Exception {

	public DataTableException(String message) {
		super("DataTableException: " + message);
	}

}
