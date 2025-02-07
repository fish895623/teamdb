package org.example.widgets;

import org.example.model.MedicalRecord;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MedicalRecordTableModel extends AbstractTableModel {
  private List<MedicalRecord> MedicalRecords;
  private final String[] columnNames =
      {"UserID", "HospitalID", "VisitDateTime", "Diagnosis", "Prescription", "MedicalRecordID"};

  public MedicalRecordTableModel(List<MedicalRecord> MedicalRecords) {
    this.MedicalRecords = MedicalRecords;
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
      case 0 -> MedicalRecord.MedicalRecordID;
      case 1 -> MedicalRecord.HospitalID;
      case 2 -> MedicalRecord.VisitDateTime;
      case 3 -> MedicalRecord.Diagnosis;
      case 4 -> MedicalRecord.Prescription;
      case 5 -> MedicalRecord.MedicalRecordID;
      default -> null;
    };
  }

  public void setMedicalRecords(List<MedicalRecord> MedicalRecords) {
    this.MedicalRecords = MedicalRecords;
    fireTableDataChanged();
  }
}
