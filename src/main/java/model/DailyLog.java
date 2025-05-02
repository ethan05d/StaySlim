package model;

import java.time.LocalDate;

public class DailyLog {
    private int logId;
    private int userId;
    private LocalDate logDate;
    private double weightKg;
    private int caloriesIntake;

    public DailyLog() {}
    public DailyLog(int userId, LocalDate logDate, double weightKg, int caloriesIntake) {
        this.userId = userId;
        this.logDate = logDate;
        this.weightKg = weightKg;
        this.caloriesIntake = caloriesIntake;
    }

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }
    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
    public int getCaloriesIntake() { return caloriesIntake; }
    public void setCaloriesIntake(int caloriesIntake) { this.caloriesIntake = caloriesIntake; }

}