package movieTicketSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import movieTicketSystem.model.*;

/**
 * Controller to handle the OrdinaryUsers and RegisteredUsers
 */
public class UserController {
    private static UserController instanceVar;
	DbController db = DbController.getInstance();
    // private ArrayList<RegisteredUser> registeredUserIdList;

    // /**
    //  * Private constructor enforcing singleton design pattern
    //  */
    // private UserController() {
    //     this.db = DbController.getInstance();
    //     registeredUserIdList = db.getAllRegisteredUserIds();
    // }

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
     * Adds registered user
     *
     * @param email    email of the user supplied during registration
     * @param password password of the user supplied during registration
     * @return the RegisteredUser object if user is found and authenticated, null
     *         otherwise
     */
    public void addUser(String email, String password, String address, String holderName, String cardNumber, LocalDate expiry){
        this.db.saveRegisteredUser(email, password, address, holderName, cardNumber, expiry);
    }

    public int addPayment(String name, String cardNum, LocalDate cardExpiryDate) {
        db.savePayment(name, cardNum, cardExpiryDate);
        return db.getPaymentIdByNameCardNumAndExpiry(name, cardNum, cardExpiryDate);
    }

    public Coupon getCouponWithCode(String couponCode) {
        return db.getCoupon(couponCode);
    }

    public void createSale(int paymentId, int ticketId){
        db.saveSale(paymentId, ticketId);
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

    public Coupon createCoupon(int ticketID, boolean loggedIn) {
        return db.createCoupon(ticketID, loggedIn);
    }

    public Ticket createNewTicket(String movie, String theatre, String showtime) {
        int movieId = db.getMovieIdByName(movie);
        int theatreId = db.getTheaterIdByName(theatre);
        int showtimeId = db.getShowtimeIdByMovieAndTheatreAndShowtime(theatreId, movieId, showtime);
        double price = db.getPrice(movieId);
        db.createNewTicket(showtimeId, price);
        return new Ticket(db.getTicketId(), showtimeId, price);
    }
}
