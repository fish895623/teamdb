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

public class UserInformationView extends JFrame {
  private static final Logger log = LoggerFactory.getLogger(UserInformationView.class);
  private List<User> users;

  private UserInformationView() {
    super("User Information");
    setSize(300, 400);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }
    });

    GridLayout gridLayout = new GridLayout(0, 2);
    setLayout(gridLayout);

  }

  public static UserInformationView getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setUserID(int userID) throws SQLException {
    log.info("User ID: {}", userID);
    // Get user information from database
    users = new UserDB().findByUserID(userID);
  }


  private static class LazyHolder {
    private static final UserInformationView INSTANCE = new UserInformationView();
  }

}
