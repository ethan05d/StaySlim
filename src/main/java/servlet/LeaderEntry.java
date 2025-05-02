package servlet;

public class LeaderEntry {
    private final int rank;
    private final String username;
    private final int currentStreak;
    private final int maxStreak;
    private final boolean isCurrentUser;

    public LeaderEntry(int rank, String username,
                       int currentStreak, int maxStreak,
                       boolean isCurrentUser) {
        this.rank = rank;
        this.username = username;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
        this.isCurrentUser = isCurrentUser;
    }

    public int getRank() { return rank; }
    public String getUsername() { return username; }
    public int getCurrentStreak() { return currentStreak; }
    public int getMaxStreak() { return maxStreak; }
    public boolean isCurrentUser() { return isCurrentUser; }
}
