package service;

import dao.GoalDao;
import dao.impl.GoalDaoImpl;
import model.Goal;

import java.sql.SQLException;
import java.util.List;

public class GoalService {
    private final GoalDao goalDao = new GoalDaoImpl();

    public void addGoal(Goal goal) throws SQLException {
        goalDao.create(goal);
    }

    public List<Goal> getGoalsForUser(int userId) throws SQLException {
        return goalDao.findByUser(userId);
    }

    public void deleteGoal(int goalId) throws SQLException {
        goalDao.delete(goalId);
    }
}