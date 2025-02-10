package org.example.database;

import org.example.DatabaseManager;
import org.example.model.HealthData;
import org.example.model.UserInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HealthDataDB {
  @SuppressWarnings("unused")
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

  /**
   * Generate a List HealData findByUserID method
   */

  public List<HealthData> findByUserID(int userID) throws SQLException {
    log.info("Finding HealthData by UserID: {}", userID);
    List<HealthData> data = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM HealthData WHERE UserID = " + userID);

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

  /**
   * Insert data into the HealthData table
   */
  public void insert(HealthData data) throws SQLException {
    log.info("Inserting HealthData: {}", data);
    PreparedStatement pstmt = db.prepareStatement("""
        INSERT INTO HealthData (
            BloodPressureDiastolic, BodyFatPercentage, UserID,
            HeartRate, BloodPressureSystolic, BloodSugar,
            Height, Weight, MeasurementDateTime
            )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """);
    pstmt.setInt(1, data.BloodPressureDiastolic);
    pstmt.setFloat(2, data.BodyFatPercentage);
    pstmt.setInt(3, data.UserID);
    pstmt.setInt(4, data.HeartRate);
    pstmt.setInt(5, data.BloodPressureSystolic);
    pstmt.setFloat(6, data.BloodSugar);
    pstmt.setFloat(7, data.Height);
    pstmt.setFloat(8, data.Weight);
    pstmt.setDate(9, data.MeasurementDateTime);
    pstmt.executeUpdate();
  }

  public UserInformation getUserHealthData(int userID) throws SQLException {
    return getUserHealthData(userID, 5);
  }

  public UserInformation getUserHealthData(int userID, int year) throws SQLException {
    log.info("Getting HealthData for User: {}", userID);
    UserInformation data = new UserInformation();

    PreparedStatement preparedStatement = db.prepareStatement("""
        SELECT
            h.Height,
            h.Weight,
            h.BodyFatPercentage,
            h.BloodPressureSystolic,
            h.BloodPressureDiastolic,
            avg_data.BloodPressureDiastolicAverage,
            avg_data.BloodPressureSystolicAverage
        FROM
            (SELECT HealthDataID, Height, Weight, BodyFatPercentage, BloodPressureSystolic, BloodPressureDiastolic
             FROM HealthData
             WHERE UserID = ?
             ORDER BY HealthDataID DESC
             LIMIT 1) h
                CROSS JOIN
            (SELECT AVG(BloodPressureDiastolic) AS BloodPressureDiastolicAverage,
                    AVG(BloodPressureSystolic)  AS BloodPressureSystolicAverage
             FROM HealthData
             WHERE UserID = ?
               AND MeasurementDateTime > DATE_SUB(NOW(), INTERVAL ? YEAR)) avg_data;
        """);

    preparedStatement.setInt(1, userID);
    preparedStatement.setInt(2, userID);
    preparedStatement.setInt(3, year);

    ResultSet resultSet = preparedStatement.executeQuery();

    data.Height = resultSet.getFloat("Height");
    data.Weight = resultSet.getFloat("Weight");
    data.BodyFatPercentage = resultSet.getFloat("BodyFatPercentage");
    data.BloodPressureSystolic = resultSet.getInt("BloodPressureSystolic");
    data.BloodPressureDiastolic = resultSet.getInt("BloodPressureDiastolic");
    data.BloodPressureSystolicAverage = resultSet.getFloat("BloodPressureSystolicAverage");
    data.BloodPressureDiastolicAverage = resultSet.getFloat("BloodPressureDiastolicAverage");

    return data;
  }
}
