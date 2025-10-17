package com.placement.controller;

import com.placement.dao.UserDAO;
import com.placement.model.User;
import com.placement.service.AuthService;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {
    private static final AuthService authService = new AuthService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/forgotpassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("new_password");
        String confirmPassword = request.getParameter("confirm_password");

        if (email == null || newPassword == null || confirmPassword == null ||
            email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "Please fill in all fields.");
        } else if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
        } else {
            User user = AuthService.getUserByEmail(email);
            if (user == null) {
                request.setAttribute("error", "Email not found.");
            } else {
                String hashed = AuthService.hashPassword(newPassword);
                user.setPassword(hashed);
                UserDAO userDao = new UserDAO();
                userDao.saveUser(user);
                request.setAttribute("success", "Password reset successfully.");
            }
        }
        request.getRequestDispatcher("views/forgotpassword.jsp").forward(request, response);
    }
}

