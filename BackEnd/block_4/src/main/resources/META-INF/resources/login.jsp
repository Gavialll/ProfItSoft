<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <title>Authorization</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>

<form class="wrapper" action="/login" method="POST" modelAttribute="loginForm">
    <h2 class="Log_in">Welcome</h2>
    <p>${message}</p>
    <input type="email" name="email" placeholder="Login">
    <input type="password" name="password" placeholder="Password">
    <input type="submit" value="Login">
</form>

</body>
</html>