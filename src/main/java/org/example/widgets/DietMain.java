package org.example.widgets;

import org.example.database.DietDB;
import org.example.model.Diet ;
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

public class DietMain extends Frame {
  private static final Logger log = LoggerFactory.getLogger(DietMain.class);
  private static DietMain instance;
  List<Diet > diets;
  DietTableModel dietTableModel;
  Long hospitalID;
  Button button = new Button("New Diet ");
  Button refreshButton = new Button("Refresh");

  DietMain() throws SQLException {
    super("Diet  Management");
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
      // AppendDiet  appendDiet  = new AppendDiet ();
      // appendDiet .setVisible(true);
      
    });
    refreshButton.addActionListener(e -> {
      
    });
    JTable table = createTable();
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    setVisible(true);
  }

  public static DietMain getInstance() throws SQLException {
    if (instance == null) {
      instance = new DietMain();
    }
    return instance;
  }

  public static void main(String[] args) throws SQLException {
    var A = new DietMain();
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = 1L;
  }

  public void receiveEvent() {
    log.info("Event received");
  }

  private JTable createTable() throws SQLException {
    JTable table = new JTable(dietTableModel);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
          JTable target = (JTable) e.getSource();
          int row = target.getSelectedRow();
          int column = target.getSelectedColumn();
          log.info("Selected row: " + row + " column: " + column);
          // Get Diet  Specific info alongside
        }
      }
    });

    return table;
  }
}
