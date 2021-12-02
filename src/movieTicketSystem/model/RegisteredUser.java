package movieTicketSystem.model;

import java.time.LocalDate;
import java.util.List;

public class RegisteredUser {
    private int id;
    private String password;
    private String email;
    private String address;
    private List<Ticket> tickets;
    private List<Receipt> receipts;
    private Payment card;
    private LocalDate lastFeePaid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public Payment getCard() {
        return card;
    }

    public void setCard(Payment card) {
        this.card = card;
    }

    public LocalDate getLastFeePaid() {
        return lastFeePaid;
    }

    public void setLastFeePaid(LocalDate lastFeePaid) {
        this.lastFeePaid = lastFeePaid;
    }

    public void buyTicket() {
        // TODO
    }

    public Coupon cancelTicket() {
        // TODO
        Coupon c = new Coupon();
        return c;
    }

    public boolean checkFeeStatus() {
        if(this.lastFeePaid.plusYears(1).isAfter(LocalDate.now())) {
            return true;
        }

        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return ("[" + this.email + ", " + this.address + ", " + this.card + ", " + lastFeePaid + "]");
    }
}
