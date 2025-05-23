package dao.impl;

import dao.LeaderboardDao;
import model.Leaderboard;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDaoImpl implements LeaderboardDao {

    // insert or update a leaderboard entry for a user
    @Override
    public void createOrUpdate(Leaderboard lb) throws SQLException {
        String select = "SELECT UserID FROM Leaderboard WHERE UserID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psCheck = conn.prepareStatement(select)) {
            psCheck.setInt(1, lb.getUserId());
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                // already exists, so update streak values
                String update = "UPDATE Leaderboard SET CurrentStreak=?, MaxStreak=?, LastCheckInDate=? WHERE UserID=?";
                try (PreparedStatement psUpd = conn.prepareStatement(update)) {
                    psUpd.setInt(1, lb.getCurrentStreak());
                    psUpd.setInt(2, lb.getMaxStreak());
                    psUpd.setDate(3, Date.valueOf(lb.getLastCheckInDate()));
                    psUpd.setInt(4, lb.getUserId());
                    psUpd.executeUpdate();
                }
            } else {
                // new entry, so insert fresh row
                String insert = "INSERT INTO Leaderboard (UserID, CurrentStreak, MaxStreak, LastCheckInDate) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psIns = conn.prepareStatement(insert)) {
                    psIns.setInt(1, lb.getUserId());
                    psIns.setInt(2, lb.getCurrentStreak());
                    psIns.setInt(3, lb.getMaxStreak());
                    psIns.setDate(4, Date.valueOf(lb.getLastCheckInDate()));
                    psIns.executeUpdate();
                }
            }
        }
    }

    // find the leaderboard row for a specific user
    @Override
    public Leaderboard findByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM Leaderboard WHERE UserID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Leaderboard lb = new Leaderboard();
                lb.setUserId(rs.getInt("UserID"));
                lb.setCurrentStreak(rs.getInt("CurrentStreak"));
                lb.setMaxStreak(rs.getInt("MaxStreak"));
                lb.setLastCheckInDate(rs.getDate("LastCheckInDate").toLocalDate());
                return lb;
            }
        }
        // return null if user not on leaderboard yet
        return null;
    }

    // get the limit amount of users by their current streak in descending order
    @Override
    public List<Leaderboard> findTopStreaks(int limit) throws SQLException {
        String sql = "SELECT * FROM Leaderboard ORDER BY CurrentStreak DESC LIMIT ?";
        List<Leaderboard> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Leaderboard lb = new Leaderboard();
                lb.setUserId(rs.getInt("UserID"));
                lb.setCurrentStreak(rs.getInt("CurrentStreak"));
                lb.setMaxStreak(rs.getInt("MaxStreak"));
                lb.setLastCheckInDate(rs.getDate("LastCheckInDate").toLocalDate());
                list.add(lb);
            }
        }

        return list;
    }

    // delete a user's leaderboard entry by their UserID
    @Override
    public void delete(int userId) throws SQLException {
        String sql = "DELETE FROM Leaderboard WHERE UserID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    // remove all entries from the Leaderboard table
    @Override
    public void deleteAll() throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Leaderboard");
        }
    }
}
