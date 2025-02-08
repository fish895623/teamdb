package org.example.widgets;

import org.example.App;
import org.example.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.time.LocalDateTime;

/**
 * This class is responsible for appending a new medical to the database.
 */
public class AppendMedicalRecord extends Frame {
  private static final Logger log = LoggerFactory.getLogger(AppendMedicalRecord.class);
  private final TextField hospitalID;
  private final TextField diagnosis;
  private final TextField prescription;
  private final TextField userID;
  private final Button submitButton;
  private final Button cancelButton;

  public AppendMedicalRecord() {
    super("Append Medical");

    /* initialize the text fields */
    hospitalID = new TextField();
    diagnosis = new TextField();
    prescription = new TextField();
    userID = new TextField();
    submitButton = new Button("Submit");
    cancelButton = new Button("Cancel");

    setSize(400, 300);
    setLayout(new GridLayout(0, 2));
    add(new Label("hospitalID:"));
    add(hospitalID);
    add(new Label("userID:"));
    add(userID);
    add(new Label("diagnosis:"));
    add(diagnosis);
    add(new Label("prescription:"));
    add(prescription);
    add(submitButton);
    add(cancelButton);

    submitButton.addActionListener(e -> {
      submitButtonClicked();
    });
    cancelButton.addActionListener(e -> {
      log.info("Cancel button clicked");
      dispose();
    });

    setVisible(true);
  }

  public void submitButtonClicked() {
    log.info("Submit button clicked");

    String ID = hospitalID.getText();
    String uID = userID.getText();
    String dino = diagnosis.getText();
    String prtion = prescription.getText();


    MedicalRecord medical = new MedicalRecord();
    //medical.UserID = userID;
    medical.HospitalID = Integer.parseInt(ID);
    medical.UserID = Integer.parseInt(uID);
    //medical.VisitDateTime = visitDateTime;
    medical.Diagnosis = dino;
    medical.Prescription = prtion;
    medical.Now = LocalDateTime.now();
    //medical.MedicalRecordID = medicalRecordID;

    try {
      new org.example.database.MedicalRecordDB().insert(medical);
    } catch (Exception e) {
      log.error("Error inserting medical", e);
    }

    App.getInstance().receiveEvent();

    dispose();
  }
}
