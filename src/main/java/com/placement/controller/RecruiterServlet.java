package com.placement.controller;

import com.placement.model.*;
import com.placement.service.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/recruiter")
public class RecruiterServlet extends HttpServlet {

    private CandidateService candidateService = new CandidateService();
    private RecruiterService recruiterService = new RecruiterService();
    private OfferService offerService = new OfferService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String minMarksStr = req.getParameter("minmarks");
        String qualification = req.getParameter("qualification");
        Double minMarks = null;
        if (minMarksStr != null && !minMarksStr.isEmpty()) {
            try {
                minMarks = Double.parseDouble(minMarksStr);
            } catch (NumberFormatException e) {
                minMarks = null;
            }
        }

        List<Candidate> candidates = candidateService.getAllCandidates();


        if (minMarks != null) {
            final Double finalMinMarks = minMarks;
            candidates = candidates.stream()
                    .filter(c -> c.getMarks() != null && c.getMarks() >= finalMinMarks)
                    .collect(Collectors.toList());
        }
        if (qualification != null && !qualification.equalsIgnoreCase("All") && !qualification.isEmpty()) {
            final String qualFilter = qualification;
            candidates = candidates.stream()
                    .filter(c -> c.getQualification() != null && c.getQualification().equalsIgnoreCase(qualFilter))
                    .collect(Collectors.toList());
        }


        for (Candidate candidate : candidates) {
            User user = AuthService.getUserById(candidate.getUserId());
            if (user != null) {
                candidate.setUserName(user.getName());
            }
        }

        req.setAttribute("candidates", candidates);

        User currentUser = (User) req.getSession().getAttribute("user");
        String currentUserId = currentUser != null ? currentUser.getId() : null;

        Recruiter recruiter = recruiterService.getRecruiterByUserId(currentUserId);
        String currentRecruiterId = recruiter != null ? recruiter.getId() : null;


        List<Offer> allOffers = offerService.getAllOffers();
        List<Offer> recruiterOffers = allOffers.stream()
                .filter(offer -> offer.getRecruiterId() != null && offer.getRecruiterId().equals(currentRecruiterId))
                .collect(Collectors.toList());


        for (Offer offer : recruiterOffers) {
            if (offer.getCandidateId() != null) {
                Candidate candidate = candidateService.getCandidateById(offer.getCandidateId());
                if (candidate != null) {
                    User candidateUser = AuthService.getUserById(candidate.getUserId());
                    if (candidateUser != null) {
                        offer.setCandidateName(candidateUser.getName());
                    }
                }
            }
        }

        req.setAttribute("offers", recruiterOffers);

        RequestDispatcher dispatcher = req.getRequestDispatcher("views/recruiterDashboard.jsp");
        dispatcher.forward(req, resp);
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = req.getParameter("userId");
        String companyName = req.getParameter("companyName");

        Timestamp now = new Timestamp(System.currentTimeMillis());

        Recruiter recruiter = new Recruiter();
        recruiter.setId(UUID.randomUUID().toString());
        recruiter.setUserId(userId);
        recruiter.setCompanyName(companyName);
        recruiter.setCreatedAt(now);
        recruiter.setUpdatedAt(now);

        recruiterService.addRecruiter(recruiter);

        resp.sendRedirect(req.getContextPath() + "/recruiter");
    }
}