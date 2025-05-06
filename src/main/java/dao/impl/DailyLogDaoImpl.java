package dao.impl;

import dao.DailyLogDao;
import model.DailyLog;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DailyLogDaoImpl implements DailyLogDao {

    // insert a new daily log and set its generated LogID
    @Override
    public void create(DailyLog log) throws SQLException {
        String sql = "INSERT INTO DailyLogs (UserID, LogDate, WeightKg, CaloriesIntake) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // bind parameters: user, date, weight, calories
            ps.setInt(1, log.getUserId());
            ps.setDate(2, Date.valueOf(log.getLogDate()));
            ps.setDouble(3, log.getWeightKg());
            ps.setInt(4, log.getCaloriesIntake());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    log.setLogId(rs.getInt(1));
                }
            }
        }
    }

    // find a single daily log by LogID
    @Override
    public DailyLog findById(int id) throws SQLException {
        String sql = "SELECT * FROM DailyLogs WHERE LogID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DailyLog log = new DailyLog();
                log.setLogId(rs.getInt("LogID"));
                log.setUserId(rs.getInt("UserID"));
                log.setLogDate(rs.getDate("LogDate").toLocalDate());
                log.setWeightKg(rs.getDouble("WeightKg"));
                log.setCaloriesIntake(rs.getInt("CaloriesIntake"));
                return log;
            }
        }
        // return null if no match found
        return null;
    }

    // get all logs for a user by chronological order
    @Override
    public List<DailyLog> findByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM DailyLogs WHERE UserID=? ORDER BY LogDate";
        List<DailyLog> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DailyLog log = new DailyLog();
                log.setLogId(rs.getInt("LogID"));
                log.setUserId(rs.getInt("UserID"));
                log.setLogDate(rs.getDate("LogDate").toLocalDate());
                log.setWeightKg(rs.getDouble("WeightKg"));
                log.setCaloriesIntake(rs.getInt("CaloriesIntake"));
                list.add(log);
            }
        }
        return list;
    }

    // update date, weight, and calories for an existing log by LogID
    @Override
    public void update(DailyLog log) throws SQLException {
        String sql = "UPDATE DailyLogs SET LogDate=?, WeightKg=?, CaloriesIntake=? WHERE LogID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(log.getLogDate()));
            ps.setDouble(2, log.getWeightKg());
            ps.setInt(3, log.getCaloriesIntake());

            // specify which row to update
            ps.setInt(4, log.getLogId());
            ps.executeUpdate();
        }
    }

    // delete a specific log entry by its LogID
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM DailyLogs WHERE LogID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // remove all daily log entries (for cleanup/testing)
    @Override
    public void deleteAll() throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM DailyLogs");
        }
    }
}
