<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/header.jspf" %>


<section class="container">
    <h1>Welcome Back</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Email</label>
        <input name="email" type="email" required placeholder="you@email"/>
        <label>Password</label>
        <input name="password" type="password" required placeholder="Your password"/>
        <button class="primary" type="submit">Sign In</button>
    </form>
    <c:if test="${not empty error}">
        <p style="color:var(--danger);font-weight:500">${error}</p>
    </c:if>
    <p><a href="${pageContext.request.contextPath}/signup">Create an account</a></p>
</section>

<%@ include file="/WEB-INF/views/footer.jspf" %>
