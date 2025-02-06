package org.example.widgets;

import org.example.model.Hospital;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HospitalTableModel extends AbstractTableModel {
  private final List<Hospital> hospitals;
  private final String[] columnNames = {"ID", "Hospital Name", "Address", "Contact Number"};

  public HospitalTableModel(List<Hospital> hospitals) {
    this.hospitals = hospitals;
  }

  @Override
  public int getRowCount() {
    return hospitals.size();
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
    var hospital = hospitals.get(rowIndex);
    return switch (columnIndex) {
      case 0 -> hospital.HospitalID;
      case 1 -> hospital.HospitalName;
      case 2 -> hospital.Address;
      case 3 -> hospital.ContactNumber;
      default -> null;
    };
  }
}
