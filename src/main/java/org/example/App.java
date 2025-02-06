package org.example;

import org.example.database.UserDB;
import org.example.model.User;
import org.example.widgets.UserTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class App extends Frame implements KeyListener {
  private static final Logger log = LoggerFactory.getLogger(App.class);

  public App() throws SQLException {
    super("Member Management");
    setSize(300, 200);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
      }
    });
    addKeyListener(this);

    // list from UserDB
    var users = new UserDB().getAll();

    JTable table = getJTable(users);

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane);
    setVisible(true);
  }

  private static JTable getJTable(java.util.List<User> users) {
    UserTableModel userTableModel = new UserTableModel(users);
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

  @Override
  public void keyTyped(KeyEvent e) {
    log.info("Key Typed: {}", e.getKeyChar());
  }

  @Override
  public void keyPressed(KeyEvent e) {
    log.info("Key Pressed: {}", e.getKeyChar());

    if (e.getKeyCode() == 'Q') {
      System.exit(0);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    log.info("Key Released: {}", e.getKeyChar());
  }
}
