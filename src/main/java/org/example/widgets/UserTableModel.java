package org.example.widgets;

import org.example.model.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
  private final List<User> users;
  private final String[] columnNames = {"UserID", "Name", "BirthDate", "Gender", "ContactNumber", "Password"};

  public UserTableModel(List<User> users) {
    this.users = users;
  }

  @Override
  public int getRowCount() {
    return users.size();
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
    User user = users.get(rowIndex);
    return switch (columnIndex) {
      case 0 -> user.userID;
      case 1 -> user.name;
      case 2 -> user.birthDate;
      case 3 -> user.gender;
      case 4 -> user.contactNumber;
      case 5 -> user.password;
      default -> null;
    };
  }
}
