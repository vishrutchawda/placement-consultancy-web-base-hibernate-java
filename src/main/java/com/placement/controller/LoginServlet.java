package com.placement.controller;

import com.placement.model.User;
import com.placement.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        String userId = AuthService.authenticateUser(email, password);

        if (userId != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);

            User user = AuthService.getUserById(userId);
            session.setAttribute("user", user);

            String role = AuthService.getRoleByUserId(userId);

            if ("candidate".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/views/candidateDashboard.jsp");
            } else if ("recruiter".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/recruiter");
            } else {
                request.setAttribute("error", "Unknown user role");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}

