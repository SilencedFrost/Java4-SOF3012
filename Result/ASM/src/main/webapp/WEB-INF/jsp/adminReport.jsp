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
                    <form action="" method="GET" class="d-flex align-items-center gap-2">
                        <label for="videoId" class="form-label mb-0 me-2 fw-bold flex-shrink-0">Video Title:</label>
                        <select name="videoId" id="videoId" class="form-select flex-grow-1">
                            <option value="" ${param.videoId == '' || param.videoId == null ? 'selected' : ''}>Select all</option>
                            <c:forEach var="cboEntry" items="${cboList1}" varStatus="entityStatus">
                                <option value="${cboEntry.videoId}" ${param.videoId == cboEntry.videoId ? 'selected' : ''}>${cboEntry.title}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="tab" id="activeTab" value="${param.tab != null ? param.tab : 'user-favourites'}">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>
                    <hr class="my-2">
                    <c:set var="index" value="1" />
                    <%@ include file="components/table.jsp" %>
                </div>
            </div>
            <div class="tab-pane fade" id="shares" role="tabpanel" aria-labelledby="shares-tab">
                <div class="p-3">
                    <form action="" method="GET" class="d-flex align-items-center gap-2">
                        <label for="videoId1" class="form-label mb-0 me-2 fw-bold flex-shrink-0">Video Title:</label>
                        <select name="videoId1" id="videoId1" class="form-select flex-grow-1">
                            <option value="" ${param.videoId1 == '' || param.videoId1 == null ? 'selected' : ''}>Select all</option>
                            <c:forEach var="cboEntry" items="${cboList2}" varStatus="entityStatus">
                                <option value="${cboEntry.videoId}" ${param.videoId1 == cboEntry.videoId ? 'selected' : ''}>${cboEntry.title}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="tab" id="activeTab" value="${param.tab != null ? param.tab : 'user-favourites'}">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>
                    <hr class="my-2">
                    <c:set var="index" value="2" />
                    <%@ include file="components/table.jsp" %>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Tab persistence functionality
        document.addEventListener('DOMContentLoaded', function() {
            // Restore active tab from URL parameter
            const urlParams = new URLSearchParams(window.location.search);
            const activeTab = urlParams.get('tab');

            if (activeTab) {
                // First, remove active classes from all tabs and content
                document.querySelectorAll('.nav-link').forEach(link => {
                    link.classList.remove('active');
                    link.setAttribute('aria-selected', 'false');
                });
                document.querySelectorAll('.tab-pane').forEach(pane => {
                    pane.classList.remove('show', 'active');
                });

                // Then activate the correct tab
                const tabButton = document.querySelector('button[aria-controls="' + activeTab + '"]');
                const tabContent = document.getElementById(activeTab);

                if (tabButton && tabContent) {
                    tabButton.classList.add('active');
                    tabButton.setAttribute('aria-selected', 'true');
                    tabContent.classList.add('show', 'active');
                }
            }

            // Update hidden form fields when tabs change
            document.querySelectorAll('button[data-bs-toggle="tab"]').forEach(tab => {
                tab.addEventListener('shown.bs.tab', function (e) {
                    const tabId = e.target.getAttribute('aria-controls');

                    // Update all hidden tab fields
                    document.querySelectorAll('input[name="tab"]').forEach(input => {
                        input.value = tabId;
                    });
                });
            });
        });
    </script>
</body>
</html>