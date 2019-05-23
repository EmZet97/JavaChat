package GUI;

import Globals.GlobalVariables;
import SQL.SQLConnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JFrame{
    private JPanel panel1;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JPanel mpanel;
    private JButton create_new_account;
    private JRadioButton visibility_of_password;
    private JLabel mistake_label;
    private JTextField ageField;

    public RegisterPanel() {
        setTitle("Register Panel");
        setSize(600,500);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //check data and if all is ok create new account:
        create_new_account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = passwordField.getText();
                String nickname = usernameField.getText();
                int age = Integer.parseInt(ageField.getText());
                mistake_label.setText("");

                if(nickname.length() == 0){
                    mistake_label.setText("nickname field is empty");
                }
                else if(age == 10){
                    ageField.setText("");
                    mistake_label.setText("Pass your age");
                }
                else if(SQLConnector.CheckIfLoginExist(nickname)) {
                    mistake_label.setText("This username is not available");
                    usernameField.setText("");
                }
                else if(password.length() < 8) {
                    mistake_label.setText("Password field is too short");
                    passwordField.setText("");
                }
                else{
                    System.out.print("Create a new user;)");
                    SQLConnector.AddNewUser(nickname, age, password);

                }
            }
        });

        //this listener enable see password
        visibility_of_password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(visibility_of_password.isSelected())
                    passwordField.setEchoChar((char) 0);
                else
                    passwordField.setEchoChar('\u25CF');
            }
        });
    }

}

