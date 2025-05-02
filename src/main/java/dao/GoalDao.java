package dao;

import model.Goal;
import java.sql.SQLException;
import java.util.List;

public interface GoalDao {
    void create(Goal goal) throws SQLException;
    Goal findById(int id) throws SQLException;
    List<Goal> findByUser(int userId) throws SQLException;
    List<Goal> findAll() throws SQLException;
    void update(Goal goal) throws SQLException;
    void delete(int id) throws SQLException;
    void deleteAll() throws SQLException;
}