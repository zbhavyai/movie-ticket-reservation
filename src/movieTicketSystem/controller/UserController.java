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
     * Adds registered user
     *
     * @param email    email of the user supplied during registration
     * @param password password of the user supplied during registration
     * @return the RegisteredUser object if user is found and authenticated, null
     *         otherwise
     */
    public void addUser(String username, String email, String password, String address, String holderName, String cardNumber,
            String expiry) {
        this.db.saveRegisteredUser(username, email, password, address, holderName, cardNumber, expiry);
    }

    public int addPayment(String name, String cardNum, String cardExpiryDate) {
        db.savePayment(name, cardNum, cardExpiryDate);
        return db.getPaymentIdByNameCardNumAndExpiry(name, cardNum, cardExpiryDate);
    }

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

    public static void main(String[] args) {
        UserController uc = new UserController();
        // Coupon cou = new Coupon();
        // cou.setCouponCode(cou.generateUniqueCouponCode());
        // cou.setExpiry(LocalDate.now().plusYears(1));
        // cou.setCouponAmount(23.56);
        // uc.emailCancelledCoupon("ensfmovieapp@gmail.com", cou);


        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(5, 3, 15.5));
        tickets.add(new Ticket(8, 4, 15.5));
        tickets.add(new Ticket(10, 5, 20.5));

        uc.emailPurchasedTicket("ensfmovieapp@gmail.com", tickets);
    }
}
