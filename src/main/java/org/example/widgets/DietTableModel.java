package org.example.widgets;

import org.example.model.Diet;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DietTableModel extends AbstractTableModel {
    private final List<Diet> diets;
    private final String[] columnNames = {"DietID", "UserID", "MealDateTime", "FoodName", "Quantity", "Calories"};

    public DietTableModel(List<Diet> diets) {
        this.diets = diets;
    }

    @Override
    public int getRowCount() {
        return diets.size();
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
        Diet diet = diets.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return diet.DietID;

            case 1:
                return diet.UserID;

            case 2:
                return diet.MealDateTime;

            case 3:
                return diet.FoodName;

            case 4:
                return diet.Quantity;

            case 5:
                return diet.Calories;
            default:
                return null;
        }
    }
}
