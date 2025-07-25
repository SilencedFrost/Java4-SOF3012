<form method="post" action="" novalidate>
    <input type="hidden" name="csrfToken" value="${csrfToken}" />
    <c:forEach var="field" items="${fieldStructure}">
        <c:choose>
            <c:when test="${field.fieldType == 'text' || field.fieldType == 'email' || field.fieldType == 'password'}">
                <div class="mb-3">
                    <label for="${field.propertyKey}" class="form-label">${field.label}</label>
                    <input type="${field.fieldType}" class="form-control shadow-sm"
                           id="${field.propertyKey}" name="${field.propertyKey}"
                           value="${requestScope[field.propertyKey]}"
                           placeholder="Enter ${field.label.toLowerCase()}">
                    <div id="${field.errorKey}" class="form-text ps-2 text-danger ${empty requestScope[field.errorKey] ? 'd-none' : ''}">
                        ${requestScope[field.errorKey]}
                    </div>
                </div>
            </c:when>
            <c:when test="${field.fieldType == 'combobox'}">
                <div class="mb-3">
                    <label for="${field.propertyKey}" class="form-label">${field.label}</label>
                    <select class="form-select shadow-sm" id="${field.propertyKey}" name="${field.propertyKey}">
                        <c:forEach var="option" items="${field.CBoxData}" varStatus="status">
                            <option value="${option}" ${(empty requestScope[field.propertyKey] && status.first) || requestScope[field.propertyKey] == option ? 'selected' : ''}>
                                ${option}
                            </option>
                        </c:forEach>
                    </select>
                    <div id="${field.errorKey}" class="form-text ps-2 text-danger ${empty requestScope[field.errorKey] ? 'd-none' : ''}">
                        ${requestScope[field.errorKey]}
                    </div>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>

    <div class="d-flex flex-wrap gap-2">
        <c:forEach var="button" items="${buttons}">
            <button type="submit" name="${button.propertyKey}" value="${button.propertyKey}" class="btn btn-${button.BSColor}">${button.label}</button>
        </c:forEach>
    </div>
</form>