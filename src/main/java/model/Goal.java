package model;

import java.time.LocalDate;

// Model representing a user’s goal period with weight and calorie targets
public class Goal {
    private int goalId;
    private int userId;
    private double targetWeightKg;
    private int targetCalories;
    private LocalDate startDate;
    private LocalDate endDate;

    public Goal() {}
    public Goal(int userId, double targetWeightKg, int targetCalories, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.targetWeightKg = targetWeightKg;
        this.targetCalories = targetCalories;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getGoalId() { return goalId; }
    public void setGoalId(int goalId) { this.goalId = goalId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public double getTargetWeightKg() { return targetWeightKg; }
    public void setTargetWeightKg(double targetWeightKg) { this.targetWeightKg = targetWeightKg; }
    public int getTargetCalories() { return targetCalories; }
    public void setTargetCalories(int targetCalories) { this.targetCalories = targetCalories; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}