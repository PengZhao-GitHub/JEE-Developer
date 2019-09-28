package suncertify.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;
import suncertify.db.*;
import java.io.*;
import javax.swing.table.JTableHeader;

/**
 * This class is the main user interface of the client program. The user interface
 * is divided to four parts. The menu part, the status bar part, the operation part
 * and the display part.
 *
 * @version 1.0  05-Sep-2002
 */
public class ClientFrame extends JFrame {
  JPanel contentPane;
  JMenuBar jMenuBar = new JMenuBar();
  JMenu jMenuDB = new JMenu();
  JMenuItem jMenuDBExit = new JMenuItem();
  BorderLayout borderLayout = new BorderLayout();
  JMenuItem jMenuDBLocal = new JMenuItem();
  JMenuItem jMenuDBRemote = new JMenuItem();
  JTable jTableMain = new JTable();
  JScrollPane jScrollPane = new JScrollPane();
  JMenu jMenuOption = new JMenu();
  JMenuItem jMenuOptSetting = new JMenuItem();
  JPanel jPanelStatus = new JPanel();
  JPanel jPanelSearch = new JPanel();
  JComboBox jComboBoxToCity = new JComboBox();
  JComboBox jComboBoxFromCity = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JButton jButtonSearch = new JButton();
  JButton jButtonBook = new JButton();
  JButton jButtonDisplayAll = new JButton();
  JLabel jLabelDescription = new JLabel();
  GridLayout gridLayout1 = new GridLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel jLabelDBMode = new JLabel();
  /**
   * The table model used by holding all the data get from database. And it works
   * as a bridge between database service and the user interface.
   */
  private ClientModel model;
  /**
   * Database location setting user interface.
   */
  private DBLocationSetting locationDlg;
  FlowLayout flowLayoutSearch = new FlowLayout();

  /**
   * This constructor initializes the user interface and sets up the
   * database location according to the input parameters.
   *
   * @param parm The database location parameters
   */
  public ClientFrame(String[] parm){
      this();
      locationDlg = new DBLocationSetting(this,parm);
      if (parm.length > 0){
           connectDataToUI(parm);
      }
  }

  /**
   * This constructor initializes the user interface.
   */
  public ClientFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This function initializes each component in the user interface and arranges them
   * to the proper location.
   *
   * @exception Exception if any error occurs.
   */
  private void jbInit() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(ClientFrame.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setSize(new Dimension(694, 439));
    this.setTitle("Fly By Night Services");

    //Initializing the status bar.
    jPanelStatus.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanelStatus.setLayout(borderLayout1);
    jTableMain.setToolTipText("");
    flowLayoutSearch.setAlignment(FlowLayout.LEFT);
    jMenuDB.setToolTipText("");
    contentPane.add(jPanelStatus, BorderLayout.SOUTH);

    //Initializing the operation buttons and the searching conditions input comboxes
    jScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanelSearch.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanelSearch.setLayout(flowLayoutSearch);
    jLabel1.setToolTipText("");
    jLabel1.setText("Origination");
    jLabel2.setText("Destination");

    jButtonSearch.setText("Search");
    jButtonSearch.setMnemonic('S');
    jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonSearch_actionPerformed(e);
      }
    });
    jButtonBook.setText("Book");
    jButtonBook.setMnemonic('B');
    jButtonBook.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonBook_actionPerformed(e);
      }
    });
    jButtonDisplayAll.setText("Display All");
    jButtonDisplayAll.setMnemonic('A');
    jButtonDisplayAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDisplayAll_actionPerformed(e);
      }
    });
    jComboBoxToCity.setMinimumSize(new Dimension(125, 21));
    jLabelDescription.setToolTipText("");
    jLabelDescription.setText("Current database mode: ");
    gridLayout1.setColumns(1);
    jPanelSearch.add(jLabel1, null);
    jPanelSearch.add(jComboBoxFromCity, null);
    jPanelSearch.add(jLabel2, null);
    jPanelSearch.add(jComboBoxToCity, null);
    jPanelSearch.add(jButtonSearch, null);
    jPanelSearch.add(jButtonDisplayAll, null);
    jPanelSearch.add(jButtonBook, null);

    jPanelStatus.add(jLabelDBMode, BorderLayout.CENTER);
    jPanelStatus.add(jLabelDescription, BorderLayout.WEST);
    contentPane.add(jScrollPane,  BorderLayout.CENTER);
    contentPane.add(jPanelSearch, BorderLayout.NORTH);

    //Initializing the menu of the window.
    jMenuDB.setText("Database");
    jMenuDB.setMnemonic('D');
    jMenuDBExit.setText("Exit");
    jMenuDBExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
    jMenuDBExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuDBExit_actionPerformed(e);
      }
    });

    jMenuDBLocal.setText("Local");
    jMenuDBLocal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK));
    jMenuDBLocal.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuDBLocal_actionPerformed(e);
      }
    });
    jMenuDBRemote.setText("Remote");
    jMenuDBRemote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
    jMenuDBRemote.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuDBRemote_actionPerformed(e);
      }
    });
    jMenuOption.setText("Option");
    jMenuOption.setMnemonic('O');
    jMenuOptSetting.setText("Database Setting");
    jMenuOptSetting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
    jMenuOptSetting.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuOptSetting_actionPerformed(e);
      }
    });
    jMenuBar.setBackground(SystemColor.window);
    jMenuDB.add(jMenuDBLocal);
    jMenuDB.add(jMenuDBRemote);
    jMenuDB.addSeparator();
    jMenuDB.add(jMenuDBExit);
    jMenuBar.add(jMenuDB);
    jMenuBar.add(jMenuOption);
    this.setJMenuBar(jMenuBar);
    jMenuOption.add(jMenuOptSetting);

    //Initializing the JTable used by display the flights' information.
    jTableMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jScrollPane.getViewport().add(jTableMain, null);
  }

  /**
   * Command to stop the program.
   */
  public void jMenuDBExit_actionPerformed(ActionEvent e) {
    if (model != null){
      model.closeDB();
    }
    System.exit(0);
  }

  /**
   * Trap the window close event.
   */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
        jMenuDBExit_actionPerformed(null);
    }
  }

  /**
   * Command to use the local database.
   */
  void jMenuDBLocal_actionPerformed(ActionEvent e) {
      String[] parm = locationDlg.getParms(1);
      connectDataToUI(parm);
  }

  /**
   * Command to use the remote database.
   */
  void jMenuDBRemote_actionPerformed(ActionEvent e) {
      String[] parm = locationDlg.getParms(2);
      connectDataToUI(parm);
  }

  /**
   * Create the table model and connect it to the JTable.
   */
  private void connectDataToUI(String[] parm){
      ClientModel orgModel = model;
      try{
          //Create the table model.
          model = new ClientModel(parm);

          //Get all data from the database.
          model.setSearchConditions(null);

          //Set the current database status to the status bar
          jLabelDBMode.setText(model.getDBMode());

          //Set the table model to the JTable
          jTableMain.setModel(model);

          //Set the column model to the JTable
          jTableMain.setColumnModel(model.getTableColumnModel(jTableMain));

          //Set the comboBox
          initComboBoxes(model);

          //Close the orignal database if it is opened now.
          if (orgModel != null){
              orgModel.closeDB();
          }
      } catch(Exception ex){
          //Display warning message.
          String dbLocation = "";
          String modeFlag = "";
          if (parm.length == 1){
             dbLocation = parm[0];
             modeFlag = "local";
          } else if (parm.length == 2){
             dbLocation = parm[0] + ":" + parm[1];
             modeFlag = "remote";
          }
          if (!dbLocation.equals("") && !dbLocation.equals(":")){
              showMessage("Cannot use the " + modeFlag + " database(" + dbLocation + ") due to [" + ex.getMessage() + "]\n"
                    + "Please check if the database location setting is correct.",
                    JOptionPane.INFORMATION_MESSAGE);
          } else {
               showMessage("There is no " + modeFlag + " database location information. "
                    + "Please setup the database location first.",
                    JOptionPane.INFORMATION_MESSAGE);
          }
          //Restore to the orignal database.
          model = orgModel;
      }
  }

  /**
   * Initializing the content of the comboBoxes.
   * @param model Data used by the client.
   */
  public void initComboBoxes(ClientModel model)
  {
      if (model == null){
          return;
      }
      try{
          //Add items to the comboBoxes.
          Object[] orgCities = model.getColumnData(1);
          Object[] dstCities = model.getColumnData(2);

          jComboBoxFromCity.removeAllItems();
          jComboBoxToCity.removeAllItems();
          jComboBoxFromCity.addItem("Any");
          jComboBoxToCity.addItem("Any");

          for (int i=0; i < orgCities.length; i++){
              jComboBoxFromCity.addItem(orgCities[i]);
          }
          for (int i=0; i < dstCities.length; i++){
              jComboBoxToCity.addItem(dstCities[i]);
          }

      }catch(Exception ex){
          return;
      }
  }

  /**
   * Display all the data in the database.
   */
  void jButtonDisplayAll_actionPerformed(ActionEvent e) {
      if (model == null){
          showMessage("Please select the database to be used first.",JOptionPane.INFORMATION_MESSAGE);
          return;
      }

      try{
          jComboBoxFromCity.setSelectedItem("Any");
          jComboBoxToCity.setSelectedItem("Any");

          //Display all data in the database
          model.setSearchConditions(null);
          //Refresh the comboBoxes
          initComboBoxes(model);
      } catch(Exception ex){
          showMessage(ex.toString(),JOptionPane.INFORMATION_MESSAGE);
      }
  }

  /**
   * Display the records found in the database which match the searching criteria.
   */
  void jButtonSearch_actionPerformed(ActionEvent e) {
      if (model == null){
          showMessage("Please select the database to be used first.",JOptionPane.INFORMATION_MESSAGE);
          return;
      }
      try{
         String searchConditions = model.getTableHeader(1) + "='" + jComboBoxFromCity.getSelectedItem() + "',"
                                + model.getTableHeader(2) +  "='" + jComboBoxToCity.getSelectedItem() + "'";

          model.setSearchConditions(searchConditions);
      } catch(Exception ex){
          showMessage(ex.toString(),JOptionPane.INFORMATION_MESSAGE);
      }
  }

  /**
   * Booking flight that suits customer's requirements
   */
  void jButtonBook_actionPerformed(ActionEvent e) {
      if (model == null){
          showMessage("Please select the database to be used first.",JOptionPane.INFORMATION_MESSAGE);
          return;
      }
      //Get the current selected row.
      int row = jTableMain.getSelectedRow();
      if (row != -1){
          model.setSelectedRow(row);
          //Let the user to book seats
          BookFlight bookDlg = new BookFlight(this,model,true);
          //Let the windows display at the center of the screen.
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
          bookDlg.setLocation((screenSize.width - bookDlg.getWidth()) / 2, (screenSize.height - bookDlg.getHeight()) / 2);
          bookDlg.show();
          int bookedSeats = bookDlg.getBookedSeats();
          bookDlg.dispose();

          if (bookedSeats > 0){
                try{
                    model.setBookedSeats(bookedSeats);
                } catch(Exception ex){
                    showMessage(ex.toString(),JOptionPane.INFORMATION_MESSAGE);
                }
          }
      } else{
          showMessage("Please select a row!",JOptionPane.INFORMATION_MESSAGE);
      }
  }

  /**
   * Display the database location setting dialog.
   */
  void jMenuOptSetting_actionPerformed(ActionEvent e) {
      displayDBSettingDialog();
  }

  private void displayDBSettingDialog(){
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      locationDlg.setLocation((screenSize.width - locationDlg.getWidth()) / 2, (screenSize.height - locationDlg.getHeight()) / 2);
      locationDlg.show();
  }
  /**
   * Show message box to display runtime information.
   * @param content The message to be displayed.
   * @param msgType The message type.
   */
  private void showMessage(String content, int msgType){
      JOptionPane.showConfirmDialog(this,content,this.getTitle(),JOptionPane.DEFAULT_OPTION,msgType);
  }
}