package org.example.database;

import org.example.DatabaseManager;
import org.example.model.Exercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    PreparedStatement prepareStatement = db.prepareStatement("""
        INSERT INTO Exercise (UserID, ExerciseDateTime, ExerciseType, Duration, CaloriesBurned)
        VALUES (?, ?, ?, ?, ?)
        """);
    prepareStatement.setInt(1, exercise.UserID);
    prepareStatement.setString(2, exercise.ExerciseDateTime);
    prepareStatement.setString(3, exercise.ExerciseType);
    prepareStatement.setInt(4, exercise.Duration);
    prepareStatement.setInt(5, exercise.CaloriesBurned);

    prepareStatement.executeUpdate();
  }

  public List<Exercise> findByUserID(int userID) throws SQLException {
    List<Exercise> data = new ArrayList<>();
    PreparedStatement prepareStatement = db.prepareStatement("SELECT * FROM Exercise WHERE UserID = ?");
    prepareStatement.setInt(1, userID);

    ResultSet resultSet = prepareStatement.executeQuery();

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
}
