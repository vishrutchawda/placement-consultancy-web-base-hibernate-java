package com.placement.controller;

import com.placement.model.Offer;
import com.placement.service.OfferService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/offer/*")
public class OfferServlet extends HttpServlet {

    private OfferService offerService = new OfferService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path != null && path.equals("/update")) {
            String id = req.getParameter("id");
            String status = req.getParameter("status");

            if (id != null && status != null) {
                Offer offer = offerService.getOfferById(id);
                if (offer != null) {
                    try {
                        offer.setStatus(Offer.Status.valueOf(status.toUpperCase()));
                        offer.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        offerService.updateOffer(offer);

                        HttpSession session = req.getSession();
                        session.setAttribute("success", "Offer " + status.toLowerCase() + " successfully!");
                    } catch (IllegalArgumentException e) {
                        HttpSession session = req.getSession();
                        session.setAttribute("error", "Invalid offer status");
                    }
                }
            }
            resp.sendRedirect(req.getContextPath() + "/views/candidateDashboard.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/views/candidateDashboard.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");
            String candidateId = req.getParameter("candidateId");
            String recruiterId = req.getParameter("recruiterId");
            String companyName = req.getParameter("companyName");
            String status = req.getParameter("status");
            String salaryStr = req.getParameter("estimatedSalary");


            if (candidateId == null || candidateId.trim().isEmpty()) {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Candidate ID is required");
                resp.sendRedirect(req.getContextPath() + "/recruiter");
                return;
            }

            if (recruiterId == null || recruiterId.trim().isEmpty()) {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Recruiter ID is required");
                resp.sendRedirect(req.getContextPath() + "/recruiter");
                return;
            }

            BigDecimal estimatedSalary = null;
            try {
                if (salaryStr != null && !salaryStr.isEmpty()) {
                    estimatedSalary = new BigDecimal(salaryStr);
                }
            } catch (NumberFormatException e) {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Invalid salary format");
                resp.sendRedirect(req.getContextPath() + "/recruiter");
                return;
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());
            Offer offer;

            if (id == null || id.isEmpty()) {
                offer = new Offer();
                offer.setId(UUID.randomUUID().toString());
                offer.setCreatedAt(now);
            } else {
                offer = offerService.getOfferById(id);
                if (offer == null) {
                    offer = new Offer();
                    offer.setId(UUID.randomUUID().toString());
                    offer.setCreatedAt(now);
                }
            }

            offer.setCandidateId(candidateId.trim());
            offer.setRecruiterId(recruiterId.trim());
            offer.setCompanyName(companyName != null ? companyName.trim() : "");

            if (status != null && !status.isEmpty()) {
                try {
                    offer.setStatus(Offer.Status.valueOf(status.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    offer.setStatus(Offer.Status.PENDING);
                }
            } else {
                offer.setStatus(Offer.Status.PENDING);
            }

            offer.setEstimatedSalary(estimatedSalary);
            offer.setUpdatedAt(now);


            if (id == null || id.isEmpty()) {
                offerService.addOffer(offer);
                HttpSession session = req.getSession();
                session.setAttribute("success", "Offer sent successfully!");
            } else {
                offerService.updateOffer(offer);
                HttpSession session = req.getSession();
                session.setAttribute("success", "Offer updated successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();

            HttpSession session = req.getSession();
            session.setAttribute("error", "Failed to send offer: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/recruiter");
    }
}