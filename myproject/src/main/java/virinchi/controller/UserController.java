package virinchi.controller;

import virinchi.model.UserTable;

public interface UserController {
    boolean userSignup(String username, String email, String password);
    UserTable userLogin(String username, String password);
}
