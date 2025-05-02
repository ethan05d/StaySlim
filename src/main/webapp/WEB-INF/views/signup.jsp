<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/header.jspf" %>

<section class="container">
    <h1>Create Your Account</h1>
    <form action="${pageContext.request.contextPath}/signup" method="post">
        <label>Username</label>
        <input name="username" type="text" required placeholder="username"/>
        <label>Email</label>
        <input name="email" type="email" required placeholder="you@email"/>
        <label>Password</label>
        <input name="password" type="password" required placeholder="Your password"/>
        <label>Height (cm)</label>
        <input name="height" type="number" step="0.1" required placeholder="175"/>
        <button class="primary" type="submit">Create Account</button>
    </form>
    <c:if test="${not empty message}">
        <p style="color:var(--success);font-weight:500">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color:var(--danger);font-weight:500">${error}</p>
    </c:if>
</section>

<%@ include file="/WEB-INF/views/footer.jspf" %>
