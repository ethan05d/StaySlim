<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/header.jspf" %>

<section class="container">
  <h1>Admin Panel</h1>

  <h2>All Users</h2>
  <table>
    <tr><th>ID</th><th>Username</th><th>Email</th><th>Action</th></tr>
    <c:forEach var="u" items="${allUsers}">
      <tr>
        <td>${u.userId}</td>
        <td>${u.username}</td>
        <td>${u.email}</td>
        <td>
          <form action="${pageContext.request.contextPath}/app/admin" method="post">
            <input type="hidden" name="_method" value="delete"/>
            <input type="hidden" name="id" value="${u.userId}"/>
            <button type="submit" class="delete-btn">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>

  <h2>Reset All Data</h2>
  <form action="${pageContext.request.contextPath}/app/admin" method="post">
    <input type="hidden" name="_method" value="reset"/>
    <button type="submit" style="background:var(--danger);color:white;">Reset Everything</button>
  </form>
</section>

<%@ include file="/WEB-INF/views/footer.jspf" %>
