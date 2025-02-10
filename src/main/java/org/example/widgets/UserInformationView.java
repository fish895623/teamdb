package org.example.widgets;

import org.example.database.HealthDataDB;
import org.example.database.UserDB;
import org.example.model.User;
import org.example.model.UserInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JLabel;
import java.awt.Button;
import java.awt.Color;
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

  Label heightLabel;
  Label weightLabel;
  Label bodyFatLabel;
  Label bloodPressureSystolicLabel;
  Label bloodPressureDiastolicLabel;
  Label bloodPressureSystolicAverageLabel;
  Label bloodPressureDiastolicAverageLabel;

  Label bloodPressureSystolicWarningLabel;
  Label bloodPressureDiastolicWarningLabel;

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
  UserInformation userInformation;
  private List<User> users;
  private int userID;

  private UserInformationView() {
    super("User Information");
    setSize(500, 600);
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

    heightLabel = new Label();
    weightLabel = new Label();
    bodyFatLabel = new Label();
    bloodPressureSystolicLabel = new Label();
    bloodPressureDiastolicLabel = new Label();
    bloodPressureSystolicAverageLabel = new Label();
    bloodPressureDiastolicAverageLabel = new Label();
    bloodPressureDiastolicWarningLabel = new Label();
    bloodPressureSystolicWarningLabel = new Label();

    heightLabel.setAlignment(Label.RIGHT);
    weightLabel.setAlignment(Label.RIGHT);
    bodyFatLabel.setAlignment(Label.RIGHT);
    bloodPressureSystolicLabel.setAlignment(Label.RIGHT);
    bloodPressureDiastolicLabel.setAlignment(Label.RIGHT);
    bloodPressureSystolicAverageLabel.setAlignment(Label.RIGHT);
    bloodPressureDiastolicAverageLabel.setAlignment(Label.RIGHT);
    bloodPressureDiastolicWarningLabel.setAlignment(Label.CENTER);
    bloodPressureSystolicWarningLabel.setAlignment(Label.CENTER);

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

    Panel userMedicalInformation = new Panel(new GridLayout(0, 3));
    Panel panel1 = new Panel(new GridLayout(0, 1));
    var health_data = new Label("Health Data");
    health_data.setAlignment(Label.CENTER);
    panel1.add(health_data);

    userMedicalInformation.add(new Label("Height")); // latest height
    userMedicalInformation.add(heightLabel); // latest height
    userMedicalInformation.add(new Label());
    userMedicalInformation.add(new Label("Weight")); // latest weight
    userMedicalInformation.add(weightLabel); // latest weight
    userMedicalInformation.add(new Label());
    userMedicalInformation.add(new Label("BodyFatPercentage")); // latest body fat percentage
    userMedicalInformation.add(bodyFatLabel); // latest body fat percentage
    userMedicalInformation.add(new Label());
    userMedicalInformation.add(new Label("Systolic BP")); // latest blood pressure systolic
    userMedicalInformation.add(bloodPressureSystolicLabel); // latest blood pressure systolic
    userMedicalInformation.add(bloodPressureSystolicWarningLabel); // latest blood pressure systolic
    userMedicalInformation.add(new Label("BloodPressureDiastolic")); // latest blood pressure diastolic
    userMedicalInformation.add(bloodPressureDiastolicLabel); // latest blood pressure diastolic
    userMedicalInformation.add(bloodPressureDiastolicWarningLabel);
    userMedicalInformation.add(new Label("Systolic BP Average")); // average blood pressure systolic 3years
    userMedicalInformation.add(bloodPressureSystolicAverageLabel); // average blood pressure systolic 3years
    userMedicalInformation.add(new Label());
    userMedicalInformation.add(new Label("Diastolic BP Average")); // average blood pressure diastolic 3years
    userMedicalInformation.add(bloodPressureDiastolicAverageLabel); // average blood pressure diastolic 3years
    userMedicalInformation.add(new Label());


    panel1.add(userMedicalInformation);

    panel.add(panel1);

    add(panel);
  }

  public static UserInformationView getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setUserID(int userID) throws SQLException {
    this.userID = userID;
    log.info("User ID: {}", userID);
    // Get user information from database
    users = new UserDB().findByUserID(userID);
    log.info("User: {}", users.get(0).name);
    nameLabel.setText("Name: " + users.get(0).name);
    birthDateLabel.setText("Birth Date: " + users.get(0).birthDate);
    genderLabel.setText("Gender: " + users.get(0).gender);
    contactLabel.setText("Contact: " + users.get(0).contactNumber);


    setUserInformationLabel();

    repaint();
  }


  public void setUserInformationLabel() {
    try {
      userInformation = new HealthDataDB().getUserHealthData(this.userID);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    heightLabel.setText(String.format("%.2fcm", userInformation.Height));
    weightLabel.setText(String.format("%.2fkg", userInformation.Weight));
    bodyFatLabel.setText(String.format("%.2f%%", userInformation.BodyFatPercentage));
    bloodPressureSystolicLabel.setText(String.format("%.2fmmHg", (float) userInformation.BloodPressureSystolic));
    bloodPressureDiastolicLabel.setText(String.format("%.2fmmHg", (float) userInformation.BloodPressureDiastolic));
    bloodPressureSystolicAverageLabel.setText(String.format("%.2fmmHg", userInformation.BloodPressureSystolicAverage));
    bloodPressureDiastolicAverageLabel.setText(
        String.format("%.2fmmHg", userInformation.BloodPressureDiastolicAverage));

    if (userInformation.BloodPressureSystolic > 180) {
      bloodPressureSystolicWarningLabel.setText("Severe");
      bloodPressureSystolicWarningLabel.setBackground(new Color(255, 0, 0)); // RED
    } else if (userInformation.BloodPressureSystolic > 160) {
      bloodPressureSystolicWarningLabel.setText("Moderate");
      bloodPressureSystolicWarningLabel.setBackground(new Color(255, 192, 203)); // PINK
    } else if (userInformation.BloodPressureSystolic > 140) {
      bloodPressureSystolicWarningLabel.setText("Mild");
      bloodPressureSystolicWarningLabel.setBackground(new Color(255, 165, 0)); // ORANGE
    } else if (userInformation.BloodPressureSystolic > 120) {
      bloodPressureSystolicWarningLabel.setText("Pre-hypertension");
      bloodPressureSystolicWarningLabel.setBackground(new Color(255, 255, 0)); // YELLOW
    } else if (userInformation.BloodPressureSystolic > 90) {
      bloodPressureSystolicWarningLabel.setText("Normal");
      bloodPressureSystolicWarningLabel.setBackground(new Color(0, 255, 0)); // GREEN
    } else {
      bloodPressureSystolicWarningLabel.setText("Low");
      bloodPressureSystolicWarningLabel.setBackground(new Color(0, 0, 255)); // BLUE
    }

    if (userInformation.BloodPressureDiastolic > 110) {
      bloodPressureDiastolicWarningLabel.setText("Severe");
      bloodPressureDiastolicWarningLabel.setBackground(new Color(255, 0, 0)); // RED
    } else if (userInformation.BloodPressureDiastolic > 100) {
      bloodPressureDiastolicWarningLabel.setText("Moderate");
      bloodPressureDiastolicWarningLabel.setBackground(new Color(255, 192, 203)); // RED
    } else if (userInformation.BloodPressureDiastolic > 90) {
      bloodPressureDiastolicWarningLabel.setText("Mild");
      bloodPressureDiastolicWarningLabel.setBackground(new Color(255, 165, 0)); // ORANGE
    } else if (userInformation.BloodPressureDiastolic > 80) {
      bloodPressureDiastolicWarningLabel.setText("Pre-hypertension");
      bloodPressureDiastolicWarningLabel.setBackground(new Color(255, 255, 0)); // YELLOW
    } else if (userInformation.BloodPressureDiastolic > 60) {
      bloodPressureDiastolicWarningLabel.setText("Normal");
      bloodPressureDiastolicWarningLabel.setBackground(new Color(0, 255, 0)); // GREEN
    } else {
      bloodPressureDiastolicWarningLabel.setText("Low");
      bloodPressureDiastolicWarningLabel.setBackground(new Color(0, 0, 255)); // BLUE
    }
  }

  public void refresh() {
    setUserInformationLabel();
  }

  private static class LazyHolder {
    private static final UserInformationView INSTANCE = new UserInformationView();
  }

}
