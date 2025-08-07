<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Report Menu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="container-fluid p-0">
    <%@ include file="components/navbarAdmin.jsp" %>
    <div class="container-fluid mt-3">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="favourites-tab" data-bs-toggle="tab" data-bs-target="#favourites" type="button" role="tab" aria-controls="favourites" aria-selected="true">Favourites</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="user-favourites-tab" data-bs-toggle="tab" data-bs-target="#user-favourites" type="button" role="tab" aria-controls="user-favourites" aria-selected="false">User Favourites</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="shares-tab" data-bs-toggle="tab" data-bs-target="#shares" type="button" role="tab" aria-controls="shares" aria-selected="false">Shares</button>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="favourites" role="tabpanel" aria-labelledby="favourites-tab">
                <div class="p-3">
                    <%@ include file="components/table.jsp" %>
                </div>
            </div>
            <div class="tab-pane fade" id="user-favourites" role="tabpanel" aria-labelledby="user-favourites-tab">
                <div class="p-3">
                    <%@ include file="components/searchbar.jsp" %>
                    <hr class="my-2">
                    <c:set var="index" value="1" />
                    <%@ include file="components/table.jsp" %>
                </div>
            </div>
            <div class="tab-pane fade" id="shares" role="tabpanel" aria-labelledby="shares-tab">
                <div class="p-3">
                    <%@ include file="components/searchbar.jsp" %>
                    <hr class="my-2">
                    <c:set var="index" value="2" />
                    <%@ include file="components/table.jsp" %>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>