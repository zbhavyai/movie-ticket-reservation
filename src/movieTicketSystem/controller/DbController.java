package movieTicketSystem.controller;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import movieTicketSystem.model.*;

public class DbController {
    // Optional to include, but recommended
    private Connection dbConnect;
    private Properties dbDetails;
    private ResultSet results;

    public DbController() {
        this.initializeConnection();
    }

    // Must create a connection to the database, no arguments, no return value
    public void initializeConnection() {
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

    public ArrayList<Movie> selectAllMovies() {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            Statement myStmt = dbConnect.createStatement();

            // Execute SQL query
            results = myStmt.executeQuery("SELECT * FROM movie");

            // Process the results set
            while (results.next()) {
                Movie mvdb = new Movie(results.getString("title"), results.getInt("movieId"),
                        results.getDouble("rating"));
                movies.add(mvdb);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return movies;
    }

    public ArrayList<Integer> selectMoviesByTheatre(int theatredId) {

        ArrayList<Integer> movieIds = new ArrayList<Integer>();

        try {
            String query = "SELECT * FROM showtime Where theatreId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, theatredId);

            ResultSet results = myStmt.executeQuery();

            // Process the results set
            while (results.next()) {
                movieIds.add(results.getInt("movieId"));
            }
            System.out.println(movieIds);
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return movieIds;
    }

    public ArrayList<Theater> selectAllTheatres() {

        ArrayList<Theater> theaters = new ArrayList<Theater>();

        try {
            Statement myStmt = dbConnect.createStatement();

            // Execute SQL query
            results = myStmt.executeQuery("SELECT * FROM theatre");

            // Process the results set
            while (results.next()) {
                Theater tr = new Theater(results.getInt("theatreId"), results.getString("theatreName"));
                theaters.add(tr);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return theaters;
    }

    public ArrayList<Integer> searchTheatresByMovie(int movieId) {

        ArrayList<Integer> theatreIds = new ArrayList<Integer>();

        try {
            String query = "SELECT * FROM showtime Where movieId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, movieId);

            ResultSet results = myStmt.executeQuery();

            // Process the results set
            while (results.next()) {
                theatreIds.add(results.getInt("theatreId"));
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return theatreIds;
    }

    public ArrayList<String> searchShowtimesByMovieAndTheatre(int theatreId, int movieId) {

        ArrayList<String> showTimes = new ArrayList<String>();

        try {
            String query = "SELECT showtime FROM showtime Where movieId = ? AND theatreId = ?";
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return showTimes;
    }

    public String searchShowtimesById(int showTimeId) {

        String showTime = "";

        try {
            String query = "SELECT showtime FROM showtime Where showTimeId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showTimeId);

            ResultSet results = myStmt.executeQuery();

            // Process the results set
            while (results.next()) {
                showTime = results.getDate("showtime").toString() + " " + results.getTime("showtime").toString();
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return showTime;
    }

    public ArrayList<Integer> searchMovieTheatreByShowTime(int showTimeId) {

        ArrayList<Integer> movieTheatreId = new ArrayList<Integer>();
        try {
            String query = "SELECT * FROM showtime Where showtimeId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showTimeId);

            ResultSet results = myStmt.executeQuery();

            if (results.next()) {
                // Process the results set
                movieTheatreId.add(results.getInt("movieId"));
                movieTheatreId.add(results.getInt("theatreId"));

            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return movieTheatreId;
    }

    public void createtNewTicket(int showtimeId, double price) {

        if (!validTicket(showtimeId)) {
            throw new IllegalArgumentException("ticket id already exists.");
        }
        try {

            String query = "INSERT INTO ticket (showtimeId, price) VALUES (?,?)";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showtimeId);
            myStmt.setDouble(2, price);

            int rowCount = myStmt.executeUpdate();
            System.out.println(rowCount);
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean validTicket(int showtimeId) {

        boolean validTicket = true;

        try {
            Statement myStmt = dbConnect.createStatement();

            // Execute SQL query
            results = myStmt.executeQuery("SELECT * FROM showtime");

            // Process the results set
            while (results.next()) {
                if (results.getInt("showtimeId") == showtimeId)
                    validTicket = false;
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return validTicket;

    }

    public ArrayList<Integer> ticketsAtShowtime(int showtimeId) {
        ArrayList<Integer> tickets = new ArrayList<Integer>();
        try {
            String query = "SELECT * FROM ticket Where showtimeId = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, showtimeId);

            ResultSet results = myStmt.executeQuery();

            // Get a list of tickets associated with that showtime
            while (results.next()) {
                tickets.add(results.getInt("ticketId"));
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tickets;
    }

    public int[][] seatGrid(int showtimeId) {

        ArrayList<Integer> tickets = ticketsAtShowtime(showtimeId);
        ArrayList<Integer> row = new ArrayList<Integer>();
        ArrayList<Integer> col = new ArrayList<Integer>();
        int[][] seatGrid = new int[10][10];

        try {
            for (int i = 0; i < tickets.size(); i++) {
                String query = "SELECT * FROM seat Where ticketId = ?";
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

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return seatGrid;
    }

    /**
     * Fetches an object of RegisteredUser from DB using username
     *
     * @param username unique username of the RegisteredUser
     * @return RegisteredUser object if found, null otherwise
     */
    public RegisteredUser searchRegisteredUser(String username) {
        try {
            String query = "SELECT * FROM RUser WHERE username = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);
            myStmt.setString(1, username);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                if (results.getString("username").equals(username)) {

                    RegisteredUser ru = new RegisteredUser();
                    ru.setId(results.getInt("userId"));
                    ru.setUsername(username);
                    ru.setPassword(results.getString("password"));
                    ru.setEmail(results.getString("email"));
                    ru.setAddress(results.getString("address"));
                    ru.setLastFeePaid(results.getDate("lastPaid").toLocalDate());
                    ru.setCard(this.getPayment(results.getInt("card")));

                    return ru;
                }
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
     * Fetches an object of Coupon from DB using couponCode
     *
     * @param couponCode unique coupon code provided during cancellation
     * @return Coupon object if coupon is found, null otherwise
     */
    public Coupon getCoupon(String couponCode) {
        try {
            String query = "SELECT * FROM COUPON WHERE couponId = ?";
            PreparedStatement myStmt = this.dbConnect.prepareStatement(query);
            myStmt.setString(1, couponCode);

            ResultSet results = myStmt.executeQuery();

            while (results.next()) {
                if (results.getString("couponCode").equals(couponCode)) {

                    Coupon c = new Coupon();
                    c.setId(results.getInt("couponId"));
                    c.setCouponCode(couponCode);
                    c.setCouponAmount(results.getDouble("couponAmount"));
                    c.setRedeemedAmount(results.getDouble("redeemedAmount"));
                    c.setExpiry(results.getDate("expiry").toLocalDate());

                    return c;
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // other demo methods for reference
    // *******************************************************

    public void deleteTeacher(String id) {

        try {
            String query = "DELETE FROM teacher WHERE TeacherID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, id);

            int rowCount = myStmt.executeUpdate();

            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void close() {
        try {
            results.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DbController dbController = new DbController();
        // dbController.seatGrid(2);
        // System.out.println(dbController.searchRegisteredUser("caitlyn.bean"));
    }
}
