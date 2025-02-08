package org.example.widgets;

import org.example.database.ExerciseDB;
import org.example.model.Exercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
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
  int userID;

  ExerciseMain() throws SQLException {
    super("Exercise Management");
    setSize(600, 400);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }

      @Override
      public void windowActivated(WindowEvent e) {
        log.info("Window activated");

        try {
          this.refreshData();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
      }

      private void refreshData() throws SQLException {
        log.info("Refreshing data");
        exercises = new ExerciseDB().findByUserID(userID);

        repaint();
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
    });
    refreshButton.addActionListener(e -> {

    });
    exercises = new ExerciseDB().findByUserID(1);
    JTable table = createTable(exercises);
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
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

  private JTable createTable(List<Exercise> exercises) throws SQLException {
    exerciseTableModel = new ExerciseTableModel();
    exerciseTableModel.setExercise(exercises);
    return new JTable(exerciseTableModel);
  }

  public void setUserID(int userID) {
    this.userID = userID;
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
