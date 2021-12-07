package movieTicketSystem.model;

import java.time.LocalDate;

import movieTicketSystem.controller.DbController;

public class OrdinaryUser {
    public void buyTicket() {
        // TODO
    }

    /**
     * Cancel the ticket t held by user
     *
     * @param t    the Ticket object that is to be cancelled
     * @param user the user who is cancelling the ticket
     * @return Coupon generated after cancellation
     */
    public Coupon cancelTicket(Ticket t, OrdinaryUser user) {
        DbController db = DbController.getInstance();

        // TODO: not just delete ticket, make sure to use release the seat as well
        // use method by calvin cancelTicket() that calls theaterController.makeSeatAvailable

        if(db.deleteTicket(t.getId()) == false) {
            return null;
        }

        // after successful ticket cancellation, generate the coupon
        Coupon c = new Coupon();
        c.setCouponCode(c.generateUniqueCouponCode());
        c.setExpiry(LocalDate.now().plusYears(1));

        if (user instanceof RegisteredUser) {
            c.setCouponAmount(t.getPrice());
        }

        else {
            c.setCouponAmount(t.getPrice() * 0.85);
        }

        c = db.saveCoupon(c.getCouponCode(), c.getCouponAmount(), c.getExpiry());
        return c;
    }
}
