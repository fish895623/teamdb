package org.example.widgets;

import org.example.database.MedicalRecordDB;
import org.example.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * This class is responsible for appending a new medical to the database.
 */
public class AppendMedicalRecord extends Frame {
  private static final Logger log = LoggerFactory.getLogger(AppendMedicalRecord.class);
  private final TextField diagnosis;
  private final TextField prescription;
  private final Button submitButton;
  private final Button cancelButton;
  private int hospitalID;
  private int userID;

  public AppendMedicalRecord() {
    super("Append Medical");

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        dispose();
      }

      @Override
      public void windowActivated(WindowEvent event) {
        log.info("Window activated");
      }
    });

    /* initialize the text fields */
    diagnosis = new TextField();
    prescription = new TextField();
    submitButton = new Button("Submit");
    cancelButton = new Button("Cancel");

    setSize(400, 300);
    setLayout(new GridLayout(0, 2));
    add(new Label("diagnosis:"));
    add(diagnosis);
    add(new Label("prescription:"));
    add(prescription);
    add(submitButton);
    add(cancelButton);

    submitButton.addActionListener(e -> {
      this.onClickSubmitButton();
    });
    cancelButton.addActionListener(e -> {
      log.info("Cancel button clicked");
      dispose();
    });
  }

  public static void main(String[] args) {
    var a = new AppendMedicalRecord();
    a.setUserID(2);
    a.setHospitalID(1);
    a.setVisible(true);
  }

  void onClickSubmitButton() {
    log.info("Submit button clicked");
    try {
      var data = new MedicalRecord();
      data.UserID = userID;
      data.HospitalID = hospitalID;
      data.Diagnosis = diagnosis.getText();
      data.Prescription = prescription.getText();
      data.VisitDateTime = new java.util.Date();

      new MedicalRecordDB().insert(data);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    dispose();
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = hospitalID;
  }
  private static class LazyHolder {
      private static final AppendMedicalRecord INSTANCE = new AppendMedicalRecord();
  }

  public static AppendMedicalRecord getInstance() {
      return LazyHolder.INSTANCE;
  }
}
