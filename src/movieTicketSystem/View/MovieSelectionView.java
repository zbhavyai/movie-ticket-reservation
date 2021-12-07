package movieTicketSystem.View;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import movieTicketSystem.model.Coupon;
import movieTicketSystem.model.RegisteredUser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieSelectionView extends JFrame {

    final int MOVIE_TAB_INDEX = 0;
    final int SIGNUP_TAB_INDEX = 1;
    final int PURCHASE_TAB_INDEX = 2;
    final int CANCEL_TAB_INDEX = 3;

    private boolean loginFormShowing;
    private int selectedSeatCount;
    JButton[][] seats;
    boolean loggedIn;

    // menu options
    ArrayList<String> movieOptions = new ArrayList<String>();
    ArrayList<String> theatreOptions = new ArrayList<String>();
    ArrayList<String> timeOptions = new ArrayList<String>();


    private JButton purchaseButton;
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
    private JPanel signupPanel;
    private JButton signUpNavButton;
    private JButton leaveSignupButton;
    private JPanel purchaseTab;
    private JTextField registeredUserCreditCardField;
    private JTextField registeredUserPasswordField;
    private JTextField registeredUserEmailField;
    private JButton signUpButton;
    private JTextField registeredUserFeeField;
    private JTextField registeredUserNameField;
    private JTextField registeredUserAddressField;
    private JTextField registeredUserCardExpiryField;
    private JButton applyCouponButton;
    private JFormattedTextField totalPriceTF;
    private JFormattedTextField couponCodeTF;
    private JFormattedTextField couponAmtTF;
    private JFormattedTextField grandTotalTF;
    private JFormattedTextField purchasingCreditCardNumTF;
    private JFormattedTextField purchasingCVCTF;
    private JFormattedTextField purchasingCardExpiryTF;
    private JButton completePaymentButton;
    private JButton cancelPaymentButton;
    private JFormattedTextField purchasingCardholderNameTF;
    private JTextField remainingCouponAmtTF;
    private JPanel paymentPanel;
    private JButton cancelPreviousTicketButton;
    private JTextField ticketCancellationTF;
    private JButton cancelTicketButton;
    private JButton leaveCancelPageButton;
    private JTextField refundCouponCode;
    private JTextField refundCouponAmount;
    private JTextField signUpCVCTextField;
    private JFormattedTextField purchaseEmailTextField;


    public MovieSelectionView() {

        // set default values
        loginPanel.setVisible(false);
        setView("main");
        setLoggedIn(null);
        seats = new JButton[10][10];
        purchaseButton.setEnabled(false);

        // general form setup
        dropdownMenuSetup();
        createSeats();


        // set up content pane
        setContentPane(tabbedPane);
        setTitle("Movie Selection Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        seatPanel.setLayout(new BoxLayout(seatPanel, BoxLayout.PAGE_AXIS));


        // add action listeners
        signUpNavButton.addActionListener(new signupButtonListener());
        leaveSignupButton.addActionListener(new backToMainButtonListener());
        cancelPaymentButton.addActionListener(new backToMainButtonListener());
        cancelPreviousTicketButton.addActionListener(new cancelTicketNavButtonListener());
        leaveCancelPageButton.addActionListener(new backToMainButtonListener());

        setVisible(true);
        this.setSize(new Dimension(720, 520));

    }

    private void dropdownMenuSetup() {
        // setup dropdown menus
        movieSelectorComboBox.setModel(new DefaultComboBoxModel(movieOptions.toArray()));
        movieSelectorComboBox.setFocusable(false);
        movieSelectorComboBox.setSelectedIndex(-1);
        movieSelectorComboBox.setEnabled(false);

        theatreSelectionComboBox.setModel(new DefaultComboBoxModel(theatreOptions.toArray()));
        theatreSelectionComboBox.setFocusable(false);
        theatreSelectionComboBox.setSelectedIndex(-1);
        theatreSelectionComboBox.setEnabled(false);

        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions.toArray()));
        showtimeSelectorComboBox.setFocusable(false);
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(false);
    }

    public void toggleLoginForm() {
        if (loginFormShowing) {
            loginPanel.setVisible(false);
            showLoginFormButton.setText("Show Login Form");
            this.setSize(new Dimension(720, 520));
        } else {
            loginPanel.setVisible(true);
            showLoginFormButton.setText("Hide Login Form");
            this.setSize(new Dimension(720, 620));
        }
        loginFormShowing = !loginFormShowing;
    }

    public void setLoggedIn(RegisteredUser user) {
        this.loggedIn = (user != null);

        if (loggedIn) {
            loginButton.setText("Log Out");
            usernameTextField.setEnabled(false);
            passwordTextField.setEnabled(false);
            showLoginFormButton.setEnabled(false);

            // sign up tab logic
            populateSignUpTab(user);

        } else {
            loginButton.setText("Log In");
            usernameTextField.setEnabled(true);
            passwordTextField.setEnabled(true);
            showLoginFormButton.setEnabled(true);
            clearSignUpTab();
        }
    }

    private void populateSignUpTab(RegisteredUser user) {
        tabbedPane.setTitleAt(SIGNUP_TAB_INDEX, "Account Settings");
        signUpNavButton.setText("Account Settings");
        registeredUserEmailField.setText(user.getEmail());
        registeredUserPasswordField.setText(user.getPassword());
        registeredUserCreditCardField.setText(user.getCard().getCardNum());
        registeredUserFeeField.setText(user.getLastFeePaid().toString());
        registeredUserEmailField.setEnabled(false);
        registeredUserPasswordField.setEnabled(false);
        registeredUserCreditCardField.setEnabled(false);
        registeredUserEmailField.setEnabled(false);
        registeredUserFeeField.setEnabled(false);
        signUpButton.setEnabled(false);
        registeredUserAddressField.setText(user.getAddress());
        registeredUserCardExpiryField.setText(user.getCard().getExpiry().toString());
        registeredUserNameField.setText(user.getCard().getCardHolderName());
        registeredUserAddressField.setEnabled(false);
        registeredUserCardExpiryField.setEnabled(false);
        registeredUserNameField.setEnabled(false);

    }

    private void clearSignUpTab() {
        registeredUserEmailField.setText("");
        registeredUserPasswordField.setText("");
        registeredUserCreditCardField.setText("");
        registeredUserEmailField.setText("");
        registeredUserFeeField.setText("");
        registeredUserEmailField.setEnabled(true);
        registeredUserPasswordField.setEnabled(true);
        registeredUserCreditCardField.setEnabled(true);
        registeredUserEmailField.setEnabled(true);
        registeredUserFeeField.setEnabled(false);
        signUpButton.setEnabled(true);
        tabbedPane.setTitleAt(SIGNUP_TAB_INDEX, "Sign Up");
        signUpNavButton.setText("Sign Up");
        registeredUserAddressField.setText("");
        registeredUserAddressField.setEnabled(true);
        registeredUserCardExpiryField.setText("");
        registeredUserCardExpiryField.setEnabled(true);
        registeredUserNameField.setText("");
        registeredUserNameField.setEnabled(true);
    }

    // method one, if a user is logged in
    public void populatePurchaseTab(RegisteredUser user, double price) {
        totalPriceTF.setText(String.format("%.02f", price));
        totalPriceTF.setEnabled(false);
        couponAmtTF.setText("0.00");
        couponAmtTF.setEnabled(false);
        remainingCouponAmtTF.setText("0.00");
        remainingCouponAmtTF.setEnabled(false);
        grandTotalTF.setText(String.format("%.02f", price));
        grandTotalTF.setEnabled(false);
        purchasingCreditCardNumTF.setText(user.getCard().getCardNum());
        purchasingCardExpiryTF.setText(user.getCard().getExpiry().toString());
        purchasingCardholderNameTF.setText(user.getCard().getCardHolderName());
        purchaseEmailTextField.setText(user.getEmail());
    }

    // if a user is not logged in
    public void populatePurchaseTab(double price) {
        totalPriceTF.setText(String.format("%.02f", price));
        totalPriceTF.setEnabled(false);
        couponAmtTF.setText("0.00");
        couponAmtTF.setEnabled(false);
        remainingCouponAmtTF.setText("0.00");
        remainingCouponAmtTF.setEnabled(false);
        grandTotalTF.setText(String.format("%.02f", price));
        grandTotalTF.setEnabled(false);
        purchasingCreditCardNumTF.setText("");
        purchasingCardExpiryTF.setText("");
        purchasingCardholderNameTF.setText("");
        purchaseEmailTextField.setText("");
    }

    public void createSeats() {
        int width = 60;
        int height = 30;

        for (int i = 0; i < 10; i++) {
            JPanel x = new JPanel();
            x.setLayout(new BoxLayout(x, BoxLayout.LINE_AXIS));
            for (int j = 0; j < 10; j++) {
                JButton btn = new JButton("" + (char) (i + 65) + (j));
                btn.setEnabled(false);
                btn.setMinimumSize(new Dimension(width, height));
                btn.setMaximumSize(new Dimension(width, height));

                btn.addActionListener(new seatButtonColorChangeListener());

                x.add(btn);
                seats[i][j] = btn;
            }
            seatPanel.add(x);
        }
        this.setVisible(true);
    }


    public void disableAllSeats() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                seats[i][j].setEnabled(false);
                seats[i][j].setBackground(null);
            }
        }
    }

    public void setSeats(int[][] showtimeSeats) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                seats[i][j].setEnabled(showtimeSeats[i][j] == 1);
            }
        }
        this.setVisible(true);
    }


    public void setTheatreOptions(ArrayList<String> theatreOptions) {
        this.theatreOptions = theatreOptions;
        theatreSelectionComboBox.setModel(new DefaultComboBoxModel(theatreOptions.toArray()));
        theatreSelectionComboBox.setSelectedIndex(-1);
        theatreSelectionComboBox.setEnabled(true);
    }

    public void setTimeOptions(ArrayList<String> timeOptions) {
        this.timeOptions = timeOptions;
        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions.toArray()));
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(true);
    }

    public void clearShowtimeOptions() {
        this.timeOptions = new ArrayList<String>();
        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions.toArray()));
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(false);
    }

    public void setMovieOptions(ArrayList<String> movieOptions) {
        this.movieOptions = movieOptions;
        movieSelectorComboBox.setModel(new DefaultComboBoxModel(movieOptions.toArray()));
        movieSelectorComboBox.setSelectedIndex(-1);
        movieSelectorComboBox.setEnabled(true);
    }

    public void setView(String viewName) {
        switch (viewName) {
            case "main":
                tabbedPane.setEnabledAt(MOVIE_TAB_INDEX, true);
                tabbedPane.setEnabledAt(SIGNUP_TAB_INDEX, false);
                tabbedPane.setEnabledAt(PURCHASE_TAB_INDEX, false);
                tabbedPane.setEnabledAt(CANCEL_TAB_INDEX, false);
                tabbedPane.setSelectedIndex(MOVIE_TAB_INDEX);
                break;
            case "signup":
                tabbedPane.setEnabledAt(MOVIE_TAB_INDEX, false);
                tabbedPane.setEnabledAt(SIGNUP_TAB_INDEX, true);
                tabbedPane.setEnabledAt(CANCEL_TAB_INDEX, false);
                tabbedPane.setEnabledAt(PURCHASE_TAB_INDEX, false);
                tabbedPane.setSelectedIndex(SIGNUP_TAB_INDEX);
                break;
            case "purchase":
                tabbedPane.setEnabledAt(MOVIE_TAB_INDEX, false);
                tabbedPane.setEnabledAt(SIGNUP_TAB_INDEX, false);
                tabbedPane.setEnabledAt(CANCEL_TAB_INDEX, false);
                tabbedPane.setEnabledAt(PURCHASE_TAB_INDEX, true);
                tabbedPane.setSelectedIndex(PURCHASE_TAB_INDEX);
                break;
            case "cancel":
                tabbedPane.setEnabledAt(MOVIE_TAB_INDEX, false);
                tabbedPane.setEnabledAt(SIGNUP_TAB_INDEX, false);
                tabbedPane.setEnabledAt(CANCEL_TAB_INDEX, true);
                tabbedPane.setEnabledAt(PURCHASE_TAB_INDEX, false);
                tabbedPane.setSelectedIndex(CANCEL_TAB_INDEX);
                break;
        }
    }

    public void clearSignUpForm() {
        registeredUserEmailField.setText("");
        registeredUserPasswordField.setText("");
        registeredUserAddressField.setText("");
        registeredUserCreditCardField.setText("");
        registeredUserCardExpiryField.setText("");
        registeredUserNameField.setText("");
        signUpCVCTextField.setText("");
        setView("main");
    }

    public void setPaymentDetailsEnabled(boolean b) {
        for (Component c : paymentPanel.getComponents()) {
            c.setEnabled(b);
        }
    }

    // ****************** Getters and Setters *************************
    public String getMovieInput() {
        return (movieSelectorComboBox.getSelectedItem() == null) ? "null" : movieSelectorComboBox.getSelectedItem().toString();
    }

    public String getTheatreInput() {
        return (theatreSelectionComboBox.getSelectedItem() == null) ? "null" : theatreSelectionComboBox.getSelectedItem().toString();
    }

    public String getShowtimeInput() {
        return (showtimeSelectorComboBox.getSelectedItem() == null) ? "null" : showtimeSelectorComboBox.getSelectedItem().toString();
    }

    public String getUserName() {
        return this.usernameTextField.getText();
    }

    public String getPassword() {
        return this.passwordTextField.getText();
    }

    public JButton[][] getSeats() {
        return seats;
    }

    public int getSelectedSeatCount() {
        return selectedSeatCount;
    }

    public void incrementSelectedSeats() {
        this.selectedSeatCount++;
    }

    public void decrementSelectedSeats() {
        this.selectedSeatCount--;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public double getTotalPrice() {
        return Double.parseDouble(totalPriceTF.getText());
    }

    public String getCouponCode() {
        return couponCodeTF.getText();
    }

    public void setCouponAmount(String s) {
        couponAmtTF.setText(s);
    }

    public void setGrandTotal(String s) {
        grandTotalTF.setText(s);
    }

    public void setRemainingCouponAmount(String s) {
        remainingCouponAmtTF.setText(s);
    }

    public void setCouponButtonText(String s) {
        applyCouponButton.setText(s);
    }

    public String getCouponButtonText() {
        return applyCouponButton.getText();
    }

    public void setCouponCodeText(String s) {
        couponCodeTF.setText("");
    }

    public void setCouponCodeEnabled(boolean b) {
        couponCodeTF.setEnabled(b);
    }

    public String getTicketCancellationID() {
        return ticketCancellationTF.getText();
    }

    public void addCompletePaymentListener(ActionListener a) {
        completePaymentButton.addActionListener(a);
    }

    public String getCreditCardNum() {
        return purchasingCreditCardNumTF.getText();
    }

    public String getCVC() {
        return purchasingCVCTF.getText();
    }

    public String getCardExpiry() {
        return purchasingCardExpiryTF.getText();
    }

    public String getCardHolderName() {
        return purchasingCardholderNameTF.getText();
    }

    public double getGrandTotal() {
        return Double.parseDouble(grandTotalTF.getText());
    }

    public void setRefundCouponCode(String s) {
        refundCouponCode.setText(s);
    }

    public void setRefundCouponAmt(String s) {
        refundCouponAmount.setText(s);
    }

    public String getSignUpEmail() {
        return registeredUserEmailField.getText();
    }

    public String getSignUpPassword() {
        return registeredUserPasswordField.getText();
    }

    public String getSignUpAddress() {
        return registeredUserAddressField.getText();
    }

    public String getSignUpCardNum() {
        return registeredUserCreditCardField.getText();
    }

    public String getSignUpCardExp() {
        return registeredUserCardExpiryField.getText();
    }

    public String getSignUpCardName() {
        return registeredUserNameField.getText();
    }

    public String getSignUpCVC() {
        return signUpCVCTextField.getText();
    }

    public String getPaymentEmail() {
        return purchaseEmailTextField.getText();
    }

    public void setPaymentEmail(String s) {
        purchaseEmailTextField.setText(s);
    }


    // ******************* ACTION LISTENERS **************************
    public void addMovieComboBoxActionListener(ActionListener a) {
        movieSelectorComboBox.addActionListener(a);
    }

    public void removeMovieComboBoxActionListener(ActionListener a) {
        movieSelectorComboBox.removeActionListener(a);
    }

    public void addTheatreComboBoxActionListener(ActionListener a) {
        theatreSelectionComboBox.addActionListener(a);
    }

    public void removeTheatreComboBoxActionListener(ActionListener a) {
        theatreSelectionComboBox.removeActionListener(a);
    }

    public void addShowtimeComboBoxActionListener(ActionListener a) {
        showtimeSelectorComboBox.addActionListener(a);
    }

    public void removeShowtimeComboBoxActionListener(ActionListener a) {
        showtimeSelectorComboBox.removeActionListener(a);
    }

    public void addPurchaseButtonActionListener(ActionListener a) {
        purchaseButton.addActionListener(a);
    }

    public void addShowLoginButtonActionListener(ActionListener a) {
        showLoginFormButton.addActionListener(a);
    }

    public void addLoginButtonListener(ActionListener a) {
        loginButton.addActionListener(a);
    }

    public void addCouponistener(ActionListener a) {
        applyCouponButton.addActionListener(a);
    }

    public void addCancelTicketButtonListener(ActionListener a) {
        cancelTicketButton.addActionListener(a);
    }

    public void addSignUpButtonListener(ActionListener a) {
        signUpButton.addActionListener(a);
    }


    class seatButtonColorChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton x = (JButton) e.getSource();
            if (x.getBackground() == Color.green) {
                x.setBackground(null);
                decrementSelectedSeats();
            } else {
                x.setBackground(Color.green);
                incrementSelectedSeats();
            }

            // enable/disable purchase button based on selected seat count
            purchaseButton.setEnabled(getSelectedSeatCount() > 0);
        }
    }

    class signupButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setView("signup");
        }
    }

    class backToMainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setView("main");
        }
    }

    class cancelTicketNavButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setView("cancel");
        }
    }


    // ************************** INTELLIJ CODE DO NOT TOUCH ***********************************

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        tabbedPane = new JTabbedPane();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMinimumSize(new Dimension(600, 168));
        mainPanel.setPreferredSize(new Dimension(600, 168));
        tabbedPane.addTab("Movie Selection", mainPanel);
        movieLabel = new JLabel();
        movieLabel.setText("  Movie");
        mainPanel.add(movieLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("  Theatre");
        mainPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        purchaseButton = new JButton();
        purchaseButton.setText("Purchase");
        panel1.add(purchaseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showLoginFormButton = new JButton();
        showLoginFormButton.setText("Show Log In Form");
        panel1.add(showLoginFormButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        signUpNavButton = new JButton();
        signUpNavButton.setText("Sign Up");
        panel1.add(signUpNavButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelPreviousTicketButton = new JButton();
        cancelPreviousTicketButton.setText("Cancel A Ticket");
        panel1.add(cancelPreviousTicketButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        movieSelectorComboBox = new JComboBox();
        mainPanel.add(movieSelectorComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        theatreSelectionComboBox = new JComboBox();
        mainPanel.add(theatreSelectionComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showtimeLabel = new JLabel();
        showtimeLabel.setText("  Showtime");
        mainPanel.add(showtimeLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showtimeSelectorComboBox = new JComboBox();
        mainPanel.add(showtimeSelectorComboBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridBagLayout());
        mainPanel.add(seatPanel, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        screenLabel = new JLabel();
        screenLabel.setHorizontalAlignment(0);
        screenLabel.setHorizontalTextPosition(0);
        screenLabel.setText("Screen");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        seatPanel.add(screenLabel, gbc);
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(loginPanel, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        usernameTextField = new JTextField();
        loginPanel.add(usernameTextField, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordTextField = new JTextField();
        loginPanel.add(passwordTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Username");
        loginPanel.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Password");
        loginPanel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loginButton = new JButton();
        loginButton.setText("Log In");
        loginPanel.add(loginButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        signupPanel.setEnabled(true);
        tabbedPane.addTab("Sign Up", signupPanel);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        signupPanel.add(panel2, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        signUpButton = new JButton();
        signUpButton.setText("Sign Up");
        panel2.add(signUpButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        leaveSignupButton = new JButton();
        leaveSignupButton.setText("Return To Main Menu");
        panel2.add(leaveSignupButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        signupPanel.add(panel3, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        registeredUserCreditCardField = new JTextField();
        panel3.add(registeredUserCreditCardField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        registeredUserEmailField = new JTextField();
        panel3.add(registeredUserEmailField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        registeredUserPasswordField = new JTextField();
        panel3.add(registeredUserPasswordField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("  Email: ");
        panel3.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("  Password: ");
        panel3.add(label5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("  Credit Card #: ");
        panel3.add(label6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registeredUserFeeField = new JTextField();
        panel3.add(registeredUserFeeField, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("  Fees Last Paid on:  ");
        panel3.add(label7, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registeredUserNameField = new JTextField();
        panel3.add(registeredUserNameField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("  First and Last Name on Credit Card: ");
        panel3.add(label8, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registeredUserCardExpiryField = new JTextField();
        panel3.add(registeredUserCardExpiryField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("  Card Expiry Date (yyyy-mm-dd): ");
        panel3.add(label9, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registeredUserAddressField = new JTextField();
        panel3.add(registeredUserAddressField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("  Address: ");
        panel3.add(label10, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        signUpCVCTextField = new JTextField();
        panel3.add(signUpCVCTextField, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("  CVC");
        panel3.add(label11, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        purchaseTab = new JPanel();
        purchaseTab.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Purchase", purchaseTab);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        purchaseTab.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("  Ticket Price Total:  $");
        panel4.add(label12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("  Enter Coupon Code: ");
        panel4.add(label13, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        applyCouponButton = new JButton();
        applyCouponButton.setText("Apply Coupon");
        panel4.add(applyCouponButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("  Coupon Amount: $");
        panel4.add(label14, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("  Grand Total: $");
        panel4.add(label15, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalPriceTF = new JFormattedTextField();
        totalPriceTF.setEditable(false);
        panel4.add(totalPriceTF, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        couponCodeTF = new JFormattedTextField();
        panel4.add(couponCodeTF, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        couponAmtTF = new JFormattedTextField();
        couponAmtTF.setEditable(false);
        panel4.add(couponAmtTF, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        grandTotalTF = new JFormattedTextField();
        grandTotalTF.setEditable(false);
        panel4.add(grandTotalTF, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        remainingCouponAmtTF = new JTextField();
        remainingCouponAmtTF.setEditable(false);
        panel4.add(remainingCouponAmtTF, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("  RemainingCoupon Amount: $");
        panel4.add(label16, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        purchaseTab.add(paymentPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Payment Details");
        paymentPanel.add(label17, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        purchasingCreditCardNumTF = new JFormattedTextField();
        paymentPanel.add(purchasingCreditCardNumTF, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("  Credit Card #: ");
        paymentPanel.add(label18, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        purchasingCVCTF = new JFormattedTextField();
        paymentPanel.add(purchasingCVCTF, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("  CVC: ");
        paymentPanel.add(label19, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        purchasingCardExpiryTF = new JFormattedTextField();
        paymentPanel.add(purchasingCardExpiryTF, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("  Cardholder First and Last Name: ");
        paymentPanel.add(label20, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("  Card Expiry Date: ");
        paymentPanel.add(label21, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        purchasingCardholderNameTF = new JFormattedTextField();
        paymentPanel.add(purchasingCardholderNameTF, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("  Email");
        paymentPanel.add(label22, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        purchaseEmailTextField = new JFormattedTextField();
        paymentPanel.add(purchaseEmailTextField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        purchaseTab.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cancelPaymentButton = new JButton();
        cancelPaymentButton.setText("Cancel");
        panel5.add(cancelPaymentButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        completePaymentButton = new JButton();
        completePaymentButton.setText("Complete Payment");
        panel5.add(completePaymentButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel5.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel5.add(spacer4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Cancel Ticket", panel6);
        final JLabel label23 = new JLabel();
        label23.setText("  Please Enter the ID of the ticket you'd like to cancel:");
        panel6.add(label23, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel7, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cancelTicketButton = new JButton();
        cancelTicketButton.setText("CancelTicket");
        panel7.add(cancelTicketButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        leaveCancelPageButton = new JButton();
        leaveCancelPageButton.setText("Return  To Main Menu");
        panel7.add(leaveCancelPageButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel7.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel7.add(spacer6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("  Refunded Coupon Code: ");
        panel6.add(label24, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refundCouponCode = new JTextField();
        refundCouponCode.setEditable(false);
        refundCouponCode.setEnabled(true);
        panel6.add(refundCouponCode, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("  Refunded Coupon Amount: $");
        panel6.add(label25, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refundCouponAmount = new JTextField();
        refundCouponAmount.setEditable(false);
        refundCouponAmount.setEnabled(true);
        panel6.add(refundCouponAmount, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ticketCancellationTF = new JTextField();
        panel6.add(ticketCancellationTF, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return tabbedPane;
    }

}
