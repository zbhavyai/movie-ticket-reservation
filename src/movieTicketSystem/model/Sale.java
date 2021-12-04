package movieTicketSystem.model;

public class Sale {
    private int paymentId;
    private int ticketId;

    public Sale(int paymentId, int ticketId){
        this.paymentId = paymentId;
        this.ticketId = ticketId;
    }

    public int getPaymentId(){
        return this.paymentId;
    }

    public int getTicketId(){
        return this.ticketId;
    }
}
