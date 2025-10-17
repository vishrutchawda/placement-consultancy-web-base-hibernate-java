package com.placement.service;

import com.placement.dao.UserDAO;
import com.placement.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthService {
    private static UserDAO userDAO = new UserDAO();
    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    public static String authenticateUser(String email, String password) {
    User user = userDAO.findByEmail(email);
    if (user != null) {
        String storedPassword = user.getPassword();

        if (BCrypt.checkpw(password, storedPassword)) {
            logger.log(Level.INFO, "Authentication successful for user: {0}", email);
            return user.getId();
        } else {
            logger.log(Level.WARNING, "Password mismatch for user: {0}", email);
        }
    } else {
        logger.log(Level.WARNING, "No user found with email: {0}", email);
    }
    return null;
}



    public static User getUserById(String userId) {
    return userDAO.findById(userId);
}

    public static String getRoleByUserId(String userId) {
        User user = userDAO.findById(userId);
        return user != null ? user.getRole() : null;
    }

    public static boolean registerUser(User user) {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            return false;
        }
        userDAO.saveUser(user);
        return true;
    }

    public static User getUserByEmail(String email) {
    return userDAO.findByEmail(email);
}

    public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(12));
}
}
