package org.example.widgets;

import org.example.App;
import org.example.filter.IntegerFilter;
import org.example.model.Diet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppendDiet extends JFrame {
  private static final Logger log = LoggerFactory.getLogger(AppendDiet.class);

  private final JTextField MealDateTime;
  private final JTextField FoodName;
  private final JTextField Quantity;
  private final JTextField Calories;

  private final JButton submitButton;
  private final JButton cancelButton;
  public int userId = 0;

  public AppendDiet() {
    super("Append Diet");

    // UI Layout 설정
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // 간격 설정
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // 컴포넌트 생성

    FoodName = new JTextField(10);
    Quantity = new JTextField(10);
    Calories = new JTextField(10);
    MealDateTime = new JTextField(10);

    ((AbstractDocument) Quantity.getDocument()).setDocumentFilter(new IntegerFilter());
    ((AbstractDocument) Calories.getDocument()).setDocumentFilter(new IntegerFilter());

    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    // 날짜 선택 (JComboBox)

    // UI 추가 (GridBagLayout 사용)
    int row = 0;

    addLabelAndComponent("FoodName:", FoodName, gbc, row++);
    addLabelAndComponent("Quantity:", Quantity, gbc, row++);
    addLabelAndComponent("Calories:", Calories, gbc, row++);
    addLabelAndComponent("MealDateTime:", MealDateTime, gbc, row++);

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

  public static AppendDiet getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setUserID(int userID) {
    userId = userID;
  }

  private void addLabelAndComponent(String label, JComponent component, GridBagConstraints gbc,
      int row) {
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

    String fName = FoodName.getText();
    String qtity = Quantity.getText();
    String Cal = Calories.getText();

    Diet diet = new Diet();

    diet.UserID = userId;

    if (MealDateTime.getText().isEmpty()) {
      diet.MealDateTime = new Date(System.currentTimeMillis());
    } else {
      try {
        // TODO: Need flexible date format parser
        diet.MealDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(MealDateTime.getText());
      } catch (ParseException e) {
        log.error(e.toString());
      }
    }

    diet.FoodName = fName;
    diet.Quantity = Integer.parseInt(qtity);
    diet.Calories = Integer.parseInt(Cal);
    // DB 삽입
    try {
      new org.example.database.DietDB().insert(diet);
    } catch (Exception e) {
      log.error("Error inserting diet", e);
    }

    // 이벤트 전송
    App.getInstance().receiveEvent();

    dispose();
  }

  public static class LazyHolder {
    private static final AppendDiet INSTANCE = new AppendDiet();
  }

}
