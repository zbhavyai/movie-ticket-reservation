package movieTicketSystem.model;

import java.util.ArrayList;
import java.util.stream.Collectors;
import movieTicketSystem.controller.DbController;

public class Ticket {
	private int id;
	private int showTimeId;
	private double price;
	DbController db = DbController.getInstance();
	private ArrayList<Theater> theaters = db.selectAllTheatres();
	private ArrayList<Movie> movies = db.selectAllMovies();


	public Ticket(int id, int showTimeId, double price) {
		super();
		this.id = id;
		this.showTimeId = showTimeId;
		this.price = price;
	}


	public void email(String emailaddress) {
		// get email address from user input and connect to email server
	}


    public void print() {
    	ArrayList<Integer> Ids = db.searchMovieTheatreByShowTime(showTimeId);
    	String movieTitle = movies.stream().filter(m->m.getMovieId() == Ids.get(0)).map(Movie::getTitle).collect(Collectors.toList()).get(0);
    	String theaterName= theaters.stream().filter(m->m.getTheatreId() == Ids.get(1)).map(Theater::getTheatreName).collect(Collectors.toList()).get(0);
    	String showTimeStr = db.searchShowtimesById(showTimeId);
    	// pass this String to GUI
    	System.out.println(movieTitle + " "+ theaterName + " "+showTimeStr);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


//	public static void main(String[] args) {
//		Ticket ticket = new Ticket(1, 4, 16.5);
//		ticket.print();
//	}
}
