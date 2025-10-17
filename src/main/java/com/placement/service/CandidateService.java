package com.placement.service;

import com.placement.dao.CandidateDAO;
import com.placement.model.Candidate;
import java.util.List;

public class CandidateService {
    private CandidateDAO candidateDAO = new CandidateDAO();

    public void addCandidate(Candidate candidate) {
        candidateDAO.saveCandidate(candidate);
    }

    public Candidate getCandidateById(String id) {
        return candidateDAO.findById(id);
    }

    public Candidate getCandidateByUserId(String userId) {
        return candidateDAO.findByUserId(userId);
    }

    public List<Candidate> getAllCandidates() {
        return candidateDAO.findAll();
    }

    public void updateCandidate(Candidate candidate) {
        candidateDAO.saveCandidate(candidate);
    }


}
