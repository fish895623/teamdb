package org.example.widgets;

import org.example.model.HealthData;


import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HealthDataTableModel extends AbstractTableModel {
    private List<HealthData> healthDataList;
    private final String[] columnNames =
            {"HealthDataID", "UserID", "MeasurementDateTime", "Height", "Weight", "BodyFatPercentage","BloodPressureSystolic","BloodPressureDiastolic"
            ,"BloodSugar","HeartRate","StressLevel"};

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
        HealthData healthRecord  = healthDataList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> healthRecord.HealthDataID;
            case 1 -> healthRecord.UserID;
            case 2 -> healthRecord.MeasurementDateTime;
            case 3 -> healthRecord.Height;
            case 4 -> healthRecord.Weight;
            case 5 -> healthRecord.BodyFatPercentage;
            case 6 -> healthRecord.BloodPressureSystolic;
            case 7 -> healthRecord.BloodPressureDiastolic;
            case 8 -> healthRecord.BloodSugar;
            case 9 -> healthRecord.HeartRate;
            case 10 -> healthRecord.StressLevel;
            default -> null;
        };
    }

    public void setHealthData(List<HealthData> healthData) {
        this.healthDataList = healthData;
        fireTableDataChanged();
    }
}


