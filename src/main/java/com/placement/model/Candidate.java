package com.placement.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "user_id", nullable = false, length = 255)
    private String userId;

    private Double marks;

    private String qualification;

    @Column(name = "cv_url", length = 2048)
    private String cvUrl;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Transient
    private String userName;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Double getMarks() { return marks; }
    public void setMarks(Double marks) { this.marks = marks; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public String getCvUrl() { return cvUrl; }
    public void setCvUrl(String cvUrl) { this.cvUrl = cvUrl; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}