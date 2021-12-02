package movieTicketSystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Showtime class to store a specified movie showing with all reservable seats
 * for the auditorium.
 */
public class Showtime {
	private int movieId;
	private int auditorium;
	private LocalDateTime time;
	private ArrayList<Seat> seats = new ArrayList<Seat>();

	/**
	 * Showtime constructor that assigns movie and showing time and creates seating
	 * plan. Seats range from 1-10 rows and 1-10 columns.
	 */
	public Showtime(int movieId, int auditorium, LocalDateTime time) {
		this.setMovie(movieId);
		this.setTime(time);
		this.setAuditorium(auditorium);

		for (int i = 1; i < 10; i++) { // columns
			for (int j = 1; j < 10; j++) { // rows
				addSeats(new Seat(i,j));
			}
		}
	}
	
	
	/**
	 * Adds a new seat to the theater ArrayList.
	 * 
	 * @param seat (Seat) new seat being added to theater.
	 */
	public void addSeats(Seat seat) {
		this.seats.add(seat);
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovie(int movieId) {
		this.movieId = movieId;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}



	/**
	 * Cancel a reservation for a specified seat number.
	 * 
	 */
	public void resetReservation(int rowNumber, int colNumber) {
		for (Seat seat : seats) {
			if (seat.getColNumber() == colNumber && seat.getRowNumber()== rowNumber)
				seat.resetReservation();
		}
	}

	/**
	 * Reserve a specified seat number.
	 * 
	 */
	public void reserveSeat(int rowNumber, int colNumber) {
		for (Seat seat : seats) {
			if (seat.getColNumber() == colNumber && seat.getRowNumber()== rowNumber)
				seat.setReserved(true);
		}
	}

	public int getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(int auditorium) {
		this.auditorium = auditorium;
	}

	public boolean availableCapacity() {
		double sum = 0.0;
		for (Seat seat : seats) {
			if (seat.isReserved()) {
				sum += 1;
			}
		}
		System.out.println(sum+","+seats.size());
		if (sum < seats.size()) {
			return true;
		}
		return false;
	}
}
