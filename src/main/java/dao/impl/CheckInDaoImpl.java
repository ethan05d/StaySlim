package dao.impl;

import dao.CheckInDao;
import model.CheckIn;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckInDaoImpl implements CheckInDao {
    @Override
    public void create(CheckIn c) throws SQLException {
        String sql = "INSERT INTO CheckIns (UserID, CheckInDate) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getUserId());
            ps.setDate(2, Date.valueOf(c.getCheckInDate()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) c.setCheckInId(rs.getInt(1));
        }
    }

    @Override
    public CheckIn findById(int id) throws SQLException {
        String sql = "SELECT * FROM CheckIns WHERE CheckInID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CheckIn c = new CheckIn();
                c.setCheckInId(rs.getInt("CheckInID"));
                c.setUserId(rs.getInt("UserID"));
                c.setCheckInDate(rs.getDate("CheckInDate").toLocalDate());
                return c;
            }
        }
        return null;
    }

    @Override
    public List<CheckIn> findByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM CheckIns WHERE UserID=? ORDER BY CheckInDate";
        List<CheckIn> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CheckIn c = new CheckIn();
                c.setCheckInId(rs.getInt("CheckInID"));
                c.setUserId(rs.getInt("UserID"));
                c.setCheckInDate(rs.getDate("CheckInDate").toLocalDate());
                list.add(c);
            }
        }
        return list;
    }

    @Override
    public List<CheckIn> findAll() throws SQLException {
        String sql = "SELECT * FROM CheckIns ORDER BY CheckInDate";
        List<CheckIn> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CheckIn c = new CheckIn();
                c.setCheckInId(rs.getInt("CheckInID"));
                c.setUserId(rs.getInt("UserID"));
                c.setCheckInDate(rs.getDate("CheckInDate").toLocalDate());
                list.add(c);
            }
        }
        return list;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM CheckIns WHERE CheckInID=?";
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
            stmt.executeUpdate("DELETE FROM CheckIns");
        }
    }
}