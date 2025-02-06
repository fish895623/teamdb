package org.example.database;

import org.example.DatabaseManager;
import org.example.model.HealthData;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HealthDataDB {
  private static final Logger log = LoggerFactory.getLogger(HealthDataDB.class);
  private final Connection db = DatabaseManager.getInstance().getConnection();

  public HealthDataDB() throws SQLException {
  }

  public List<HealthData> getAll() throws SQLException {
    List<HealthData> data = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM HealthData");

    while (resultSet.next()) {
      var d = new HealthData();
      d.BloodPressureDiastolic = resultSet.getInt("BloodPressureDiastolic");
      d.BodyFatPercentage = resultSet.getInt("BodyFatPercentage");
      d.UserID = resultSet.getInt("UserID");
      d.HeartRate = resultSet.getInt("HeartRate");
      d.BloodPressureSystolic = resultSet.getInt("BloodPressureSystolic");
      d.BloodSugar = resultSet.getInt("BloodSugar");
      d.Height = resultSet.getInt("Height");
      d.Weight = resultSet.getInt("Weight");
      d.MeasurementDateTime = resultSet.getDate("MeasurementDateTime");
      d.HealthDataID = resultSet.getInt("HealthDataID");


      data.add(d);
    }

    return data;
  }
}
