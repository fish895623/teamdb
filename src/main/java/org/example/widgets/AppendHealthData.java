package org.example.widgets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;

public class AppendHealthData extends JFrame {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AppendHealthData.class);
  private int userID;

  AppendHealthData() {
    super("Add Health Data");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  public static AppendHealthData getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }


  private static class LazyHolder {
    private static final AppendHealthData INSTANCE = new AppendHealthData();
  }
}
