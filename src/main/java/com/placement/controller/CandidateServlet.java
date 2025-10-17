package com.placement.controller;

import com.placement.model.Candidate;
import com.placement.service.CandidateService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.UUID;

@MultipartConfig(maxFileSize = 16177215)
@WebServlet("/candidate/*")
public class CandidateServlet extends HttpServlet {

    private CandidateService candidateService = new CandidateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path != null && path.equals("/edit")) {
            String id = req.getParameter("id");
            Candidate candidate = null;

            if (id != null && !id.isEmpty()) {
                candidate = candidateService.getCandidateById(id);
            } else {

                HttpSession session = req.getSession(false);
                if (session != null) {
                    String userId = (String) session.getAttribute("userId");
                    if (userId != null) {
                        candidate = candidateService.getCandidateByUserId(userId);
                    }
                }
            }

            req.setAttribute("candidate", candidate);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/candidateForm.jsp");
            dispatcher.forward(req, resp);

        } else {

            resp.sendRedirect(req.getContextPath() + "/views/candidateDashboard.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String userId = request.getParameter("userId");
        String marksStr = request.getParameter("marks");
        String qualification = request.getParameter("qualification");

        Double marks = null;
        try {
            if (marksStr != null && !marksStr.isEmpty()) {
                marks = Double.parseDouble(marksStr);
            }
        } catch (NumberFormatException ignored) {
        }


        Part filePart = request.getPart("cvFile");
        String cvUrl = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            String filePath = uploadPath + File.separator + uniqueFileName;
            filePart.write(filePath);

            cvUrl = "uploads/" + uniqueFileName;
        }

        Candidate candidate;
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (id == null || id.isEmpty()) {
            candidate = new Candidate();
            candidate.setId(UUID.randomUUID().toString());
            candidate.setCreatedAt(now);
        } else {
            candidate = candidateService.getCandidateById(id);
            if (candidate == null) {
                candidate = new Candidate();
                candidate.setId(UUID.randomUUID().toString());
                candidate.setCreatedAt(now);
            }
        }

        candidate.setUserId(userId);
        candidate.setMarks(marks);
        candidate.setQualification(qualification);

        if (cvUrl != null) {
            candidate.setCvUrl(cvUrl);
        }
        candidate.setUpdatedAt(now);

        candidateService.updateCandidate(candidate);


        response.sendRedirect(request.getContextPath() + "/views/candidateDashboard.jsp");
    }
}
