<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/header.jspf" %>

<section class="container">
  <h1>Leaderboard</h1>

  <c:if test="${empty leaderEntries}">
    <p>No entries yet.</p>
  </c:if>

  <c:if test="${not empty leaderEntries}">
    <table style="width:100%; border-collapse:collapse;">
      <thead>
      <tr style="border-bottom:2px solid var(--border); text-align:left;">
        <th>#</th>
        <th>User</th>
        <th>Current Streak</th>
        <th>Max Streak</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="entry" items="${leaderEntries}">
        <tr
                <c:if test="${entry.currentUser}">
                  style="background-color: var(--accent-light); font-weight:600;"
                </c:if>
        >
          <td>${entry.rank}</td>
          <td>${fn:escapeXml(entry.username)}</td>
          <td>${entry.currentStreak}</td>
          <td>${entry.maxStreak}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
</section>

<%@ include file="/WEB-INF/views/footer.jspf" %>