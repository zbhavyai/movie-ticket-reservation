package movieTicketSystem.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame{
    private JTextField usernameTexttField;
    private JLabel usernameLabel;
    private JTextField passwordTextField;
    private JLabel passwordLabel;
    private JPanel mainPanel;
    private JButton logInButton;
    private JButton cancelButton;

    public LoginView() throws HeadlessException {
        setContentPane(mainPanel);
        setTitle("Login View");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(400,125));
        this.setVisible(true);
    }

    public void addLoginButtonListener(ActionListener a){
        logInButton.addActionListener(a);
    }

    public String getUserName(){
        return this.usernameTexttField.getText();
    }

    public String getPassword(){
        return this.passwordTextField.getText();
    }

}
