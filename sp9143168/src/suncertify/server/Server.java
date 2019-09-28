package suncertify.server;

import java.io.*;
import java.net.*;
import java.util.*;
import suncertify.db.*;

/**
 * This class implements multi-thread server based on TCP.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class Server {

	static String SERVER_STARTED 	= "Server started";
	static String SERVER_STOPED	= "Server stopped";
	static String DATABASE_LOADED	= "Database loaded successfully";
	static String DATABASE_CLOSED	= "Database closed";

	public static void main(String[] args) throws IOException {
		// Choose a port outside of the range 1-1024:
		int PORT;
		Data db = null;
		String serverInfo;

		// Check parameters
                if (args.length != 2) {
			//Show usage
			System.out.println("usage: java SingleThreadServer port dataPath");
			return;
		} else {
			try {
				PORT = Integer.parseInt(args[0]);
			} catch(Exception e){
				System.out.println("You must provide a valid prot number (1-65535)");
				return;
			}

			try{
				db = new Data(args[1]);
				ServerUtility.writeLog("I", "INIT", DATABASE_LOADED);
			} catch(Exception e){
				System.out.println("Cannot load DB.");
                                return;
			}
		}

		// Create a server socket
		ServerSocket s = new ServerSocket(PORT);
		serverInfo = ServerUtility.getSocketInfo(s);

		ServerUtility.writeLog("I", "INIT", "Listening port " + s.getLocalPort());
		ServerUtility.writeLog("I", "INIT", SERVER_STARTED );


		while(true){
			try {
				//Blocks until a connection occurs:
				Socket socket = s.accept();

				//Start a new thread to handle the connection.
				ServerThread serverThread = new ServerThread(socket, db);
				serverThread.start();

			} catch(Exception e){
				ServerUtility.writeLog("E", serverInfo, e.toString());
			}
		}
	}
}

/**
 * This class implements a service thread to handle client's request.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
class ServerThread extends Thread {
        static String CLIENT_CONNECTED 		= "Client connected";
	static String CLIENT_DISCONNECTED 	= "Client disconnected";
	private Socket socket;
	private Data db;
	private String clientInfo;

       /**
         * This constructor creates a service thread.
         *
         * @param Socket socket client socket
         * @param Data db database loaded by the server
         */
	public ServerThread(Socket socket, Data db){
		this.socket = socket;
		this.db = db;
		clientInfo = ServerUtility.getSocketInfo(socket);
	}

	public void run() {
		//Thread starting...
		ServerUtility.writeLog("I",clientInfo, CLIENT_CONNECTED);

		try{
			//Start the service which handle the client's request.
			ServerStub stub = new ServerStub(socket,db);
			stub.startService();

		} catch(Exception e){
			ServerUtility.writeLog("E", clientInfo,  e.toString());
		} finally {  // Always close the client socket...
			try{
				socket.close();
				ServerUtility.writeLog("I", clientInfo, CLIENT_DISCONNECTED );
			} catch (Exception ex){
				ServerUtility.writeLog("E", clientInfo, ex.toString());
			}
		}
	}
}