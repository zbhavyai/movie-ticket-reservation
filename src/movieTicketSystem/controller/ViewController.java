package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;

import javax.swing.text.View;

public class ViewController {

    public static void main(String[] args) {
        MovieSelectionView theView = new MovieSelectionView();
        ViewController viewController = new ViewController();
        MovieSelectionViewController x = new MovieSelectionViewController(theView, viewController);
    }

    public String[] getMovies(){

        // **** TALK TO BACK END AND GET INITIAL MOVIE LIST ****
        // USE THE GET ALL MOVIES HERE
        String[] movieOptions = {
                "Knives Out",
                "House of Gucci",
                "Lord of the Rings"
        };

        return movieOptions;
    }

    public String[] getTheatres(){

        // **** TALK TO BACK END AND GET LIST OF THEATRES PLAYING THIS MOVIE ****
        // USE SEARCH THEATRES BY MOVIE ID HERE
        String[] theatreOptions = {
                "Scotiabank Theatre Chinook",
                "Canyon Meadows Cinema",
                "Shawnessey Theatre"
        };

        return theatreOptions;
    }

    public String[] getShowTimes(){

        // *** TALK TO BACK END AND GET LIST OF SHOWTIMES FOR THIS MOVIE/THEATRE ****
        // CALL METHOD TO GET SHOWTIMES BY MOVIE ID AND THEATRE ID
        String[] timeOptions = {
                "1:00 pm",
                "3:00 pm",
                "5:00 pm",
                "7:00 pm",
        };

        return timeOptions;
    }

    public int[][] getSeats(){
        // *** TALK TO BACK END AND GET ARRAY OF SEATS FOR THIS SHOWTIME/MOVIE/THEATRE
        // CALL METHOD TO GET SEATS ARRAY BASED ON MOVIE ID, THEATRE ID, SHOWTIME ID
        int[][] seats = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };
        return seats;

    }



}
