package org.example.widgets;

import org.example.model.Diet;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author kimkyungmin12
 * @author fish895623
 */
public class DietTableModel extends AbstractTableModel {
  private List<Diet> diets;
  private final String[] columnNames =
      {"DietID", "UserID", "MealDateTime", "FoodName", "Quantity", "Calories"};

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
    return switch (columnIndex) {
      case 0 -> diet.DietID;
      case 1 -> diet.UserID;
      case 2 -> diet.MealDateTime;
      case 3 -> diet.FoodName;
      case 4 -> diet.Quantity;
      case 5 -> diet.Calories;
      default -> null;
    };
  }

  public void setData(List<Diet> diets) {
    this.diets = diets;
    fireTableDataChanged();
  }
}
