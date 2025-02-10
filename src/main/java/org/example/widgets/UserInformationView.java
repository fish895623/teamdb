package org.example.widgets;

import org.example.database.UserDB;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JLabel;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class UserInformationView extends Frame {
  private static final Logger log = LoggerFactory.getLogger(UserInformationView.class);
  JLabel nameLabel;
  JLabel birthDateLabel;
  JLabel genderLabel;
  JLabel contactLabel;

  Button insertHealth;
  Button insertDiet;
  Button insertExercise;
  Button insertMediaRecord;

  Button displayHeathData;
  Button displayDiet;
  Button displayExercise;
  Button displayMediaRecord;

  AppendDiet appendDiet;
  AppendExercise appendExercise;
  AppendMedicalRecord appendMedicalRecord;
  AppendHealthData appendHealthData;

  DietMain dietMain;
  ExerciseMain exerciseMain;
  MedicalRecordMain medicalRecordMain;
  HealthDataMain healthDataMain;

  private List<User> users;

  private UserInformationView() {
    super("User Information");
    setSize(300, 600);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }
    });

    insertHealth = new Button("Enter Health");
    insertDiet = new Button("Enter Diet");
    insertExercise = new Button("Enter Exercise");
    insertMediaRecord = new Button("Enter Media Record");
    displayHeathData = new Button("Display Health");
    displayDiet = new Button("Display Diet");
    displayExercise = new Button("Display Exercise");
    displayMediaRecord = new Button("Display Medical Record");
    users = List.of(new User());
    nameLabel = new JLabel();
    birthDateLabel = new JLabel();
    genderLabel = new JLabel();
    contactLabel = new JLabel();

    insertHealth.addActionListener(e -> {
      log.info("Insert Health button clicked");
      appendHealthData = AppendHealthData.getInstance();
      appendHealthData.setUserID(users.get(0).userID);
      appendHealthData.setVisible(true);
    });
    insertDiet.addActionListener(e -> {
      log.info("Insert Diet button clicked");
      appendDiet = AppendDiet.getInstance();
      appendDiet.setUserID(users.get(0).userID);
      appendDiet.setVisible(true);
    });
    insertExercise.addActionListener(e -> {
      log.info("Insert Exercise button clicked");
      appendExercise = AppendExercise.getInstance();
      appendExercise.setUserID(users.get(0).userID);
      appendExercise.setVisible(true);
    });
    insertMediaRecord.addActionListener(e -> {
      log.info("Insert Medical Record button clicked");
      appendMedicalRecord = AppendMedicalRecord.getInstance();
      appendMedicalRecord.setUserID(users.get(0).userID);
      appendMedicalRecord.setHospitalID(users.get(0).hospitalID);
      appendMedicalRecord.setVisible(true);
    });
    displayHeathData.addActionListener(e -> {
      log.info("View Health button clicked");
      healthDataMain = HealthDataMain.getInstance();
      healthDataMain.setUserID(users.get(0).userID);
      healthDataMain.setVisible(true);
    });
    displayDiet.addActionListener(e -> {
      log.info("Display Diet button clicked");
      dietMain = DietMain.getInstance();
      dietMain.setUserID(users.get(0).userID);
      dietMain.setVisible(true);
    });
    displayExercise.addActionListener(e -> {
      log.info("Display Exercise button clicked");
      exerciseMain = ExerciseMain.getInstance();
      exerciseMain.setUserID(users.get(0).userID);
      exerciseMain.setVisible(true);
    });
    displayMediaRecord.addActionListener(e -> {
      log.info("Display Medical Record button clicked");
      medicalRecordMain = MedicalRecordMain.getInstance();
      medicalRecordMain.setUserID(users.get(0).userID);
      medicalRecordMain.setHospitalID(users.get(0).hospitalID);
      medicalRecordMain.setVisible(true);
    });

    GridLayout gridLayout = new GridLayout(0, 1);
    Panel panel0 = new Panel(gridLayout);
    var labelInfo = new Label("User Information");
    labelInfo.setAlignment(Label.CENTER);
    panel0.add(labelInfo);
    panel0.add(nameLabel);
    panel0.add(birthDateLabel);
    panel0.add(genderLabel);
    panel0.add(contactLabel);

    Panel panel = new Panel();
    panel.add(panel0);
    panel.add(insertHealth);
    panel.add(insertDiet);
    panel.add(insertExercise);
    panel.add(insertMediaRecord);
    panel.add(displayHeathData);
    panel.add(displayDiet);
    panel.add(displayExercise);
    panel.add(displayMediaRecord);

    Panel userMedicalInformation = new Panel(new GridLayout(0, 2));
    Panel panel1 = new Panel(new GridLayout(0, 1));
    var health_data = new Label("Health Data");
    health_data.setAlignment(Label.CENTER);
    panel1.add(health_data);

    userMedicalInformation.add(new Label("Height")); // latest height
    userMedicalInformation.add(new Label("Height"));
    userMedicalInformation.add(new Label("Weight")); // latest weight
    userMedicalInformation.add(new Label("Weight"));
    userMedicalInformation.add(new Label("BodyFatPercentage")); // latest body fat percentage
    userMedicalInformation.add(new Label("BodyFatPercentage"));
    userMedicalInformation.add(new Label("BloodPressureSystolic")); // latest blood pressure systolic
    userMedicalInformation.add(new Label("BloodPressureSystolic"));
    userMedicalInformation.add(new Label("BloodPressureSystolic")); // average blood pressure systolic 3years
    userMedicalInformation.add(new Label("BloodPressureSystolic"));
    userMedicalInformation.add(new Label("BloodPressureDiastolic")); // latest blood pressure diastolic
    userMedicalInformation.add(new Label("BloodPressureDiastolic"));
    userMedicalInformation.add(new Label("BloodPressureDiastolic")); // average blood pressure diastolic 3years
    userMedicalInformation.add(new Label("BloodPressureDiastolic"));

    panel1.add(userMedicalInformation);

    panel.add(panel1);

    add(panel);
  }

  public static UserInformationView getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setUserID(int userID) throws SQLException {
    log.info("User ID: {}", userID);
    // Get user information from database
    users = new UserDB().findByUserID(userID);
    log.info("User: {}", users.get(0).name);
    nameLabel.setText("Name: " + users.get(0).name);
    birthDateLabel.setText("Birth Date: " + users.get(0).birthDate);
    genderLabel.setText("Gender: " + users.get(0).gender);
    contactLabel.setText("Contact: " + users.get(0).contactNumber);

    repaint();
  }

  private static class LazyHolder {
    private static final UserInformationView INSTANCE = new UserInformationView();
  }

}
