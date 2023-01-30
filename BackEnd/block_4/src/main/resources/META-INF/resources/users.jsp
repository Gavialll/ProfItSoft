<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link rel="stylesheet" href="/css/users.css">
</head>
<body>
<%@ include file="templates/header.jsp" %>

<div class="wrapper">
    <c:forEach items="${users}" var="user">
        <div class="user">
            <span>${user.id}</span>
            <span>${user.name}</span>
            <span>${user.email}</span>
        </div>
    </c:forEach>
</div>
</body>
</html>