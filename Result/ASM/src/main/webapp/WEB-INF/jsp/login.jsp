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
            <a class="text-secondary text-decoration-none position-absolute top-0 start-0 m-2" href="/home"><h3>Home</h3></a>
            <div class="card p-3 shadow-sm" style="width: 300px;">
                <h3 class="text-center">Login</h3>
                <hr>
                <form id="loginForm" method="POST" action="/login">
                <input type="hidden" name="targetUrl" value="${targetUrl}" />
                    <input type="hidden" id="csrfToken" name="csrfToken" value="${csrfToken}" />
                    <div class="mb-3">
                        <label for="idOrEmail" class="form-label">User ID or Email</label>
                        <input type="text" class="form-control shadow-sm" id="idOrEmail" name="idOrEmail" value="${idOrEmail}" placeholder="Enter user id or email">
                        <div id="idOrEmailError" class="my-2 form-text ps-2 text-danger d-none form-error"></div>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control shadow-sm" id="password" name="password" placeholder="Enter password">
                        <div id="passwordError" class="my-2 form-text ps-2 text-danger d-none form-error"></div>
                        <a class="w-100 small" href="/forgotpassword">Forgot Password?</a>
                    </div>
                    <div id="loginError" class="my-2 form-text ps-2 text-danger d-none form-error"></div>
                    <button type="submit" name="action" value="Login" class="btn btn-primary">Login</button>
                </form>
            </div>
        </div>

        <script>
            document.getElementById('loginForm').addEventListener('submit', async function(e) {
                e.preventDefault();

                const form = e.target;

                const data = {
                    idOrEmail: form.idOrEmail.value,
                    password: form.password.value,
                    csrfToken: form.csrfToken.value,
                    targetUrl: form.targetUrl.value
                };

                const response = await fetch(form.getAttribute('action'), {
                    method: form.method,
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                const contentType = response.headers.get('content-type');

                if (contentType && contentType.includes('application/json')) {
                    const result = await response.json();
                    switch (response.status) {
                        case 200:
                            if (result.redirect) {
                                window.location.href = result.redirect;
                            } else {
                                console.log("Logged in, but no redirect specified.");
                            }
                            break;
                        case 400:
                            clearAllErrors();
                            for (const [field, message] of Object.entries(result.errors)) {
                                showFieldError(field, message);
                            }
                            form.csrfToken.value = result.csrfToken;
                            break;
                        case 401:
                            clearAllErrors();
                            for (const [field, message] of Object.entries(result.errors)) {
                                showFieldError(field, message);
                            }
                            form.csrfToken.value = result.csrfToken;
                            break;
                        case 403:
                            alert(result.forbiddenError);
                            window.location.reload();
                            break;
                    }

                } else {
                    console.error('Server returned non-JSON response');
                    const textResponse = await response.text();
                    console.error('Response body:', textResponse);
                }
            });
            <%@ include file="components/fieldErrorJs.jsp" %>
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
