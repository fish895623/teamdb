package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws SQLException {
    try {
      DatabaseManager.migrate();
    } catch (IOException e) {
      log.error("database migration failed", e);
      return;
    }

    App.getInstance();
  }
}
