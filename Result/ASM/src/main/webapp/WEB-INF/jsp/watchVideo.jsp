<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${video.title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="container-fluid p-0">
        <%@ include file="components/navbar.jsp" %>
        <div class="container-fluid p-3">
            <div class="row">
                <div class="col-lg-7 col-xl-8">
                    <%@ include file="components/videoMaximized.jsp" %>
                </div>
                <div class="col-lg-5 col-xl-4">
                    <c:forEach var="video" items="${dataList}">
                        <%@ include file="components/videoMinimized.jsp" %>
                    </c:forEach>
                </div>
            </div>
        </div>
        <%@ include file="components/shareModal.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

<style>
    html {
        overflow-y: scroll;
    }
</style>