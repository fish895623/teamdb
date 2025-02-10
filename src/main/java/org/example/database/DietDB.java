package org.example.database;

import org.example.DatabaseManager;
import org.example.model.Diet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DietDB {
  private static final Logger log = LoggerFactory.getLogger(DietDB.class);
  private final Connection db = DatabaseManager.getInstance().getConnection();

  public DietDB() throws SQLException {
  }

  public List<Diet> getAll() throws SQLException {
    List<Diet> data = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM Diet");

    while (resultSet.next()) {
      Diet d = new Diet();
      d.DietID = resultSet.getInt("DietID");
      d.UserID = resultSet.getInt("UserID");

      try {
        d.MealDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString("MealDateTime"));
      } catch (ParseException e) {
        log.error(e.toString());
      }

      d.FoodName = resultSet.getString("FoodName");
      d.Quantity = resultSet.getInt("Quantity");
      d.Calories = resultSet.getInt("Calories");

      data.add(d);
    }

    return data;
  }

  public List<Diet> findByUserID(int UserID) throws SQLException {
    log.info("Finding Diet by UserID: {}", UserID);
    List<Diet> data = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM Diet WHERE UserID = " + UserID);

    while (resultSet.next()) {
      Diet d = new Diet();
      d.DietID = resultSet.getInt("DietID");
      d.UserID = resultSet.getInt("UserID");

      try {
        d.MealDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString("MealDateTime"));
      } catch (ParseException e) {
        log.error(e.toString());
      }

      d.FoodName = resultSet.getString("FoodName");
      d.Quantity = resultSet.getInt("Quantity");
      d.Calories = resultSet.getInt("Calories");

      data.add(d);
    }

    return data;
  }

  public void insert(Diet diet) throws SQLException {
    PreparedStatement statement = db.prepareStatement("""
                INSERT INTO Diet (UserID, MealDateTime, FoodName, Quantity,Calories)
                VALUES (?, ?, ?, ?, ?)
        """);
    statement.setInt(1, diet.UserID);
    statement.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diet.MealDateTime));
    statement.setString(3, diet.FoodName);
    statement.setFloat(4, diet.Quantity);
    statement.setInt(5, diet.Calories);
    statement.executeUpdate();
  }

}
