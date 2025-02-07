package org.example.database;

import org.example.DatabaseManager;
import org.example.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDB {
  private static final Logger log = LoggerFactory.getLogger(MedicalRecordDB.class);
  private final Connection db = DatabaseManager.getInstance().getConnection();

  public MedicalRecordDB() throws SQLException {
  }

  public List<MedicalRecord> getAll() throws SQLException {
    List<MedicalRecord> data = new ArrayList<>();
    Statement statement = db.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM MedicalRecord");

    while (resultSet.next()) {
      MedicalRecord d = new MedicalRecord();
      d.MedicalRecordID = resultSet.getInt("MedicalRecordID");
      d.UserID = resultSet.getInt("UserID");
      d.HospitalID = resultSet.getInt("HospitalID");
      d.VisitDateTime = resultSet.getDate("VisitDateTime");
      d.Diagnosis = resultSet.getString("Diagnosis");
      d.Prescription = resultSet.getString("Prescription");

      data.add(d);
    }

    return data;
  }
  public void insert(MedicalRecord medicalRecord) throws SQLException {
	    Statement statement = db.createStatement();
	    statement.executeUpdate("INSERT INTO MedicalRecord (HospitalID,UserID,VisitDateTime, Diagnosis, Prescription) VALUES ('" + medicalRecord.HospitalID + "', '"+ medicalRecord.UserID+"', '"+ medicalRecord.Now+ "', '"  + medicalRecord.Diagnosis + "', '" + medicalRecord.Prescription + "')");
	  }
 
}
