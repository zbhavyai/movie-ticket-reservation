package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;
import movieTicketSystem.model.Coupon;
import movieTicketSystem.model.Payment;
import movieTicketSystem.model.RegisteredUser;

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


    public void addUser(String email, String password, String address, String holderName,String cardNumber, LocalDate expiry){
        userController.addUser(email, password, address, holderName, cardNumber, expiry);
    }

    public RegisteredUser authenticateUser(String userName, String password){

        // DUMMY PLACEHOLDER LOGIC
        boolean loggedIn = (userName.equals("Graydon") && password.equals("123"));

        if (loggedIn){
            // create Dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String date1 = "2016/08/16";
            String date2 = "2016/08/01";
            LocalDate lastPaidDate = LocalDate.parse(date1, formatter);
            LocalDate expiryDate = LocalDate.parse(date2, formatter);

            RegisteredUser dummyUser = new RegisteredUser();
            dummyUser.setEmail("testUser@gmail.com");
            dummyUser.setAddress("123 St. NW");
            dummyUser.setPassword("password");

            var x = new Payment();
            x.setCardNum("123-456-789");
            x.setExpiry(expiryDate);
            x.setCardHolderName("Graydon Hall");

            dummyUser.setCard(x);


            dummyUser.setLastFeePaid(lastPaidDate);
            return dummyUser;
        }
        return null;

        // CURRENTLY GIVES ME NULL POINTER EXCPEPTION WHEN I USE THIS LINE YOU HAD BELOW...
//        return userController.verifyUser(userName, password);
    }

    public Coupon getCoupon(String couponCode) {
        Coupon c1 = new Coupon();
        c1.setCouponCode("QWER");
        c1.setCouponAmount(25);

        Coupon c2 = new Coupon();
        c2.setCouponCode("ASDF");
        c2.setCouponAmount(15);

        switch(couponCode) {
            case "QWER":
                return c1;
            case "ASDF":
                return c2;
            default:
                return null;
        }
    }

    public double getTicketPrice(String showTime, String theatre, String movie, int row, int col) {
        return 20;
    }


    public Coupon cancelTicket(String ticketID, boolean loggedIn) {
        Coupon c1 = new Coupon();
        c1.setCouponCode("OIUY");
        c1.setCouponAmount(25);

        Coupon c2 = new Coupon();
        c2.setCouponCode("MNBV");
        c2.setCouponAmount(15);

        switch(ticketID) {
            case "101":
                return c1;
            case "102":
                return c2;
            default:
                return null;
        }
    }
}
