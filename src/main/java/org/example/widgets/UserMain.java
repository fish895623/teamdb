package org.example.widgets;

import org.example.database.UserDB;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO
 * 1. display hospital information on top of the table or using super("User
 * Management - " + hospitalName);
 */
public class UserMain extends JFrame {
  private static final Logger log = LoggerFactory.getLogger(UserMain.class);
  List<User> users;
  UserTableModel userTableModel;
  int hospitalID;
  Button button = new Button("New User");
  Button refreshButton = new Button("Refresh");
  private UserInformationView userInformationView;

  private UserMain() throws SQLException {
    super("User Management");
    setSize(600, 400);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }

      @Override
      public void windowActivated(WindowEvent e) {
        refreshTableData();
      }
    });

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
      Component focusedComponent =
          KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

      if (!(focusedComponent instanceof JTextComponent)) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
          if (e.getKeyCode() == KeyEvent.VK_R) {
            dispose();
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
      refreshTableData();
    });
    users = new UserDB().findByHospitalID(hospitalID);
    JTable table = getJTable(users);
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
  }

  public static UserMain getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = hospitalID;
  }

  public void receiveEvent() {
    log.info("Event received");
  }

  private JTable getJTable(List<User> users) {
    userTableModel = new UserTableModel();
    userTableModel.setUsers(users);
    JTable table = new JTable(userTableModel);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          JTable table = (JTable) e.getSource();
          int row = table.getSelectedRow();
          int val = (int) table.getModel().getValueAt(row, 0);

          log.info("Double clicked on val = {}", val);

          userInformationView = UserInformationView.getInstance();
          try {
            userInformationView.setUserID(val);
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }
          userInformationView.setVisible(true);
        }
      }
    });

    return table;
  }

  public void refreshTableData() {
    try {
      users = new UserDB().findByHospitalID(hospitalID);
      hospitalID = 1;
      userTableModel.setUsers(users);
    } catch (SQLException ex) {
      log.error("Failed to refresh user data", ex);
    }
  }

  private static class LazyHolder {
    private static final UserMain INSTANCE;

    static {
      try {
        INSTANCE = new UserMain();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
