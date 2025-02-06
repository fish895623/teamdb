package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App extends Frame implements KeyListener {
  private static final Logger log = LoggerFactory.getLogger(App.class);

  public App() {
    super("Member Management");
    setSize(300, 200);
    setVisible(true);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
      }
    });
    addKeyListener(this);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    log.info("Key Typed: {}", e.getKeyChar());
  }

  @Override
  public void keyPressed(KeyEvent e) {
    log.info("Key Pressed: {}", e.getKeyChar());

    if (e.getKeyCode() == 'Q') {
      System.exit(0);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    log.info("Key Released: {}", e.getKeyChar());
  }
}
