package org.example.database;

import org.example.DatabaseManager;
import org.example.model.Hospital;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HospitalDB {
  private static final Logger log = LoggerFactory.getLogger(HospitalDB.class);
  private final Connection db = DatabaseManager.getInstance().getConnection();

  public HospitalDB() throws SQLException {
  }

  public List<Hospital> getAll() throws SQLException {
    List<Hospital> data = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM Hospital");

    while (resultSet.next()) {
      Hospital d = new Hospital();
      d.HospitalName = resultSet.getString("HospitalName");
      d.Address = resultSet.getString("Address");
      d.ContactNumber = resultSet.getString("ContactNumber");
      d.HospitalID = resultSet.getInt("HospitalID");

      data.add(d);
    }

    return data;
  }
}
