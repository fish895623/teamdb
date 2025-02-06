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
    switch (columnIndex) {
      case 0:
        return user.userID;
      case 1:
        return user.name;
      case 2:
        return user.birthDate;
      case 3:
        return user.gender;
      case 4:
        return user.contactNumber;
      case 5:
        return user.password;
      default:
        return null;
    }
  }
}
