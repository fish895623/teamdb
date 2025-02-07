package org.example.widgets;

import org.example.App;
import org.example.model.Hospital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * This class is responsible for appending a new hospital to the database.
 */
public class AppendHospital extends Frame {
  private static final Logger log = LoggerFactory.getLogger(AppendHospital.class);
  private final TextField hospitalName;
  private final TextField hospitalAddress;
  private final TextField hospitalContactNumber;
  private final Button submitButton;
  private final Button cancelButton;



  public AppendHospital() {
    super("Append Hospital");

    /* initialize the text fields */
    hospitalName = new TextField();
    hospitalAddress = new TextField();
    hospitalContactNumber = new TextField();
    submitButton = new Button("Submit");
    cancelButton = new Button("Cancel");

    setSize(400, 300);
    setLayout(new GridLayout(0, 2));
    add(new Label("Hospital Name:"));
    add(hospitalName);
    add(new Label("Hospital Address:"));
    add(hospitalAddress);
    add(new Label("Hospital Contact Number:"));
    add(hospitalContactNumber);
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

    String name = hospitalName.getText();
    String address = hospitalAddress.getText();
    String contactNumber = hospitalContactNumber.getText();

    Hospital hospital = new Hospital();
    hospital.HospitalName = name;
    hospital.Address = address;
    hospital.ContactNumber = contactNumber;

    try {
      new org.example.database.HospitalDB().insert(hospital);
    } catch (Exception e) {
      log.error("Error inserting hospital", e);
    }

    App.getInstance().receiveEvent();

    dispose();
  }
}
