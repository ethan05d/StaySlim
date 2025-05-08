-- Create & select the database
CREATE DATABASE IF NOT EXISTS StaySlim;
USE StaySlim;

-- Drop tables if they already exist
DROP TABLE IF EXISTS Leaderboard;
DROP TABLE IF EXISTS CheckIns;
DROP TABLE IF EXISTS Goals;
DROP TABLE IF EXISTS DailyLogs;
DROP TABLE IF EXISTS Users;

-- Users table
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50)  NOT NULL UNIQUE,
    Email VARCHAR(100) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    HeightCm DECIMAL(5,2) NOT NULL
);

-- DailyLogs table
CREATE TABLE DailyLogs (
    LogID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    LogDate DATE NOT NULL,
    WeightKg DECIMAL(5,2) NOT NULL,
    CaloriesIntake INT NOT NULL,
    UNIQUE (UserID, LogDate),
    FOREIGN KEY (UserID)
       REFERENCES Users(UserID)
       ON DELETE CASCADE
);

-- Goals table
CREATE TABLE Goals (
    GoalID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    TargetWeightKg DECIMAL(5,2) NOT NULL,
    TargetCalories INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    FOREIGN KEY (UserID)
        REFERENCES Users(UserID)
        ON DELETE CASCADE
);

-- CheckIns table
CREATE TABLE CheckIns (
    CheckInID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    CheckInDate DATE NOT NULL,
    UNIQUE (UserID, CheckInDate),
    FOREIGN KEY (UserID)
      REFERENCES Users(UserID)
      ON DELETE CASCADE
);

-- Leaderboard table
CREATE TABLE Leaderboard (
     UserID INT PRIMARY KEY,
     CurrentStreak INT NOT NULL,
     MaxStreak INT NOT NULL,
     LastCheckInDate DATE NOT NULL,
     FOREIGN KEY (UserID)
         REFERENCES Users(UserID)
         ON DELETE CASCADE
);