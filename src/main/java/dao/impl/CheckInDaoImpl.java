package dao.impl;

import dao.CheckInDao;
import model.CheckIn;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckInDaoImpl implements CheckInDao {

    // insert a new check-in record and set its generated ID on the model
    @Override
    public void create(CheckIn c) throws SQLException {
        // prepare SQL to insert a new check-in row
        String sql = "INSERT INTO CheckIns (UserID, CheckInDate) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getUserId());
            ps.setDate(2, Date.valueOf(c.getCheckInDate()));
            // run the insert
            ps.executeUpdate();

            // grab the generated ID and stick it on our CheckIn object
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setCheckInId(rs.getInt(1));
                }
            }
        }
    }

    // retrieve all check-ins for a given user, sorted by date ascending
    @Override
    public List<CheckIn> findByUser(int userId) throws SQLException {
        // get all check-ins for a user in date order
        String sql = "SELECT * FROM CheckIns WHERE UserID=? ORDER BY CheckInDate";
        List<CheckIn> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            // loop through each row and map to model
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

    // remove every record from CheckIns
    @Override
    public void deleteAll() throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM CheckIns");
        }
    }
}
