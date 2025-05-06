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

    // record a check-in for the user and update their streak on the leaderboard
    public void recordCheckIn(CheckIn ci) throws SQLException {
        // load all existing check-ins for this user and don't record if they've already checked in
        List<CheckIn> existing = checkInDao.findByUser(ci.getUserId());
        for (CheckIn c: existing) {
            if (c.getCheckInDate().equals(ci.getCheckInDate())) {
                return;
            }
        }

        checkInDao.create(ci);

        // load the user's leaderboard entry to update streaks
        Leaderboard lb = lbDao.findByUser(ci.getUserId());
        LocalDate lastDate = lb.getLastCheckInDate();
        int current = lb.getCurrentStreak();

        // if this check-in is the day after the last, increment streak. Else just reset to 1
        if (ci.getCheckInDate().equals(lastDate.plusDays(1))) {
            current++;
        } else {
            current = 1;
        }

        // update max streak if needed
        int max = Math.max(current, lb.getMaxStreak());
        lb.setCurrentStreak(current);
        lb.setMaxStreak(max);
        lb.setLastCheckInDate(ci.getCheckInDate());

        // save changes back to the leaderboard
        lbDao.createOrUpdate(lb);
    }

    // return true if the user has a check-in entry for the given date
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
