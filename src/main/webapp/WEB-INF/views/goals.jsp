<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/header.jspf" %>

<section class="container">
  <h1>Your Goals</h1>
  <table>
    <tr><th>Target Weight</th><th>Target Calories</th><th>Period</th><th>Action</th></tr>
    <c:forEach var="g" items="${goals}">
      <tr>
        <td>${g.targetWeightKg} kg</td>
        <td>${g.targetCalories}</td>
        <td>${g.startDate} to ${g.endDate}</td>
        <td>
          <form action="${pageContext.request.contextPath}/app/goals" method="post">
            <input type="hidden" name="_method" value="delete"/>
            <input type="hidden" name="id" value="${g.goalId}"/>
            <button type="submit" class="delete-btn">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>

  <h2>Add New Goal</h2>
  <form action="${pageContext.request.contextPath}/app/goals" method="post">
    <label>Target Weight (kg)</label>
    <input name="targetWeightKg" type="number" step="0.1" required/>
    <label>Target Calories</label>
    <input name="targetCalories" type="number" required/>
    <label>Start Date</label>
    <input name="startDate" type="date" required/>
    <label>End Date</label>
    <input name="endDate" type="date" required/>
    <button class="primary" type="submit">Add Goal</button>
  </form>
</section>

<%@ include file="/WEB-INF/views/footer.jspf" %>
