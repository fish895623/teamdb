package org.example;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
  public static void main(String[] args) {
    Frame frame = new Frame("AWT Example");
    Button button = new Button("Click Me");

    button.addActionListener(e -> System.out.println("Button Clicked!"));

    frame.add(button, BorderLayout.CENTER);
    frame.setSize(300, 200);
    frame.setVisible(true);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
      }
    });
  }
}
