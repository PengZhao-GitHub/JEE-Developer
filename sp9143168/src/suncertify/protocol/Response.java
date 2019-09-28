package suncertify.protocol;

import java.io.*;

/**
 * This class embodies the description of a network response object from server to client.
 * It describes a status code which is defined in the NetProtocol Interface,
 * a return object and exception object if exception occurs.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class Response implements Serializable {
	private int status;
	private Object returnValue;
	private Exception exception;

        /**
         * This constructor creates a response object by defining it's status code.
         *
         * @param int status status code being defined in the NetProtocol Interface
         */
	public Response(int status){
		this.status = status;
	}

        /**
         * This constructor creates a response object by defining it's status code and
         * a return object.
         *
         * @param int status status code being defined in the NetProtocol Interface
         * @param Object returnValue value returned by the method being called.
         */
	public Response(int status, Object returnValue){
		this.status = status;
		this.returnValue = returnValue;
	}

        /**
         * This constructor creates a response object by defining it's status code and
         * a exception object.
         *
         * @param int status status code being defined in the NetProtocol Interface
         * @param Exception exception exception object throwed by the method being called.
         */
	public Response(int status, Exception exception){
		this.status = status;
		this.exception = exception;
	}

        /**
         * set the status code for this object
         *
         * @param int flag status code being defined in the NetProtocol Interface
         */
	public void setStatus(int flag){
		status = flag;
	}

        /**
         * return the status code of this object
         *
         * @return int status code
         */
	public int getStatus(){
		return status;
	}

        /**
         * set the return value for this object
         *
         * @param Object obj value returned by the method being called.
         */
	public void setReturnValue(Object obj){
		returnValue = obj;
	}

        /**
         * returns the return value of this object
         *
         * @return Object return value of this object
         */
	public Object getReturnValue(){
		return returnValue;
	}

        /**
         * set the exception object for this object
         *
         * @param Exception ex exception throwed by the method being called.
         */
	public void setException(Exception ex){
		exception = ex;
	}

        /**
         * returns the exception object of this object
         *
         * @return Object exception object of this object
         */
	public Object getException(){
		return exception;
	}

        /**
         * returns the description of this object
         *
         * @return String object's description.
         */
	public String toString(){
		String str = status + " ";
		if (returnValue != null){
			str = str + returnValue + " ";
		}

		if (exception != null){
			str = str + exception;
		}

		return str;
	}
}