package org.example.widgets;

import org.example.model.HealthData;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HealDataTableModel extends AbstractTableModel {
  private final String[] columnNames =
      {"HealthDataID", "UserID", "MeasurementDateTime", "Height", "Weight", "BodyFatPercentage",
          "BloodPressureSystolic", "BloodPressureDiastolic", "BloodSugar", "HeartRate", "StressLevel",};
  private List<HealthData> data;

  public HealDataTableModel(List<HealthData> data) {
    this.data = data;
  }

  public void setData(List<HealthData> data) {
    this.data = data;
    fireTableDataChanged();
  }

  @Override
  public int getRowCount() {
    return data.size();
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
    HealthData d = data.get(rowIndex);
    return switch (columnIndex) {
      case 0 -> d.HealthDataID;
      case 1 -> d.MeasurementDateTime;
      case 2 -> d.Height;
      case 3 -> d.Weight;
      case 4 -> d.BodyFatPercentage;
      case 5 -> d.BloodPressureSystolic;
      case 6 -> d.BloodPressureDiastolic;
      case 7 -> d.BloodSugar;
      case 8 -> d.HeartRate;
      case 9 -> d.StressLevel;
      default -> null;
    };
  }
}
