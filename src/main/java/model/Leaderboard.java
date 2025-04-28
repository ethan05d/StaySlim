package model;

import java.time.LocalDate;

public class Leaderboard {
    private int userId;
    private int currentStreak;
    private int maxStreak;
    private LocalDate lastCheckInDate;

    public Leaderboard() {}
    public Leaderboard(int userId, int currentStreak, int maxStreak, LocalDate lastCheckInDate) {
        this.userId = userId;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
        this.lastCheckInDate = lastCheckInDate;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; }
    public int getMaxStreak() { return maxStreak; }
    public void setMaxStreak(int maxStreak) { this.maxStreak = maxStreak; }
    public LocalDate getLastCheckInDate() { return lastCheckInDate; }
    public void setLastCheckInDate(LocalDate lastCheckInDate) { this.lastCheckInDate = lastCheckInDate; }
}