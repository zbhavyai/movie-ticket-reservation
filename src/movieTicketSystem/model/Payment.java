package movieTicketSystem.model;

import java.time.LocalDate;

public class Payment {
    private int id;
    private String cardHolderName;
    private String cardNum;
    private LocalDate expiry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
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
        return ("[" + this.cardHolderName + ", " + this.cardNum + " " + this.expiry + "]");
    }
}
