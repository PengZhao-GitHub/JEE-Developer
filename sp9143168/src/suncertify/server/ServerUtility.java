package suncertify.server;

import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;

/**
 * This class implements common functions used by the server side object.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class ServerUtility {

      /**
       * writes log information.
       *
       * @param String msgType message type
       * @param String component source description
       * @param String msg message to be written
       */
	public static void writeLog(String msgType, String component, String msg){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          	String dateTime = sdf.format(new Date());
		// Time:<MsgType><Component> Description
		System.out.println(dateTime  + " <" + msgType + ">" + " <" + component + "> " + msg);
	}

       /**
        * Get the description of the socket
        *
        * @param Object s socket or server socket object
        * @return String  Object description
        */
	public static String getSocketInfo(Object s){
		String ret = null;
		if (s != null){
			if (s instanceof ServerSocket){
				ServerSocket server = (ServerSocket)s;
				ret =  server.getInetAddress().getHostAddress() + ":" + server.getLocalPort();
			} else if (s instanceof Socket){
				Socket client = (Socket) s;
				ret = client.getInetAddress().getHostAddress() + ":" + client.getPort();
			}
		}
		return ret;
	}
}