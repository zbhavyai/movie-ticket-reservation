package movieTicketSystem.controller;

import java.util.ArrayList;
import movieTicketSystem.model.Movie;

public class MovieController {

	private ArrayList<Movie> movies;

	DbController db = DbController.getInstance();

	public MovieController() {
		ArrayList<Movie> moviesdb =	db.selectAllMovies();
		this.movies = moviesdb;
	}
	
	
	/**
	 * Search for movie based on movieId. Returns a movie name list
	 *
	 */
	public ArrayList<String> selectAllReleasedMovies() {

		ArrayList<Movie> moviesdb =	db.selectAllReleasedMovies();
		ArrayList<String> movieNames = new ArrayList<String>();
		for(int i = 0; i < moviesdb.size(); i++){
			movieNames.add(moviesdb.get(i).getTitle());
		}
		return movieNames;
	}
	
	
	

	/**
	 * Search for movie based on movieId. Returns a movie object
	 *
	 */
	public Movie searchMovieById(int movieId) {

		for (Movie movie : this.movies) {
			if (movie.getMovieId() == movieId) {
				return movie;
			}
		}
		return null;
	}

	public double getPrice(String movieName){
		return db.getPrice(db.getMovieIdByName(movieName));
	}

	/**
	 * 
	 * Method used to get a list of all movies by their names, available in the system
	 * 
	 * @return an arraylist of movie names
	 */
	public ArrayList<String> getMovieNames() {
		ArrayList<String> movieNames = new ArrayList<String>();
		for(int i = 0; i < movies.size(); i++){
			movieNames.add(movies.get(i).getTitle());
		}
		return movieNames;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}
	
	

}
