package org.example.widgets;

import org.example.database.UserDB;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class UserMain extends Frame {
  private static final Logger log = LoggerFactory.getLogger(UserMain.class);
  private static UserMain instance;
  List<User> users;
  UserTableModel userTableModel;
  Long hospitalID;
  Button button = new Button("New User");
  Button refreshButton = new Button("Refresh");

  UserMain() throws SQLException {
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

    add(button, BorderLayout.SOUTH);
    add(refreshButton, BorderLayout.NORTH);
    button.addActionListener(e -> {
      log.info("Button clicked");
      // TODO
      // AppendUser appendUser = new AppendUser();
      // appendUser.setVisible(true);
    });
    refreshButton.addActionListener(e -> {
      log.info("Refresh button clicked");
      try {
        users = new UserDB().findByHospitalID(hospitalID);
        userTableModel.setUsers(users);
      } catch (SQLException ex) {
        log.error("Failed to refresh user data", ex);
      }
    });
    JTable table = createTable();
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    setVisible(true);
  }

  public static UserMain getInstance() throws SQLException {
    if (instance == null) {
      instance = new UserMain();
    }
    return instance;
  }

  public static void main(String[] args) throws SQLException {
    var A = new UserMain();
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = 1L;
  }

  public void receiveEvent() {
    log.info("Event received");
  }

  private JTable createTable() throws SQLException {
    users = new UserDB().findByHospitalID(hospitalID);
    userTableModel = new UserTableModel(users);
    JTable table = new JTable(userTableModel);
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
}
