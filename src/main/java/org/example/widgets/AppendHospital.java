package org.example.widgets;

import java.awt.*;

/**
 * This class is responsible for appending a new hospital to the database.
 */
public class AppendHospital extends Frame {
  public AppendHospital() {
    super("Append Hospital");
    setSize(400, 300);
    setLayout(new GridLayout(0, 2));
    add(new Label("Hospital Name:"));
    add(new TextField());
    add(new Label("Hospital Address:"));
    add(new TextField());
    add(new Label("Hospital Contact Number:"));
    add(new TextField());
    add(new Label("Hospital Email:"));
    add(new TextField());
    add(new Button("Submit"));
    add(new Button("Cancel"));
    setVisible(true);
  }
}
