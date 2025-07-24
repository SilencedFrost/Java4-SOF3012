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
    <h2 class="text-center my-4">User Favourite</h2>
    <div class="container-fluid px-3">
        <%@ include file="components/searchbar.jsp" %>

        <br>

        <div class="row p-0 g-2">
            <c:forEach var="userMap" items="${dataList}">
                <div class="col-sm-4">
                    <div class="card shadow-sm p-3 h-100">
                        <h5>${userMap.fullName}</h5>
                        <b>các video đã yêu thích</b>
                        <ul class="list-unstyled">
                            <c:forEach var="like" items="${userMap.videoNameList}">
                                <li>- ${like}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>