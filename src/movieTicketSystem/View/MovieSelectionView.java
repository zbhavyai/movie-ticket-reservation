package movieTicketSystem.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieSelectionView extends JFrame{
    private JTextField tfStudentName;
    private JTextField tfStudentID;
    private JButton purchaseTicketButton;
    private JButton showLoginFormButton;
    private JPanel mainPanel;

    private JLabel movieLabel;
    private JComboBox movieSelectorComboBox;
    private JComboBox theatreSelectionComboBox;
    private JLabel showtimeLabel;
    private JComboBox showtimeSelectorComboBox;
    private JPanel seatPanel;

    private JLabel screenLabel;
    private JPanel loginPanel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private JTabbedPane tabbedPane;
    private boolean loginFormShowing;


    // menu options
    String[] movieOptions = {};
    String[] theatreOptions = {};
    String[] timeOptions = {};

    JButton[][] seats;
    boolean loggedIn;

    public void toggleLoginForm(){
        if(loginFormShowing){
            loginPanel.setVisible(false);
            showLoginFormButton.setText("Show Login Form");
            this.setSize(new Dimension(720,520));
        } else{
            loginPanel.setVisible(true);
            showLoginFormButton.setText("Hide Login Form");
            this.setSize(new Dimension(720,620));
        }
        loginFormShowing = !loginFormShowing;
    }


    public MovieSelectionView(){

        // initially hidden
        loginPanel.setVisible(false);

        // seats array
        seats = new JButton[10][10];

        // sets up our dropdown menu
        dropdownMenuSetup();

        // create all seats (start off as disabled)
        createSeats();

        // set logged in as false to start off
        setLoggedIn(false);

        setContentPane(tabbedPane);
        setTitle("Movie Selection Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        seatPanel.setLayout(new BoxLayout(seatPanel, BoxLayout.PAGE_AXIS));


        setVisible(true);
        this.setSize(new Dimension(720,520));

    }

    private void dropdownMenuSetup(){
        // setup dropdown menus
        movieSelectorComboBox.setModel(new DefaultComboBoxModel(movieOptions));
        movieSelectorComboBox.setFocusable(false);
        movieSelectorComboBox.setSelectedIndex(-1);
        movieSelectorComboBox.setEnabled(false);

        theatreSelectionComboBox.setModel(new DefaultComboBoxModel(theatreOptions));
        theatreSelectionComboBox.setFocusable(false);
        theatreSelectionComboBox.setSelectedIndex(-1);
        theatreSelectionComboBox.setEnabled(false);

        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions));
        showtimeSelectorComboBox.setFocusable(false);
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(false);
    }


    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;

        if (loggedIn){
            loginButton.setText("Log Out");
            usernameTextField.setEnabled(false);
            passwordTextField.setEnabled(false);
            showLoginFormButton.setEnabled(false);
        } else{
            loginButton.setText("Log In");
            usernameTextField.setEnabled(true);
            passwordTextField.setEnabled(true);
            showLoginFormButton.setEnabled(true);
        }
    }

    public void createSeats() {
        int width = 60;
        int height = 30;

        for(int i=0; i<10; i++){
            var x = new JPanel();
            x.setLayout(new BoxLayout(x, BoxLayout.LINE_AXIS));
            for(int j=0; j<10; j++){
                JButton btn = new JButton(""+ (char)(i+65) + (j));
                btn.setEnabled(false);
                btn.setMinimumSize(new Dimension(width, height));
                btn.setMaximumSize(new Dimension(width, height));

                btn.addActionListener(new buttonListener());

                x.add(btn);
                seats[i][j]=btn;
            }
            seatPanel.add(x);
        }
        this.setVisible(true);
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    class buttonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            var x = (JButton) e.getSource();
            if(x.getBackground()==Color.green){
                x.setBackground(null);
            }else{
                x.setBackground(Color.green);
            }

        }
    }

    public void disableAllSeats(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                seats[i][j].setEnabled(false);
                seats[i][j].setBackground(null);
            }
        }
    }

    public void setSeats(int[][] showtimeSeats) {
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                seats[i][j].setEnabled(showtimeSeats[i][j]==1);
            }
        }
        this.setVisible(true);
    }

    public JButton[][] getSeats() {
        return seats;
    }

    public void setTheatreOptions(String[] theatreOptions) {
        this.theatreOptions = theatreOptions;
        theatreSelectionComboBox.setModel(new DefaultComboBoxModel(theatreOptions));
        theatreSelectionComboBox.setSelectedIndex(-1);
        theatreSelectionComboBox.setEnabled(true);
    }

    public void setTimeOptions(String[] timeOptions) {
        this.timeOptions = timeOptions;
        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions));
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(true);
    }

    public void clearShowtimeOptions(){
        this.timeOptions = new String[]{};
        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions));
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(false);
    }

    public void setMovieOptions(String[] movieOptions) {
        this.movieOptions = movieOptions;
        movieSelectorComboBox.setModel(new DefaultComboBoxModel(movieOptions));
        movieSelectorComboBox.setSelectedIndex(-1);
        movieSelectorComboBox.setEnabled(true);
    }

    // get user selections
    public String getMovieInput(){
        return (movieSelectorComboBox.getSelectedItem() == null) ? "null" : movieSelectorComboBox.getSelectedItem().toString();
    }

    public String getTheatreInput(){
        return (theatreSelectionComboBox.getSelectedItem() == null) ? "null" : theatreSelectionComboBox.getSelectedItem().toString();
    }

    public String getShowtimeInput(){
        return (showtimeSelectorComboBox.getSelectedItem() == null) ? "null" : showtimeSelectorComboBox.getSelectedItem().toString();
    }

    // add and remove action listeners
    public void addMovieComboBoxActionListener(ActionListener a){
        movieSelectorComboBox.addActionListener(a);
    }

    public void removeMovieComboBoxActionListener(ActionListener a){movieSelectorComboBox.removeActionListener(a);}

    public void addTheatreComboBoxActionListener(ActionListener a){
        theatreSelectionComboBox.addActionListener(a);
    }

    public void removeTheatreComboBoxActionListener(ActionListener a){theatreSelectionComboBox.removeActionListener(a);}


    public void addShowtimeComboBoxActionListener(ActionListener a){
        showtimeSelectorComboBox.addActionListener(a);
    }

    public void removeShowtimeComboBoxActionListener(ActionListener a){
        showtimeSelectorComboBox.removeActionListener(a);
    }

    public void addPurchaseButtonActionListener(ActionListener a){
        purchaseTicketButton.addActionListener(a);
    }

    public void addShowLoginButtonActionListener(ActionListener a){
        showLoginFormButton.addActionListener(a);
    }

    public void addLoginButtonListener(ActionListener a){
        loginButton.addActionListener(a);
    }

    public String getUserName(){
        return this.usernameTextField.getText();
    }

    public String getPassword(){
        return this.passwordTextField.getText();
    }

}
