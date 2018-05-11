package template.costTemplate;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        JTextField textField = new JTextField();
        JOptionPane.showMessageDialog(null, textField);
        System.out.println(textField.getText() == null);
        System.out.println(textField.getText().length());
    }
}
