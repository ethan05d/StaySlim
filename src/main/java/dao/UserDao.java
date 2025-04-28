package dao;

import model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void create(User user) throws SQLException;
    User findById(int id) throws SQLException;
    List<User> findAll() throws SQLException;
    void update(User user) throws SQLException;
    void delete(int id) throws SQLException;
}
