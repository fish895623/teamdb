package org.example.widgets;

import org.example.database.DietDB;
import org.example.model.Diet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class DietMain extends Frame {
  private static final Logger log = LoggerFactory.getLogger(DietMain.class);
  List<Diet> diets;
  DietTableModel dietTableModel;
  Long hospitalID;
  Button button = new Button("New Diet");
  Button refreshButton = new Button("Refresh");
  int userID = 0;

  DietMain() throws SQLException {
    super("Diet Management");
    setSize(600, 400);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }
    });

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
    });
    refreshButton.addActionListener(e -> {
    });
    diets = new DietDB().findByUserID(userID);
    JTable table = createTable(diets);
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
  }

  public static void main(String[] args) throws SQLException {
    var A = new DietMain();
  }

  public static DietMain getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = 1L;
  }

  public void receiveEvent() {
    log.info("Event received");
  }

  private JTable createTable(List<Diet> diets) throws SQLException {
    dietTableModel = new DietTableModel();
    dietTableModel.setData(diets);

    return new JTable(dietTableModel);
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }


  private static class LazyHolder {
    private static final DietMain INSTANCE;

    static {
      try {
        INSTANCE = new DietMain();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
