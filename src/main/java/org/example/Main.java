package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
  public static void main(String[] args) {
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
