package service;

import dao.DailyLogDao;
import dao.impl.DailyLogDaoImpl;
import model.DailyLog;

import java.sql.SQLException;
import java.util.List;

public class DailyLogService {
    private final DailyLogDao logDao = new DailyLogDaoImpl();

    /**
     * Adds or updates a daily log; enforces one entry per user per date.
     */
    public void addOrUpdateLog(DailyLog log) throws SQLException {
        if (log.getLogId() > 0) {
            logDao.update(log);
        } else {
            // New row
            logDao.create(log);
        }
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