package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;

public class ViewController {

    public static void main(String[] args) {
        UserController userController = UserController.getInstance();
        TheaterController theaterController = new TheaterController();
        MovieController movieController = new MovieController();
        MovieSelectionView movieSelectionView = new MovieSelectionView();
        MovieSelectionViewController movieSelectionViewController = new MovieSelectionViewController(movieSelectionView, userController, theaterController, movieController);
    }

}
