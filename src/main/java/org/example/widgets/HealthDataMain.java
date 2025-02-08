package org.example.widgets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HealthDataMain extends Frame {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(HealthDataMain.class);

  public HealthDataMain() {
    super("Health Data");
    setSize(300, 400);
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
  }

  public static void main(String[] args) {
    HealthDataMain.getInstance().setVisible(true);
  }

  public static HealthDataMain getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void createTable() {

  }


  private static class LazyHolder {
    private static final HealthDataMain INSTANCE = new HealthDataMain();
  }
}
