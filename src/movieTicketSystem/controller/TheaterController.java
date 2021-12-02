package movieTicketSystem.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import movieTicketSystem.model.Theater;

/**
 * TheaterController class to control system access to Theater, Showtime, and Seat classes.
 */
public class TheaterController {
	
	private ArrayList<Theater> theaters = new ArrayList<Theater>();
	DbController db = new DbController();
	
	public TheaterController() {
		ArrayList<Theater> theatersdb =	db.selectAllTheatres();
        this.theaters = theatersdb;
	}
	
	/**
	 * Search for theater based on theatreId.
	 * 
	 */
	public Theater searchTheatreById(int theatreId) {
		// search theatre table
		for (Theater theater : this.theaters) {
			if (theater.getTheatreId() == theatreId)
				return theater;
		}
		return null;
	}
	
	
	/**
	 * Search for movie based on theatre Id. Returns list of movie objects
	 * 
	 */
	public ArrayList<Theater> searchTheatresByMovie(int movieId) {
		ArrayList<Integer> theatreIds = db.searchTheatresByMovie(movieId);
		ArrayList<Theater> filteredTheatres = new ArrayList<Theater>();
		
		for (int id : theatreIds) {
			for (Theater theatre : theaters) {
				if(theatre.getTheatreId()==id) {
					filteredTheatres.add(theatre);
				}
			}
		}
		filteredTheatres.stream().forEach(p-> System.out.println(p.getTheatreId()));
		return filteredTheatres;
	}

    
	
	public ArrayList<Theater> getTheaters() {
		return this.theaters;
	}


	/**
	 * Search for all showtimes that match the specified movie id and theater id/movieId.
	 */
	public ArrayList<String> searchShowtimesByMovieAndTheatre(int theatreId, int movieId) {
		return db.searchShowtimesByMovieAndTheatre(theatreId, movieId);
		
	}
	

	/**
	 * Reserve a seat for a specified showtime and seatNumber.
	 */
	public void reserveSeat(int theaterId, int movieId, LocalDateTime time, int colNum, int rowNum) {
	      this.searchTheatreById(theaterId).reserveSeat(movieId, time, rowNum, colNum);
    }
	
	/** 
	 * Cancel a reservation for a specified seat number and showtime.
	 */
	public void resetReservation(int theaterId, int movieId, LocalDateTime time, int rowNum, int colNum) {
		this.searchTheatreById(theaterId).resetReservation(movieId, time, rowNum, colNum);
	}

	
	
// 	public static void main(String[] args) {
//	 TheaterController mv = new TheaterController();
//	 ArrayList<String> st = mv.searchShowtimesByMovieAndTheatre(1,1);
//	 st.stream().forEach(p-> System.out.println(p));
//}

}
