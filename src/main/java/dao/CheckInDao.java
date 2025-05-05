package dao;

import model.CheckIn;
import java.sql.SQLException;
import java.util.List;

public interface CheckInDao {
    void create(CheckIn checkIn) throws SQLException;
    List<CheckIn> findByUser(int userId) throws SQLException;
    void deleteAll() throws SQLException;
}