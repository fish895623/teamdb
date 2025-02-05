package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Prop {
  private static final Logger log = LoggerFactory.getLogger(Prop.class);
  private static final Properties properties = new Properties();

  static {
    try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (input != null) {
        properties.load(input);
      } else {
        log.warn("config.properties not found");
      }
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
  }

  public static String get(String key) {
    return properties.getProperty(key);
  }
}
