package movieTicketSystem.controller;

import movieTicketSystem.View.LoginView;

import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginViewController {
    LoginView loginView;
    LoginButtonListener loginButtonListener;

    public LoginViewController(LoginView loginView) {
        this.loginView = loginView;

        loginButtonListener = new LoginButtonListener();
        loginView.addLoginButtonListener(loginButtonListener);
    }

    public String[] getLoginInfo() {
        loginView.setVisible(true);
        String[] userInfo = new String[]{"Graydon", "password"};
        return userInfo;
    }

    // login button
    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userName = loginView.getUserName();
            String password = loginView.getPassword();
            boolean authenticated = authenticateUser(userName, password);
            System.out.println(authenticated);
        }
    }

    public void login(){
        loginView.setVisible(true);
    }

    public static void main(String[] args) {
        LoginView x = new LoginView();

        LoginViewController z = new LoginViewController(x);
    }

    private boolean authenticateUser(String userName, String password){
        if((userName.equals("Graydon")) & (password.equals("1234"))){
            return true;
        } else {
            return false;
        }
    }

}
