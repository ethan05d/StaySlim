package dao;

import model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void create(User user) throws SQLException;
    User findByUsername(String username) throws SQLException;
    User findByEmail(String email) throws SQLException;
    User findById(int id) throws SQLException;
    List<User> findAll() throws SQLException;
    void delete(int userId) throws SQLException;
    void deleteAll() throws SQLException;
}
