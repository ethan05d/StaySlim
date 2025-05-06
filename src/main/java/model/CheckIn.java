package model;

import java.time.LocalDate;

// Model representing a user’s daily check-in record
public class CheckIn {
    private int checkInId;
    private int userId;
    private LocalDate checkInDate;

    public CheckIn() {}
    public CheckIn(int userId, LocalDate checkInDate) {
        this.userId = userId;
        this.checkInDate = checkInDate;
    }

    public int getCheckInId() { return checkInId; }
    public void setCheckInId(int checkInId) { this.checkInId = checkInId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
}