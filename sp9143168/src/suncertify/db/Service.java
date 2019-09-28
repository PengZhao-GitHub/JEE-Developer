package suncertify.db;

import java.io.*;

/**
 * The Service interface provides a set of database functions as the database API.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public interface Service{

    /**
    * Gets the number of records stored in the database.
    */
    public int getRecordCount();

    /**
    * Gets a requested record from the database based on record number.
    *
    * @param recNum The number of the record to read (first record is 1).
    * @return DataInfo for the record or null if the record has been marked for
    *    deletion.
    * @exception DatabaseException Thrown if database file cannot be accessed.
    */
    public DataInfo getRecord(int recNum) throws DatabaseException;

    /**
    * This method returns a description of the database schema, as an
    * array of FieldInfo objects.
    *
    * @return FieldInfo[] The array of FieldInfo objects that comprise
    *          the schema to this database.
    */
    public FieldInfo[] getFieldInfo();

    /**
    * This method searches the database for an entry which has a first
    * field which exactly matches the string supplied. If the required
    * record cannot be found, this method returns null. For this
    * assignment, the key field is the record number field.
    *
    * @param toMatch The key field value to match upon for
    *           a successful find.
    * @return DataInfo The matching record.
    * @exception DatabaseException Thrown when database file could not be accessed.
    */
    public DataInfo find(String toMatch) throws DatabaseException;

    /**
    * This method adds a new record to the database. The array of
    * strings must have exactly the same number of elements as the
    * field count of the database schema, otherwise a RuntimeException
    * is issued. The first field, the key, must be unique in the
    * database or a RuntimeException is thrown.
    *
    * @param newData The elements of the record to add.
    * @exception DatabaseException Attempted to add a duplicate key or
    *        database file could not be accessed.
    */
    public void add(String [] newData) throws DatabaseException;

    /**
    * This method updates the record specified by the record number
    * field in the DataInfo argument. The fields are all modified
    * to reflect the values in that argument. If the key field
    * specified in the argument matches any record other than the
    * one indicated by the record number of the argument, then a
    * RuntimeException is thrown.
    *
    * @param newData The updated record to modify.
    * @exception DatabaseException Thrown if attempting to add a duplicate
    *       key.
    */
    public void modify(DataInfo newData) throws DatabaseException;

    /**
    * This method deletes the record referred to by the record
    * number in the DataInfo argument.
    *
    * @param DataInfo newData The record to delete.
    * @exception DatabaseException Thrown if database cannot be accessed.
    */
    public void delete(DataInfo toDelete) throws DatabaseException;

    /**
    * This method closes the database, flushing any outstanding
    * writes at the same time. Any attempt to access the
    * database after this results in a IOException.
    */
    public void close();

    /**
    * Lock the requested record. If the argument is -1, lock the whole
    * database. This method blocks until the lock succeeds. No timeouts
    * are defined for this.
    *
    * @param recno The record number to lock.
    * @exception IOException If the record position is invalid.
    */
    public void lock(int record) throws IOException;

    /**
    * Unlock the requested record. Ignored if the caller does not have
    * a current lock on the requested record.
    */
    public void unlock(int record);

    /**
     * This method searches the database for entries matching the criteria supplied.
     *
     * @param criteria The search condition string
     * @return DataInfo[] All the records found in the database which match these criteria
     */
    public DataInfo[] criteriaFind(String criteria) throws DatabaseException;
}