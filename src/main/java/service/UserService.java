package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import dao.LeaderboardDao;
import dao.impl.LeaderboardDaoImpl;
import dao.DailyLogDao;
import dao.impl.DailyLogDaoImpl;
import dao.GoalDao;
import dao.impl.GoalDaoImpl;
import dao.CheckInDao;
import dao.impl.CheckInDaoImpl;
import model.User;
import model.Leaderboard;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDaoImpl();
    private final LeaderboardDao leaderboardDao = new LeaderboardDaoImpl();
    private final DailyLogDao dailyLogDao = new DailyLogDaoImpl();
    private final GoalDao goalDao = new GoalDaoImpl();
    private final CheckInDao checkInDao = new CheckInDaoImpl();

    public void register(User user) throws SQLException {
        String hashed = hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashed);

        userDao.create(user);

        Leaderboard lb = new Leaderboard();
        lb.setUserId(user.getUserId());
        lb.setCurrentStreak(0);
        lb.setMaxStreak(0);
        lb.setLastCheckInDate(LocalDate.now());
        leaderboardDao.createOrUpdate(lb);
    }

    public User login(String usernameOrEmail, String plainPassword) throws SQLException {
        // allow login by either username or email
        User user = userDao.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userDao.findByEmail(usernameOrEmail);
        }
        if (user == null) return null;

        String hashed = hashPassword(plainPassword);
        return user.getPasswordHash().equals(hashed) ? user : null;
    }

    public User findById(int userId) throws SQLException {
        return userDao.findById(userId);
    }

    public boolean usernameExists(String username) throws SQLException {
        return userDao.findByUsername(username) != null;
    }

    public boolean emailExists(String email) throws SQLException {
        return userDao.findByEmail(email) != null;
    }

    private String hashPassword(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(plain.getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to hash password", e);
        }
    }

    public List<User> findAll() throws SQLException {
        return userDao.findAll();
    }

    public void delete(int userId) throws SQLException {
        userDao.delete(userId);
    }

    public void resetAll() throws SQLException {
        userDao.deleteAll();
        dailyLogDao.deleteAll();
        goalDao.deleteAll();
        checkInDao.deleteAll();
        leaderboardDao.deleteAll();
    }
}
