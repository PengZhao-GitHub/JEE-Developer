package suncertify.db;

/**
 * Signals that an database error has been reached.
 *
 * @author Peng Zhao
 * @version 1.0  05-Sep-2002
 */
public class DatabaseException extends Exception {
    public DatabaseException() {}

    public DatabaseException(String msg) {
        super(msg);
    }
}