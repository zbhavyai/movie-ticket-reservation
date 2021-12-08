package movieTicketSystem.controller;

import java.util.ArrayList;
import movieTicketSystem.model.Movie;

/**
 * Handles functions related to movie
 */
public class MovieController {
	private ArrayList<Movie> movies;
	DbController db = DbController.getInstance();

	/**
	 * Default Contructor
	 */
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

	/**
	 *
	 * Method is used to get the price of a movie by providing the name of the movie.
	 *
	 * @param movieName is the name of the the movie to get the price for
	 * @return a double which is the price of the movie
	 */
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

	/**
	 * This method is used to return a list of movies
	 *
	 * @return an arraylist of movie objects
	 */
	public ArrayList<Movie> getMovies() {
		return movies;
	}
}
