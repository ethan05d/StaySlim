package service;

import dao.CheckInDao;
import dao.LeaderboardDao;
import dao.impl.CheckInDaoImpl;
import dao.impl.LeaderboardDaoImpl;
import model.CheckIn;
import model.Leaderboard;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CheckInService {
    private final CheckInDao checkInDao = new CheckInDaoImpl();
    private final LeaderboardDao lbDao = new LeaderboardDaoImpl();

    /**
     * Records a check-in if not already done for that date,
     * and updates streaks on the leaderboard.
     */
    public void recordCheckIn(CheckIn ci) throws SQLException {
        // Avoid duplicates
        List<CheckIn> existing = checkInDao.findByUser(ci.getUserId());
        for (CheckIn c: existing) {
            if (c.getCheckInDate().equals(ci.getCheckInDate())) {
                return;
            }
        }
        // Create check-in
        checkInDao.create(ci);

        // Update leaderboard
        Leaderboard lb = lbDao.findByUser(ci.getUserId());
        LocalDate lastDate = lb.getLastCheckInDate();
        int current = lb.getCurrentStreak();
        // New streak if consecutive day
        if (ci.getCheckInDate().equals(lastDate.plusDays(1))) {
            current++;
        } else {
            current = 1;
        }
        int max = Math.max(current, lb.getMaxStreak());
        lb.setCurrentStreak(current);
        lb.setMaxStreak(max);
        lb.setLastCheckInDate(ci.getCheckInDate());
        lbDao.createOrUpdate(lb);
    }

    public boolean hasCheckedIn(int userId, LocalDate targetDate) throws SQLException {
        List<CheckIn> existing = checkInDao.findByUser(userId);
        for (CheckIn c: existing) {
            if (c.getCheckInDate().equals(targetDate)) {
                return true;
            }
        }

        return false;
    }
}