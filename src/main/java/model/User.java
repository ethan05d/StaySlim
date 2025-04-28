package model;

public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private double heightCm;

    public User() {}

    public User(String username, String email, String passwordHash, double heightCm) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.heightCm = heightCm;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public double getHeightCm() { return heightCm; }
    public void setHeightCm(double heightCm) { this.heightCm = heightCm; }
}