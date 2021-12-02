package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MovieSelectionViewController {

    MovieComboBoxListener movieListener;
    TheatreComboBoxListener theatreListener;
    ShowtimeComboBoxListener showtimeListener;
    PurchaseButtonListener purchaseButtonListener;
    LoginButtonListener loginButtonListener;

    MovieSelectionView theView;


    public MovieSelectionViewController(MovieSelectionView theView) {
        this.theView = theView;

        // action listeners
        movieListener = new MovieComboBoxListener();
        theatreListener = new TheatreComboBoxListener();
        showtimeListener = new ShowtimeComboBoxListener();
        purchaseButtonListener = new PurchaseButtonListener();
        loginButtonListener = new LoginButtonListener();



        String[] movieOptions = getMovies();

        theView.setMovieOptions(movieOptions);

        // add action listeners
        theView.addMovieComboBoxActionListener(movieListener);
        theView.addTheatreComboBoxActionListener(theatreListener);
        theView.addShowtimeComboBoxActionListener(showtimeListener);
        theView.addPurchaseButtonActionListener(purchaseButtonListener);
        theView.addLoginButtonActionListener(loginButtonListener);

        theView.setVisible(true);

    }

    class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("login");
        }
    }

    class MovieComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Movie Selected: "  + theView.getMovieInput());
            String[] theatreOptions = getTheatres();

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

    class TheatreComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            System.out.println("Theatre Selected: "  + theView.getTheatreInput());
            String[] timeOptions = getShowTimes();

            //disable any previously selected seats for a different showtime
            theView.disableAllSeats();

            // add showtimes for this theatre / movie combo
            theView.removeShowtimeComboBoxActionListener(showtimeListener);
            theView.setTimeOptions(timeOptions);
            theView.addShowtimeComboBoxActionListener(showtimeListener);

        }
    }

    class ShowtimeComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Showtime Selected: "  + theView.getShowtimeInput());
            int[][] seats = getSeats();

            theView.setSeats(seats);}
    }

    class PurchaseButtonListener implements ActionListener {

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

    private int[][] getSeats(){
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
