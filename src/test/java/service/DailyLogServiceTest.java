package service;

import model.User;
import model.DailyLog;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DailyLogServiceTest {
    private UserService userService;
    private DailyLogService logService;
    private User testUser;

    @BeforeAll
    void setup() throws SQLException {
        userService = new UserService();
        logService  = new DailyLogService();

        // create a throwaway user
        testUser = new User();
        testUser.setUsername("logtest" + System.currentTimeMillis());
        testUser.setEmail(testUser.getUsername() + "@example.com");
        testUser.setPasswordHash("pass123");
        testUser.setHeightCm(170);
        userService.register(testUser);
    }

    @Test
    void testAddUpdateDeleteLog() throws SQLException {
        LocalDate today = LocalDate.now();
        DailyLog log = new DailyLog();
        log.setUserId(testUser.getUserId());
        log.setLogDate(today);
        log.setWeightKg(70.0);
        log.setCaloriesIntake(2000);

        // Create
        logService.addOrUpdateLog(log);
        assertTrue(log.getLogId() > 0);

        // Read
        List<DailyLog> logs = logService.getLogsForUser(testUser.getUserId());
        assertTrue(logs.stream().anyMatch(dl -> dl.getLogDate().equals(today)));

        // Update
        log.setWeightKg(71.5);
        logService.addOrUpdateLog(log);
        DailyLog updated = logService.getLogsForUser(testUser.getUserId()).stream()
                .filter(dl -> dl.getLogId() == log.getLogId())
                .findFirst().orElseThrow();
        assertEquals(71.5, updated.getWeightKg());

        // Delete
        logService.deleteLog(log.getLogId());
        List<DailyLog> after = logService.getLogsForUser(testUser.getUserId());
        assertTrue(after.stream().noneMatch(dl -> dl.getLogId() == log.getLogId()));
    }

    @AfterAll
    void cleanup() throws SQLException {
        // remove the test user
        userService.delete(testUser.getUserId());
    }
}
