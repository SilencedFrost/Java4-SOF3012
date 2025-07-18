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
                <c:forEach var="field" items="${userRow}" varStatus="status">
                <!-- If match breakindex, apply text-break -->
                    <c:choose>
                            <c:when test="${status.index == breakIndex}">
                                <td class="text-break"><c:out value="${field}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><c:out value="${field}"/></td>
                            </c:otherwise>
                        </c:choose>
                </c:forEach>
            </tr>
        </c:forEach>
    </tbody>
</table>