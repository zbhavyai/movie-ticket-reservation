package movieTicketSystem;

import movieTicketSystem.View.MovieSelectionView;
import movieTicketSystem.controller.*;

public class movieApp {
    public static void main(String[] args) {
        UserController userController = UserController.getInstance();
        TheaterController theaterController = new TheaterController();
        MovieController movieController = new MovieController();

        ViewController viewController = new ViewController(userController, theaterController, movieController);

        MovieSelectionView movieSelectionView = new MovieSelectionView();
        MovieSelectionViewController movieSelectionViewController = new MovieSelectionViewController(movieSelectionView,
                viewController);
    }
}
