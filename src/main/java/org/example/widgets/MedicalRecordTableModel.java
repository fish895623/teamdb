package org.example.widgets;

import org.example.model.MedicalRecord;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MedicalRecordTableModel extends AbstractTableModel {
  private final String[] columnNames = {"VisitDateTime", "Diagnosis", "Prescription"};
  private List<MedicalRecord> MedicalRecords;

  public MedicalRecordTableModel() {
  }

  @Override
  public int getRowCount() {
    return MedicalRecords.size();
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
    MedicalRecord MedicalRecord = MedicalRecords.get(rowIndex);
    return switch (columnIndex) {
      case 0 -> MedicalRecord.VisitDateTime;
      case 1 -> MedicalRecord.Diagnosis;
      case 2 -> MedicalRecord.Prescription;
      default -> null;
    };
  }

  public void setMedicalRecords(List<MedicalRecord> MedicalRecords) {
    this.MedicalRecords = MedicalRecords;
    fireTableDataChanged();
  }
}
