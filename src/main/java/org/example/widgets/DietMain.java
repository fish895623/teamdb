package org.example.widgets;

import org.example.database.DietDB;
import org.example.model.Diet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class DietMain extends Frame {
  private static final Logger log = LoggerFactory.getLogger(DietMain.class);
  List<Diet> data;
  DietTableModel dietTableModel;
  Long hospitalID;
  int userID = 0;

  DietMain() throws SQLException {
    super("Diet Management");
    setSize(600, 400);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        dispose();
      }

      @Override
      public void windowActivated(WindowEvent event) {
        log.info("Window activated");
        try {
          refreshData();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    });

    JTable table = createTable();
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
  }

  public static DietMain getInstance() {
    return LazyHolder.INSTANCE;
  }

  private void refreshData() throws SQLException {
    log.info("Refreshing data");
    data = new DietDB().findByUserID(userID);
    dietTableModel.setData(data);

    repaint();
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public JTable createTable() {
    try {
      data = new DietDB().findByUserID(userID);
    } catch (Exception e) {
      log.error("Error getting HealthData", e);
    }
    dietTableModel = new DietTableModel();
    dietTableModel.setData(data);

    return new JTable(dietTableModel);
  }


  private static class LazyHolder {
    private static final DietMain INSTANCE;

    static {
      try {
        INSTANCE = new DietMain();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
