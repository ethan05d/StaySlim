package dao;

import model.DailyLog;
import java.sql.SQLException;
import java.util.List;

public interface DailyLogDao {
    void create(DailyLog log) throws SQLException;
    DailyLog findById(int id) throws SQLException;
    List<DailyLog> findByUser(int userId) throws SQLException;
    List<DailyLog> findAll() throws SQLException;
    void update(DailyLog log) throws SQLException;
    void delete(int id) throws SQLException;
    void deleteAll() throws SQLException;
}