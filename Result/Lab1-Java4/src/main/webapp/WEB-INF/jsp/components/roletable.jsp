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