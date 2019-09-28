package suncertify.client;

import java.awt.*;
import javax.swing.*;
import suncertify.db.*;
import java.awt.event.*;

/**
 * This class lets the user to be able to book sites of the selected flight.
 *
 * @version 1.1  05-Sep-2002
 */
public class BookFlight extends JDialog {
  ClientModel model;
  JTable jTableFlightInfo;
  GridLayout gridLayout = new GridLayout();
  JButton jButton1 = new JButton();
  JPanel jPanelMain = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JPanel jPanelSeats = new JPanel();
  JPanel jPanelButtons = new JPanel();
  JButton jButtonOk = new JButton();
  JButton jButtonCancel = new JButton();
  JComboBox jComboBoxSeatsAvailable = new JComboBox();
  FlowLayout flowLayoutButtons = new FlowLayout();
  JLabel jLabelBook = new JLabel();
  FlowLayout flowLayoutBook = new FlowLayout();
  JLabel jLabelSeat = new JLabel();

  private int bookedSeats = 0;

  public BookFlight(Frame frame, ClientModel model, boolean modal) {
    super(frame, "", modal);
    this.model = model;
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public BookFlight() {
    this(null, null, false);
  }

  void jbInit() throws Exception {
    if (model != null){
        String[] headers = model.getTableHeaders();
        String[] values = model.getSelectedRow().getValues();

        String flightInfo[][] = new String[headers.length][2];;
        String header[] = {"",""};
        for (int i=0; i < headers.length; i++){
            flightInfo[i][0] = headers[i];
            flightInfo[i][1] = values[i].trim();
        }
        jTableFlightInfo = new JTable(flightInfo,header);
        jTableFlightInfo.disable();
        this.getContentPane().add(jTableFlightInfo, BorderLayout.CENTER);

        int seats = Integer.parseInt(values[values.length-1].trim());
        for(int i=0; i<=seats; i++){
              jComboBoxSeatsAvailable.addItem(String.valueOf(i));
        }
    }

    jPanelMain.setLayout(gridLayout);
    gridLayout.setColumns(2);
    jButtonOk.setToolTipText("");
    jButtonOk.setText("  OK  ");
    jButtonOk.setMnemonic('O');
    jButtonOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonOk_actionPerformed(e);
      }
    });
    jButtonCancel.setText("Cancel");
    jButtonCancel.setMnemonic('C');
    jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonCancel_actionPerformed(e);
      }
    });
    jPanelButtons.setLayout(flowLayoutButtons);
    flowLayoutButtons.setAlignment(FlowLayout.RIGHT);
    this.setResizable(false);
    this.setTitle("Making A Reservation");
    jLabelBook.setText("Book");
    jPanelSeats.setLayout(flowLayoutBook);
    flowLayoutBook.setAlignment(FlowLayout.LEFT);
    jLabelSeat.setText("seat(s)");
    jPanelMain.add(jPanelSeats);
    jPanelSeats.add(jLabelBook, null);
    jPanelSeats.add(jComboBoxSeatsAvailable, null);
    jPanelSeats.add(jLabelSeat, null);
    jPanelMain.add(jPanelButtons);
    jPanelButtons.add(jButtonOk, null);
    jPanelButtons.add(jButtonCancel, null);

    this.getContentPane().add(jPanelMain, BorderLayout.SOUTH);
  }

  void jButtonCancel_actionPerformed(ActionEvent e) {
      bookedSeats = 0;
      this.hide();
  }

  /**
   * Get the seats which the user want to book.
   * @return int Seats wanted to book.
   */
  public int getBookedSeats(){
      return bookedSeats;
  }

  void jButtonOk_actionPerformed(ActionEvent e) {
      bookedSeats = Integer.parseInt((String)(jComboBoxSeatsAvailable.getSelectedItem()));
      this.hide();
  }
}