package com.placement.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String loginURI = req.getContextPath() + "/login";
        String signupURI = req.getContextPath() + "/signup";
        String forgotPasswordURI = req.getContextPath() + "/forgotpassword";
        String resourceURI = req.getRequestURI();

        boolean loggedIn = session != null &&
                          (session.getAttribute("user") != null || session.getAttribute("userId") != null);

        boolean loginRequest = resourceURI.equals(loginURI);
        boolean signupRequest = resourceURI.equals(signupURI);
        boolean forgotPasswordRequest = resourceURI.equals(forgotPasswordURI);

        boolean resourceRequest = resourceURI.startsWith(req.getContextPath() + "/css") ||
                resourceURI.startsWith(req.getContextPath() + "/js") ||
                resourceURI.startsWith(req.getContextPath() + "/images") ||
                resourceURI.startsWith(req.getContextPath() + "/assets");


        if (loggedIn || loginRequest || signupRequest || forgotPasswordRequest || resourceRequest) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(loginURI);
        }
    }
}
