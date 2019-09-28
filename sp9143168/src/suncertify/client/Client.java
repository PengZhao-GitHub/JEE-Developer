package suncertify.client;

import javax.swing.UIManager;
import java.awt.*;

/**
 * This class is the entry point of the client program.
 *
 * @version 1.0  05-Sep-2002
 */
public class Client {
  boolean packFrame = false;

  /**
    * This constructor creates the main user interface and display it in the
    * center of the screen.
    *
    * @param parm The database location parameters
    */
  public Client(String[] parm) {
    ClientFrame frame = new ClientFrame(parm);

    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Let the windows display at the center of the screen.
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }

  /**
   * The client program's entry point.
   *
   * @param String[] args The Location of the database.
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    //Checking parameters
    if (args.length > 2){
        printUsage();
        System.exit(-1);
    }

    //Start to work...
    new Client(args);
  }

  /**
   * Print the client's command usage.
   */
  private static void printUsage(){
     System.out.println("Usage 1:");
     System.out.println("java suncertify.client.ClientApp");
     System.out.println("Usage 2:");
     System.out.println("java suncertify.client.ClientApp local_path");
     System.out.println("Usage 3:");
     System.out.println("java suncertify.client.ClientApp server_address server_port\n");
     System.out.println("Examples:");
     System.out.println("java suncertify.client.ClientApp e:\\db.db");
     System.out.println("java suncertify.client.ClientApp 192.168.12.2 2000");

  }
}