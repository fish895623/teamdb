package org.example;

public class DatabaseManager {
  private static DatabaseManager instance;

  private DatabaseManager() {
  }

  public static synchronized DatabaseManager getInstance() {
    if (instance == null) {
      instance = new DatabaseManager();
    }
    return instance;
  }

}
