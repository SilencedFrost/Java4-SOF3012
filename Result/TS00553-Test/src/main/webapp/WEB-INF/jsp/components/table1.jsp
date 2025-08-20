
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
            <c:forEach var="entity" items="${requestScope['dataList'.concat(empty index ? '' : index)]}" varStatus="entityStatus">
                <tr class="clickable-row" data-row-index="${entityStatus.index}">
                    <c:forEach var="field" items="${requestScope['tableFields'.concat(empty index ? '' : index)]}" varStatus="status">
                        <c:choose>
                            <c:when test="${status.index == breakIndex}">
                                <td class="text-break"
                                    <c:if test="${field.fieldType == 'id'}">data-id="${entity[field.propertyKey]}"</c:if>>
                                    <c:out value="${entity[field.propertyKey]}"/>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td <c:if test="${field.fieldType == 'id'}">data-id="${entity[field.propertyKey]}"</c:if>>
                                    <c:out value="${entity[field.propertyKey]}"/>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <td>
                        <a href="/book/${entity.authorId}">Show books<a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Check if any field has fieldType = "id" to generate the script -->
<c:set var="hasIdField" value="false" />
<c:forEach var="field" items="${requestScope['tableFields'.concat(empty index ? '' : index)]}">
    <c:if test="${field.fieldType == 'id'}">
        <c:set var="hasIdField" value="true" />
    </c:if>
</c:forEach>

<c:if test="${hasIdField}">
<script>
document.addEventListener('DOMContentLoaded', function() {
    // Add click event listeners to all clickable rows
    const rows = document.querySelectorAll('.clickable-row');

    rows.forEach(function(row) {
        row.style.cursor = 'pointer'; // Make it visually clear that rows are clickable

        row.addEventListener('click', function() {
            // Find the cell with data-id attribute in this row
            const idCell = this.querySelector('td[data-id]');

            if (idCell) {
                const id = idCell.getAttribute('data-id');
                if (id && typeof getData === 'function') {
                    getData(id);
                } else if (id) {
                    console.warn('getData function not found, but ID is:', id);
                } else {
                    console.warn('No ID found in clicked row');
                }
            }
        });
    });
});
</script>
</c:if>