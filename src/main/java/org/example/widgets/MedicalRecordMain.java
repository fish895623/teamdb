package org.example.widgets;

import org.example.database.MedicalRecordDB;
import org.example.model.MedicalRecord;
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

public class MedicalRecordMain extends Frame {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(MedicalRecordMain.class);
  List<MedicalRecord> data;
  MedicalRecordTableModel dataTableModel;
  int hospitalID;
  private int userID;

  MedicalRecordMain() throws SQLException {
    super("Medical Record");
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

  public static MedicalRecordMain getInstance() {
    return LazyHolder.INSTANCE;
  }

  private void refreshData() throws SQLException {
    data = new MedicalRecordDB().findByUserIDAndHospital(userID, hospitalID);
    dataTableModel.setMedicalRecords(data);

    repaint();
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = hospitalID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public JTable createTable() throws SQLException {
    try {
      data = new MedicalRecordDB().findByUserIDAndHospital(userID, hospitalID);
    } catch (Exception e) {
      log.error("Error getting HealthData", e);
    }
    dataTableModel = new MedicalRecordTableModel();
    dataTableModel.setMedicalRecords(data);

    return new JTable(dataTableModel);
  }


  private static class LazyHolder {
    private static final MedicalRecordMain INSTANCE;

    static {
      try {
        INSTANCE = new MedicalRecordMain();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
