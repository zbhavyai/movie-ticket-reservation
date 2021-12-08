package movieTicketSystem.controller;

import java.util.ArrayList;
import movieTicketSystem.model.*;

/**
 * Controller to handle the OrdinaryUsers and RegisteredUsers
 */
public class UserController {
    private static UserController instanceVar;
    DbController db = DbController.getInstance();

    /**
     * Returns the one and only instance of UserController
     *
     * @return instance of UserController
     */
    public static UserController getInstance() {
        if (instanceVar == null) {
            instanceVar = new UserController();
            return instanceVar;
        }

        else {
            return instanceVar;
        }
    }

    /**
     *
     * Method used to add a user into the system given their entered credentials
     *
     * @param username is their username
     * @param email is the email for their profile
     * @param password is the password they'll use to log in
     * @param address is their home address
     * @param holderName is the name that is on the payment card
     * @param cardNumber is the number of the payment card
     * @param expiry is the expiry date on the payment card
     */
    public void addUser(String username, String email, String password, String address, String holderName, String cardNumber, String expiry) {
            RegisteredUser ru = db.saveRegisteredUser(username, email, password, address, holderName, cardNumber, expiry);
    }

    /**
     *
     * Method creates a new payment in the system based on the entered credentials and selections
     *
     * @param name is the name of the person making the payment/on the payment card
     * @param cardNum is the number of the payment card
     * @param cardExpiryDate is the expiration date of the payment card
     * @return an integer value which is the payementId for the transaction
     */
    public int addPayment(String name, String cardNum, String cardExpiryDate) {
        db.savePayment(name, cardNum, cardExpiryDate);
        return db.getPaymentIdByNameCardNumAndExpiry(name, cardNum, cardExpiryDate);
    }

    /**
     *
     * This method searches for a coupon with the entered code
     *
     * @param couponCode is the code the user provides
     * @return a coupon object that corresponds to the coupon code that was provided
     */
    public Coupon getCouponWithCode(String couponCode) {
        return db.getCoupon(couponCode);
    }

    /**
     * Verifies the registered user
     *
     * @param email    email of the user supplied during registration
     * @param password password of the user supplied during registration
     * @return the RegisteredUser object if user is found and authenticated, null
     *         otherwise
     */
    public RegisteredUser verifyUser(String email, String password) {
        return this.db.getRegisteredUser(email, password);
    }

    /**
     *
     * This method creates a coupon in the system using the ticketId and boolean of whether the user is logged in or not
     *
     * @param ticketID is the ticket that the coupon is being created for
     * @param loggedIn true if the user is logged in
     * @return a coupon object created by using the ticket attributes and logged in logic
     */
    public Coupon createCoupon(int ticketID, boolean loggedIn) {
        return db.createCoupon(ticketID, loggedIn);
    }

    /**
     *
     * This method is used to create a new ticket from the movie, theater and showtime that has been selected
     *
     * @param movie is the movie that the ticket corresponds to
     * @param theatre is the theatre that the ticket corresponds to
     * @param showtime is the showtime that the ticket corresponds to
     * @return a ticket object that matches with the entered attributes
     */
    public Ticket createNewTicket(String movie, String theatre, String showtime) {
        int movieId = db.getMovieIdByName(movie);
        int theatreId = db.getTheaterIdByName(theatre);
        int showtimeId = db.getShowtimeIdByMovieAndTheatreAndShowtime(theatreId, movieId, showtime);
        double price = db.getPrice(movieId);
        db.createNewTicket(showtimeId, price);
        return new Ticket(db.getTicketId(), showtimeId, price);
    }
}
