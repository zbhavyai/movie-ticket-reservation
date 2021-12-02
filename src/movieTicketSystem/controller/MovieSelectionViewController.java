package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MovieSelectionViewController {

    // action listeners
    MovieSelectionView theView;
    movieComboBoxListener movieListener;
    theatreComboBoxListener theatreListener;
    showtimeComboBoxListener showtimeListener;
    purchaseButtonListener purchaseButtonListener;

    TheaterController theTheater;


    public MovieSelectionViewController(MovieSelectionView theView) {
        this.theView = theView;
        movieListener = new movieComboBoxListener();
        theatreListener = new theatreComboBoxListener();
        showtimeListener = new showtimeComboBoxListener();
        purchaseButtonListener = new purchaseButtonListener();

        // **** TALK TO BACK END AND GET INITIAL MOVIE LIST ****
        String[] movieOptions = {
                "Knives Out",
                "House of Gucci",
                "Lord of the Rings"
        };
        theView.setMovieOptions(movieOptions);

        // add action listeners
        theView.addMovieComboBoxActionListener(movieListener);
        theView.addTheatreComboBoxActionListener(theatreListener);
        theView.addShowtimeComboBoxActionListener(showtimeListener);
        theView.addPurchaseButtonActionListener(purchaseButtonListener);

        theView.setVisible(true);

    }

    class movieComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // **** TALK TO BACK END AND GET LIST OF THEATRES PLAYING THIS MOVIE ****
            System.out.println("Movie Selected: "  + theView.getMovieInput());
            String[] theatreOptions = {
                    "Scotiabank Theatre Chinook",
                    "Canyon Meadows Cinema",
                    "Shawnessey Theatre"
            };

            // clear any previous showtime options
            theView.removeShowtimeComboBoxActionListener(showtimeListener);
            theView.clearShowtimeOptions();
            theView.addShowtimeComboBoxActionListener(showtimeListener);

            // clear previously selected seats
            theView.disableAllSeats();

            // add theatre options based on movie selected
            theView.removeTheatreComboBoxActionListener(theatreListener);
            theView.setTheatreOptions(theatreOptions);
            theView.addTheatreComboBoxActionListener(theatreListener);

        }
    }

    class theatreComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // *** TALK TO BACK END AND GET LIST OF SHOWTIMES FOR THIS MOVIE/THEATRE ****
            System.out.println("Theatre Selected: "  + theView.getTheatreInput());
            String[] timeOptions = {
                    "1:00 pm",
                    "3:00 pm",
                    "5:00 pm",
                    "7:00 pm",
            };

            //disable any previously selected seats for a different showtime
            theView.disableAllSeats();

            // add showtimes for this theatre / movie combo
            theView.removeShowtimeComboBoxActionListener(showtimeListener);
            theView.setTimeOptions(timeOptions);
            theView.addShowtimeComboBoxActionListener(showtimeListener);

        }
    }

    class showtimeComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String showtime = theView.getShowtimeInput();
            String movie = theView.getMovieInput();
            String theater = theView.getTheatreInput();
            System.out.println("Showtime Selected: "  + theView.getShowtimeInput());
            String[] showtimeSearch = {theater, movie, showtime};
            int showtimeId = getShowtimeId(showtimeSearch);
            int[][] seats = getSeats(showtimeId);

            theView.setSeats(seats);}
    }

    class purchaseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //**** FOR EACH SELECTED SEAT, SEND ROW, COL, MOVIE, THEATRE, SHOWTIME TO BACK END FOR PURCHASE ****
            JButton[][] seats = theView.getSeats();
            for(int i=0; i<10; i++){
                for(int j=0; j<10; j++){
                    Color seatBackground = seats[i][j].getBackground();
                    if(seatBackground == Color.green){
                        int row = i;
                        int col = j;
                        System.out.println(
                                "\nPURCHASE INFO:"
                                + "\n row: " + (i+1)
                                + "\n col: " + (j+1)
                                + "\n movie: " + theView.getMovieInput()
                                + "\n theatre: " + theView.getTheatreInput()
                                + "\n showtime: " + theView.getShowtimeInput()
                        );
                    }
                }
            }
        }
    }

    private String[] getMovies(){

        // **** TALK TO BACK END AND GET INITIAL MOVIE LIST ****
        // USE THE GET ALL MOVIES HERE
        String[] movieOptions = {
                "Knives Out",
                "House of Gucci",
                "Lord of the Rings"
        };

        return movieOptions;
    }

    private String[] getTheatres(){

        // **** TALK TO BACK END AND GET LIST OF THEATRES PLAYING THIS MOVIE ****
        // USE SEARCH THEATRES BY MOVIE ID HERE
        String[] theatreOptions = {
                "Scotiabank Theatre Chinook",
                "Canyon Meadows Cinema",
                "Shawnessey Theatre"
        };

        return theatreOptions;
    }

    private String[] getShowTimes(){

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

    private int[][] getSeats(int showtimeId){

        int [][] seats = theTheater.getSeatGrid(showtimeId);
        return seats;
    }

    public int getShowtimeId(String[] searchValues){
        int showtimeId = theTheater.getShowtimeId(searchValues);
        return showtimeId;
    }

    private boolean authenticateUser(String userName, String password){
        if((userName.equals("Graydon")) & (password.equals("1234"))){
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        MovieSelectionView theView = new MovieSelectionView();
        MovieSelectionViewController x = new MovieSelectionViewController(theView);
    }
}
