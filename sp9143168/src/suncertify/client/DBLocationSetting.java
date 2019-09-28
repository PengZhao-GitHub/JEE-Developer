package suncertify.client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * This class lets the user to be able to set the database location.
 *
 * @version 1.1  05-Sep-2002
 */
public class DBLocationSetting extends JDialog {
  private static String DATABASE_LOCATION_FILE = "DBSetting";
  private static String PORT_NUMBER_ERROR = "The port must be integer!";
  private static String INPUT_ERROR = "Input Error";

  protected String localPath = "";
  protected String remoteServer = "";
  protected String remotePort = "";

  JLabel jLabel1 = new JLabel();
  JTextField jTextFieldLocal = new JTextField();
  JButton jButtonLocal = new JButton();
  JLabel jLabel2 = new JLabel();
  JTextField jTextFieldRemote = new JTextField();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JTextField jTextFieldPort = new JTextField();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();

  public DBLocationSetting(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public DBLocationSetting() {
    this(null, "", false);

  }

  public DBLocationSetting(Frame frame,String[] parm) {
    this(frame, "", true);
    if (parm != null && parm.length > 0) {
        switch(parm.length){
           case 1: localPath = parm[0];
                   break;
           case 2: remoteServer = parm[0];
                   remotePort = parm[1];
                   break;
           case 3: localPath = parm[0];
                   remoteServer = parm[1];
                   remotePort = parm[2];
        }
    } else {
        try {
                FileInputStream in = new FileInputStream(DATABASE_LOCATION_FILE);
                ObjectInputStream objStream = new ObjectInputStream(in);
                localPath = (String)objStream.readObject();
                remoteServer = (String)objStream.readObject();
                remotePort = (String)objStream.readObject();
        } catch (Exception ex){
                System.out.println(ex);
        }
    }

    jTextFieldLocal.setText(localPath);
    jTextFieldRemote.setText(remoteServer);
    jTextFieldPort.setText(remotePort);
  }

  void jbInit() throws Exception {
    jLabel1.setText("Local Database");
    jLabel1.setBounds(new Rectangle(20, 11, 115, 23));
    this.getContentPane().setLayout(null);
    jTextFieldLocal.setBounds(new Rectangle(21, 43, 255, 22));
    jButtonLocal.setToolTipText("");
    jButtonLocal.setSelected(true);

    jButtonLocal.setText("File");
    jButtonLocal.setMnemonic('F');
    jButtonLocal.setBounds(new Rectangle(285, 39, 56, 30));
    jButtonLocal.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonLocal_actionPerformed(e);
      }
    });
    jLabel2.setText("Remote Database");
    jLabel2.setBounds(new Rectangle(20, 72, 112, 23));
    jTextFieldRemote.setToolTipText("");
    jTextFieldRemote.setBounds(new Rectangle(68, 103, 173, 22));
    jLabel3.setText("Server");
    jLabel3.setBounds(new Rectangle(20, 104, 47, 21));
    jLabel4.setText("Port");
    jLabel4.setBounds(new Rectangle(254, 104, 30, 21));
    jTextFieldPort.setBounds(new Rectangle(287, 103, 54, 22));
    jButtonOK.setText("OK");
    jButtonOK.setMnemonic('O');
    jButtonOK.setBounds(new Rectangle(190, 156, 74, 30));
    jButtonOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonOK_actionPerformed(e);
      }
    });
    jButtonCancel.setText("Cancel");
    jButtonCancel.setMnemonic('C');
    jButtonCancel.setBounds(new Rectangle(267, 156, 74, 30));
    jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonCancel_actionPerformed(e);
      }
    });

    this.setTitle("Database Location Setup");
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jTextFieldRemote, null);
    this.getContentPane().add(jTextFieldPort, null);
    this.getContentPane().add(jButtonLocal, null);
    this.getContentPane().add(jButtonOK, null);
    this.getContentPane().add(jButtonCancel, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jTextFieldLocal, null);

    this.setResizable(false);
    this.setSize(new Dimension(373, 252));
  }

  void jButtonLocal_actionPerformed(ActionEvent e) {
     JFileChooser file = new JFileChooser();
     file.showOpenDialog(this);
     try{
        jTextFieldLocal.setText(file.getSelectedFile().getAbsolutePath());
     } catch(Exception ex){
        //do nothing when error occurs
     }
  }

  void jButtonOK_actionPerformed(ActionEvent e) {
      //Data check
      String port = jTextFieldPort.getText();
      try{
          if ( port != null && port.length() != 0){
              Integer.parseInt(jTextFieldPort.getText());
          }
      }catch(Exception ex){
          JOptionPane.showConfirmDialog(this,PORT_NUMBER_ERROR,INPUT_ERROR,JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE );
          return;
      }

      localPath = jTextFieldLocal.getText();
      remoteServer = jTextFieldRemote.getText();
      remotePort = jTextFieldPort.getText();

      //Write the current database connection setting to file
      try {
          FileOutputStream out = new FileOutputStream(DATABASE_LOCATION_FILE);
          ObjectOutputStream objStream = new ObjectOutputStream(out);
          objStream.writeObject(this.localPath);
          objStream.writeObject(this.remoteServer);
          objStream.writeObject(this.remotePort);
          objStream.flush();
      } catch (Exception ex){
          System.out.println("ex");
      }
      this.hide();
  }

  void jButtonCancel_actionPerformed(ActionEvent e) {
      jTextFieldLocal.setText(localPath);
      jTextFieldRemote.setText(remoteServer);
      jTextFieldPort.setText(remotePort);
      this.hide();
  }

  /**
   * Get the database location parameters.
   * @param flag Indicate which database want to use.
   *             1: Local database
   *             2: Remote database
   * @return String[] The database location parameters
   */
  public String[] getParms(int flag){
       if (flag == 1){
           return new String[]{localPath};
       } else if (flag == 2) {
           return new String[]{remoteServer,remotePort};
       }
       return null;
  }

  protected void processWindowEvent(WindowEvent e){
       if (e.getID() == WindowEvent.WINDOW_CLOSING){
          jButtonCancel_actionPerformed(null);
       } else{
           super.processWindowEvent(e);
       }
  }
}