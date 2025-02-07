package org.example.widgets;

import org.example.model.Exercise;
import org.example.model.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ExerciseTableModel extends AbstractTableModel {
    private List<Exercise> exercises;
    private final String[] columnNames = { "ExerciseID", "UserID", "ExerciseDateTime", "ExerciseType", "Duration",
            "CaloriesBurned" };

    ExerciseTableModel() {
    }

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
            case 0 -> exercise.ExerciseID;
            case 1 -> exercise.UserID;
            case 2 -> exercise.ExerciseDateTime;
            case 3 -> exercise.ExerciseType;
            case 4 -> exercise.Duration;
            case 5 -> exercise.CaloriesBurned;
            default -> null;
        };
    }

    public void setExercise(List<Exercise> exercises) {
        this.exercises = exercises;
        fireTableDataChanged();
    }
}
