package suncertify.protocol;

/**
 * The NetProtocol class provides a set of codes for indicating the request
 * commands from the clients.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class NetProtocol {
        /* Status code */
	public static final int STATUS_OK 		= 100;
	public static final int STATUS_ERROR 		= 101;

        /* Database command code */
	public static final int GET_FIELD_INFO 	        = 200;
	public static final int GET_RECORD_COUNT	= 201;
	public static final int GET_RECORD 		= 202;
	public static final int ADD 			= 203;
	public static final int FIND			= 204;
	public static final int MODIFY 		        = 205;
	public static final int DELETE 		        = 206;
	public static final int CLOSE 		        = 207;
	public static final int LOCK 			= 208;
	public static final int UNLOCK 		        = 209;
	public static final int CRITERIA_FIND		= 210;

        /* Network command code */
	public static final int DISCONNECT		= 300;

        /**
         * Get the command code's description.
         *
         * @param cmd Command code.
         * @return String Command's description.
         */
        public String getCmdDescription(int cmd){
            String retStr = "";
            switch(cmd){
                   case GET_FIELD_INFO:
                            retStr = "getFieldInfo";
                            break;
                    case GET_RECORD_COUNT:
                            retStr = "getRecordCount";
                            break;
                    case GET_RECORD:
                            retStr = "getRecord";
                            break;
                    case ADD:
                            retStr = "add";
                            break;
                    case FIND:
                            retStr = "find";
                            break;
                    case MODIFY:
                            retStr = "modify";
                            break;
                    case DELETE:
                            retStr = "delete";
                            break;
                    case CLOSE:
                            retStr = "close";
                            break;
                    case LOCK:
                            retStr = "lock";
                            break;
                    case UNLOCK:
                            retStr = "unlock";
                            break;
                    case CRITERIA_FIND:
                            retStr = "criteriaFind";
                            break;
                    case DISCONNECT:
                            retStr = "disconnect";
                            break;
                    default: retStr = "not defined";
              }

              return retStr;
        }

        /**
         * Get the status code's description.
         *
         * @param status Status code.
         * @return String Status description.
         */
        public String getStatusDescription(int status){
             String retStr;
             switch(status){
                   case STATUS_OK:
                            retStr = "ok";
                            break;
                    case STATUS_ERROR:
                            retStr = "error";
                            break;
                    default: retStr = "not defined";
             }

             return retStr;
        }
}