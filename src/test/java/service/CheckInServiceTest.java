package service;

import model.CheckIn;
import model.Leaderboard;
import model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckInServiceTest {
    private UserService userService;
    private CheckInService checkInService;
    private LeaderboardService leaderboardService;
    private User testUser;

    @BeforeAll
    void setup() throws SQLException {
        userService        = new UserService();
        checkInService     = new CheckInService();
        leaderboardService = new LeaderboardService();

        // create a throwaway user (also initializes their leaderboard)
        testUser = new User();
        testUser.setUsername("ciTest" + System.currentTimeMillis());
        testUser.setEmail(testUser.getUsername() + "@example.com");
        testUser.setPasswordHash("password");
        testUser.setHeightCm(170);
        userService.register(testUser);
    }

    @Test
    void testRecordCheckInAndHasCheckedIn() throws SQLException {
        LocalDate day1 = LocalDate.now();

        // Before anything, hasCheckedIn should be false
        assertFalse(checkInService.hasCheckedIn(testUser.getUserId(), day1));

        // Record a check-in for today
        CheckIn ci1 = new CheckIn();
        ci1.setUserId(testUser.getUserId());
        ci1.setCheckInDate(day1);
        checkInService.recordCheckIn(ci1);

        // Now hasCheckedIn returns true
        assertTrue(checkInService.hasCheckedIn(testUser.getUserId(), day1));

        // Leaderboard should be updated: streak = 1, maxStreak = 1, lastDate = day1
        Leaderboard lb1 = leaderboardService.getForUser(testUser.getUserId());
        assertEquals(1, lb1.getCurrentStreak());
        assertEquals(1, lb1.getMaxStreak());
        assertEquals(day1, lb1.getLastCheckInDate());

        // Recording the same date again should do nothing
        checkInService.recordCheckIn(ci1);
        Leaderboard lb1b = leaderboardService.getForUser(testUser.getUserId());
        assertEquals(1, lb1b.getCurrentStreak());
        assertEquals(1, lb1b.getMaxStreak());
        assertEquals(day1, lb1b.getLastCheckInDate());
    }

    @Test
    void testConsecutiveStreakIncrements() throws SQLException {
        LocalDate day1 = LocalDate.now();
        LocalDate day2 = day1.plusDays(1);

        // Record check-in on day2
        CheckIn ci2 = new CheckIn();
        ci2.setUserId(testUser.getUserId());
        ci2.setCheckInDate(day2);
        checkInService.recordCheckIn(ci2);

        // Now streak should be 2
        Leaderboard lb2 = leaderboardService.getForUser(testUser.getUserId());
        assertEquals(2, lb2.getCurrentStreak());
        assertEquals(2, lb2.getMaxStreak());
        assertEquals(day2, lb2.getLastCheckInDate());
    }

    @AfterAll
    void cleanup() throws SQLException {
        // wipe all test data
        userService.resetAll();
    }
}
