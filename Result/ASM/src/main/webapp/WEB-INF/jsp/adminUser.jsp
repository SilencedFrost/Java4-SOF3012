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
    <%@ include file="components/navbarAdmin.jsp" %>
    <div class="container-fluid">
        <div class="row g-2 p-2">
            <div class="col-12 col-lg-4">
                <div class="card card-border p-2 shadow-sm">
                    <%@ include file="components/form.jsp" %>
                </div>
            </div>
            <div class="col-12 col-lg-8">
                <div class="container-fluid px-0 pb-2">
                    <%@ include file="components/searchbar.jsp" %>
                </div>
                <!-- text-break for password col -->
                <c:set var="breakIndex" value="1" />
                <%@ include file="components/table.jsp" %>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>