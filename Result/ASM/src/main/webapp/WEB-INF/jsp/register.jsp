<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Register</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="container-fluid p-0">
        <div class="d-flex justify-content-center align-items-center vh-100">
            <a class="text-secondary text-decoration-none position-absolute top-0 start-0 m-2" href="/home"><h3>Home</h3></a>
            <div class="card p-3 shadow-sm" style="width: 300px;">
                <h3 class="text-center">Register</h3>
                <hr>
                <%@ include file="components/form.jsp" %>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>