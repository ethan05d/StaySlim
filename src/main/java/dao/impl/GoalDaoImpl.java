package dao.impl;

import dao.GoalDao;
import model.Goal;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalDaoImpl implements GoalDao {
    @Override
    public void create(Goal goal) throws SQLException {
        String sql = "INSERT INTO Goals (UserID, TargetWeightKg, TargetCalories, StartDate, EndDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, goal.getUserId());
            ps.setDouble(2, goal.getTargetWeightKg());
            ps.setInt(3, goal.getTargetCalories());
            ps.setDate(4, Date.valueOf(goal.getStartDate()));
            ps.setDate(5, Date.valueOf(goal.getEndDate()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) goal.setGoalId(rs.getInt(1));
        }
    }

    @Override
    public List<Goal> findByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM Goals WHERE UserID=? ORDER BY StartDate";
        List<Goal> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Goal g = new Goal();
                g.setGoalId(rs.getInt("GoalID"));
                g.setUserId(rs.getInt("UserID"));
                g.setTargetWeightKg(rs.getDouble("TargetWeightKg"));
                g.setTargetCalories(rs.getInt("TargetCalories"));
                g.setStartDate(rs.getDate("StartDate").toLocalDate());
                g.setEndDate(rs.getDate("EndDate").toLocalDate());
                list.add(g);
            }
        }
        return list;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Goals WHERE GoalID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Goals");
        }
    }
}
