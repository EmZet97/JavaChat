package GUI;

import SQL.SQLConnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JFrame{
    private JPanel panel1;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JPanel mpanel;
    private JButton OKButton;
    private JRadioButton SeeRadioButton;
    private JLabel errorLabel;
    private JTextField ageField;
    private JButton resetButton;
    private JButton signInButton;

    public RegisterPanel() {
        setTitle("Register Panel");
        setSize(600,500);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //if user click a OK button and want to create new account:
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(data_is_correct()) {
                   //System.out.print("Create a new user;)");
                   SQLConnector.AddNewUser(usernameField.getText(), ageField.getText(), passwordField.getText());
                   LoginPanel loginPanel = new LoginPanel();
                   loginPanel.setVisible(true);
                   loginPanel.SetLoginField(usernameField.getText());
                   loginPanel.SetErrorLabel("You create a account! Now you can login");
                   setVisible(false);
                   dispose();
                }
            }
        });

        //if user select a See radiobutton to see password:
        SeeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(SeeRadioButton.isSelected())
                    passwordField.setEchoChar((char) 0);
                else
                    passwordField.setEchoChar('\u25CF');
            }
        });

        //this button reset all fields:
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorLabel.setText("");
                usernameField.setText("");
                ageField.setText("");
                passwordField.setText("");
                if(SeeRadioButton.isSelected())
                    SeeRadioButton.doClick();
            }
        });

        //come back to LoginPanel:
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPanel loginPanel = new LoginPanel();
                loginPanel.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }

    //function check if all data is correct:
    private boolean data_is_correct() {
        try {
            errorLabel.setText("");

            String username = usernameField.getText();
            if(username.isEmpty()){
                errorLabel.setText("Username field is empty");
                return false;
            }

            if(username.indexOf(' ') != -1){
                errorLabel.setText("Username field can't have spaces");
                return false;
            }

            else if(SQLConnector.CheckIfLoginExist(username)) {
                errorLabel.setText("This username is not available");
                usernameField.setText("");
                return false;
            }

            else if(passwordField.getText().length() < 8) {
                errorLabel.setText("Password field is too short");
                passwordField.setText("");
                return false;
            }

            int age = Integer.parseInt(ageField.getText());
            if(age < 0 || age > 150) {
                throw new NumberFormatException();
            }

            return true;

        } catch(NumberFormatException | NullPointerException exc) {
            ageField.setText("");
            errorLabel.setText("Pass your age");
            return false;
        }
    }
}

