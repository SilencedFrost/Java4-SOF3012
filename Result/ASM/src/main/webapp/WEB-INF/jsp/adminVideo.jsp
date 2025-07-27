<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Video Menu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="container-fluid p-0">
    <%@ include file="components/navbarAdmin.jsp" %>
    <div class="container-fluid mt-3">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="edit-video-tab" data-bs-toggle="tab" data-bs-target="#edit-video" type="button" role="tab" aria-controls="edit-video" aria-selected="true">Edit Video</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="video-list-tab" data-bs-toggle="tab" data-bs-target="#video-list" type="button" role="tab" aria-controls="video-list" aria-selected="false">Video List</button>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="edit-video" role="tabpanel" aria-labelledby="edit-video-tab">
                <div class="p-3">
                    <div class="row">
                        <div class="col-xl-5">
                            <img class="rounded-2 w-100" src="/assets/images/youtube-thumbnail-template-desig.jpg" alt="">
                        </div>
                        <hr class="d-xl-none d-block mt-2">
                        <div class="col-xl-7">
                            <%@ include file="components/form.jsp" %>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="video-list" role="tabpanel" aria-labelledby="video-list-tab">
                <div class="p-3">
                    <h5>Video List</h5>
                    <%@ include file="components/table.jsp" %>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
