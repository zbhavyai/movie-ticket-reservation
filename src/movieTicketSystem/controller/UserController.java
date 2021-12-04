package movieTicketSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import movieTicketSystem.model.*;

/**
 * Controller to handle the OrdinaryUsers and RegisteredUsers
 */
public class UserController {
    private static UserController instanceVar;
    private DbController db;
    // private ArrayList<RegisteredUser> registeredUserIdList;

    // /**
    //  * Private constructor enforcing singleton design pattern
    //  */
    // private UserController() {
    //     this.db = DbController.getInstance();
    //     registeredUserIdList = db.getAllRegisteredUserIds();
    // }

    /**
     * Returns the one and only instance of UserController
     *
     * @return instance of UserController
     */
    public static UserController getInstance() {
        if (instanceVar == null) {
            instanceVar = new UserController();
            return instanceVar;
        }

        else {
            return instanceVar;
        }
    }

	// public double refundUser(int ticketId){
    //     if(db.verifyUserWithTicket(ticketId) != null){
    //         int userId = db.verifyUserWithTicket(ticketId);
    //     }
	// 	db.makeSeatAvailable(ticketId);
	// }

    /**
     * Adds registered user
     *
     * @param email    email of the user supplied during registration
     * @param password password of the user supplied during registration
     * @return the RegisteredUser object if user is found and authenticated, null
     *         otherwise
     */
    public void addUser(String email, String password, String address, String holderName, String cardNumber, LocalDate expiry){
        this.db.saveRegisteredUser(email, password, address, holderName, cardNumber, expiry);
    }

    /**
     * Verifies the registered user
     *
     * @param email    email of the user supplied during registration
     * @param password password of the user supplied during registration
     * @return the RegisteredUser object if user is found and authenticated, null
     *         otherwise
     */
    public RegisteredUser verifyUser(String email, String password) {
        RegisteredUser ru = this.db.getRegisteredUser(email);

        if (ru == null) {
            return null;
        }

        else if (!ru.getPassword().equals(password)) {
            return null;
        }

        else {
            return ru;
        }
    }
}
