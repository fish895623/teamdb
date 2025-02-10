package org.example.widgets;

import org.example.database.MedicalRecordDB;
import org.example.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MedicalRecordMain extends Frame {
  private static final Logger log = LoggerFactory.getLogger(MedicalRecordMain.class);
  List<MedicalRecord> users;
  MedicalRecordTableModel medicalRecordTableModel;
  Long hospitalID;
  Button button = new Button("new MedicalRecord");
  Button refreshButton = new Button("Refresh");
  private int userID;

  MedicalRecordMain() throws SQLException {
    super("User Management");
    setSize(600, 400);

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
      Component focusedComponent =
          KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

      if (!(focusedComponent instanceof JTextComponent)) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
          if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
          }
        }
      }

      return false;
    });

   
    add(refreshButton, BorderLayout.NORTH);
    add(button, BorderLayout.SOUTH);
   
    JTable table = createTable();
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    setVisible(true);
  }

  public static void main(String[] args) throws SQLException {
    var A = new MedicalRecordMain();
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = 1L;
  }

  public void receiveEvent() {
    log.info("Event received");
  }

  private JTable createTable() throws SQLException {
    users = new MedicalRecordDB().getAll();
    medicalRecordTableModel = new MedicalRecordTableModel(users);
    JTable table = new JTable(medicalRecordTableModel);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
          JTable target = (JTable) e.getSource();
          int row = target.getSelectedRow();
          int column = target.getSelectedColumn();
          log.info("Selected row: " + row + " column: " + column);
          // Get User Specific info alongside
        }
      }
    });

    return table;
  }

  public void setUserID(int userID) {
    this.userID =  userID;
  }

  private static class LazyHolder {
      private static final MedicalRecordMain INSTANCE;

    static {
      try {
        INSTANCE = new MedicalRecordMain();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static MedicalRecordMain getInstance() {
      return LazyHolder.INSTANCE;
  }
}
