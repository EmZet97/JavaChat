import GUI.LoginPanel;
import javax.swing.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setVisible(true);

    }
}
