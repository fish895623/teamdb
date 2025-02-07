package org.example.database;

import org.example.DatabaseManager;
import org.example.model.Exercise;
import org.example.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDB {
  private static final Logger log = LoggerFactory.getLogger(ExerciseDB.class);
  private final Connection db = DatabaseManager.getInstance().getConnection();

  public ExerciseDB() throws SQLException {
  }

  public List<Exercise> getAll() throws SQLException {
    List<Exercise> data = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM Exercise");

    while (resultSet.next()) {
      Exercise exercise = new Exercise();

      exercise.CaloriesBurned = resultSet.getInt("CaloriesBurned");
      exercise.Duration = resultSet.getInt("Duration");
      exercise.ExerciseDateTime = resultSet.getDate("ExerciseDateTime").toString();
      exercise.ExerciseID = resultSet.getInt("ExerciseID");
      exercise.ExerciseType = resultSet.getString("ExerciseType");
      exercise.UserID = resultSet.getInt("UserID");

      data.add(exercise);
    }

    return data;
  }
  public void insert(Exercise exercise) throws SQLException {
	    Statement statement = db.createStatement();
	    statement.executeUpdate("INSERT INTO Exercise (UserID,ExerciseDateTime, ExerciseType, Duration,CaloriesBurned) VALUES ('" + exercise.UserID+"', '"+ exercise.ExerciseDateTime+ "', '"+  exercise.ExerciseType+ "', '" + exercise.Duration + "', '" +exercise.CaloriesBurned + "')");
	  }

}
