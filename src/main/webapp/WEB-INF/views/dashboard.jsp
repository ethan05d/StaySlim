<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jspf" %>

<section class="container">
  <h1>Your Dashboard</h1>
  <div id="stats">
    <p><strong>Username:</strong> ${sessionScope.user.username}</p>
    <p><strong>Height:</strong> ${sessionScope.user.heightCm} cm</p>

    <c:choose>
      <c:when test="${empty logs}">
        <p><strong>Last Weight:</strong> —</p>
        <p><strong>BMI:</strong> —</p>
      </c:when>
      <c:otherwise>
        <p>
          <strong>Last Weight:</strong>
            ${lastWeightKg} kg on ${lastWeightDate}
        </p>
        <p>
          <strong>BMI:</strong>
            ${bmiFormatted}
        </p>
      </c:otherwise>
    </c:choose>

    <p><strong>Current Streak:</strong> ${leaderboard.currentStreak} days</p>
  </div>

  <div id="check-in-container">
    <h2>Daily Check-In</h2>
    <c:choose>
      <c:when test="${checkedInToday}">
        <div id="check-in-completed">
          ✅ <strong>Checked in today!</strong>
          <p>Keep up the good work!</p>
        </div>
      </c:when>
      <c:otherwise>
        <form action="${pageContext.request.contextPath}/app/checkin" method="post">
          <button class="primary" type="submit">
            💪 I worked out today!
          </button>
        </form>
      </c:otherwise>
    </c:choose>
  </div>

  <hr/>
  <h2>Daily Log</h2>
  <form action="${pageContext.request.contextPath}/app/logs" method="post">
    <label>Date</label>
    <input type="date" name="date" required/>
    <label>Weight (kg)</label>
    <input name="weightKg" type="number" step="0.1" required/>
    <label>Calories Intake</label>
    <input name="caloriesIntake" type="number" required/>
    <button class="primary" type="submit">Save Entry</button>
  </form>

  <hr/>
  <h2>Weight History</h2>
  <jsp:include page="logs.jsp"/>
</section>

<%@ include file="/WEB-INF/views/footer.jspf" %>
