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

    public ViewController(UserController userController, TheaterController theaterController,
            MovieController movieController) {
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
        MovieSelectionViewController movieSelectionViewController = new MovieSelectionViewController(movieSelectionView,
                viewController);
    }

    // *** MOVIE SELECTION CONNECTION TO BACK END ***
    public ArrayList<String> getMovies() {
        return movieController.getMovieNames();
    }

    public ArrayList<String> getTheatres() {
        return theaterController.getTheaterNames();
    }

    public ArrayList<String> getShowTimes(String movieName, String theaterName) {
        return theaterController.getTheatreShowtimes(movieName, theaterName);
    }

    public int[][] getSeats(int showtimeId) {
        return theaterController.getSeatGrid(showtimeId);
    }

    public int getShowtimeId(String[] searchValues) {
        return theaterController.getShowtimeId(searchValues);
    }
    // *** MOVIE SELECTION CONNECTION TO BACK END ***

    // *** LOGIN AND SIGNUP CONNECTION TO BACK END ***

    public void signupPayment(String name, String cardNum, String cardExpiryDate) {
        userController.addPayment(name, cardNum, cardExpiryDate);
    }

<<<<<<< HEAD
    public void signup(String email, String password, String address, String cardNum, LocalDate cardExpiryDate,
            String name) {
        userController.addUser(email, password, address, name, cardNum, cardExpiryDate);
    }

    public void addUser(String email, String password, String address, String holderName, String cardNumber,
            LocalDate expiry) {
        userController.addUser(email, password, address, holderName, cardNumber, expiry);
    }

    public RegisteredUser authenticateUser(String userName, String password) {
=======
    public void signup(String email, String password, String address, String cardNum, String cardExpiryDate, String name) {
        userController.addUser(email, password, address, name, cardNum, cardExpiryDate);
    }

    public RegisteredUser authenticateUser(String userName, String password){
>>>>>>> cg
        return userController.verifyUser(userName, password);
    }
    // *** LOGIN AND SIGNUP CONNECTION TO BACK END ***

    // *** PRICES AND COUPON CONNECTION TO BACK END ***
    public Coupon getCoupon(String couponCode) {
        return userController.getCouponWithCode(couponCode);
    }

    public double getTicketPrice(String showTime, String theatre, String movie, int row, int col) {
        return movieController.getPrice(movie);
    }
<<<<<<< HEAD

    public boolean checkShowtime(int ticketId) {
        return theaterController.checkValidShowtime(ticketId);
    }

    public Coupon cancelTicket(int ticketID, boolean loggedIn) {
        return userController.createCoupon(ticketID, loggedIn);
    }
    // *** PRICES AND COUPON CONNECITON TO BACK END ***
=======
    // *** PRICES AND COUPON CONNECTION TO BACK END ***
>>>>>>> cg

    // *** PURCHASE CONNECTION TO BACK END ***
    public int ticketPayment(String name, String cardNum, String cardExpiryDate) {
        return userController.addPayment(name, cardNum, cardExpiryDate);
    }

    public Ticket makeTicket(String movie, String theatre, String showtime, int row, int col) {
        Ticket newTicket = userController.createNewTicket(movie, theatre, showtime);
        theaterController.createSeat(row, col, newTicket.getId());
        return newTicket;
    }

    public void makeSale(int paymentId, int ticketId) {
        userController.createSale(paymentId, ticketId);
    }
    // *** PURCHASE CONNECTION TO BACK END ***

<<<<<<< HEAD
    // *** CANCELLATION CONNECTION TO BACK END

    /**
     * Emails the generated coupon
     *
     * @param userEmail recipient of coupon
     * @param c         the coupon to email
     */
    public void emailCancelledCoupon(String userEmail, Coupon c) {
        Email e = Email.getInstance();

        String subject = "ENSF-614 Movie App - Here's your coupon";

        String body = e.getTemplate("coupon");
        body = body.replace("#INSERTCODE#", c.getCouponCode());
        body = body.replace("#INSERTAMOUNT#", String.format("%.2f", c.getCouponAmount()));
        body = body.replace("#INSERTEXPIRY#", c.getExpiry().toString());

        e.sendEmail(userEmail, subject, body);
    }

    /**
     * Emails the generated ticket
     *
     * @param userEmail recipient of ticket
     * @param t         the ticket to email
     */
    public void emailPurchasedTicket(String userEmail, Ticket t) {
        Email e = Email.getInstance();

        String subject = "ENSF-614 Movie App - Here's your ticket";
        String body = e.getTemplate("ticket");
        body = body.replace("#INSERTID#", String.valueOf(t.getId()));
        body = body.replace("#INSERTAMOUNT#", String.format("%.2f", t.getPrice()));

        e.sendEmail(userEmail, subject, body);
    }
=======
    // *** CANCELLATION CONNECTION TO BACK END ***
    public boolean checkShowtime(int ticketId){
        return theaterController.checkValidShowtime(ticketId);
    }

    public Coupon cancelTicket(int ticketID, boolean loggedIn) {
        return userController.createCoupon(ticketID, loggedIn);
    }
    // *** CANCELLATION CONNECTION TO BACK END ***

>>>>>>> cg
}
