package org.example.widgets;

import org.example.model.Exercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class ExerciseMain extends Frame {
  private static final Logger log = LoggerFactory.getLogger(ExerciseMain.class);
  List<Exercise> exercises;
  ExerciseTableModel exerciseTableModel;
  Long hospitalID;
  Button button = new Button("New Exercise");
  Button refreshButton = new Button("Refresh");

  ExerciseMain() throws SQLException {
    super("Exercise Management");
    setSize(600, 400);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }
    });

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
      Component focusedComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

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
      // AppendExercise appendExercise = new AppendExercise();
      // appendExercise.setVisible(true);

    });
    refreshButton.addActionListener(e -> {

    });
    JTable table = createTable();
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    setVisible(true);
  }

  public static ExerciseMain getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = 1L;
  }

  public void receiveEvent() {
    log.info("Event received");
  }

  private JTable createTable() throws SQLException {
    JTable table = new JTable(exerciseTableModel);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
          JTable target = (JTable) e.getSource();
          int row = target.getSelectedRow();
          int column = target.getSelectedColumn();
          log.info("Selected row: " + row + " column: " + column);
          // Get Exercise Specific info alongside
        }
      }
    });

    return table;
  }


  private static class LazyHolder {
    private static final ExerciseMain INSTANCE;

    static {
      try {
        INSTANCE = new ExerciseMain();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
