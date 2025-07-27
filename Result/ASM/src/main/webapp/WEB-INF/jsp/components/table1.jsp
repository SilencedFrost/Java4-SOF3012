<div class="table-responsive-sm">
    <table class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <c:forEach var="field" items="${tableFields1}">
                    <th><c:out value="${field.label}"/></th>
                </c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entity" items="${dataList1}">
                <tr>
                    <c:forEach var="field" items="${tableFields1}" varStatus="status">
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
</div>