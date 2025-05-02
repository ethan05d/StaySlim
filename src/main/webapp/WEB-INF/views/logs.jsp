<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${empty logs}">
    <p>No weight entries yet.</p>
</c:if>

<c:if test="${not empty logs}">
    <table class="weight-table">
        <thead>
        <tr>
            <th>Date</th>
            <th>Weight (kg)</th>
            <th>Calories</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="log" items="${logs}">
            <tr>
                <td>${log.logDate}</td>
                <td>${log.weightKg}</td>
                <td>${log.caloriesIntake}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/app/logs/edit?id=${log.logId}">
                        <button class="primary" style="padding:4px 8px;font-size:13px;">Edit</button>
                    </a>
                </td>

                <td>
                 <form action="${pageContext.request.contextPath}/app/logs/delete" method="post"
                       onsubmit="return confirm('Delete this entry?');" style="display:inline;">
                   <input type="hidden" name="id" value="${log.logId}" />
                   <button type="submit" class="delete-btn">Delete</button>
                 </form>
               </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
