package com.placement.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "offers")
public class Offer {

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    @Id
    @Column(length = 255)
    private String id;

    @Transient
    private String candidateName;

    @Column(name = "candidate_id")
    private String candidateId;

    @Column(name = "recruiter_id")
    private String recruiterId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "estimated_salary", precision = 10, scale = 2)
    private BigDecimal estimatedSalary;

    @Column(name = "position")
    private String position;

    @Column(name = "eligibility")
    private String eligibility;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCandidateId() { return candidateId; }
    public void setCandidateId(String candidateId) { this.candidateId = candidateId; }

    public String getRecruiterId() { return recruiterId; }
    public void setRecruiterId(String recruiterId) { this.recruiterId = recruiterId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public BigDecimal getEstimatedSalary() { return estimatedSalary; }
    public void setEstimatedSalary(BigDecimal estimatedSalary) { this.estimatedSalary = estimatedSalary; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getEligibility() { return eligibility; }
    public void setEligibility(String eligibility) { this.eligibility = eligibility; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
}
