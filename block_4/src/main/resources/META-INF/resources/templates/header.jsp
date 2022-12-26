<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav>
    <h2>Hello! ${userName}</h2>
    <button class="button" onclick="window.location.href = '/users'">Users</button>
    <button onclick="window.location.href = '/dashboard'" class="button">Dashboard</button>
    <button class="button" onclick="window.location.href = '/logout'">Logout</button>
</nav>
