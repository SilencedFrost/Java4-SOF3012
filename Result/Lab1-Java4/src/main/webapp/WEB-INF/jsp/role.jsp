<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Table data pouring</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <!-- Toggler/collapsing button -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Navbar links -->
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item d-flex align-items-center">
                        <div class="d-flex align-items-center p-2">

                            <a class="nav-link d-inline p-0" href="user">User</a>
                        </div>
                    </li>
                    <li class="nav-item d-flex align-items-center">
                        <div class="d-flex align-items-center p-2">
                            <a class="nav-link d-inline p-0" href="role">Role</a>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <h2 class="text-center my-4">Role List</h2>
    <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>RoleID</th>
                    <th>UserID</th>
                    <th>Role</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="role" items="${roleList}">
                    <tr>
                        <td><c:out value="${role.roleId}"/></td>
                        <td><c:out value="${role.userId}"/></td>
                        <td><c:out value="${role.role}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>