package org.example.widgets;

import org.example.App;
import org.example.database.UserDB;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AppendUser extends JFrame {
  private static final Logger log = LoggerFactory.getLogger(AppendUser.class);

  private final JTextField nameTextField;
  private final JTextField contactNumberTextField;
  private final JTextField genderTextField;

  private final JButton submitButton;
  private final JButton cancelButton;

  private final JComboBox<String> yearBox;
  private final JComboBox<String> monthBox;
  private final JComboBox<String> dayBox;

  private int userID;
  private int hospitalID;

  public AppendUser() {
    super("Append Exercise");

    // UI Layout 설정
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // 간격 설정
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // 컴포넌트 생성

    contactNumberTextField = new JTextField(10);
    genderTextField = new JTextField(10);
    nameTextField = new JTextField(10);

    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    // 날짜 선택 (JComboBox)
    String[] years = new String[200];
    for (int i = 0; i < years.length; i++) {
      years[i] = 1900 + i + "";
    }
    yearBox = new JComboBox<String>(years);

    String[] months = new String[12];
    for (int i = 0; i < months.length; i++) {
      if (i < 9) {
        months[i] = "0" + (i + 1);
      } else {
        months[i] = i + 1 + "";
      }
    }
    monthBox = new JComboBox<String>(months);

    String[] days = new String[31];
    for (int i = 0; i < days.length; i++) {
      if (i < 9) {
        days[i] = "0" + (i + 1);
      } else {
        days[i] = i + 1 + "";
      }

    }
    dayBox = new JComboBox<String>(days);

    // UI 추가 (GridBagLayout 사용)
    int row = 0;

    addLabelAndComponent("Name:", nameTextField, gbc, row++);

    // 날짜 선택 추가 (연도, 월, 일)
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.gridwidth = 1;
    add(new JLabel("Birth Date:"), gbc);
    gbc.gridx = 1;
    add(yearBox, gbc);
    gbc.gridx = 2;
    add(monthBox, gbc);
    gbc.gridx = 3;
    add(dayBox, gbc);
    row++;

    addLabelAndComponent("Gender:", genderTextField, gbc, row++);
    addLabelAndComponent("ContactNumber:", contactNumberTextField, gbc, row++);

    // 버튼 추가
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.gridwidth = 2;
    add(submitButton, gbc);
    gbc.gridx = 2;
    add(cancelButton, gbc);

    // 버튼 이벤트 리스너
    submitButton.addActionListener(e -> {
      try {
        submitButtonClicked();
      } catch (ParseException ex) {
        ex.printStackTrace();
      }
    });

    cancelButton.addActionListener(e -> {
      log.info("Cancel button clicked");
      dispose();
    });

    setVisible(true);
  }

  public static AppendUser getInstance() {
    return LazyHolder.INSTANCE;
  }

  private void addLabelAndComponent(String label, JComponent component, GridBagConstraints gbc, int row) {
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.gridwidth = 1;
    add(new JLabel(label), gbc);

    gbc.gridx = 1;
    gbc.gridwidth = 3;
    add(component, gbc);
  }

  public void submitButtonClicked() throws ParseException {
    log.info("Submit button clicked");

    String nameString = nameTextField.getText();
    String year = (String) yearBox.getSelectedItem();
    String month = (String) monthBox.getSelectedItem();
    String day = (String) dayBox.getSelectedItem();
    String genderString = genderTextField.getText();
    String contactNumberString = contactNumberTextField.getText();

    var exercise = new User();
    exercise.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse("%s-%s-%s".formatted(year, month, day));
    exercise.gender = genderString; // TODO: change into select box
    exercise.contactNumber = contactNumberString;
    exercise.name = nameString;


    // DB 삽입
    try {
      new UserDB().insert(exercise);
    } catch (Exception e) {
      log.error("Error inserting exercise", e);
    }

    // 이벤트 전송
    App.getInstance().receiveEvent();

    dispose();
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public void setHospitalID(int hospitalID) {
    this.hospitalID = hospitalID;
  }

  private static class LazyHolder {
    private static final AppendUser INSTANCE = new AppendUser();
  }
}
