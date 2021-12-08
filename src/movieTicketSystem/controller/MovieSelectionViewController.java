package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;
import movieTicketSystem.model.*;
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
    CompletePaymentButtonListener completePaymentButtonListener;
    CancelTicketButtonListener cancelTicketButtonListener;
    SignUpButtonListener signUpButtonListener;

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
        completePaymentButtonListener = new CompletePaymentButtonListener();
        cancelTicketButtonListener = new CancelTicketButtonListener();
        signUpButtonListener = new SignUpButtonListener();
        theView.addMovieComboBoxActionListener(movieListener);
        theView.addTheatreComboBoxActionListener(theatreListener);
        theView.addShowtimeComboBoxActionListener(showtimeListener);
        theView.addPurchaseButtonActionListener(purchaseButtonListener);
        theView.addShowLoginButtonActionListener(showLoginButtonListener);
        theView.addLoginButtonListener(loginButtonListener);
        theView.addCouponistener(couponButtonListener);
        theView.addCompletePaymentListener(completePaymentButtonListener);
        theView.addCancelTicketButtonListener(cancelTicketButtonListener);
        theView.addSignUpButtonListener(signUpButtonListener);


        // true means registered user,  false means ordinary user
        ArrayList<String> movieOptions = viewController.getMovies(true);
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

                loggedInUser = viewController.authenticateUser(userName, password);
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
            ArrayList<String> theatreOptions = viewController.getTheatres();

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
            ArrayList<String> timeOptions = viewController.getShowTimes(movie, theater);

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
            int showtimeId = viewController.getShowtimeId(showtimeSearch);
            if(showtimeId<0) {
                System.out.println("10% exceeded");
                JOptionPane.showMessageDialog(theView,
                        "Maximum 10% of reserved seats have been sold for this unreleased movie's showtime." +
                                "Please select another showtime.",
                        "Alert", JOptionPane.WARNING_MESSAGE);
            }
            int[][] seats = viewController.getSeats(showtimeId);

            theView.setSeats(seats);}
    }

    class SignUpButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String username = theView.getUserName();
            String email = theView.getSignUpEmail();
            String password = theView.getSignUpPassword();
            String address = theView.getSignUpAddress();
            String cardNum = theView.getSignUpCardNum();
            String cardExpiryDate = theView.getSignUpCardExp();
            String name = theView.getSignUpCardName();
            String cvc = theView.getSignUpCVC();
            System.out.println(cvc);
            theView.clearSignUpForm();

            viewController.signupPayment(name, cardNum, cardExpiryDate);
            viewController.signup(username, email, password, address, cardNum, cardExpiryDate, name);
            return;
        }
    }

    class CompletePaymentButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String movie = theView.getMovieInput();
            String theatre = theView.getTheatreInput();
            String showTime = theView.getShowtimeInput();

            // check if a valid coupon has been applied
            Coupon coupon = null;
            if(theView.getCouponButtonText().equals("Remove Coupon")){
                String couponCode = theView.getCouponCode();
                coupon = viewController.getCoupon(couponCode);
            }

            String email = "gwhall@ualberta.ca";  // REPLACE THIS WITH METHOD FROM VIEW

            // payment amount
            double totalAmount = theView.getTotalPrice();
            double grandTotal = theView.getGrandTotal();
            double couponCharge = totalAmount - grandTotal;

            // Payment details
            String cardNumber = theView.getCreditCardNum();
            String CVC = theView.getCVC();
            String cardExpiryDate = theView.getCardExpiry();
            String cardHolderName = theView.getCardHolderName();

            //Figure out selected seats
            ArrayList<Seat> selectedSeats = new ArrayList<Seat>();
            JButton[][] seats = theView.getSeats();
            for(int i=0; i<10; i++){
                for(int j=0; j<10; j++){
                    Color seatBackground = seats[i][j].getBackground();
                    if(seatBackground == Color.green){
                        Seat dummySeat = new Seat(i+1, j+1);
                        selectedSeats.add(dummySeat);
                    }
                }
            }

            int paymentId = 0;

            if(grandTotal != 0) {
                if(theView.getCouponButtonText().equals("Apply Coupon")){
                    paymentId = viewController.ticketPayment(cardHolderName, cardNumber, cardExpiryDate);
                }

                if(theView.getCouponButtonText().equals("Remove Coupon")){
                    DbController.getInstance().updateCoupon(coupon, couponCharge);
                    paymentId = viewController.ticketPayment(cardHolderName, cardNumber, cardExpiryDate);
                }
            }

            else {
                DbController.getInstance().updateCoupon(coupon, couponCharge);
            }


            ArrayList<Ticket> newTicketList = new ArrayList<Ticket>();
            for(int i = 0; i < selectedSeats.size(); i++){
                newTicketList.add(viewController.makeTicket(movie, theatre, showTime, selectedSeats.get(i).getRowNumber(), selectedSeats.get(i).getColNumber()));
            }

            viewController.emailPurchasedTicket(email, newTicketList);
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

                Coupon coupon = viewController.getCoupon(couponCode);
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

    class CancelTicketButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = "gwhall@ualberta.ca";  // REPLACE THIS WITH METHOD FROM VIEW

            int ticketId = Integer.parseInt(theView.getTicketCancellationID());
            boolean registered = theView.getLoggedIn();
            boolean showtimeCheck = viewController.checkShowtime(ticketId);
            if(showtimeCheck == false){
                JOptionPane.showMessageDialog(theView, "Movie is playing in less than 72 hours, cannot cancel.",
                        "Alert", JOptionPane.WARNING_MESSAGE);
                theView.setRefundCouponAmt("");
                theView.setRefundCouponCode("");
                return;
            }
            Coupon coupon = viewController.cancelTicket(ticketId, registered);
            if(coupon == null){
                JOptionPane.showMessageDialog(theView, "Ticket Not Found.",
                        "Alert", JOptionPane.WARNING_MESSAGE);
                theView.setRefundCouponAmt("");
                theView.setRefundCouponCode("");
                return;
            }

            // ticket is found
            theView.setRefundCouponAmt(String.format("%.02f", coupon.getCouponAmount()));
            theView.setRefundCouponCode(coupon.getCouponCode());

            viewController.emailCoupon(email, coupon);
        }
    }

    class PurchaseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            double totalPrice =0;
            int seatCountPurchased = 0;

            //**** FOR EACH SELECTED SEAT, SEND ROW, COL, MOVIE, THEATRE, SHOWTIME TO BACK END FOR PURCHASE ****
            JButton[][] seats = theView.getSeats();
            String movie = theView.getMovieInput();
            String theatre = theView.getTheatreInput();
            String showTime = theView.getShowtimeInput();

            for(int i=0; i<10; i++){
                for(int j=0; j<10; j++){
                    Color seatBackground = seats[i][j].getBackground();
                    if(seatBackground == Color.green){
                        int row = i+1;
                        int col = j+1;
                        System.out.println(
                                "\nPURCHASE INFO:"
                                        + "\n row: " + row
                                        + "\n col: " + col
                                        + "\n movie: " + movie
                                        + "\n theatre: " + theatre
                                        + "\n showtime: " + showTime
                        );
                        totalPrice += viewController.getTicketPrice(showTime, theatre, movie, row, col);
                        seatCountPurchased++;
                    }
                }
            }
            String[] showtimeSearch = {theatre, movie, showTime};

            boolean movieReleased = viewController.checkShowtimeReleased(showtimeSearch);
            if(!movieReleased){
                int countTaken = viewController.getSeatCount(showtimeSearch);
                if ((countTaken + seatCountPurchased) > 10){
                    System.out.println("10% exceeded");
                    JOptionPane.showMessageDialog(theView,
                            "Maximum 10% of reserved seats have been exceeded. " +
                                    "Please limit seats purchased to " + (10-countTaken),
                            "Alert", JOptionPane.WARNING_MESSAGE);
                    return;
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
}
