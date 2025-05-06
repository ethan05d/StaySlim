package service;

import dao.CheckInDao;
import dao.DailyLogDao;
import dao.GoalDao;
import dao.LeaderboardDao;
import dao.UserDao;
import dao.impl.CheckInDaoImpl;
import dao.impl.DailyLogDaoImpl;
import dao.impl.GoalDaoImpl;
import dao.impl.LeaderboardDaoImpl;
import dao.impl.UserDaoImpl;
import model.Leaderboard;
import model.User;

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

    // register a new user, hash their password, and initialize their leaderboard
    public void register(User user) throws SQLException {
        // hash the plaintext password before saving
        String hashed = hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashed);

        // save the user record
        userDao.create(user);

        // create initial leaderboard entry with zero streak
        Leaderboard lb = new Leaderboard();
        lb.setUserId(user.getUserId());
        lb.setCurrentStreak(0);
        lb.setMaxStreak(0);
        lb.setLastCheckInDate(LocalDate.now());
        leaderboardDao.createOrUpdate(lb);
    }

    // authenticate a user by username or email and password
    public User login(String usernameOrEmail, String plainPassword) throws SQLException {
        // try to find user
        User user = userDao.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userDao.findByEmail(usernameOrEmail);
        }

        // if no record found, login fails
        if (user == null) {
            return null;
        }

        // hash the password
        String hashed = hashPassword(plainPassword);

        // compare stored hash to computed hash
        if (user.getPasswordHash().equals(hashed)) {
            return user;
        } else {
            return null;
        }
    }

    // fetch a user by their userID
    public User findById(int userId) throws SQLException {
        return userDao.findById(userId);
    }

    // check if a username is already in use
    public boolean usernameExists(String username) throws SQLException {
        return userDao.findByUsername(username) != null;
    }

    // check if an email is already registered
    public boolean emailExists(String email) throws SQLException {
        return userDao.findByEmail(email) != null;
    }

    // hash a password string using SHA-256 and Base64-encode the result
    private String hashPassword(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(plain.getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            // should never happen unless SHA-256 is unavailable
            throw new RuntimeException("Unable to hash password", e);
        }
    }

    // list all registered users
    public List<User> findAll() throws SQLException {
        return userDao.findAll();
    }

    // delete a specific user by userID
    public void delete(int userId) throws SQLException {
        userDao.delete(userId);
    }

    // reset all data across all tables
    public void resetAll() throws SQLException {
        userDao.deleteAll();
        dailyLogDao.deleteAll();
        goalDao.deleteAll();
        checkInDao.deleteAll();
        leaderboardDao.deleteAll();
    }
}
