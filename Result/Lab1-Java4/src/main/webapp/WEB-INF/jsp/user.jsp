<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User Menu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container-fluid p-0">
    <%@ include file="components/navbar.jsp" %>
    <h2 class="text-center my-4">User Menu</h1>
    <div class="container-fluid">
        <div class="row g-2 p-2">
            <div class="col-md-4">
                <%@ include file="components/userform.jsp" %>
            </div>
            <div class="col-md-8">
                <%@ include file="components/table.jsp" %>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>