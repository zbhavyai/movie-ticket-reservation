package movieTicketSystem.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import movieTicketSystem.model.Movie;
import movieTicketSystem.model.Theater;

/**
 * TheaterController class to control system access to Theater, Showtime, and Seat classes.
 */
public class TheaterController {



	private ArrayList<Theater> theaters = new ArrayList<Theater>();
	DbController db = DbController.getInstance();
	MovieController mvController = new MovieController();

	/**
	 * Default constructor
	 */
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
	 *
	 * Search for theatres based on movie Id
	 *
	 * @param movieId
	 * @return list of theatres playing the selected movie
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

	/**
	 *
	 * Method gets a list of theatre names available in the system
	 *
	 * @return an arraylist of theatre names
	 */
	public ArrayList<String> getTheaterNames() {
		ArrayList<String> theaterNames = new ArrayList<String>();
		for(int i = 0; i < theaters.size(); i++){
			theaterNames.add(theaters.get(i).getTheatreName());
		}
		return theaterNames;
	}

	/**
	 *
	 * Method is used to check if the showtime is valid or not by sending the ticketId number
	 *
	 * @param ticketId is the ticket that will be searched to see if it's associated showtime is valid
	 * @return true if the showtime is valid or false if not
	 */
	public boolean checkValidShowtime(int ticketId){
		return db.checkValidShowtime(ticketId);
	}

	/**
	 *
	 * Method gets a list of showtimes that are available from the selected movie and theatre
	 *
	 * @param movieName is the name of the movie that has been chosen
	 * @param theaterName is the name of the theatre that has been chosen
	 * @return a list of showtimes for the selected movie and theatre
	 */
	public ArrayList<String>getTheatreShowtimes(String movieName, String theaterName){
		ArrayList<String> showtimeList = new ArrayList<String>();
		showtimeList = db.searchShowtimesByMovieAndTheatre(db.getMovieIdByName(movieName), db.getTheaterIdByName(theaterName));
		return showtimeList;
	}

	/**
	 * Search for the seat availability for a given showtime.
	 */
	public int[][] getSeatGrid(int showtimeId) {
		return db.seatGrid(showtimeId);

	}

	/**
	 *
	 * Method used to check if a ticket is valid
	 *
	 * @param ticketId is the id number of the ticket to check for
	 * @return true if the ticket is valid and false if not
	 */
	public boolean checkValidTicket(int ticketId){
		return db.checkValidTicket(ticketId);
	}

	/**
	 * Search for the showtimeId based on the movieId, theaterId and showtime
	 */
	public int getShowtimeId(String[] searchValues){

		int theatreId = db.getTheaterIdByName(searchValues[0]);
		int movieId = db.getMovieIdByName(searchValues[1]);
		// check 10% here
		int showtimeId = db.getShowtimeIdByMovieAndTheatreAndShowtime(theatreId, movieId, searchValues[2]);
		if(mvController.searchMovieById(movieId).getReleaseDay().compareTo(LocalDate.now())>0) {
			//future release
			int count = db.getSeatCount(showtimeId);
			// if more than 10% seat taken just return invalid id to front end
			if((count/100.00)>0.1) {
				return -999;
			}
		}
		return showtimeId;
	}

	/**
	 *
	 * Method used to check if the showtime has been released or not
	 *
	 * @param searchValues is an array of the theater and movie to check the release date for
	 * @return true if the movie has been release and false if not
	 */
	public boolean checkShowtimeReleased(String[] searchValues){

		int theatreId = db.getTheaterIdByName(searchValues[0]);
		int movieId = db.getMovieIdByName(searchValues[1]);
		// check 10% here
		int showtimeId = db.getShowtimeIdByMovieAndTheatreAndShowtime(theatreId, movieId, searchValues[2]);
		return mvController.searchMovieById(movieId).getReleaseDay().compareTo(LocalDate.now()) < 0;
	}

	/**
	 *
	 * Method used to get the number of seats taken at the theater and movie entered
	 *
	 * @param searchValues is an array of the theater and movie to check the release date for
	 * @return a count of the tickets that have been taken for the movie and theater selected
	 */
	public int geteSeatsTaken(String[] searchValues){

		int theatreId = db.getTheaterIdByName(searchValues[0]);
		int movieId = db.getMovieIdByName(searchValues[1]);
		// check 10% here
		int showtimeId = db.getShowtimeIdByMovieAndTheatreAndShowtime(theatreId, movieId, searchValues[2]);
		//future release
		int count = db.getSeatCount(showtimeId);
		return count;
	}


	/**
	 * Search for all showtimes that match the specified movie id and theater id/movieId.
	 */
	public ArrayList<String> searchShowtimesByMovieAndTheatre(int theatreId, int movieId) {
		return db.searchShowtimesByMovieAndTheatre(theatreId, movieId);

	}

	/**
	 *
	 * Method is used to add a seat with the corresponding ticket id number and selected row and column values
	 *
	 * @param row is the row of the seat
	 * @param col is the column of the seat
	 * @param ticketId is the ticket id number for the seat
	 */
	public void createSeat(int row, int col, int ticketId){
		db.saveSeat(row, col, ticketId);
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

	/**
	 *
	 * Cancels the ticket and deletes it in the database
	 *
	 * @param ticketId is the ticket to cancel
	 * @return true if success and false if not
	 */
	public boolean makeSeatAvailable(int ticketId){
		boolean success = false;
		if(db.makeSeatAvailable(ticketId) == true){
			success = true;
		}
		return success;
	}

	/**
	 *
	 * Method used to cancel a ticket with it's specific id number
	 *
	 * @param ticketId is the specific ticket to cancel
	 */
	public void cancelTicket(int ticketId) {
		db.deleteTicket(ticketId);
	}
}
