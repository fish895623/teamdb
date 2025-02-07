package org.example.widgets;

import org.example.App;
import org.example.model.Diet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AppendDiet extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(AppendDiet.class);
    
    
    private final JTextField MealDateTime;
    private final JTextField FoodName;
    private final JTextField UserID;
    private final JTextField Quantity;
    private final JTextField Calories;
    
    
    private final JButton submitButton;
    private final JButton cancelButton;

    

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
        
        UserID = new JTextField(10);
        FoodName = new JTextField(10);
        Quantity = new JTextField(10);
        Calories = new JTextField(10);
        MealDateTime = new JTextField(10);
        
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        // 날짜 선택 (JComboBox)
       
        // UI 추가 (GridBagLayout 사용)
        int row = 0;
        
        addLabelAndComponent("User ID:", UserID, gbc, row++);
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

        
        String uID = UserID.getText();
        int MealTime = Integer.parseInt(MealDateTime.getText());
        String fName = FoodName.getText();
        String qtity = Quantity.getText();
        String Cal = Calories.getText();
        
        Diet diet = new Diet();
        
        diet.UserID = Integer.parseInt(uID);
        diet.MealDateTime = MealTime;
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
        try {
            App.getInstance().receiveEvent();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        dispose();
    }
}
