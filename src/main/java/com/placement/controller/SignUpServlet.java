package com.placement.controller;

import com.placement.model.*;
import com.placement.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmpassword");
        String role = req.getParameter("role");
        String company = req.getParameter("company");

        if (name == null || name.isEmpty() || email == null || email.isEmpty()
                || password == null || password.isEmpty() || role == null || role.isEmpty()) {
            req.setAttribute("error", "Please fill in all mandatory fields.");
            req.getRequestDispatcher("/views/signup.jsp").forward(req, resp);

            return;
        }

        if (!password.equals(confirmPassword)) {
        req.setAttribute("error", "Passwords do not match.");
        req.getRequestDispatcher("/views/signup.jsp").forward(req, resp);
        return;
    }

        if (role.equalsIgnoreCase("recruiter") && (company == null || company.trim().isEmpty())) {
            req.setAttribute("error", "Company name is required for recruiters.");
            req.getRequestDispatcher("/views/signup.jsp").forward(req, resp);

            return;
        }

        String hashedPassword = AuthService.hashPassword(password);

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(name);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(role.toLowerCase());
        user.setCompany(company);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        boolean created = AuthService.registerUser(user);
    if (created) {
        if ("candidate".equalsIgnoreCase(role)) {
            Candidate candidate = new Candidate();
            candidate.setId(UUID.randomUUID().toString());
            candidate.setUserId(user.getId());
            candidate.setCreatedAt(now);
            candidate.setUpdatedAt(now);

            CandidateService candidateService = new CandidateService();
            candidateService.addCandidate(candidate);


        } else if ("recruiter".equalsIgnoreCase(role)) {
            Recruiter recruiter = new Recruiter();
            recruiter.setId(UUID.randomUUID().toString());
            recruiter.setUserId(user.getId());
            recruiter.setCompanyName(company);
            recruiter.setCreatedAt(now);
            recruiter.setUpdatedAt(now);

            RecruiterService recruiterService = new RecruiterService();
            recruiterService.addRecruiter(recruiter);

}

        resp.sendRedirect(req.getContextPath() + "/login");
    } else {
        req.setAttribute("error", "User registration failed. Email may already exist.");
        req.getRequestDispatcher("/views/signup.jsp").forward(req, resp);
    }

    }
}
