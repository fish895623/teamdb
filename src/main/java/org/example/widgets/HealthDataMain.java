package org.example.widgets;

import org.example.database.ExerciseDB;
import org.example.database.HealthDataDB;
import org.example.model.HealthData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class HealthDataMain extends Frame {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(HealthDataMain.class);
  Button button = new Button("Add Health Data");
  Button refreshButton = new Button("Refresh");
  List<HealthData> data;
  HealthDataTableModel healthDataTableModel;

  public HealthDataMain() {
    super("Health Data");
    setSize(900, 400);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        dispose();
      }

      @Override
      public void windowActivated(WindowEvent event) {
        log.info("Window activated");
      }
    });

    add(button, BorderLayout.SOUTH);
    add(refreshButton, BorderLayout.NORTH);
    button.addActionListener(e -> {
      log.info("Button clicked");
    });
    refreshButton.addActionListener(e -> {
    });
    JTable table = createTable();
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
  }

  public static void main(String[] args) {
    HealthDataMain.getInstance().setVisible(true);
  }

  public static HealthDataMain getInstance() {
    return LazyHolder.INSTANCE;
  }

  public JTable createTable() {
    try {
      data = new HealthDataDB().getAll();
    } catch (Exception e) {
      log.error("Error getting HealthData", e);
    }
    healthDataTableModel = new HealthDataTableModel(data);

    return new JTable(healthDataTableModel);
  }


  private static class LazyHolder {
    private static final HealthDataMain INSTANCE = new HealthDataMain();
  }
}
