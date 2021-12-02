package movieTicketSystem.model;

import java.time.LocalDate;

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
        return "[" + this.couponCode + ", " + String.format("%.2f", this.couponAmount)
                + this.expiry + "]";
    }
}
