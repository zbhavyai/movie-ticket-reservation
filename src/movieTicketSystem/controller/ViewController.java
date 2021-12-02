package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;

public class ViewController {

    public static void main(String[] args) {
        UserController userController = UserController.getInstance();
        MovieSelectionView movieSelectionView = new MovieSelectionView();
        MovieSelectionViewController movieSelectionViewController = new MovieSelectionViewController(movieSelectionView, userController);
    }

}
