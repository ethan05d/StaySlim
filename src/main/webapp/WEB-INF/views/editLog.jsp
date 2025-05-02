<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jspf" %>

<section class="container">
  <h1>Edit Entry</h1>
  <form action="${pageContext.request.contextPath}/app/logs/edit" method="post">
    <input type="hidden" name="id" value="${log.logId}" />

    <label for="date">Date</label>
    <input id="date" name="date" type="date"
           value="${log.logDate}" required />

    <label for="weightKg">Weight (kg)</label>
    <input id="weightKg" name="weightKg" type="number" step="0.1"
           value="${log.weightKg}" required />

    <label for="caloriesIntake">Calories Intake</label>
    <input id="caloriesIntake" name="caloriesIntake" type="number"
           value="${log.caloriesIntake}" required />

    <button class="primary" type="submit">Save Changes</button>
    <a href="${pageContext.request.contextPath}/app/dashboard">
      <button type="button">Cancel</button>
    </a>
  </form>
</section>

<%@ include file="/WEB-INF/views/footer.jspf" %>
