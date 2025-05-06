package service;

import dao.LeaderboardDao;
import dao.impl.LeaderboardDaoImpl;
import model.Leaderboard;

import java.sql.SQLException;
import java.util.List;

public class LeaderboardService {
    private final LeaderboardDao lbDao = new LeaderboardDaoImpl();

    /*
     * Retrieves top N users by current streak.
     */
    public List<Leaderboard> getTopStreaks(int limit) throws SQLException {
        return lbDao.findTopStreaks(limit);
    }

    /*
       Retrieves leaderboard entry for a specific user.
    */
    public Leaderboard getForUser(int userId) throws SQLException {
        return lbDao.findByUser(userId);
    }
}
