package org.example.database;

import org.example.DatabaseManager;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
    var statement = db.prepareStatement("""
        SELECT u.UserID,
               u.Name,
               u.BirthDate,
               u.Gender,
               u.ContactNumber
        FROM User u
        WHERE u.HospitalID = ?
        """);
    statement.setInt(1, HospitalID);
    var resultSet = statement.executeQuery();

    return getUsers(users, resultSet);
  }

  public void insert(User user) throws SQLException {
    var statement = db.prepareStatement("""
        INSERT INTO User (Name, BirthDate, Gender, ContactNumber, Password)
        VALUES (?, ?, ?, ?, ?);
        """);
    statement.setString(1, user.name);
    statement.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(user.birthDate));
    statement.setString(3, user.gender);
    statement.setString(4, user.contactNumber);
    if (user.password == null) {
      user.password = "";
    }
    statement.setString(5, user.password);
    statement.executeUpdate();
  }
}
