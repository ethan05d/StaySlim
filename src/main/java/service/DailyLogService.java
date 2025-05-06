package service;

import dao.DailyLogDao;
import dao.impl.DailyLogDaoImpl;
import model.DailyLog;

import java.sql.SQLException;
import java.util.List;

public class DailyLogService {
    private final DailyLogDao logDao = new DailyLogDaoImpl();

    // create a new log or update an existing one based on logID
    public void addOrUpdateLog(DailyLog log) throws SQLException {
        if (log.getLogId() > 0) {
            // existing log
            logDao.update(log);
        } else {
            // new log
            logDao.create(log);
        }
    }

    // fetch all logs for a user in date order
    public List<DailyLog> getLogsForUser(int userId) throws SQLException {
        return logDao.findByUser(userId);
    }

    // delete a specific log entry by logID
    public void deleteLog(int logId) throws SQLException {
        logDao.delete(logId);
    }

    // get log entry by logID
    public DailyLog findById(int userId) throws SQLException {
        return logDao.findById(userId);
    }

}
