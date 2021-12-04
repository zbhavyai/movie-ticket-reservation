package movieTicketSystem.controller;

import movieTicketSystem.model.*;

/**
 * Controller to handle the OrdinaryUsers and RegisteredUsers
 */
public class UserController {
    private static UserController instanceVar;
    private DbController db;

    /**
     * Private constructor enforcing singleton design pattern
     */
    private UserController() {
        this.db = DbController.getInstance();
    }

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
