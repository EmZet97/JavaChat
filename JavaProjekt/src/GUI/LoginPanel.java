package GUI;

import SQL.SQLConnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JFrame{
    private JPanel panel1;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JTextField loginField;
    private JLabel ErrorLabel;

    public  LoginPanel(){
        setTitle("Login Panel");
        setSize(600,500);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TryLogin();
            }
        });


    }

    private void TryLogin(){
        String nick = loginField.getText();
        String password = passwordField.getText();
        boolean exist = SQLConnector.CheckIfUserExist(nick, password);
        String text = new String();
        if(exist)
            text = "No elo";
        else
            text = "Incorrect nick or password";
        ErrorLabel.setText(text);
    }
}
