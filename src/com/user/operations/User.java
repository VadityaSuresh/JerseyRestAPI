package com.user.operations;

public class User {
    private int id;
    private String emailId;
    private String isActive;
    private String role;

    // Constructors
    public User() {}

    public User(int id, String emailId, String isActive, String role) {
        this.id = id;
        this.emailId = emailId;
        this.isActive = isActive;
        this.role = role;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getIsActive() { return isActive; }
    public void setIsActive(String isActive) { this.isActive = isActive; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
