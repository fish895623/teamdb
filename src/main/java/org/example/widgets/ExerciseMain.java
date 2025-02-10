package org.example.widgets;

import org.example.database.ExerciseDB;
import org.example.model.Exercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class ExerciseMain extends Frame {
  private static final Logger log = LoggerFactory.getLogger(ExerciseMain.class);
  List<Exercise> exercises;
  ExerciseTableModel exerciseTableModel;
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
      public void windowActivated(WindowEvent event) {
        log.info("Window activated");
        try {
          refreshData();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    });

    JTable table = createTable();
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
  }

  public static ExerciseMain getInstance() {
    return LazyHolder.INSTANCE;
  }

  private void refreshData() throws SQLException {
    log.info("Refreshing data");
    exercises = new ExerciseDB().findByUserID(userID);
    exerciseTableModel.setExercise(exercises);

    repaint();
  }

  private JTable createTable() throws SQLException {
    exercises = new ExerciseDB().findByUserID(userID);
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
