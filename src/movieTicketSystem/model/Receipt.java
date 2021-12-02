package movieTicketSystem.model;

public class Receipt {
    private int id;
    private Movie movie;
    private Theater theater;
    private Showtime showTime;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheatre() {
        return theater;
    }

    public void setTheatre(Theater theater) {
        this.theater = theater;
    }

    public Showtime getShowTime() {
        return showTime;
    }

    public void setShowTime(Showtime showTime) {
        this.showTime = showTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
