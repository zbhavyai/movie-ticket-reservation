package movieTicketSystem.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import movieTicketSystem.model.Theatre;

/**
 * TheaterController class to control system access to Theater, Showtime, and Seat classes.
 */
public class TheaterController {
	
	private ArrayList<Theatre> theatres = new ArrayList<Theatre>();
	DbController db = new DbController();
	
	public TheaterController() {
		ArrayList<Theatre> theatersdb =	db.selectAllTheatres();
        this.theatres = theatersdb;
	}
	
	/**
	 * Search for theater based on theatreId.
	 * 
	 */
	public Theatre searchTheatreById(int theatreId) {
		// search theatre table
		for (Theatre theatre : this.theatres) {
			if (theatre.getTheatreId() == theatreId)
				return theatre;
		}
		return null;
	}
	
	
	/**
	 * Search for movie based on theatre Id. Returns list of movie objects
	 * 
	 */
	public ArrayList<Theatre> searchTheatresByMovie(int movieId) {
		ArrayList<Integer> theatreIds = db.searchTheatresByMovie(movieId);
		ArrayList<Theatre> filteredTheatres = new ArrayList<Theatre>();
		
		for (int id : theatreIds) {
			for (Theatre theatre : theatres) {
				if(theatre.getTheatreId()==id) {
					filteredTheatres.add(theatre);
				}
			}
		}
		filteredTheatres.stream().forEach(p-> System.out.println(p.getTheatreId()));
		return filteredTheatres;
	}

    
	
	public ArrayList<Theatre> getTheaters() {
		return this.theatres;
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
