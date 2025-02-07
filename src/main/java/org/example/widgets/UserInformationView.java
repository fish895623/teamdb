package org.example.widgets;

import org.example.database.UserDB;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
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
  Button viewHealth;
  Button displayDiet;
  Button displayExercise;
  Button medicalRecord;
  private List<User> users;

  AppendDiet appendDiet;

  private UserInformationView() {
    super("User Information");
    setSize(300, 400);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }
    });

    insertHealth = new Button("Enter Health");
    insertDiet = new Button("Enter Diet");
    insertExercise = new Button("Enter Exercise");
    viewHealth = new Button("Display Health");
    displayDiet = new Button("Display Diet");
    displayExercise = new Button("Display Exercise");
    medicalRecord = new Button("Medical Record");
    users = List.of(new User());
    nameLabel = new JLabel();
    birthDateLabel = new JLabel();
    genderLabel = new JLabel();
    contactLabel = new JLabel();

    insertHealth.addActionListener(e -> {
      log.info("Insert Health button clicked");
    });
    insertDiet.addActionListener(e -> {
      appendDiet = AppendDiet.getInstance();
      appendDiet.setUserID(users.get(0).userID);
      appendDiet.setVisible(true);
      log.info("Insert Diet button clicked");
    });
    insertExercise.addActionListener(e -> {
      log.info("Insert Exercise button clicked");
    });
    viewHealth.addActionListener(e -> {
      log.info("View Health button clicked");
    });
    displayDiet.addActionListener(e -> {
      log.info("Display Diet button clicked");
    });
    displayExercise.addActionListener(e -> {
      log.info("Display Exercise button clicked");
    });
    medicalRecord.addActionListener(e -> {
      log.info("Medical Record button clicked");
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
    panel.add(viewHealth);
    panel.add(displayDiet);
    panel.add(displayExercise);

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
