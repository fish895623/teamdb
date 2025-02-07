package org.example.widgets;

import org.example.model.HealthData;


import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HealthDataTableModel extends AbstractTableModel {
    private List<HealthData> healthDatas;
    private final String[] columnNames =
            {"HealthDataID", "UserID", "MeasurementDateTime", "Height", "Weight", "BodyFatPercentage","BloodPressureSystolic","BloodPressureDiastolic"
            ,"BloodSugar","HeartRate","StressLevel"};

    public HealthDataTableModel(List<HealthData> healthDatas) {
        this.healthDatas = healthDatas;
    }

    @Override
    public int getRowCount() {
        return healthDatas.size();
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
        HealthData healthData = healthDatas.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> healthData.HealthDataID;
            case 1 -> healthData.UserID;
            case 2 -> healthData.MeasurementDateTime;
            case 3 -> healthData.Height;
            case 4 -> healthData.Weight;
            case 5 -> healthData.BodyFatPercentage;
            case 6 -> healthData.BloodPressureSystolic;
            case 7 -> healthData.BloodSugar;
            case 8 -> healthData.HeartRate;
            case 9 -> healthData.StressLevel;
            default -> null;
        };
    }

    public void setHealthDatas(List<HealthData> healthDatas) {
        this.healthDatas = healthDatas;
        fireTableDataChanged();
    }
}


