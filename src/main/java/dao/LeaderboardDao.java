package dao;

import model.Leaderboard;
import java.sql.SQLException;
import java.util.List;

public interface LeaderboardDao {
    void createOrUpdate(Leaderboard lb) throws SQLException;
    Leaderboard findByUser(int userId) throws SQLException;
    List<Leaderboard> findTopStreaks(int limit) throws SQLException;
    void delete(int userId) throws SQLException;
    void deleteAll() throws SQLException;
}