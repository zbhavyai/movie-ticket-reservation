package movieTicketSystem.controller;

import java.io.FileReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.sql.Date;
import movieTicketSystem.model.*;

/**
 * Controller to interact with the database
 */
public class DbController {
    private static DbController instanceVar;
    private Connection dbConnect;
    private Properties dbDetails;
    private ResultSet results;

    /**
     * Private constructor enforcing singleton design pattern
     */
    private DbController() {
        try {
            String dbDetailsLocation = "config/db_details.properties";

            this.dbDetails = new Properties();
            this.dbDetails.load(new FileReader(dbDetailsLocation));

            String connectionString = String.format("jdbc:mysql://%s:%s/%s", this.dbDetails.getProperty("db.host"),
                    this.dbDetails.getProperty("db.port"), this.dbDetails.getProperty("db.schema"));

            this.dbConnect = DriverManager.getConnection(connectionString, this.dbDetails.getProperty("db.user"),
                    this.dbDetails.getProperty("db.password"));
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the connection to the database and returns its instance
     *
     * @return instance of DbController
     */
    public static DbController getInstance() {
        if (instanceVar == null) {
            instanceVar = new DbController();
            return instanceVar;
        }

        else {
            return instanceVar;
        }
    }

    /**
     * Get count of occupied seats in the specific showtime id
     *
     * @param showtimeId id of the showtime
     * @return seat count
     */
    public int getSeatCount(int showtimeId) {
        int sum = 0;
        try {
            String query = "SELECT * FROM TICKET WHERE showtimeId=?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showtimeId);

            ResultSet results = myStmt.executeQuery();
            while (results.next()) {
                sum++;
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sum;
    }

    /**
     * Get a list of all movies in the application
     *
     * @return an arraylist that contains all movies in the application
     */
    public ArrayList<Movie> selectAllMovies() {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            Statement myStmt = dbConnect.createStatement();

            // Execute SQL query
            results = myStmt.executeQuery("SELECT * FROM MOVIE");

            // Process the results set
            while (results.next()) {
                Movie mvdb = new Movie(results.getString("title"), results.getInt("movieId"),
                        results.getDouble("rating"), results.getDate("releasedate").toLocalDate());
                movies.add(mvdb);
            }
            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return movies;
    }

    /**
     * Get a list of all released movies in the application
     *
     * @return an arraylist that contains all movies in the application
     */
    public ArrayList<Movie> selectAllReleasedMovies() {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            Statement myStmt = dbConnect.createStatement();

            // Execute SQL query
            results = myStmt.executeQuery("select * from MOVIE \r\n" +
                    "where releasedate <= cast(now() as date)");

            // Process the results set
            while (results.next()) {
                Movie mvdb = new Movie(results.getString("title"), results.getInt("movieId"),
                        results.getDouble("rating"), results.getDate("releasedate").toLocalDate());
                movies.add(mvdb);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return movies;
    }

    /**
     *
     * Get a list of all available theatres in the application
     *
     * @return an arraylist that contains all theatres in the system.
     */
    public ArrayList<Theater> selectAllTheatres() {
        ArrayList<Theater> theaters = new ArrayList<Theater>();

        try {
            Statement myStmt = dbConnect.createStatement();

            // Execute SQL query
            results = myStmt.executeQuery("SELECT * FROM THEATRE");

            // Process the results set
            while (results.next()) {
                Theater tr = new Theater(results.getInt("theatreId"), results.getString("theatreName"));
                theaters.add(tr);
            }

            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return theaters;
    }

    /**
     *
     * Method is used to get a list of all the theatres that are showing a selected
     * movie
     *
     * @param movieId is the Id of the movie that has been selected
     * @return is an arraylist of all theatres playing the selected movie
     */
    public ArrayList<Integer> searchTheatresByMovie(int movieId) {
        ArrayList<Integer> theatreIds = new ArrayList<Integer>();

        try {
            String query = "SELECT * FROM SHOWTIME Where movieId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, movieId);

            ResultSet results = myStmt.executeQuery();

            // Process the results set
            while (results.next()) {
                theatreIds.add(results.getInt("theatreId"));
            }

            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return theatreIds;
    }

    /**
     * This method is used to get the theatreId from the database by using the
     * theatreName provided
     *
     * @param theaterName is the name of the theatre to search
     * @return the Id number of the theatre searched
     */
    public int getTheaterIdByName(String theaterName) {
        int theaterId = 0;

        try {
            String query = "SELECT theatreId FROM THEATRE Where theatreName = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setString(1, theaterName);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                theaterId = results.getInt("theatreId");
            }
            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return theaterId;
    }

    /**
     * This method is used to get the movieId from the database by using the
     * movieName provided
     *
     * @param movieName is the name of the movie to search
     * @return the Id number of the movie searched
     */
    public int getMovieIdByName(String movieName) {
        int movieId = 0;

        try {
            String query = "SELECT movieId FROM MOVIE Where title = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setString(1, movieName);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                movieId = results.getInt("movieId");
            }

            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return movieId;
    }

    /**
     * This method is used to get the price of a movie
     *
     * @param movieId is the Id of the movie to search
     * @return the price of the movie as a double
     */
    public double getPrice(int movieId) {
        double moviePrice = 0.0;

        try {
            String query = "SELECT price FROM MOVIE Where movieId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setInt(1, movieId);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                moviePrice = results.getDouble("price");
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return moviePrice;
    }

    /**
     * This method is used to get the price of a ticket
     *
     * @param ticketId is the Id of the movie to search
     * @return the price of the ticket as a double
     */
    public double getPriceFromTicket(int ticketId) {
        double ticketPrice = 0.0;

        try {
            String query = "SELECT price FROM TICKET Where ticketId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setInt(1, ticketId);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                ticketPrice = results.getDouble("price");
            }
            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ticketPrice;
    }

    /**
     * This method is used to retrieve the showtimeId based on the theatreId,
     * movieId and showtimeString
     *
     * @param theatreId      is the id of the theatre to search in
     * @param movieId        is the id of the movie to search in
     * @param showtimeString is the showtime to search in
     * @return the showtimeId that matches with all three of the input criteria
     */
    public int getShowtimeIdByMovieAndTheatreAndShowtime(int theatreId, int movieId, String showtimeString) {
        int showtimeId = 0;

        try {
            String query = "SELECT showtimeId FROM SHOWTIME Where movieId = ? AND theatreId = ? AND showtime = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, movieId);
            myStmt.setInt(2, theatreId);
            myStmt.setString(3, showtimeString);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                showtimeId = results.getInt("showtimeId");
            }

            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return showtimeId;
    }

    /**
     * Check if there is any showtime related to the ticketid
     *
     * @param ticketId the id of the ticket
     * @return true if showtime is found, false otherwise
     */
    public boolean checkValidShowtime(int ticketId) {
        boolean validShowtime = true;
        Date showtimedate = getShowtimeByTicketId(ticketId);
        LocalDate showtime;

        // means that it was not found
        if (showtimedate == null) {
            return false;
        }

        else {
            showtime = showtimedate.toLocalDate();
        }

        LocalDate now = LocalDate.now().plusDays(3);
        if (now.isAfter(showtime)) {
            validShowtime = false;
        }
        return validShowtime;
    }

    /**
     * Get the date of the showtime from the ticketId
     *
     * @param ticketId the id of the ticket
     * @return true if associated showtime is found in the db, false otherwise
     */
    public boolean checkValidTicket(int ticketId) {
        boolean validShowtime = true;
        // LocalDate showtime = getShowtimeByTicketId(ticketId).toLocalDate();
        Date showtimedate = getShowtimeByTicketId(ticketId);
        LocalDate showtime;

        // means that it was not found
        return showtimedate != null;

    }

    /**
     * Gets the id of the showtime from ticketId
     *
     * @param ticketId the id of the ticket
     * @return the date of the showtime
     */
    public Date getShowtimeByTicketId(int ticketId) {
        Date showtime = null;
        int showtimeId = 0;
        try {
            String query = "SELECT showtimeId FROM TICKET Where ticketId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, ticketId);

            ResultSet results = myStmt.executeQuery();

            if (results.next()) {
                showtimeId = results.getInt("showtimeId");
            }

            else {
                return null;
            }

            myStmt.close();
            try {
                query = "SELECT showtime FROM SHOWTIME Where showtimeId = ?";
                myStmt = dbConnect.prepareStatement(query);

                myStmt.setInt(1, showtimeId);

                results = myStmt.executeQuery();

                while (results.next()) {
                    showtime = results.getDate("showtime");
                }

                myStmt.close();
            }

            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return showtime;
    }

    /**
     * Method is used to find showtimes that are available for the selected movie
     * and theatre
     *
     * @param movieId   is the movie that has been selected
     * @param theatreId is the theatre that has been selected
     * @return a list of showtimes that are available for the movie and theatre
     *         selected
     */
    public ArrayList<String> searchShowtimesByMovieAndTheatre(int movieId, int theatreId) {

        ArrayList<String> showTimes = new ArrayList<String>();

        try {
            String query = "SELECT showtime FROM SHOWTIME Where movieId = ? AND theatreId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, movieId);
            myStmt.setInt(2, theatreId);

            ResultSet results = myStmt.executeQuery();

            // Process the results set
            while (results.next()) {
                String dtime = results.getDate("showtime").toString() + " " + results.getTime("showtime").toString();
                showTimes.add(dtime);
            }

            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return showTimes;
    }

    /**
     * Method is used to return the date/time of the showtime when providing the
     * unique Id
     *
     * @param showTimeId is the unique Id of the showtime that has been selected
     * @return the showtime that corresponds to the unique showtime id
     */
    public String searchShowtimesById(int showTimeId) {
        String showTime = "";

        try {
            String query = "SELECT showtime FROM SHOWTIME Where showTimeId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showTimeId);

            ResultSet results = myStmt.executeQuery();

            // Process the results set
            while (results.next()) {
                showTime = results.getDate("showtime").toString() + " " + results.getTime("showtime").toString();
            }

            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return showTime;
    }

    /**
     * Method is used to retrieve the movies and theatres that correspond to the
     * showtime selected
     *
     * @param showTimeId is the showtime that has been selected
     * @return an arraylist of movie and theatre Id's related to the showtime
     */
    public ArrayList<Integer> searchMovieTheatreByShowTime(int showTimeId) {
        ArrayList<Integer> movieTheatreId = new ArrayList<Integer>();

        try {
            String query = "SELECT * FROM SHOWTIME Where showtimeId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showTimeId);

            ResultSet results = myStmt.executeQuery();

            if (results.next()) {
                // Process the results set
                movieTheatreId.add(results.getInt("movieId"));
                movieTheatreId.add(results.getInt("theatreId"));
            }

            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return movieTheatreId;
    }

    /**
     * Method is used to make a new ticket depending on what the showtime selection
     * was and the price
     *
     * @param showtimeId is the showtime selected
     * @param price      is the price to set for the ticket
     */
    public void createNewTicket(int showtimeId, double price) {
        // if (!validTicket(showtimeId)) {
        // throw new IllegalArgumentException("ticket id already exists.");
        // }

        try {
            String query = "INSERT INTO TICKET (showtimeId, price) VALUES (?,?)";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showtimeId);
            myStmt.setDouble(2, price);
            myStmt.executeUpdate();
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the max of ticketId in the db
     *
     * @return the maximum ticketid present in the db
     */
    public int getTicketId() {
        int ticketId = 0;
        try {
            String query = "SELECT MAX(ticketId) FROM TICKET";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            ResultSet results = myStmt.executeQuery();

            // Process the results set
            while (results.next()) {
                ticketId = results.getInt("MAX(ticketId)");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ticketId;
    }

    /**
     * Deletes the ticket with ticketId from the DB
     *
     * @param ticketId the ticket with ticketId to be deleted
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteTicket(int ticketId) {
        try {
            String query = "DELETE FROM SEAT WHERE ticketId = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            myStmt.setInt(1, ticketId);
            int rowAffected = myStmt.executeUpdate();

            if (rowAffected != 1) {
                myStmt.close();
                return false;
            }

            query = "DELETE FROM TICKET WHERE ticketId = ?";
            myStmt = this.dbConnect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            myStmt.setInt(1, ticketId);
            rowAffected = myStmt.executeUpdate();

            if (rowAffected != 1) {
                myStmt.close();
                return false;
            }
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    /**
     * Method is used to verify if a ticket is valid or not
     *
     * @param showtimeId is the showtime to check validity for
     * @return true or false if the ticket is valid or not
     */
    public boolean validTicket(int showtimeId) {
        boolean validTicket = true;

        try {
            Statement myStmt = dbConnect.createStatement();

            // Execute SQL query
            results = myStmt.executeQuery("SELECT * FROM SHOWTIME");

            // Process the results set
            while (results.next()) {
                if (results.getInt("showtimeId") == showtimeId)
                    validTicket = false;
            }

            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return validTicket;
    }

    /**
     * This method finds all tickets that have been created for a particular
     * showtime
     *
     * @param showtimeId is the showtime to search for tickets in
     * @return a list of tickets that have been created for the given showtime
     */
    public ArrayList<Integer> ticketsAtShowtime(int showtimeId) {
        ArrayList<Integer> tickets = new ArrayList<Integer>();

        try {
            String query = "SELECT * FROM TICKET Where showtimeId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showtimeId);

            ResultSet results = myStmt.executeQuery();

            // Get a list of tickets associated with that showtime
            while (results.next()) {
                tickets.add(results.getInt("ticketId"));
            }
            myStmt.close();
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tickets;
    }

    /**
     * Searches for the ticket in the database and calls for it to be deleted
     *
     * @param ticketId is the ticket to get rid of
     * @return a boolean of whether the ticket was deleted or not
     */
    public boolean makeSeatAvailable(int ticketId) {
        boolean success = false;
        int foundTicket = 0;

        try {
            String query = "SELECT ticketId FROM TICKET WHERE ticketId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setInt(1, ticketId);
            ResultSet results = myStmt.executeQuery();
            while (results.next()) {
                foundTicket = results.getInt("ticketId");
            }
            myStmt.close();
            if (foundTicket != 0) {
                deleteTicket(ticketId);
                success = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    /**
     * Marks the seat as booked in the db
     *
     * @param row      the row number of the seat
     * @param col      the column number of the seat
     * @param ticketId the id of the ticket that is booking the seat
     */
    public void saveSeat(int row, int col, int ticketId) {
        try {
            String query = "INSERT INTO SEAT(seatRow, seatNum, ticketId) VALUES (?, ?, ?)";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setInt(1, row);
            myStmt.setInt(2, col);
            myStmt.setInt(3, ticketId);
            myStmt.executeUpdate();
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is used to return a grid of seats for a particular showtime. The
     * grid will show available seats as 1's and unavailable as 0's
     *
     * @param showtimeId is the Id of the showtime to search for
     * @return a 2d integer array representing the seats and their availability for
     *         the showtime
     */
    public int[][] seatGrid(int showtimeId) {
        ArrayList<Integer> tickets = ticketsAtShowtime(showtimeId);
        ArrayList<Integer> row = new ArrayList<Integer>();
        ArrayList<Integer> col = new ArrayList<Integer>();
        int[][] seatGrid = new int[10][10];

        try {
            for (int i = 0; i < tickets.size(); i++) {
                String query = "SELECT * FROM SEAT Where ticketId = ?";
                PreparedStatement myStmt = dbConnect.prepareStatement(query);
                myStmt.setInt(1, tickets.get(i));

                ResultSet results = myStmt.executeQuery();
                while (results.next()) {
                    row.add(results.getInt("seatRow"));
                    col.add(results.getInt("seatNum"));
                }
            }

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    seatGrid[i][j] = 2;
                }
            }

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < row.size(); k++) {
                        if (i + 1 == row.get(k) && j + 1 == col.get(k)) {
                            seatGrid[i][j] = 0;
                        }
                        if (seatGrid[i][j] != 0) {
                            seatGrid[i][j] = 1;
                        }
                    }
                }
            }
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (tickets.size() == 0) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    seatGrid[i][j] = 1;
                }
            }
        }

        return seatGrid;
    }

    /**
     * Fetches an object of RegisteredUser from DB using email
     *
     * @param email email while registering in the system
     * @return RegisteredUser object if found, null otherwise
     */
    public RegisteredUser getRegisteredUser(String email, String password) {
        try {
            String query = "SELECT * FROM REGISTERED_USER WHERE email = ? AND password = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);
            myStmt.setString(1, email);
            myStmt.setString(2, password);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                if (results.getString("email").equals(email) && results.getString("password").equals(password)) {
                    RegisteredUser ru = new RegisteredUser();
                    ru.setId(results.getInt("userId"));
                    ru.setUsername(results.getString("username"));
                    ru.setPassword(results.getString("password"));
                    ru.setEmail(email);
                    ru.setAddress(results.getString("address"));
                    ru.setLastFeePaid(results.getDate("lastPaid").toLocalDate());
                    ru.setCard(this.getPayment(results.getInt("card")));

                    myStmt.close();
                    return ru;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Writes an object of RegisteredUser to the DB
     *
     * @param email      email of the user to be registered
     * @param password   password of the user to be registered
     * @param address    address of the user to be registered
     * @param holderName Name of the card holder
     * @param cardNumber the credit/debit card number
     * @param expiry     expiry date of the card
     * @return the RegisteredUser object saved, null if insertion is unsuccessful
     */
    public RegisteredUser saveRegisteredUser(String username, String email, String password, String address,
            String holderName, String cardNumber, String expiry) {
        // if the email already exists, dont save
        if (this.getRegisteredUser(email, password) != null) {
            return null;
        }

        try {
            Payment card = this.savePayment(holderName, cardNumber, expiry);

            // if there is problem while saving payment
            if (card == null) {
                return null;
            }

            LocalDate now = LocalDate.now();

            String query = "INSERT INTO REGISTERED_USER(username, password, email, address, card, lastPaid) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            myStmt.setString(1, username);
            myStmt.setString(2, password);
            myStmt.setString(3, email);
            myStmt.setString(4, address);
            myStmt.setInt(5, card.getId());
            myStmt.setDate(6, java.sql.Date.valueOf(now));

            int rowAffected = myStmt.executeUpdate();

            if (rowAffected == 1) {
                ResultSet rs = myStmt.getGeneratedKeys();

                if (rs.next()) {
                    RegisteredUser ru = new RegisteredUser();
                    ru.setId(rs.getInt(1));
                    ru.setUsername(username);
                    ru.setEmail(email);
                    ru.setPassword(password);
                    ru.setAddress(address);
                    ru.setCard(card);
                    ru.setLastFeePaid(now);

                    myStmt.close();
                    return ru;
                }
            }

            else {
                throw new SQLException(
                        String.format("[FAIL] %d rows affected during insertion of payment. Check!%n", rowAffected));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fetches an object of Payment from DB using paymentId
     *
     * @param paymentId primary key of PAYMENT
     * @return Payment object if found, null otherwise
     */
    public Payment getPayment(int paymentId) {
        try {
            String query = "SELECT * FROM PAYMENT WHERE paymentId = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);
            myStmt.setInt(1, paymentId);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                if (results.getInt("paymentId") == paymentId) {
                    Payment p = new Payment();
                    p.setId(paymentId);
                    p.setCardHolderName(results.getString("holderName"));
                    p.setCardNum(results.getString("cardNumber"));
                    p.setExpiry(results.getDate("expiry").toLocalDate());

                    myStmt.close();
                    return p;
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fetches the payment id using the card details
     *
     * @param name           holder name of the card
     * @param cardNum        number of the card
     * @param cardExpiryDate expiry date of the card
     * @return payment id in the system that matches the card details
     */
    public int getPaymentIdByNameCardNumAndExpiry(String name, String cardNum, String cardExpiryDate) {
        int paymentId = 0;
        try {
            String query = "SELECT paymentId FROM PAYMENT WHERE holderName =? AND cardNumber = ? AND expiry = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);
            myStmt.setString(1, name);
            myStmt.setString(2, cardNum);
            myStmt.setDate(3, Date.valueOf(cardExpiryDate));

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                paymentId = results.getInt("paymentId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentId;
    }

    /**
     * Writes an object of Payment to the DB
     *
     * @param holderName Name of the card holder
     * @param cardNumber the credit/debit card number
     * @param expiry     expiry date of the card
     * @return the Payment object saved, null if insertion is unsuccessful
     */
    public Payment savePayment(String holderName, String cardNumber, String expiry) {
        try {
            String query = "INSERT INTO PAYMENT(holderName, cardNumber, expiry) VALUES (?, ?, ?)";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            myStmt.setString(1, holderName);
            myStmt.setString(2, cardNumber);
            // myStmt.setDate(3, java.sql.Date.valueOf(expiry));
            // myStmt.setDate(3, LocalDate.parse(expiry, dateformatter));
            myStmt.setDate(3, Date.valueOf(expiry));

            int rowAffected = myStmt.executeUpdate();

            if (rowAffected == 1) {
                ResultSet rs = myStmt.getGeneratedKeys();

                if (rs.next()) {
                    Payment p = new Payment();
                    p.setId(rs.getInt(1));
                    p.setCardHolderName(holderName);
                    p.setCardNum(cardNumber);
                    p.setExpiry(LocalDate.parse(expiry));

                    myStmt.close();
                    return p;
                }
            }

            else {
                throw new SQLException(
                        String.format("[FAIL] %d rows affected during insertion of payment. Check!%n", rowAffected));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fetches an object of Coupon from DB using couponCode
     *
     * @param couponCode unique coupon code provided during cancellation
     * @return Coupon object if coupon is found, null otherwise
     */
    public Coupon getCoupon(String couponCode) {
        try {
            String query = "SELECT * FROM COUPON WHERE couponCode = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);
            myStmt.setString(1, couponCode);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                if (results.getString("couponCode").equals(couponCode)) {

                    Coupon c = new Coupon();
                    c.setId(results.getInt("couponId"));
                    c.setCouponCode(couponCode);
                    c.setCouponAmount(results.getDouble("couponAmount"));
                    c.setExpiry(results.getDate("expiry").toLocalDate());

                    myStmt.close();
                    return c;
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Writes an object of Coupon to the DB
     *
     * @param couponCode   the generated coupon code
     * @param couponAmount the amount of the coupon
     * @param expiry       expiry date of the coupon code
     * @return the Coupon object saved, null if insertion is unsuccessful
     */
    public Coupon saveCoupon(String couponCode, double couponAmount, LocalDate expiry) {
        try {
            String query = "INSERT INTO COUPON(couponCode, couponAmount, expiry) VALUES (?, ?, ?)";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            myStmt.setString(1, couponCode);
            myStmt.setDouble(2, couponAmount);
            myStmt.setDate(3, java.sql.Date.valueOf(expiry));

            int rowAffected = myStmt.executeUpdate();

            if (rowAffected == 1) {
                ResultSet rs = myStmt.getGeneratedKeys();

                if (rs.next()) {
                    Coupon c = new Coupon();
                    c.setId(rs.getInt(1));
                    c.setCouponCode(couponCode);
                    c.setCouponAmount(couponAmount);
                    c.setExpiry(expiry);

                    myStmt.close();
                    return c;
                }
            }

            else {
                throw new SQLException(
                        String.format("[FAIL] %d rows affected during insertion of coupon. Check!%n", rowAffected));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fetches movie name and showtime from ticket id
     *
     * @param ticketId the ticketId
     * @return array of strings containing movie title and showtime
     */
    public String[] getMovieAndShowtime(int ticketId) {
        String[] movieShowtime = new String[2];

        try {
            String query = "SELECT M.title, S.showtime FROM TICKET T, SHOWTIME S, MOVIE M WHERE T.showtimeId=S.showtimeId AND S.movieId=M.movieId AND T.ticketId=?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);
            myStmt.setInt(1, ticketId);

            ResultSet results = myStmt.executeQuery();

            if (results.next()) {
                movieShowtime[0] = results.getString("title");
                movieShowtime[1] = results.getTimestamp("showtime").toString();

                myStmt.close();
                return movieShowtime;
            }

            else {
                throw new SQLException(String.format("[FAIL] Couldn't fetch movie title and showtime%n"));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Verifies if the coupon code is valid
     *
     * @param couponCode the coupon code generated during cancellation
     * @return true if coupon code is found in the Db, false otherwise
     */
    private boolean checkCode(String couponCode) {
        boolean goodCode = true;
        ArrayList<String> codesTaken = new ArrayList<String>();
        try {
            String query = "SELECT couponCode FROM COUPON";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                codesTaken.add(results.getString("couponCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < codesTaken.size(); i++) {
            if (couponCode.equals(codesTaken.get(i))) {
                goodCode = false;
            }
        }
        return goodCode;
    }

    /**
     * Creates a random coupon code
     *
     * @return a randomly generated coupon code
     */
    private String createCouponCode() {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            b.append((char) ('a' + r.nextInt(26)));
        }
        if (checkCode(b.toString()) == true) {
            return b.toString();
        } else {
            createCouponCode();
        }
        return "";
    }

    /**
     * Gets the id of the showtime for which ticket was bought
     *
     * @param ticketId the id of ticket
     * @return the id of the showtime
     */
    public int getShowtimeIdByTicketId(int ticketId) {
        int showtimeId = 0;
        try {
            String query = "SELECT showtimeId FROM TICKET Where ticketId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, ticketId);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                showtimeId = results.getInt("showtimeId");
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return showtimeId;
    }

    /**
     * Creates a coupon object and returns it
     *
     * @param ticketId the ticket that is getting cancelled
     * @param loggedIn flag to identify is user was logged in during cancellation
     * @return the coupon object
     */
    public Coupon createCoupon(int ticketId, boolean loggedIn) {
        // int showtimeId = getShowtimeIdByTicketId(ticketId);
        double price = getPriceFromTicket(ticketId);

        if (loggedIn == false) {
            price = price * 0.85;
        }

        return saveCoupon(createCouponCode(), price, LocalDate.now().plusYears(1));
    }

    /**
     * Gets the movie id from the id of the showtime
     *
     * @param showtimeId the id of the showtime
     * @return the id of the movie
     */
    public int getMovieIdByShowtimeId(int showtimeId) {
        int movieId = 0;
        try {
            String query = "SELECT movieId FROM SHOWTIME WHERE showtimeId = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);
            myStmt.setDouble(1, showtimeId);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                movieId = results.getInt("movieId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieId;
    }

    /**
     * Updates the coupon value in the DB
     *
     * @param c      the Coupon object
     * @param amount the updated amount
     * @return the updated Coupon object if updated in the DB, null otherwise
     */
    public Coupon updateCoupon(Coupon c, double amount) {
        // dont use more than coupon amount
        if (amount > c.getCouponAmount()) {
            return null;
        }

        double finalAmount = c.getCouponAmount() - amount;

        try {
            String query = "UPDATE COUPON SET couponAmount = ? WHERE couponId = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            myStmt.setDouble(1, finalAmount);
            myStmt.setInt(2, c.getId());

            int rowAffected = myStmt.executeUpdate();

            if (rowAffected == 1) {
                ResultSet rs = myStmt.getGeneratedKeys();

                if (rs.next()) {
                    c.setCouponAmount(amount);

                    myStmt.close();
                    return c;
                }
            }

            else {
                throw new SQLException(
                        String.format("[FAIL] %d rows affected during update of coupon. Check!%n", rowAffected));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Closes the db connection
     */
    public void close() {
        try {
            results.close();
            dbConnect.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
