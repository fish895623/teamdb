package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Properties properties = new Properties();

    try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
      properties.load(input);
    } catch (IOException e) {
      log.warn(e.getMessage());
    }

    log.info("Application Name: {}", properties.getProperty("hello"));

    Frame frame = new Frame("AWT Example");

    frame.setSize(300, 200);
    frame.setVisible(true);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
      }
    });

    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyChar());
      }

      @Override
      public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed: " + e.getKeyChar());
      }

      @Override
      public void keyReleased(KeyEvent e) {
        System.out.println("Key Released: " + e.getKeyChar());
      }
    });
  }
}
