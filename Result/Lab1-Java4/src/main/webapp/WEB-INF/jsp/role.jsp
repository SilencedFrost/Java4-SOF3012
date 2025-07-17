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
<body class="container-fluid p-0">
    <%@ include file="navbar.jsp" %>
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