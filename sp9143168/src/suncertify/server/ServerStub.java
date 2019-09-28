package suncertify.server;

import java.io.*;
import java.net.*;
import suncertify.db.*;
import suncertify.protocol.*;

/**
 * This class implements server stub on behalf of the client.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class ServerStub extends NetProtocol{
	private Data db;
	private Socket socket;
	private ObjectInputStream in;
	private	ObjectOutputStream out;
	private String clientInfo;

        /**
         * This constructor creates a server stub.
         *
         * @param Socket socket client socket
         * @param Data db database loaded by the server
         * @exception Exception if cannot create the server stub object
         */
	public ServerStub(Socket s, Data db) throws Exception{
		this.socket = s;
		this.db = db;
		clientInfo = ServerUtility.getSocketInfo(socket);
	}

        /**
         * Get the client's request and process it then give response to the client.
         *
         * @exception Exception if network error occurs
         */
	public void startService() throws Exception  {
		while(true){
			in  =  new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

			Request rcv = (Request) in.readObject();
                        String parms = rcv.getParameter() == null? "" : rcv.getParameter().toString();
			ServerUtility.writeLog("I", clientInfo, "Request command: " + getCmdDescription(rcv.getMethod()) + "(" + parms + ")");

			Response res = null;

                        String logStr = "";
			try{
				switch (rcv.getMethod()){
                                        case DISCONNECT:
                                             //Client request to disconnet. So stop the loop.
                                             return;
					case GET_FIELD_INFO:
						FieldInfo[] fi = db.getFieldInfo();
						res = new Response(STATUS_OK, fi);
						break;
					case GET_RECORD_COUNT:
						int recordCount = db.getRecordCount();
						res = new Response(STATUS_OK,new Integer(recordCount));
						break;
					case GET_RECORD:
						DataInfo records = db.getRecord(((Integer)rcv.getParameter()).intValue());
						res = new Response(STATUS_OK,records);
						break;
					case ADD:
						db.add((String[])rcv.getParameter());
						res = new Response(STATUS_OK);
						break;
					case FIND:
						DataInfo record = db.find((String)rcv.getParameter());
						res = new Response(STATUS_OK,record);
						break;
					case MODIFY:
						db.modify((DataInfo)rcv.getParameter());
						res = new Response(STATUS_OK);
						break;
					case DELETE:
						db.delete((DataInfo)rcv.getParameter());
						res = new Response(STATUS_OK);
						break;
					case CLOSE:
						db.close();
						res = new Response(STATUS_OK);
						break;
					case LOCK:
						db.lock(((Integer)rcv.getParameter()).intValue());
						res = new Response(STATUS_OK);
						break;
					case UNLOCK:
						db.unlock(((Integer)rcv.getParameter()).intValue());
						res = new Response(STATUS_OK);
						break;
					case CRITERIA_FIND:
						DataInfo[] recordSet = db.criteriaFind((String)rcv.getParameter());
						res = new Response(STATUS_OK,recordSet);
						break;
				}
                                ServerUtility.writeLog("I", clientInfo, "Response status: " + getStatusDescription(res.getStatus()));
			} catch (Exception e){
				res = new Response(STATUS_ERROR,e);
                                ServerUtility.writeLog("E", clientInfo, "Response status: " + getStatusDescription(res.getStatus()) + " " + e.toString());
			}

			out.writeObject(res);
		}
	}
}