<table class="table table-striped table-bordered table-hover">
    <thead>
        <tr>
            <c:forEach var="headerName" items="${tableHeaders}">
                <th><c:out value="${headerName}"/></th>
            </c:forEach>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="userRow" items="${tableData}">
            <tr>
                <c:forEach var="field" items="${userRow}">
                    <td><c:out value="${field}"/></td>
                </c:forEach>
            </tr>
        </c:forEach>
    </tbody>
</table>