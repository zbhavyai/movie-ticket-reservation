package movieTicketSystem.model;

import java.time.LocalDate;
import java.util.Random;

import movieTicketSystem.controller.DbController;

/**
 * Represents a coupon in the system, that gets generated during ticket cancellation
 */
public class Coupon {
    private int id;
    private String couponCode;
    private double couponAmount;
    private LocalDate expiry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public boolean isExpired() {
        if (this.expiry.isBefore(LocalDate.now())) {
            return true;
        }

        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[" + this.couponCode + ", " + String.format("%.2f ", this.couponAmount)
                + this.expiry + "]";
    }

    /**
     * Generates a random coupon code of length 10
     *
     * @return random coupon code
     */
    private String generateCouponCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    /**
     * Generates a unique coupon code of length 10
     *
     * @return unique coupon code
     */
    public String generateUniqueCouponCode() {
        DbController db = DbController.getInstance();

        while (true) {
            String code = this.generateCouponCode();

            // if code is not found in DB, then only its unique
            if (db.getCoupon(code) == null) {
                return code;
            }
        }
    }
}
