package service;

import model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    private UserService userService;

    @BeforeAll
    void setup() {
        userService = new UserService();
    }

    @Test
    void testRegisterAndLoginAndDelete() throws SQLException {
        // use a unique username to avoid collisions
        String uname = "testuser" + System.currentTimeMillis();
        String email = uname + "@example.com";
        String pwd = "pass123";
        double height = 175.0;

        User u = new User();
        u.setUsername(uname);
        u.setEmail(email);
        u.setPasswordHash(pwd);
        u.setHeightCm(height);

        // Register
        userService.register(u);
        assertTrue(u.getUserId() > 0, "UserID should be set after registration");

        // Login should succeed
        User logged = userService.login(uname, pwd);
        assertNotNull(logged, "Login returned null");
        assertEquals(uname, logged.getUsername());

        // Duplicate username should throw
        User dup = new User();
        dup.setUsername(uname);
        dup.setEmail("other@example.com");
        dup.setPasswordHash("x");
        dup.setHeightCm(160);
        assertThrows(SQLException.class, () -> userService.register(dup));

        // Cleanup
        userService.delete(u.getUserId());
        assertNull(userService.login(uname, pwd), "User should no longer exist");
    }
}
