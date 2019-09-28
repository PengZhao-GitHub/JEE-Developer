package suncertify.protocol;

import java.io.*;

/**
 * This class embodies the description of a network request object from client to server.
 * It describes a method code which is defined in the NetProtocol Interface and
 * a parameter object.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class Request implements Serializable{
	private int method;
	private Object parameter;

        /**
         * This constructor creates a request object by defining it's method code and
         * a parameter object.
         *
         * @param int method method code being defined in the NetProtocol Interface
         * @param Object parameter a object as the parameter of the method being called.
         */
	public Request(int method, Object parameter){
		this.method = method;
		this.parameter = parameter;
	}

        /**
         * This constructor creates a request object by defining it's method code.
         *
         * @param int method method code being defined in the NetProtocol Interface
         */
	public Request(int method){
		this.method = method;
	}

        /**
         * set the method code for this object
         *
         * @param int flag method code being defined in the NetProtocol Interface
         */
	public void setMethod(int code){
		method = code;
	}

        /**
         * return the method code of this object
         *
         * @return int method code
         */
	public int getMethod(){
		return method;
	}

        /**
         * set the parameter object for this object
         *
         * @param Object obj object as the parameter
         */
	public void setParameter(Object obj){
		parameter = obj;
	}

        /**
         * return the parameter object for this object
         *
         * @return Object object as the parameter
         */
	public Object getParameter(){
		return parameter;
	}

        /**
         * returns the description of this object
         *
         * @return String object's description.
         */
	public String toString(){
		String str =  method + " ";
		if (parameter != null){
			str = str + parameter;
		}

		return str;
	}
}