<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Homepage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="container-fluid p-0">
    <%@ include file="components/navbar.jsp" %>
    <div class="container-fluid p-3">
        <h1>Edit profile</h1>
        <hr>
        <form action="" method="post" novalidate>
            <div class="mb-3">
                <label for="userId" class="form-label">User ID</label>
                <input type="text" class="form-control shadow-sm" id="userId" name="userId" value="${sessionScope.user.userId}" readonly>
            </div>
            <div class="mb-3">
                <label for="fullName" class="form-label">Full Name</label>
                <input type="text" class="form-control shadow-sm" id="fullName" name="fullName" value="${sessionScope.user.fullName}">
            </div>
            <hr>
            <div class="mb-3">
                <label for="oldPassword" class="form-label">Old Password</label>
                <input type="password" class="form-control shadow-sm" id="oldPassword" name="oldPassword">
                <div class="form-text ps-2 text-danger ${empty formData.oldPasswordError ? 'd-none' : ''}">
                    ${formData.oldPasswordError}
                </div>
            </div>
            <div class="mb-3">
                <label for="newPassword" class="form-label">New Password</label>
                <input type="password" class="form-control shadow-sm" id="newPassword" name="newPassword">
                <div class="form-text ps-2 text-danger ${empty formData.newPasswordError ? 'd-none' : ''}">
                    ${formData.newPasswordError}
                </div>
            </div>
            <div class="mb-3">
                <label for="retypePassword" class="form-label">Re-enter Password</label>
                <input type="password" class="form-control shadow-sm" id="retypePassword" name="retypePassword">
                <div class="form-text ps-2 text-danger ${empty formData.retypePasswordError ? 'd-none' : ''}">
                    ${formData.retypePasswordError}
                </div>
            </div>
            <hr>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control shadow-sm" id="email" name="email" value="${sessionScope.user.email}">
                <div class="form-text ps-2 text-danger ${empty formData.emailError ? 'd-none' : ''}">
                    ${formData.emailError}
                </div>
            </div>
            <button type="submit" name="update" value="update" class="btn btn-primary">Update Info</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>