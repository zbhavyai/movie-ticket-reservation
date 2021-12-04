package movieTicketSystem.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import movieTicketSystem.model.Theater;

/**
 * TheaterController class to control system access to Theater, Showtime, and Seat classes.
 */
public class TheaterController {

	private ArrayList<Theater> theaters = new ArrayList<Theater>();
	DbController db = DbController.getInstance();

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
		ArrayList<Theater> filteredTheaters = new ArrayList<Theater>();

		for (int id : theatreIds) {
			for (Theater theater : theaters) {
				if(theater.getTheatreId()==id) {
					filteredTheaters.add(theater);
				}
			}
		}
		filteredTheaters.stream().forEach(p-> System.out.println(p.getTheatreId()));
		return filteredTheaters;
	}

	public ArrayList<String> getTheaterNames() {
		ArrayList<String> theaterNames = new ArrayList<String>();
		for(int i = 0; i < theaters.size(); i++){
			theaterNames.add(theaters.get(i).getTheatreName());
		}
		return theaterNames;
	}

	public ArrayList<Theater> getTheaters() {
		return this.theaters;
	}

	public ArrayList<String>getTheatreShowtimes(String movieName, String theaterName){
		ArrayList<String> showtimeList = new ArrayList<String>();
		showtimeList = db.getTheatreShowtimes(db.getMovieIdByName(movieName), db.getTheaterIdByName(theaterName));
		return showtimeList;
	}

	/**
	 * Search for the seat availability for a given showtime.
	 */
	public int[][] getSeatGrid(int showtimeId) {
		return db.seatGrid(showtimeId);

	}

	/**
	 * Search for the showtimeId based on the movieId, theaterId and showtime
	 */
	public int getShowtimeId(String[] searchValues){
		int theatreId = db.getTheaterIdByName(searchValues[0]);
		int movieId = db.getMovieIdByName(searchValues[1]);
		return db.getShowtimeIdByMovieAndTheatreAndShowtime(theatreId, movieId, searchValues[2]);
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
