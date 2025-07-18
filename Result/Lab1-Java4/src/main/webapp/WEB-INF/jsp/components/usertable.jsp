<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Password</th>
            <th>Email</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.fullname}"/></td>
                <td><c:out value="${user.passwordHash}"/></td>
                <td><c:out value="${user.email}"/></td>
            </tr>
        </c:forEach>
    </tbody>
</table>