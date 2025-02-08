package org.example.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FloatFilter extends DocumentFilter {
  private static final String NUMERIC_DOT_PATTERN = "^\\d*\\.?\\d*$";

  @Override
  public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
    String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
    if (isValid(newText)) {
      super.insertString(fb, offset, text, attrs);
    }
  }

  @Override
  public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
      throws BadLocationException {
    String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
    String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
    if (isValid(newText)) {
      super.replace(fb, offset, length, text, attrs);
    }
  }

  private boolean isValid(String text) {
    return text.matches(NUMERIC_DOT_PATTERN) && countDots(text) <= 1;
  }

  private int countDots(String text) {
    return (int) text.chars().filter(c -> c == '.').count();
  }
}
