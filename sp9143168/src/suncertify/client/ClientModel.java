package suncertify.client;

import java.util.*;
import javax.swing.table.*;
import javax.swing.*;
import suncertify.db.*;

/**
 * This class implements AbstractTableModel and holds all the data needed
 * by the GUI.
 *
 * @version 1.0  05-Sep-2002
 */
public class ClientModel extends AbstractTableModel{
  private Service db = null;
  private String[] headers = null;
  private DataInfo[] displayRecords = null;
  private int selectedRow = -1;
  private DataInfo modifiedRecord = null;
  private String searchConditions = null;
  private String DBMode = "";
  private String REMOTE_DATABASE = "REMOTE";
  private String LOCAL_DATABASE = "LOCAL";
  private String NO_DATABASE = "";

  /**
    * This constructor gets the DB service according to the DB location parameters.
    * Then read the table headers for using later.
    *
    * @param parms The database location parameters.
    * @exception Exception if any error occurs.
    */
  public ClientModel(String[] parms) throws Exception {
      //Close the db if it still open
      if (db != null){
              //If the data service is in local, the command will close the file
              //If the data service is in network, the command will close the connection.
              closeDB();
      }
      //Get the data service according to it's parameters.
      db = ClientProxy.getService(parms);

      if (db == null){
           //throw new Exception("Cannot use the database.");
           DBMode = NO_DATABASE;
      } else if (db instanceof Data){
           DBMode = LOCAL_DATABASE + " [PATH: " + parms[0] + "]";
      } else {
           DBMode = REMOTE_DATABASE + " [SERVER: " + parms[0] + "  PORT: " + parms[1] + "]";
      }

      //Create table's headers
      FieldInfo[] fi = db.getFieldInfo();
      headers = new String[fi.length];
      for (int i=0; i< fi.length; i++){
          headers[i] = fi[i].getName();
      }
  }

  /**
   * Set the currently selected records' index.
   * @param row The selected row number.
   */
  public void setSelectedRow(int row){
      if ( row >= 0 && displayRecords != null && displayRecords.length >= row){
          selectedRow = row;
      } else {
          selectedRow = -1;
      }
  }

  /**
   * Get currently selected records.
   * @return DataInfo The selected records.
   */
  public DataInfo getSelectedRow(){
      if (displayRecords!=null && selectedRow != -1){
          return displayRecords[selectedRow];
      } else {
          return null;
      }
  }

  /**
   * Modify the JTable's column model by rewriting the headers.
   * @param tb The JTable which holds this TableModel.
   * @return TableColumnModel Modified TableColumnModel.
   */
  public TableColumnModel getTableColumnModel(JTable tb){
      if (tb == null){
          return null;
      }

      TableColumnModel colModel = tb.getTableHeader().getColumnModel();
      for(int i=0; i < headers.length; i++){
          TableColumn tc = colModel.getColumn(i);
          tc.setHeaderValue(headers[i]);
      }

      return colModel;
  }

  /**
   * Get alll headers.
   * @return String[] The table's header names.
   */
  public String[] getTableHeaders(){
      return headers;
  }

  /**
   * Get header by column index.
   * @param columnIndex Column index(starting from 0)
   * @return String The column name related to the column index.
   */
  public String getTableHeader(int columnIndex){
      if (columnIndex > 0 && headers != null && headers.length >= columnIndex){
          return headers[columnIndex];
      } else{
          return null;
      }
  }

  /**
   * Provide the data used by the UI.
   */
  public Object[] getColumnData(int col) throws Exception{
      String[] values;
      Set data = new TreeSet();
      for (int i=0; i < displayRecords.length; i++){
          values = displayRecords[i].getValues();
          data.add(values[col]);
      }
      return data.toArray();
  }

  /**
   * return the current dabase mode. Local or remote.
   */
  public String getDBMode(){
      return DBMode;
  }

  /**
   * Get the record set count.
   * @return int The count of the record set.
   */
  public int getRowCount(){
      return displayRecords.length;
  }

  /**
   * Get the count of the columns.
   * @return int The count of the columns of the table.
   */
  public int getColumnCount(){
      return headers.length;
  }

  /**
   * Get the value identified by the row number and the column number.
   * @param row Row number
   * @param col Column number
   * @return Object The value identified by the row number and the column number.
   */
  public Object getValueAt(int row, int col){
      String[] values = null;
      values = displayRecords[row].getValues();
      return values[col].trim();
  }

  /**
   * Book seats.
   * @param bookedSeats The seat number which is going to be booked.
   * @exception DatabaseException Thrown when database file could not be accessed.
   */
  public void setBookedSeats(int bookedSeats) throws Exception{
      int recordNumber = getSelectedRow().getRecordNumber();;
      try{
            db.lock(recordNumber);

            //Reread the selected record from db, because other people may have
            //made reservations.
            DataInfo serverRecord = db.getRecord(recordNumber);

            String[] values = serverRecord.getValues();
            int seatsIndex = values.length -1;

            int AvailableSeats =Integer.parseInt(values[seatsIndex].trim());
            if (AvailableSeats - bookedSeats >= 0) {
                values[seatsIndex] = String.valueOf(AvailableSeats - bookedSeats);
            } else {
                throw new DatabaseException("Cannot make the reservation!");
            }

            DataInfo modifiedRecord = new DataInfo(serverRecord.getRecordNumber()
                ,serverRecord.getFields(),values);

            db.modify(modifiedRecord);
            db.unlock(recordNumber);

            //Retrieve the data again
            setSearchConditions(getSearchConditions());

      }catch(Exception e){
            db.unlock(recordNumber);
            throw e;
      }
  }

  /**
   * Search the database by using the search condition parameter. If the paramter
   * is null or "", the function will return all records in the database. Otherwise
   * it will return the records exactly meet the search condition.
   * @param str The search conditions.
   * @exception DatabaseException Thrown when database file could not be accessed.
   */
  public void setSearchConditions(String str) throws DatabaseException {
      try{
          searchConditions = str;
          //Retrieve new record set from db according to the serach conditions
          if (searchConditions == null || searchConditions.equals("")) {
              displayRecords = db.criteriaFind("ALL");
          } else {
              displayRecords = db.criteriaFind(searchConditions);
          }
          //Notify all listener.
          this.fireTableDataChanged();
      } catch(DatabaseException ex){
          throw ex;
      }
  }

  /**
   * Get the current search conditions
   * @return String The current search conditions.
   */
  public String getSearchConditions(){
      return searchConditions;
  }

  /**
   * Close Database. In local database condition, it will close the db.db file.
   * In network database condtion, it will close the connection to the database.
   */
  public void closeDB(){
      db.close();
  }
}