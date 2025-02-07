package org.example.database;

import org.example.DatabaseManager;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
  private static final Logger log = LoggerFactory.getLogger(UserDB.class);
  private final Connection db = DatabaseManager.getInstance().getConnection();

  public UserDB() throws SQLException {
  }

  public List<User> getAll() throws SQLException {
    List<User> users = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

    return getUsers(users, resultSet);
  }

  private List<User> getUsers(List<User> users, ResultSet resultSet) throws SQLException {
    while (resultSet.next()) {
      User user = new User();
      user.userID = resultSet.getInt("UserID");
      user.name = resultSet.getString("Name");
      user.birthDate = resultSet.getDate("BirthDate");
      user.gender = resultSet.getString("Gender");
      user.contactNumber = resultSet.getString("ContactNumber");

      users.add(user);
    }

    return users;
  }

  public List<User> findByUserID(int userID) throws SQLException {
    List<User> users = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("""
        SELECT UserID, Name, BirthDate, Gender, ContactNumber
        FROM User
        WHERE UserID =
        """ + userID + ";");

    return getUsers(users, resultSet);
  }

  public List<User> findByHospitalID(int HospitalID) throws SQLException {
    List<User> users = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("""
        SELECT u.UserID,
               u.Name,
               u.BirthDate,
               u.Gender,
               u.ContactNumber
        FROM User u
                 JOIN MedicalRecord m ON u.UserID = m.UserID
        WHERE m.HospitalID =
        """ + HospitalID + ";");

    return getUsers(users, resultSet);
  }

}
