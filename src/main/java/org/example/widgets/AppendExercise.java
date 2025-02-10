package org.example.widgets;

import org.example.App;
import org.example.filter.FloatFilter;
import org.example.filter.IntegerFilter;
import org.example.model.Exercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;

public class AppendExercise extends JFrame {
  private static final Logger log = LoggerFactory.getLogger(AppendExercise.class);

  private final JTextField caloriesBurned_text;
  private final JTextField duration_text;
  private final JTextField exerciseType_text;

  private final JButton submitButton;
  private final JButton cancelButton;

  private final JComboBox<String> yearBox;
  private final JComboBox<String> monthBox;
  private final JComboBox<String> dayBox;

  private int userID;

  public AppendExercise() {
    super("Append Exercise");

    // UI Layout 설정
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // 간격 설정
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // 컴포넌트 생성

    caloriesBurned_text = new JTextField(10);
    duration_text = new JTextField(10);
    exerciseType_text = new JTextField(10);

    ((AbstractDocument) caloriesBurned_text.getDocument()).setDocumentFilter(new FloatFilter());
    ((AbstractDocument) duration_text.getDocument()).setDocumentFilter(new IntegerFilter());

    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    // 날짜 선택 (JComboBox)
    String[] years = new String[11];
    for (int i = 0; i < years.length; i++) {
      years[i] = 2020 + i + "";
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

    addLabelAndComponent("Exercise Type:", exerciseType_text, gbc, row++);

    // 날짜 선택 추가 (연도, 월, 일)
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.gridwidth = 1;
    add(new JLabel("Exercise Date:"), gbc);
    gbc.gridx = 1;
    add(yearBox, gbc);
    gbc.gridx = 2;
    add(monthBox, gbc);
    gbc.gridx = 3;
    add(dayBox, gbc);
    row++;

    addLabelAndComponent("Duration:", duration_text, gbc, row++);
    addLabelAndComponent("Calories Burned:", caloriesBurned_text, gbc, row++);

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

  public static AppendExercise getInstance() {
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

    String Extype = exerciseType_text.getText();
    String year = (String) yearBox.getSelectedItem();
    String month = (String) monthBox.getSelectedItem();
    String day = (String) dayBox.getSelectedItem();
    String duration = duration_text.getText();
    String calBurn = caloriesBurned_text.getText();

    Exercise exercise = new Exercise();
    exercise.ExerciseDateTime = year + month + day;
    exercise.UserID = this.userID;
    exercise.ExerciseType = Extype;
    exercise.Duration = Integer.parseInt(duration);
    exercise.CaloriesBurned = Integer.parseInt(calBurn);

    // DB 삽입
    try {
      new org.example.database.ExerciseDB().insert(exercise);
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

  private static class LazyHolder {
    private static final AppendExercise INSTANCE = new AppendExercise();
  }
}
