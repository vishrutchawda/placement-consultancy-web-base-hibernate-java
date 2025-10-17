package com.placement.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/download-cv")
public class FileDownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cvUrl = request.getParameter("file");

        if (cvUrl == null || cvUrl.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File parameter is required");
            return;
        }

        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";


        String fileName = cvUrl;
        if (cvUrl.contains("/")) {
            fileName = cvUrl.substring(cvUrl.lastIndexOf("/") + 1);
        }

        String filePath = uploadPath + File.separator + fileName;
        File file = new File(filePath);


        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                "CV file not found: " + cvUrl + " at path: " + filePath);
            return;
        }


        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        response.setContentLength((int) file.length());


        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } catch (IOException e) {
            System.err.println("Error streaming CV file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
