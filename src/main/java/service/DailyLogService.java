package service;

import dao.DailyLogDao;
import dao.impl.DailyLogDaoImpl;
import model.DailyLog;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DailyLogService {
    private final DailyLogDao logDao = new DailyLogDaoImpl();

    /**
     * Adds or updates a daily log; enforces one entry per user per date.
     */
    public void addOrUpdateLog(DailyLog log) throws SQLException {
        // Check for existing log
        List<DailyLog> existing = logDao.findByUser(log.getUserId());
        for (DailyLog dl : existing) {
            if (dl.getLogDate().equals(log.getLogDate())) {
                // Update
                log.setLogId(dl.getLogId());
                logDao.update(log);
                return;
            }
        }
        // Create new
        logDao.create(log);
    }

    public List<DailyLog> getLogsForUser(int userId) throws SQLException {
        return logDao.findByUser(userId);
    }

    public void deleteLog(int logId) throws SQLException {
        logDao.delete(logId);
    }

    public DailyLog findById(int userId) throws SQLException {
        return logDao.findById(userId);
    }

    public List<DailyLog> findByUser(int userId) throws SQLException {
        return logDao.findByUser(userId);
    }
}