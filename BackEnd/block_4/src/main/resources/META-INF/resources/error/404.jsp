<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<html lang="en">
<head>
    <title>Error</title>
    <link rel="stylesheet" href="/css/error.css">
</head>
<body>
<div class="wrapper">
    <h2>Status: 404 </h2>
    <h3>Message: Page not found</h3>
    <button onclick="window.location.href = '/dashboard'">Go home</button>
</div>

</body>
</html>