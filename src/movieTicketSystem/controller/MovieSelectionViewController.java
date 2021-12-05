package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;
import movieTicketSystem.model.Coupon;
import movieTicketSystem.model.Payment;
import movieTicketSystem.model.RegisteredUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MovieSelectionViewController {

    MovieComboBoxListener movieListener;
    TheatreComboBoxListener theatreListener;
    ShowtimeComboBoxListener showtimeListener;
    PurchaseButtonListener purchaseButtonListener;
    LoginButtonListener loginButtonListener;
    ShowLoginButtonListener showLoginButtonListener;
    CouponButtonListener couponButtonListener;

    MovieSelectionView theView;
    ViewController viewController;

    RegisteredUser loggedInUser;


    public MovieSelectionViewController(MovieSelectionView theView, ViewController viewController) {
        this.theView = theView;
        this.viewController = viewController;

        // create and add action listeners
        movieListener = new MovieComboBoxListener();
        theatreListener = new TheatreComboBoxListener();
        showtimeListener = new ShowtimeComboBoxListener();
        purchaseButtonListener = new PurchaseButtonListener();
        loginButtonListener = new LoginButtonListener();
        showLoginButtonListener = new ShowLoginButtonListener();
        couponButtonListener = new CouponButtonListener();
        theView.addMovieComboBoxActionListener(movieListener);
        theView.addTheatreComboBoxActionListener(theatreListener);
        theView.addShowtimeComboBoxActionListener(showtimeListener);
        theView.addPurchaseButtonActionListener(purchaseButtonListener);
        theView.addShowLoginButtonActionListener(showLoginButtonListener);
        theView.addLoginButtonListener(loginButtonListener);
        theView.addCouponistener(couponButtonListener);

        ArrayList<String> movieOptions = getMovies();
        theView.setMovieOptions(movieOptions);

        theView.setVisible(true);

    }

    // login button
    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean loggedIn = theView.getLoggedIn();
            if(!loggedIn){
                // attempt to log in
                String userName = theView.getUserName();
                String password = theView.getPassword();

                loggedInUser = authenticateUser(userName, password);
                if(loggedInUser == null){
                    JOptionPane.showMessageDialog(theView, "Invalid Credentials.",
                            "Alert", JOptionPane.WARNING_MESSAGE);
                            theView.setLoggedIn(null);
                }
                theView.setLoggedIn(loggedInUser);
            }
            else{
                // log out
                theView.setLoggedIn(null);
                loggedInUser = null;
            }
        }
    }

    class ShowLoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.toggleLoginForm();
        }
    }

    class MovieComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Movie Selected: "  + theView.getMovieInput());
            ArrayList<String> theatreOptions = getTheatres();

            // clear any previous showtime options
            theView.removeShowtimeComboBoxActionListener(showtimeListener);
            theView.clearShowtimeOptions();
            theView.addShowtimeComboBoxActionListener(showtimeListener);

            // clear previously selected seats
            theView.disableAllSeats();

            // add theatre options based on movie selected
            theView.removeTheatreComboBoxActionListener(theatreListener);
            theView.setTheatreOptions(theatreOptions);
            theView.addTheatreComboBoxActionListener(theatreListener);

        }
    }

    class TheatreComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String theater = theView.getTheatreInput();
            String movie = theView.getMovieInput();
            System.out.println("Theatre Selected: "  + theater);
            ArrayList<String> timeOptions = getShowTimes(movie, theater);

            //disable any previously selected seats for a different showtime
            theView.disableAllSeats();

            // add showtimes for this theatre / movie combo
            theView.removeShowtimeComboBoxActionListener(showtimeListener);
            theView.setTimeOptions(timeOptions);
            theView.addShowtimeComboBoxActionListener(showtimeListener);

        }
    }

    class ShowtimeComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String showtime = theView.getShowtimeInput();
            String movie = theView.getMovieInput();
            String theater = theView.getTheatreInput();
            System.out.println("Showtime Selected: "  + theView.getShowtimeInput());
            String[] showtimeSearch = {theater, movie, showtime};
            int showtimeId = getShowtimeId(showtimeSearch);
            int[][] seats = getSeats(showtimeId);

            theView.setSeats(seats);}
    }

    class PurchaseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            double totalPrice =0;

            //**** FOR EACH SELECTED SEAT, SEND ROW, COL, MOVIE, THEATRE, SHOWTIME TO BACK END FOR PURCHASE ****
            JButton[][] seats = theView.getSeats();
            for(int i=0; i<10; i++){
                for(int j=0; j<10; j++){
                    Color seatBackground = seats[i][j].getBackground();
                    if(seatBackground == Color.green){
                        int row = i+1;
                        int col = j+1;
                        String movie = theView.getMovieInput();
                        String theatre = theView.getTheatreInput();
                        String showTime = theView.getShowtimeInput();
                        System.out.println(
                                "\nPURCHASE INFO:"
                                + "\n row: " + row
                                + "\n col: " + col
                                + "\n movie: " + movie
                                + "\n theatre: " + theatre
                                + "\n showtime: " + showTime
                        );
                        totalPrice += getTicketPrice(showTime, theatre, movie, row, col);

                    }
                }
            }
            if(loggedInUser==null){
                theView.populatePurchaseTab(totalPrice);
            }else{
                theView.populatePurchaseTab(loggedInUser, totalPrice);
            }

            theView.setView("purchase");
        }
    }

    class CouponButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            double totalPrice = theView.getTotalPrice();

            String couponAction = theView.getCouponButtonText();
            if (couponAction.equals("Apply Coupon")){
                String couponCode = theView.getCouponCode();
                double grandTotal = totalPrice;
                double couponAmountRemaining = 0;

                Coupon coupon = getCoupon(couponCode);
                if(coupon == null){
                    JOptionPane.showMessageDialog(theView, "Coupon Not found.",
                            "Alert", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else{
                    double couponAmount = coupon.getCouponAmount();
                    if(couponAmount > grandTotal){
                        grandTotal = 0;
                        couponAmountRemaining = couponAmount - totalPrice;
                    }else{
                        grandTotal = totalPrice - couponAmount;
                    }

                    theView.setCouponAmount(String.format("%.02f", couponAmount));
                    theView.setGrandTotal(String.format("%.02f", grandTotal));
                    theView.setRemainingCouponAmount(String.format("%.02f", couponAmountRemaining));
                    theView.setCouponButtonText("Remove Coupon");
                    theView.setCouponCodeEnabled(false);

                    // if total is 0 no need to enter payment details
                    if(grandTotal==0){
                        theView.setPaymentDetailsEnabled(false);
                    } else{
                        theView.setPaymentDetailsEnabled(true);
                    }
                }
            } else{
                // Remove Coupon
                theView.setCouponAmount(String.format("0.00"));
                theView.setGrandTotal(String.format("%.02f", totalPrice));
                theView.setRemainingCouponAmount(String.format("0.00"));
                theView.setCouponButtonText("Apply Coupon");
                theView.setCouponCodeText("");
                theView.setPaymentDetailsEnabled(true);
                theView.setCouponCodeEnabled(true);
            }

        }
    }


    // METHODS TO TALK TO VIEW CONTROLLER

    private ArrayList<String> getMovies(){
        return viewController.getMovies();
    }

    private ArrayList<String> getTheatres(){
        return viewController.getTheatres();
    }

    private ArrayList<String>getShowTimes(String movieName, String theaterName){
        return viewController.getShowTimes(movieName, theaterName);
    }

    private int[][] getSeats(int showtimeId){
        return viewController.getSeats(showtimeId);
    }

    public int getShowtimeId(String[] searchValues){
        return viewController.getShowtimeId(searchValues);
    }

    private RegisteredUser authenticateUser(String userName, String password){

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
    }

    private double getTicketPrice(String showTime, String theatre, String movie, int row, int col) {
        return 20;
    }

    private Coupon getCoupon(String couponCode) {
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

}
