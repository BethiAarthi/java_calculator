import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SimpleCalculatorGUI {
    private JFrame frame;
    private JTextField textField;
    private double num1;
    private double num2;
    private char operator;
    private boolean isOperatorClicked = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SimpleCalculatorGUI window = new SimpleCalculatorGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    SimpleCalculatorGUI() {
        frame = new JFrame();
        frame.setBounds(100, 100, 350, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        textField = new JTextField();
        frame.getContentPane().add(textField, BorderLayout.NORTH);
        textField.setColumns(10);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(5, 4, 5, 5)); // Updated to 5 rows for clear button

        JButton[] buttons = new JButton[17];
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C" // Clear button
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            panel.add(buttons[i]);
            buttons[i].addActionListener(new ButtonClickListener());
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
                if (isOperatorClicked) {
                    textField.setText("");
                    isOperatorClicked = false;
                }
                textField.setText(textField.getText() + command);
            } else if (command.equals("=")) {
                if (operator != '\0') {
                    num2 = Double.parseDouble(textField.getText());
                    double result = calculate(num1, num2, operator);
                    textField.setText(String.valueOf(result));
                    operator = '\0';
                }
            } else if (command.equals("C")) {
                // Clear button functionality
                textField.setText("");
                num1 = 0;
                num2 = 0;
                operator = '\0';
                isOperatorClicked = false;
            } else {
                if (operator != '\0') {
                    num2 = Double.parseDouble(textField.getText());
                    num1 = calculate(num1, num2, operator);
                    textField.setText(String.valueOf(num1));
                } else {
                    num1 = Double.parseDouble(textField.getText());
                }
                operator = command.charAt(0);
                isOperatorClicked = true;
            }
        }
    }

    private double calculate(double num1, double num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    JOptionPane.showMessageDialog(frame, "Error! Division by zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
            default:
                return 0;
        }
    }
}