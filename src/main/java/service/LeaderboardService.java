package service;

import dao.LeaderboardDao;
import dao.impl.LeaderboardDaoImpl;
import model.Leaderboard;

import java.sql.SQLException;
import java.util.List;

public class LeaderboardService {
    private final LeaderboardDao lbDao = new LeaderboardDaoImpl();

    // retrieve number of users ordered by their current streak (amount = limit)
    public List<Leaderboard> getTopStreaks(int limit) throws SQLException {
        return lbDao.findTopStreaks(limit);
    }

    // fetch the leaderboard entry for one user
    public Leaderboard getForUser(int userId) throws SQLException {
        return lbDao.findByUser(userId);
    }
}
