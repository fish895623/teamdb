package org.example.widgets;

import org.example.model.Exercise;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ExerciseTableModel extends AbstractTableModel {
  private List<Exercise> exercises;
  private final String[] columnNames =
      {"UserID", "ExerciseDateTime", "ExerciseType", "Duration", "CaloriesBurned", "ExerciseID"};

  public ExerciseTableModel(List<Exercise> exercises) {
    this.exercises = exercises;
  }

  @Override
  public int getRowCount() {
    return exercises.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Exercise exercise = exercises.get(rowIndex);
    return switch (columnIndex) {
      case 0 -> exercise.UserID;
      case 1 -> exercise.ExerciseDateTime;
      case 2 -> exercise.ExerciseType;
      case 3 -> exercise.Duration;
      case 4 -> exercise.CaloriesBurned;
      case 5 -> exercise.ExerciseID;
      default -> null;
    };
  }

  public void setExercises(List<Exercise> exercises) {
    this.exercises = exercises;
    fireTableDataChanged();
  }
}
