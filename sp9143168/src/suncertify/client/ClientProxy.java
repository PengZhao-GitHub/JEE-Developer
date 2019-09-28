package suncertify.client;

import suncertify.db.*;
import suncertify.protocol.*;
import java.io.*;

/**
 * This class is a proxy to the database service. This class cannot be constructed
 * directly. The client must call the getService() function to get a reference to the
 * database service.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class ClientProxy extends NetProtocol implements Service {
	private ClientNet cn = null;

        /**
         * This constructor connects to an existing database given the server address
         * and the port.
         *
         * @param url The address of the database server to connect.
         * @param port The port of the database server to connect.
         * @exception Exception Thrown if cannot establish a connection to the
         *     dabase server.
         */
	private ClientProxy(String url, int port) throws Exception{
		cn = new ClientNet(url, port);
	}

        /**
         * Gets a reference to the db service based on database location parameters.
         *
         * @param args The database location parameters.
         * @return Service as a reference to the db service.
         * @exception Exception Thrown if database cannot be accessed.
         */
	public static Service getService(String[] args) throws Exception{
		Service ret;

		switch(args.length){
			case 2: ret = new ClientProxy(args[0],Integer.parseInt(args[1]));
				break;
			case 1: ret = new Data(args[0]);
				break;
			default: ret = null;
		}

		return ret;
	}

        /**
         * Gets the number of records stored in the database.
         */
    	public int getRecordCount(){
		try{
			Request req = new Request(GET_RECORD_COUNT);
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return ((Integer)res.getReturnValue()).intValue();
			} else {
				return 0;
			}

		} catch(Exception ex){
			return 0;
		}
	}

        /**
         * Gets a requested record from the database based on record number.
         *
         * @param recNum The number of the record to read (first record is 1).
         * @return DataInfo for the record or null if the record has been marked for
         *    deletion.
         * @exception DatabaseException Thrown if database file cannot be accessed.
         */
   	public DataInfo getRecord(int recNum) throws DatabaseException{
		try{
			Request req = new Request(GET_RECORD, new Integer(recNum));
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return (DataInfo)res.getReturnValue();
			} else {
				throw (DatabaseException)res.getException();
			}
		} catch(Exception ex){
			throw new DatabaseException(ex.toString());
		}
	}

        /**
         * This method returns a description of the database schema, as an
         * array of FieldInfo objects.
         *
         * @return FieldInfo[] The array of FieldInfo objects that comprise
         *          the schema to this database.
         */
	public FieldInfo[] getFieldInfo(){
		try{
			Request req = new Request(GET_FIELD_INFO);
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return (FieldInfo[])res.getReturnValue();
			} else {
				return null;
			}
		} catch(Exception ex){
			return null;
		}
	}

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
    	public DataInfo find(String toMatch) throws DatabaseException{
		try{
			Request req = new Request(FIND, toMatch);
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return (DataInfo)res.getReturnValue();
			} else {
				throw (DatabaseException)res.getException();
			}
		} catch(Exception ex){
			throw new DatabaseException(ex.toString());
		}
	}

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
    	public void  add(String [] newData) throws DatabaseException{
		try{
			Request req = new Request(ADD, newData);
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return;
			} else {
				throw (DatabaseException)res.getException();
			}
		} catch(Exception ex){
			throw new DatabaseException(ex.toString());
		}
	}

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
    	public void  modify(DataInfo newData) throws DatabaseException{
		try{
			Request req = new Request(MODIFY, newData);
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return;
			} else {
				throw (DatabaseException)res.getException();
			}
		} catch(Exception ex){
			throw new DatabaseException(ex.toString());
		}
	}

        /**
         * This method deletes the record referred to by the record
         * number in the DataInfo argument.
         *
         * @param DataInfo newData The record to delete.
         * @exception DatabaseException Thrown if database cannot be accessed.
         */
    	public void  delete(DataInfo toDelete) throws DatabaseException{
		try{
			Request req = new Request(DELETE, toDelete);
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return;
			} else {
				throw (DatabaseException)res.getException();
			}
		} catch(Exception ex){
			throw new DatabaseException(ex.toString());
		}

	}

        /**
         * This method closes the database, flushing any outstanding
         * writes at the same time. Any attempt to access the
         * database after this results in a IOException.
         */
    	public void  close(){
		try{
			if (cn != null){
				Request req = new Request(DISCONNECT);
				cn.sendObject(req);
				cn.disconnect();
			}
		} catch (Exception ex){

		}
	}

        /**
         * Lock the requested record. If the argument is -1, lock the whole
         * database. This method blocks until the lock succeeds. No timeouts
         * are defined for this.
         *
         * @param recno The record number to lock.
         * @exception IOException If the record position is invalid.
         */
    	public void  lock(int record) throws IOException{
		try{
			Request req = new Request(LOCK, new Integer(record));
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return;
			} else {
				throw (IOException)res.getException();
			}
		} catch(Exception ex){
			throw new IOException(ex.toString());
		}
	}

        /**
         * Unlock the requested record. Ignored if the caller does not have
         * a current lock on the requested record.
         */
    	public void  unlock(int record){
		try{
			Request req = new Request(UNLOCK, new Integer(record));
			Response res = (Response) cn.sendObject(req);
			return;
		} catch(Exception ex){
			return;
		}

	}

        /**
         * This method searches the database for entries matching the criteria supplied.
         *
         * @param criteria The search condition string
         * @return DataInfo[] All the records found in the database which match these criteria
         */
	public DataInfo[] criteriaFind(String criteria) throws DatabaseException{
		try{
			Request req = new Request(CRITERIA_FIND, criteria);
			Response res = (Response) cn.sendObject(req);
			if (res.getStatus() == STATUS_OK){
				return (DataInfo[])res.getReturnValue();
			} else {
				throw (DatabaseException)res.getException();
			}
		} catch(Exception ex){
			throw new DatabaseException(ex.toString());
		}
    	}
}