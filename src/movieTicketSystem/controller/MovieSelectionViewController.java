package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;
import movieTicketSystem.model.RegisteredUser;

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
    ShowLoginButtonListener showLoginButtonListener;

    MovieSelectionView theView;

    TheaterController theTheater;
    UserController userController;


    public MovieSelectionViewController(MovieSelectionView theView, UserController userController) {
        this.theView = theView;
        this.userController = userController;

        // action listeners
        movieListener = new MovieComboBoxListener();
        theatreListener = new TheatreComboBoxListener();
        showtimeListener = new ShowtimeComboBoxListener();
        purchaseButtonListener = new PurchaseButtonListener();
        loginButtonListener = new LoginButtonListener();
        showLoginButtonListener = new ShowLoginButtonListener();



        String[] movieOptions = getMovies();

        theView.setMovieOptions(movieOptions);

        // add action listeners
        theView.addMovieComboBoxActionListener(movieListener);
        theView.addTheatreComboBoxActionListener(theatreListener);
        theView.addShowtimeComboBoxActionListener(showtimeListener);
        theView.addPurchaseButtonActionListener(purchaseButtonListener);
        theView.addShowLoginButtonActionListener(showLoginButtonListener);
        theView.addLoginButtonListener(loginButtonListener);

        theView.setVisible(true);

    }

    // login button
    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean loggedIn = theView.getLoggedIn();
            if(!loggedIn){
                // attempt to log in
                String userName = theView.getUserName();
                String password = theView.getPassword();
                boolean authenticated = authenticateUser(userName, password);
                if(!authenticated){
                    JOptionPane.showMessageDialog(theView, "Invalid Credentials.",
                            "Alert", JOptionPane.WARNING_MESSAGE);
                }
                theView.setLoggedIn(authenticated);
            }
            else{
                // log out
                theView.setLoggedIn(false);
            }



        }
    }


    class ShowLoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            theView.toggleLoginForm();
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

            String showtime = theView.getShowtimeInput();
            String movie = theView.getMovieInput();
            String theater = theView.getTheatreInput();
            System.out.println("Showtime Selected: "  + theView.getShowtimeInput());
            String[] showtimeSearch = {theater, movie, showtime};
            int showtimeId = getShowtimeId(showtimeSearch);
            int[][] seats = getSeats(showtimeId);

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

    private int[][] getSeats(int showtimeId){

        int [][] seats = theTheater.getSeatGrid(showtimeId);
        return seats;
    }

    public int getShowtimeId(String[] searchValues){
        int showtimeId = theTheater.getShowtimeId(searchValues);
        return showtimeId;
    }

    private boolean authenticateUser(String userName, String password){
        RegisteredUser ru = userController.verifyUser(userName, password);


        if((ru == null)){
            return false;  // user does not exist
        } else {
            return true;  // user does exist
        }
    }

}
