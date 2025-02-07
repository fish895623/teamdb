package org.example.database;

import org.example.DatabaseManager;
import org.example.model.Diet;
import org.example.model.Exercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
      d.MealDateTime = resultSet.getInt("MealDateTime");
      d.FoodName = resultSet.getString("FoodName");
      d.Quantity = resultSet.getInt("Quantity");
      d.Calories = resultSet.getInt("Calories");

      data.add(d);
    }
    
    return data;
  }
  public void insert(Diet diet) throws SQLException {
	    Statement statement = db.createStatement();
	    statement.executeUpdate("INSERT INTO Diet (UserID,MealDateTime, FoodName, Quantity,Calories) VALUES ('" + diet.UserID+"', '"+ diet.MealDateTime+ "', '"+  diet.FoodName+ "', '" + diet.Quantity + "', '" +diet.Calories + "')");
	  }

}
