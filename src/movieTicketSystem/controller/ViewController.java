package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;
import movieTicketSystem.model.RegisteredUser;

import java.util.ArrayList;

public class ViewController {
    UserController userController;
    TheaterController theaterController;
    MovieController movieController;

    public ViewController(UserController userController, TheaterController theaterController, MovieController movieController) {
        this.userController = userController;
        this.theaterController = theaterController;
        this.movieController = movieController;
    }

    public static void main(String[] args) {
        UserController userController = UserController.getInstance();
        TheaterController theaterController = new TheaterController();
        MovieController movieController = new MovieController();

        ViewController viewController = new ViewController(userController, theaterController, movieController);

        MovieSelectionView movieSelectionView = new MovieSelectionView();
        MovieSelectionViewController movieSelectionViewController = new MovieSelectionViewController(movieSelectionView, viewController);
    }

    public ArrayList<String> getMovies(){
        return movieController.getMovieNames();
    }

    public ArrayList<String> getTheatres(){
        return theaterController.getTheaterNames();
    }

    public ArrayList<String>getShowTimes(String movieName, String theaterName){
        return theaterController.getTheatreShowtimes(movieName, theaterName);
    }

    public int[][] getSeats(int showtimeId){
        return theaterController.getSeatGrid(showtimeId);
    }

    public int getShowtimeId(String[] searchValues){
        return theaterController.getShowtimeId(searchValues);
    }

    public boolean authenticateUser(String userName, String password){
        RegisteredUser ru = userController.verifyUser(userName, password);

        // user does exist
        return ru != null;  // user does not exist
    }
}
