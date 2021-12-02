package movieTicketSystem.controller;

// import java.util.ArrayList;
import movieTicketSystem.model.RegisteredUser;

public class UserController {
    // private ArrayList<RegisteredUser> allRegisteredUsers;
    private static UserController instanceVar;
    private DbController db;

    public static UserController getInstance() {
        if (instanceVar == null) {
            instanceVar = new UserController();
            return instanceVar;
        }

        else {
            return instanceVar;
        }
    }

    private UserController() {
        // this.allRegisteredUsers = new ArrayList<>();
        this.db = new DbController();

    }

    public RegisteredUser verifyUser(String email, String password) {
        RegisteredUser ru = this.db.searchRegisteredUser(email);

        if(ru == null) {
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
