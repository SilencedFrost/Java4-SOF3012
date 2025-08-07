<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Homepage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        html {
            overflow-y: scroll;
        }
    </style>
</head>

<body class="container-fluid p-0">
    <%@ include file="components/navbar.jsp" %>
    <div class="container-fluid p-3">
        <%@ include file="components/searchbar.jsp" %>
        <hr class="py-2">

        <div class="row g-2 p-0">
            <c:forEach var="video" items="${dataList}">
                <%@ include file="components/videoElement.jsp" %>
            </c:forEach>
        </div>

        <c:forEach var="video" items="${dataList}">
            <%@ include file="components/shareModal.jsp" %>
        </c:forEach>

        <div class="text-center mt-2">
            <div class="btn-group" role="group">
                <button class="btn btn-outline-primary" onclick="goToPage(1)">&laquo;&laquo;</button>
                <button class="btn btn-outline-primary" onclick="goToPage(${param.page != null ? param.page - 1 : 0})">&laquo;</button>
                <button class="btn btn-outline-primary" onclick="goToPage(${param.page != null ? param.page + 1 : 2})">&raquo;</button>
                <button class="btn btn-outline-primary" onclick="goToPage(${requestScope.pageCount})">&raquo;&raquo;</button>
            </div>
        </div>
    </div>

    <script>
    const pageCount = ${requestScope.pageCount};

    function goToPage(page) {
        if (page < 1) page = 1;
        if (page > pageCount) page = pageCount;

        const url = new URL(window.location);
        url.searchParams.set('page', page);
        window.location.href = url.toString();
    }
    </script>
    <script src="/assets/js/shareJs.js" defer></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>