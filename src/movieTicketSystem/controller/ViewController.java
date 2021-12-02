package movieTicketSystem.controller;

import movieTicketSystem.View.LoginView;
import movieTicketSystem.View.MovieSelectionView;

import javax.swing.text.View;
import java.util.Objects;

public class ViewController {

    public static void main(String[] args) {
        LoginView loginView= new LoginView();
        LoginViewController loginViewController = new LoginViewController(loginView);

        MovieSelectionView movieSelectionView = new MovieSelectionView();
        MovieSelectionViewController movieSelectionViewController = new MovieSelectionViewController(movieSelectionView);
    }

}
