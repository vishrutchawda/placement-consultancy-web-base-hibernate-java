package com.placement.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "recruiters")
public class Recruiter {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "user_id", nullable = false, length = 255)
    private String userId;

    @Column(name = "companyname")
    private String companyName;

    @Column(name = "createdat")
    private Timestamp createdAt;

    @Column(name = "updatedat")
    private Timestamp updatedAt;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}