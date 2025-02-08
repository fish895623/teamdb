package org.example.widgets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Frame;

public class AppendHealthData extends Frame {
  private static final Logger log = LoggerFactory.getLogger(AppendHealthData.class);
  private int userID;

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
