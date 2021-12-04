package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;
import movieTicketSystem.model.RegisteredUser;

import java.time.LocalDate;
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

    // public String cancelTicket(int ticketId){
    //     theaterController.makeSeatAvailable(ticketId);
    //     double refundAmount = userController.refundUser(ticketId);
    //     return "Ticket " + ticketId + " has been cancelled and you have been refunded " + refundAmount + " dollars.";
    // }

    public RegisteredUser authenticateUser(String userName, String password){
        return userController.verifyUser(userName, password);
    }

    public void addUser(String email, String password, String address, String holderName,String cardNumber, LocalDate expiry){
        userController.addUser(email, password, address, holderName, cardNumber, expiry);
    }
}
