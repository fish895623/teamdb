package org.example.widgets;

import org.example.model.HealthData;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HealthDataTableModel extends AbstractTableModel {
  private final String[] columnNames =
      {"ID", "Date", "Height", "Weight", "BodyFatPercentage", "BloodPressureSystolic", "BloodPressureDiastolic",
          "BloodSugar", "HeartRate", "StressLevel"};
  private List<HealthData> healthDataList;

  public HealthDataTableModel() {}

  public HealthDataTableModel(List<HealthData> healthData) {
    this.healthDataList = healthData;
  }

  @Override
  public int getRowCount() {
    return healthDataList.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    HealthData healthRecord = healthDataList.get(rowIndex);
    return switch (columnIndex) {
      case 0 -> healthRecord.HealthDataID;
      case 1 -> healthRecord.MeasurementDateTime;
      case 2 -> healthRecord.Height;
      case 3 -> healthRecord.Weight;
      case 4 -> healthRecord.BodyFatPercentage;
      case 5 -> healthRecord.BloodPressureSystolic;
      case 6 -> healthRecord.BloodPressureDiastolic;
      case 7 -> healthRecord.BloodSugar;
      case 8 -> healthRecord.HeartRate;
      case 9 -> healthRecord.StressLevel;
      default -> null;
    };
  }

  public void setHealthData(List<HealthData> healthData) {
    this.healthDataList = healthData;
    fireTableDataChanged();
  }
}


