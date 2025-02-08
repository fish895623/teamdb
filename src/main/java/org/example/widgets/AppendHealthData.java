package org.example.widgets;

import org.example.database.HealthDataDB;
import org.example.filter.FloatFilter;
import org.example.filter.IntegerFilter;
import org.example.model.HealthData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AppendHealthData extends JFrame {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AppendHealthData.class);

  private final JButton submitButton;
  private final JButton cancelButton;
  private final JTextField bloodPressureDiastolicTextField;
  private final JTextField bloodPressureSystolicTextField;
  private final JTextField bloodSugerTextField;
  private final JTextField bodyFatPercentageTextField;
  private final JTextField heartRateTextField;
  private final JTextField heightTextField;
  private final JTextField measurementDateTimeTextField; // TODO: empty to today
  private final JTextField stressLevelTextField;
  private final JTextField weightTextField;
  public int userId = 0;

  public AppendHealthData() {
    super("Append Diet");
    /* setup the layout */
    setSize(400, 300);
    setMinimumSize(new Dimension(400, 400));
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    /* initialize the components */
    bloodPressureDiastolicTextField = new JTextField(10);
    bloodPressureSystolicTextField = new JTextField(10);
    bloodSugerTextField = new JTextField(10);
    bodyFatPercentageTextField = new JTextField(10);
    heartRateTextField = new JTextField(10);
    heightTextField = new JTextField(10);
    measurementDateTimeTextField = new JTextField(10); // TODO: empty to today
    stressLevelTextField = new JTextField(10);
    weightTextField = new JTextField(10);

    ((AbstractDocument) bloodPressureDiastolicTextField.getDocument()).setDocumentFilter(new IntegerFilter());
    ((AbstractDocument) bloodPressureSystolicTextField.getDocument()).setDocumentFilter(new IntegerFilter());
    ((AbstractDocument) bloodSugerTextField.getDocument()).setDocumentFilter(new IntegerFilter());
    ((AbstractDocument) bodyFatPercentageTextField.getDocument()).setDocumentFilter(new IntegerFilter());
    ((AbstractDocument) heartRateTextField.getDocument()).setDocumentFilter(new IntegerFilter());
    ((AbstractDocument) heightTextField.getDocument()).setDocumentFilter(new FloatFilter());
    ((AbstractDocument) stressLevelTextField.getDocument()).setDocumentFilter(new IntegerFilter());
    ((AbstractDocument) weightTextField.getDocument()).setDocumentFilter(new FloatFilter());

    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    int row = 0;

    addLabelAndComponent("Blood Pressure Diastolic: ", bloodPressureDiastolicTextField, gbc, row++);
    addLabelAndComponent("Blood Pressure Systolic: ", bloodPressureSystolicTextField, gbc, row++);
    addLabelAndComponent("Blood Sugar: ", bloodSugerTextField, gbc, row++);
    addLabelAndComponent("Body Fat Percentage: ", bodyFatPercentageTextField, gbc, row++);
    addLabelAndComponent("Heart Rate: ", heartRateTextField, gbc, row++);
    addLabelAndComponent("Height: ", heightTextField, gbc, row++);
    addLabelAndComponent("Measurement Date Time: ", measurementDateTimeTextField, gbc, row++);
    addLabelAndComponent("Stress Level: ", stressLevelTextField, gbc, row++);
    addLabelAndComponent("Weight: ", weightTextField, gbc, row++);

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
  }

  public static AppendHealthData getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setUserID(int userID) {
    this.userId = userID;
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

    var data = new HealthData();
    data.UserID = this.userId;
    if (!measurementDateTimeTextField.getText().isEmpty()) {
      data.MeasurementDateTime =
          new Date(new SimpleDateFormat("yyyy-MM-dd").parse(measurementDateTimeTextField.getText()).getTime());
    } else {
      data.MeasurementDateTime = new Date(System.currentTimeMillis());
    }
    data.Height = Integer.parseInt(heightTextField.getText());
    data.Weight = Float.parseFloat(weightTextField.getText());
    data.BodyFatPercentage = Float.parseFloat(bodyFatPercentageTextField.getText());
    data.BloodPressureSystolic = Integer.parseInt(bloodPressureSystolicTextField.getText());
    data.BloodPressureDiastolic = Integer.parseInt(bloodPressureDiastolicTextField.getText());
    data.BloodSugar = Integer.parseInt(bloodSugerTextField.getText());
    data.HeartRate = Integer.parseInt(heartRateTextField.getText());
    data.StressLevel = Integer.parseInt(stressLevelTextField.getText());

    /* insert the data into the database */
    try {
      new HealthDataDB().insert(data);
    } catch (Exception e) {
      log.error("Error inserting diet", e);
    }

    dispose();
  }


  public static class LazyHolder {
    private static final AppendHealthData INSTANCE = new AppendHealthData();
  }
}
