<div class="table-responsive-sm">
    <table class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <c:forEach var="field" items="${requestScope['tableFields'.concat(empty index ? '' : index)]}">
                    <th><c:out value="${field.label}"/></th>
                </c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entity" items="${requestScope['dataList'.concat(empty index ? '' : index)]}">
                <tr>
                    <c:forEach var="field" items="${requestScope['tableFields'.concat(empty index ? '' : index)]}" varStatus="status">
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