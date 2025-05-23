USE StaySlim;

-- Users
INSERT INTO Users (Username, Email, PasswordHash, HeightCm) VALUES
('user1',  'user1@gmail.com',  'password1', 170.0),
('user2',  'user2@gmail.com',  'password2', 180.2),
('user3',  'user3@gmail.com',  'password3', 165.3),
('user4',  'user4@gmail.com',  'password4', 175.5),
('user5',  'user5@gmail.com',  'password5', 160.0),
('user6',  'user6@gmail.com',  'password6', 182.4),
('user7',  'user7@gmail.com',  'password7', 168.9),
('user8',  'user8@gmail.com',  'password8', 177.2),
('user9',  'user9@gmail.com',  'password9', 158.7),
('user10', 'user10@gmail.com', 'password10',190.0),
('user11', 'user11@gmail.com', 'password11',172.5),
('user12', 'user12@gmail.com', 'password12',163.4),
('user13', 'user13@gmail.com', 'password13',178.1),
('user14', 'user14@gmail.com', 'password14',169.8),
('user15', 'user15@gmail.com', 'password15',185.6);

-- DailyLogs
INSERT INTO DailyLogs (UserID, LogDate, WeightKg, CaloriesIntake) VALUES
(1,  '2025-04-01',  70.5, 2100),
(2,  '2025-04-01',  82.3, 2300),
(3,  '2025-04-01',  65.2, 1800),
(4,  '2025-04-01',  78.4, 2200),
(5,  '2025-04-01',  59.8, 1700),
(6,  '2025-04-01',  90.1, 2500),
(7,  '2025-04-01',  68.7, 2000),
(8,  '2025-04-01',  80.0, 2250),
(9,  '2025-04-01',  55.4, 1600),
(10, '2025-04-01',  95.2, 2600),
(11, '2025-04-01',  72.0, 2100),
(12, '2025-04-01',  63.5, 1900),
(13, '2025-04-01',  85.6, 2400),
(14, '2025-04-01',  66.1, 1950),
(15, '2025-04-01',  88.3, 2450);

-- Goals
INSERT INTO Goals (UserID, TargetWeightKg, TargetCalories, StartDate, EndDate) VALUES
(1,  68.0, 2000, '2025-04-01', '2025-05-01'),
(2,  80.0, 2200, '2025-04-05', '2025-06-05'),
(3,  62.0, 1800, '2025-04-10', '2025-05-10'),
(4,  75.0, 2100, '2025-03-20', '2025-05-20'),
(5,  58.0, 1700, '2025-04-01', '2025-05-01'),
(6,  85.0, 2400, '2025-04-02', '2025-06-02'),
(7,  65.0, 2000, '2025-04-07', '2025-05-07'),
(8,  78.0, 2250, '2025-04-12', '2025-06-12'),
(9,  54.0, 1600, '2025-04-03', '2025-05-03'),
(10, 92.0, 2600, '2025-04-15', '2025-06-15'),
(11, 70.0, 2100, '2025-04-08', '2025-05-08'),
(12, 60.0, 1900, '2025-04-04', '2025-05-04'),
(13, 88.0, 2450, '2025-04-11', '2025-06-11'),
(14, 67.0, 1950, '2025-04-06', '2025-05-06'),
(15, 90.0, 2500, '2025-04-09', '2025-06-09');

-- CheckIns
INSERT INTO CheckIns (UserID, CheckInDate) VALUES
(1,  '2025-04-01'),
(2,  '2025-04-05'),
(3,  '2025-04-10'),
(4,  '2025-03-20'),
(5,  '2025-04-01'),
(6,  '2025-04-02'),
(7,  '2025-04-07'),
(8,  '2025-04-12'),
(9,  '2025-04-03'),
(10, '2025-04-15'),
(11, '2025-04-08'),
(12, '2025-04-04'),
(13, '2025-04-11'),
(14, '2025-04-06'),
(15, '2025-04-09');

-- Leaderboard
INSERT INTO Leaderboard (UserID, CurrentStreak, MaxStreak, LastCheckInDate) VALUES
(1,  5,  7,  '2025-04-01'),
(2,  3,  5,  '2025-04-05'),
(3,  7,  7,  '2025-04-10'),
(4,  2,  4,  '2025-03-20'),
(5,  6,  6,  '2025-04-01'),
(6,  1,  2,  '2025-04-02'),
(7,  4,  4,  '2025-04-07'),
(8,  8,  8,  '2025-04-12'),
(9,  3,  3,  '2025-04-03'),
(10, 9,  9,  '2025-04-15'),
(11, 2,  5,  '2025-04-08'),
(12, 5,  6,  '2025-04-04'),
(13, 7, 10,  '2025-04-11'),
(14, 4,  4,  '2025-04-06'),
(15, 1,  3,  '2025-04-09');
