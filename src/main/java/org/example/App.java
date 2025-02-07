package org.example;

import org.example.database.HospitalDB;
import org.example.model.Hospital;
import org.example.widgets.AppendHospital;
import org.example.widgets.HospitalTableModel;
import org.example.widgets.UserMain;
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
  private final UserMain userMain = UserMain.getInstance();
  Button button = new Button("New Hospital");
  Button refreshButton = new Button("Refresh");
  List<Hospital> hospitals;
  HospitalTableModel hospitalTableModel;

  private App() throws SQLException {
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
    hospitals = new HospitalDB().getAll();
    JTable table = getJTable(hospitals);

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
    setVisible(true);
  }

  public static App getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void receiveEvent() {
    log.info("Event received");
  }

  private JTable getJTable(List<Hospital> users) {
    hospitalTableModel = new HospitalTableModel();
    hospitalTableModel.setHospitals(users);
    JTable table = new JTable(hospitalTableModel);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          JTable target = (JTable) e.getSource();
          int row = target.getSelectedRow();
          int val = (int) table.getModel().getValueAt(row, 0);
          log.info("Double clicked on val = {}", val);
          userMain.setHospitalID(val);
          userMain.setVisible(true);
          log.info("Opening UserMain {}", val);
        }
      }
    });
    return table;
  }

  private void refreshTableData() {
    try {
      hospitals = new HospitalDB().getAll();
      hospitalTableModel.setHospitals(hospitals);
    } catch (SQLException e) {
      log.error("Error refreshing table data", e);
    }
  }


  private static class LazyHolder {
    private static final App INSTANCE;

    static {
      try {
        INSTANCE = new App();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
