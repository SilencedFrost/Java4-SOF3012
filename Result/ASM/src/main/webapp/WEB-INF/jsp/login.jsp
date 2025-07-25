<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="container-fluid p-0">
        <div class="d-flex justify-content-center align-items-center vh-100">
            <div class="card p-3 shadow-sm" style="width: 300px;">
                <h3 class="text-center">Login</h3>
                <form action="" method="post">
                    <input type="hidden" name="csrfToken" value="${csrfToken}" />
                    <div class="mb-3">
                        <label for="idOrEmail" class="form-label">User ID or Email</label>
                        <input type="text" class="form-control shadow-sm" id="idOrEmail" name="idOrEmail" value="${idOrEmail}" placeholder="Enter user id or email">
                        <div id="idOrEmailError" class="form-text ps-2 text-danger ${empty requestScope['idOrEmailError'] ? 'd-none' : ''}">
                            ${idOrEmailError}
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control shadow-sm" id="password" name="password" placeholder="Enter password">
                        <div id="passwordError" class="form-text ps-2 text-danger ${empty requestScope['passwordError'] ? 'd-none' : ''}">
                            ${passwordError}
                        </div>
                    </div>

                    <div id="loginError" class="form-text ps-2 text-danger ${empty requestScope['loginError'] ? 'd-none' : ''}">
                        ${loginError}
                        <br>
                    </div>
                    <button type="submit" name="action" value="Login" class="btn btn-primary">Login</button>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>