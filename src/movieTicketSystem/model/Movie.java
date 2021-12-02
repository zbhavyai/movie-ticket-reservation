package movieTicketSystem.model;


import java.util.ArrayList;


public class Movie {

	private int movieId;
	private String title;
	private ArrayList<Showtime> showtimes;
	private double rating;

	public Movie(String title, int movieId, double rating) {
		this.setTitle(title);
		this.setMovieId(movieId);
		this.rating = rating;
	}
	

	
	public void addShowTimes(Showtime showtime) {
		if(this.movieId!=showtime.getMovieId()) {
			System.out.println("movieId must match before adding");
			return;
		}
		// if not match time
		if(!this.showtimes.stream().anyMatch(t->t.getTime().equals(showtime.getTime()))) {
			this.showtimes.add(showtime);
		}
	}
	
	
	public void removeShowTimes(Showtime showtime) {
		if(this.movieId!=showtime.getMovieId()) {
			System.out.println("movieId must match before remove");
			return;
		}
		// if match time
		if(this.showtimes.parallelStream().anyMatch(t->t.getTime().equals(showtime.getTime()))) {
			this.showtimes.remove(showtime);
		}	
	}
	
	

	public ArrayList<Showtime> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(ArrayList<Showtime> showtimes) {
		this.showtimes = showtimes;
	}





	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	

	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}





	@Override
	public boolean equals(Object o) {
		Movie m = (Movie) o;
		return (m.getMovieId() == this.movieId);
	}



	

}
