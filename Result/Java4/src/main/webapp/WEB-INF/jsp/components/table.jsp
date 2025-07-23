<table class="table table-striped table-bordered table-hover">
    <thead>
        <tr>
            <c:forEach var="field" items="${tableFields}">
                <th><c:out value="${field.label}"/></th>
            </c:forEach>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="entity" items="${dataList}">
            <tr>
                <c:forEach var="field" items="${tableFields}" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index == breakIndex}">
                            <td class="text-break">
                                <c:out value="${entity[field.propertyKey]}"/>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <c:out value="${entity[field.propertyKey]}"/>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </tr>
        </c:forEach>
    </tbody>
</table>