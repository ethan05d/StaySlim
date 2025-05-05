package dao;

import model.Goal;
import java.sql.SQLException;
import java.util.List;

public interface GoalDao {
    void create(Goal goal) throws SQLException;
    List<Goal> findByUser(int userId) throws SQLException;
    void delete(int id) throws SQLException;
    void deleteAll() throws SQLException;
}