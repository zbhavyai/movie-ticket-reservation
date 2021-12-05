package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;
import movieTicketSystem.model.Coupon;
import movieTicketSystem.model.Payment;
import movieTicketSystem.model.RegisteredUser;
import movieTicketSystem.model.Ticket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    // *** MOVIE SELECTION CONNECTION TO BACK END ***
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
    // *** MOVIE SELECTION CONNECTION TO BACK END ***


    // *** LOGIN AND SIGNUP CONNECTION TO BACK END ***

    public void signupPayment(String name, String cardNum, LocalDate cardExpiryDate) {
        userController.addPayment(name, cardNum, cardExpiryDate);
    }

    public void signup(String email, String password, String address, String cardNum, LocalDate cardExpiryDate, String name) {
        userController.addUser(email, password, address, name, cardNum, cardExpiryDate);
    }

    public void addUser(String email, String password, String address, String holderName,String cardNumber, LocalDate expiry){
        userController.addUser(email, password, address, holderName, cardNumber, expiry);
    }

    public RegisteredUser authenticateUser(String userName, String password){
        return userController.verifyUser(userName, password);
    }
    // *** LOGIN AND SIGNUP CONNECTION TO BACK END ***


    // *** PRICES AND COUPON CONNECITON TO BACK END ***
    public Coupon getCoupon(String couponCode) {
        return userController.getCouponWithCode(couponCode);
    }

    public double getTicketPrice(String showTime, String theatre, String movie, int row, int col) {
        return movieController.getPrice(movie);
    }

    public boolean checkShowtime(int ticketId){
        return theaterController.checkValidShowtime(ticketId);
    }

    public Coupon cancelTicket(int ticketID, boolean loggedIn) {
        return userController.createCoupon(ticketID, loggedIn);
    }
    // *** PRICES AND COUPON CONNECITON TO BACK END ***
}
