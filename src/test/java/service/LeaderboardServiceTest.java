package service;

import model.CheckIn;
import model.Leaderboard;
import model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LeaderboardServiceTest {
    private UserService userService;
    private CheckInService checkInService;
    private LeaderboardService lbService;
    private User u1, u2;

    @BeforeAll
    void setup() throws SQLException {
        userService    = new UserService();
        checkInService = new CheckInService();
        lbService      = new LeaderboardService();

        // create user #1
        u1 = new User();
        u1.setUsername("lb1");
        u1.setEmail("lb1@example.com");
        u1.setPasswordHash("p");
        u1.setHeightCm(170);
        userService.register(u1);

        // create user #2
        u2 = new User();
        u2.setUsername("lb2");
        u2.setEmail("lb2@example.com");
        u2.setPasswordHash("p");
        u2.setHeightCm(170);
        userService.register(u2);

        // u1: 3 consecutive days
        LocalDate today = LocalDate.now();
        for (int i = 2; i >= 0; i--) {
            CheckIn ci = new CheckIn();
            ci.setUserId(u1.getUserId());
            ci.setCheckInDate(today.minusDays(i));
            checkInService.recordCheckIn(ci);
        }

        // u2: only today
        CheckIn ci2 = new CheckIn();
        ci2.setUserId(u2.getUserId());
        ci2.setCheckInDate(today);
        checkInService.recordCheckIn(ci2);
    }

    @Test
    void testGetTopStreaks() throws SQLException {
        List<Leaderboard> top = lbService.getTopStreaks(10);
        assertTrue(top.size() >= 2);

        // u1 should appear before u2
        assertEquals(u1.getUserId(), top.get(0).getUserId());
        assertEquals(3, top.get(0).getCurrentStreak());

        assertEquals(u2.getUserId(), top.get(1).getUserId());
        assertEquals(1, top.get(1).getCurrentStreak());
    }

    @AfterAll
    void cleanup() throws SQLException {
        // reset everything
        userService.resetAll();
    }
}
