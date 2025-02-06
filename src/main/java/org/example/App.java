package org.example;

import org.example.database.HospitalDB;
import org.example.model.Hospital;
import org.example.widgets.AppendHospital;
import org.example.widgets.HospitalTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class App extends Frame {
  private static final Logger log = LoggerFactory.getLogger(App.class);
  private static App instance;
  Button button = new Button("New Hospital");
  Button refreshButton = new Button("Refresh");
  List<Hospital> users = new HospitalDB().getAll();
  HospitalTableModel userTableModel;

  public App() throws SQLException {
    super("Member Management");
    setSize(600, 400);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
      }
    });

    /* Keyboard Event Handling */
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
      AppendHospital appendHospital = new AppendHospital();
      appendHospital.setVisible(true);
    });
    refreshButton.addActionListener(e -> {
      log.info("Refresh button clicked");
      refreshTableData();
    });

    JTable table = getJTable(users);

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
    setVisible(true);
  }

  public static App getInstance() throws SQLException {
    if (instance == null) {
      instance = new App();
    }
    return instance;
  }

  public void receiveEvent() {
    log.info("Event received");
  }

  private JTable getJTable(List<Hospital> users) {
    userTableModel = new HospitalTableModel();
    userTableModel.setHospitals(users);
    JTable table = new JTable(userTableModel);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          JTable target = (JTable) e.getSource();
          int row = target.getSelectedRow();
          var val = table.getModel().getValueAt(row, 0);
          log.info("Double clicked on val = {}", val);
        }
      }
    });
    return table;
  }

  private void refreshTableData() {
    try {
      users = new HospitalDB().getAll();
      userTableModel.setHospitals(users);
    } catch (SQLException e) {
      log.error("Error refreshing table data", e);
    }
  }
}
