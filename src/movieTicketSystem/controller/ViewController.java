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
    public ArrayList<String> getMovies(boolean registered) {
        if (registered) {
            return movieController.getMovieNames();
        } else {
            return movieController.selectAllReleasedMovies();
        }

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

    public void signup(String email, String password, String address, String cardNum, String cardExpiryDate,
            String name) {
        userController.addUser(email, password, address, name, cardNum, cardExpiryDate);
    }

    public RegisteredUser authenticateUser(String userName, String password) {
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
    // *** PRICES AND COUPON CONNECTION TO BACK END ***

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

    // *** CANCELLATION CONNECTION TO BACK END ***
    public boolean checkShowtime(int ticketId) {
        return theaterController.checkValidShowtime(ticketId);
    }

    public Coupon cancelTicket(int ticketID, boolean loggedIn) {
        return userController.createCoupon(ticketID, loggedIn);
    }
    // *** CANCELLATION CONNECTION TO BACK END ***

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
     * Emails the generated list of tickets
     *
     * @param userEmail recipient of tickets
     * @param t         the list of tickets to email
     */
    public void emailPurchasedTicket(String userEmail, ArrayList<Ticket> t) {
        Email e = Email.getInstance();

        String subject = "ENSF-614 Movie App - Here's your ticket";

        if(t.size() > 1) {
            subject += "s";
        }

        String list_of_units = "";
        String unit = e.getTemplate("ticket_unit");

        for (int i = 0; i < t.size(); i++) {
            String tempUnit = unit.replace("#INSERTID#", String.valueOf(t.get(i).getId()));
            tempUnit = tempUnit.replace("#INSERTPRICE#", String.format("%.2f", t.get(i).getPrice()));

            String[] movieShowtime = DbController.getInstance().getMovieAndShowtime(t.get(i).getId());
            tempUnit = tempUnit.replace("#INSERTMOVIE#", movieShowtime[0]);
            tempUnit = tempUnit.replace("#INSERTSHOWTIME#", movieShowtime[1]);

            list_of_units += tempUnit;
        }

        String body = e.getTemplate("ticket");
        body = body.replace("#LISTGOESHERE#", list_of_units);
        e.sendEmail(userEmail, subject, body);
    }
}
