package suncertify.db;

import java.io.*;

/**
 * This class embodies the name of a field and the maximum width
 * that it may have.
 *
 * @author Peng Zhao
 * @version 1.0  11-Sep-1997
 */
public class FieldInfo implements Serializable {
  private String name;
  private int length;
  final static char sc = 'A';

  /**
   * This constructs an initialized FieldInfo object.
   *
   * @param String name - the name of the field.
   * @param int length - the length of the field.
   */
  public FieldInfo(String name, int length) {
    this.name = name;
    this.length = length;
  }

  /**
   * This method returns the name of the field.
   *
   * @returns String - the name of the field.
   */
  public String getName() {
    return name;
  }

  /**
   * This method returns the length of the field.
   *
   * @returns int - the length of the field.
   */
  public int getLength() {
    return length;
  }
}
