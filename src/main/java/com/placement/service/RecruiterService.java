package com.placement.service;

import com.placement.dao.RecruiterDAO;
import com.placement.model.Recruiter;

import java.util.*;

public class RecruiterService {
    private RecruiterDAO recruiterDAO = new RecruiterDAO();

    public void addRecruiter(Recruiter recruiter) {
        recruiterDAO.saveRecruiter(recruiter);
    }

    public Recruiter getRecruiterById(String id) {
        return recruiterDAO.findById(id);
    }

    public Recruiter getRecruiterByUserId(String userId) {
    Recruiter recruiter = recruiterDAO.findByUserId(userId);
    return recruiter;
}

    public List<Recruiter> findAllRecruiters() {
        return recruiterDAO.findAll();
    }
}