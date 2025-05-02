package dao;

import model.CheckIn;
import java.sql.SQLException;
import java.util.List;

public interface CheckInDao {
    void create(CheckIn checkIn) throws SQLException;
    CheckIn findById(int id) throws SQLException;
    List<CheckIn> findByUser(int userId) throws SQLException;
    List<CheckIn> findAll() throws SQLException;
    void delete(int id) throws SQLException;
    void deleteAll() throws SQLException;
}