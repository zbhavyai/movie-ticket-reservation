package movieTicketSystem.controller;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import movieTicketSystem.model.Movie;



public class MovieController {
	
	private ArrayList<Movie> movies;
	
	DbController db = new DbController();

	public MovieController() {
		ArrayList<Movie> moviesdb =	db.selectAllMovies();
		this.movies = moviesdb;
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
	 * Search for movie based on theatre Id. Returns list of movie objects
	 * 
	 */
	public ArrayList<Movie> searchMovieByTheatre(int theatredId) {
		ArrayList<Integer> movieIds = db.selectMoviesByTheatre(theatredId);
		ArrayList<Movie> filteredmovies = new ArrayList<Movie>();
		
		for (int id : movieIds) {
			for (Movie movie : movies) {
				if(movie.getMovieId()==id) {
					filteredmovies.add(movie);
				}
			}
		}
		filteredmovies.stream().forEach(p-> System.out.println(p.getMovieId()));
		return filteredmovies;
	}

	
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
	
//	 public static <T> ArrayList<T> getArrayListFromStream(Stream<T> stream)
//	    {
//	  
//	        // Convert the Stream to List
//	        List<T>
//	            list = stream.collect(Collectors.toList());
//	  
//	        // Create an ArrayList of the List
//	        ArrayList<T>
//	            arrayList = new ArrayList<T>(list);
//	  
//	        // Return the ArrayList
//	        return arrayList;
//	    }
//	 
//	 
//	 	public static void main(String[] args) {
//			 MovieController mv = new MovieController();
//			 mv.searchMovieByTheatre(1);
//		}

}

			




