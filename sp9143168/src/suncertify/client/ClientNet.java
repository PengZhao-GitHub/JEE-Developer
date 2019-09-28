package suncertify.client;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * This class implements the client's network functions
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class ClientNet{

	private InetAddress addr;
	private int port;
	private Socket socket;

        /**
         * Create the client socket and establish the connection to server.
         *
         * @param String url Server's address
         * @param int port Server's port
         * @exception Exception if network error occurs
         */
	public ClientNet(String url, int port) throws Exception{
		addr = InetAddress.getByName(url);
		this.port = port;
		socket = new Socket(addr, port);
	}

        /**
         * Send serializable object to the server and get the response serializable
         * object from the server.
         *
         * @param Object obj Server's address
         * @exception Exception if network error occurs
         */
	public Object sendObject(Object obj) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		//Send object to server
		out.writeObject(obj);

		//Receive object from server
		Object res = in.readObject();

		return res;

	}

        /**
         * Close the connection.
         *
         * @exception Exception if network error occurs
         */
	public void disconnect() throws Exception {
		if (socket != null){
			socket.close();
		}
	}
}