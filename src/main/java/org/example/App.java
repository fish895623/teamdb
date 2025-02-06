package org.example;

import org.example.database.HospitalDB;
import org.example.model.Hospital;
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

  public App() throws SQLException {
    super("Member Management");
    setSize(300, 200);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
      }
    });

    /* Keyboard Event Handling */
    KeyboardFocusManager
        .getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(e -> {
          Component focusedComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

          if (!(focusedComponent instanceof JTextComponent)) {
            if (e.getID() == KeyEvent.KEY_TYPED) {
              log.info("Key Typed: {}", e.getKeyChar());
            } else if (e.getID() == KeyEvent.KEY_PRESSED) {
              log.info("Key Pressed: {}", e.getKeyChar());
              if (e.getKeyCode() == KeyEvent.VK_Q) {
                System.exit(0);
              }
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
              log.info("Key Released: {}", e.getKeyChar());
            }
          }

          return false;
        });

    Button button = new Button("Click Me");
    add(button, BorderLayout.SOUTH);

    List<Hospital> users = new HospitalDB().getAll();

    JTable table = getJTable(users);

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
    setVisible(true);
  }

  private static JTable getJTable(List<Hospital> users) {
    HospitalTableModel userTableModel = new HospitalTableModel(users);
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
}
