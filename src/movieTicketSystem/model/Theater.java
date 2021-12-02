package movieTicketSystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Theater class to store theater/movie/showtime news and current movie showtimes.
 * 
 */
public class Theater {
	private int theatreId;
	private String theatreName;
	// show times already contains movieId and TheatreId
	private LinkedHashSet<Showtime> showtimes;
	
	
	/**
	 * Theater constructor that assigns id.
	 * @param theaterId (int) id assigned to the theater
	 */
	public Theater(int theaterId, String name) {
		this.setTheatreId(theaterId);
		this.setTheatreName(name);
	}
	
	public void addShowTimes(Showtime showtime) {
		if(this.theatreId!=showtime.getAuditorium()) {
			System.out.println("theaterId must match before adding");
			return;
		}
		// if not match time
		if(!this.showtimes.stream().anyMatch(t->t.getTime().equals(showtime.getTime()))) {
			this.showtimes.add(showtime);
		}
	}
	
	
	public void removeShowTimes(Showtime showtime) {
		if(this.theatreId!=showtime.getAuditorium()) {
			System.out.println("theaterId must match before adding");
			return;
		}
		// if match time
		if(this.showtimes.stream().anyMatch(t->t.getTime().equals(showtime.getTime()))) {
			this.showtimes.remove(showtime);
		}	
	}
	
	
	/**
	 * Cancel a reservation for a specified seat number and showtime.
	 */
	public void resetReservation(int movieId, LocalDateTime time, int colNum, int rowNum) {
		for (Showtime showtime : showtimes) {
			if (showtime.getMovieId() == movieId && 
					showtime.getTime().isEqual(time))
				showtime.resetReservation(rowNum, colNum);
		}
	}
	
	/**
	 * Reserve a seat for a specified show time and seat number.
	 */
	public void reserveSeat(int movieId, LocalDateTime time, int colNum, int rowNum) {
		for (Showtime showtime : showtimes) {
			if (showtime.getMovieId() == movieId && 
					showtime.getTime().isEqual(time))
				showtime.reserveSeat(rowNum, colNum);
		}
	}
	


	/**
	 * Search for all showtimes where the theatreId matches the searched id.
	 */
	public ArrayList<Showtime> searchShowtimes(int theatreId) {
		ArrayList<Showtime> foundShowtimes = new ArrayList<Showtime>();
		
		for (Showtime show : this.getShowtimes()) {
			if (show.getAuditorium() == theatreId)
				foundShowtimes.add(show);
		}
		return foundShowtimes;
	}
	
	
	
	

	



	public int getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(int theatreId) {
		this.theatreId = theatreId;
	}

	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public LinkedHashSet<Showtime> getShowtimes() {
		return showtimes;
	}




}
