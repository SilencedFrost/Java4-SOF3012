<c:if test="${empty index}">
    <c:set var="index" value="" />
</c:if>

<c:if test="${empty index}">
    <c:set var="index" value="" />
</c:if>

<c:set var="structureKey" value="${'fieldStructure' += index}" />

<c:set var="resolvedFieldStructure" value="${requestScope[structureKey]}" />

<form id="${structureKey}" method="post" action="" novalidate>
    <input type="hidden" id="targetUrl" name="targetUrl" value="${targetUrl}"/>
    <input type="hidden" id="csrfToken" name="csrfToken" value="${csrfToken}"/>
    <c:forEach var="field" items="${resolvedFieldStructure}">
        <c:choose>
            <c:when test="${field.fieldType == 'text' || field.fieldType == 'email' || field.fieldType == 'password'}">
                <div class="mb-3">
                    <label for="${field.propertyKey}" class="form-label">${field.label}</label>
                    <input type="${field.fieldType}" class="form-control shadow-sm"
                           id="${field.propertyKey}" name="${field.propertyKey}"
                           placeholder="Enter ${field.label.toLowerCase()}">
                    <div id="${field.errorKey}" class="my-2 form-text ps-2 text-danger d-none form-error"></div>
                </div>
            </c:when>

            <c:when test="${field.fieldType == 'combobox'}">
                <div class="mb-3">
                    <label for="${field.propertyKey}" class="form-label">${field.label}</label>
                    <select class="form-select shadow-sm" id="${field.propertyKey}" name="${field.propertyKey}">
                        <c:forEach var="option" items="${field.selectionData}" varStatus="status">
                            <option value="${option}" ${(empty requestScope[field.propertyKey] && status.first) || requestScope[field.propertyKey] == option ? 'selected' : ''}>
                                ${option}
                            </option>
                        </c:forEach>
                    </select>
                    <div id="${field.errorKey}" class="my-2 form-text ps-2 text-danger d-none form-error"></div>
                </div>
            </c:when>

            <c:when test="${field.fieldType == 'radio'}">
                <div class="mb-3">
                    <label class="form-label">${field.label}</label>
                    <c:forEach var="option" items="${field.selectionData}" varStatus="status">
                        <div class="form-check">
                            <input class="form-check-input shadow-sm" type="radio" 
                                id="${field.propertyKey}_${status.index}" 
                                name="${field.propertyKey}"
                                ${requestScope[field.propertyKey] == option ? 'checked' : ''}>
                            <label class="form-check-label" for="${field.propertyKey}_${status.index}">
                                ${option}
                            </label>
                        </div>
                    </c:forEach>
                    <div id="${field.errorKey}" class="my-2 form-text ps-2 text-danger d-none form-error"></div>
                </div>
            </c:when>

            <c:when test="${field.fieldType == 'textarea'}">
                <div class="mb-3">
                    <label for="${field.propertyKey}" class="form-label">${field.label}</label>
                    <textarea class="form-control shadow-sm"
                            id="${field.propertyKey}" name="${field.propertyKey}"
                            placeholder="Enter ${field.label.toLowerCase()}">${requestScope[field.propertyKey]}</textarea>
                    <div id="${field.errorKey}" class="my-2 form-text ps-2 text-danger d-none form-error"></div>
                </div>
            </c:when>

            <c:when test="${field.fieldType == 'link'}">
                <div class="mb-3">
                    <a class="w-100 small" href="${field.propertyKey}">${field.label}</a>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>

    <div id="specialError" class="mb-3 form-text ps-2 text-danger d-none form-error"></div>

    <div class="d-flex flex-wrap gap-2">
        <c:forEach var="button" items="${buttons}">
            <button type="submit" name="${button.propertyKey}" value="${button.propertyKey}" class="btn btn-${button.BSColor}">${button.label}</button>
        </c:forEach>
    </div>
</form>
<script>
    document.getElementById('${structureKey}').addEventListener('submit', async function(e) {
        e.preventDefault();

        const form = e.target;
        const buttons = form.querySelectorAll('button');
        
        // Store original button states and disable them
        const originalStates = [];
        buttons.forEach((btn, index) => {
            originalStates[index] = {
                disabled: btn.disabled,
                innerHTML: btn.innerHTML
            };
            btn.disabled = true;
            if (btn.type === 'submit') {
                btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Processing...';
            }
        });

        try {
            const data = {};
            <c:forEach var="field" items="${resolvedFieldStructure}">
                <c:if test="${field.fieldType != 'link'}">
                    data["${field.propertyKey}"] = form.${field.propertyKey}.value;
                </c:if>
            </c:forEach>
            data["csrfToken"] = form.csrfToken.value;

            const response = await fetch(form.getAttribute('action'), {
                method: form.method,
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(data)
            });

            const contentType = response.headers.get('content-type');

            if (contentType && contentType.includes('application/json')) {
                const result = await response.json();
                switch (response.status) {
                    case 200:
                        if (result.redirect) {
                            window.location.href = result.redirect;
                        } else {
                            console.log("Logged in, but no redirect specified.");
                        }
                        break;
                    case 400:
                        clearAllErrors();
                        for (const [field, message] of Object.entries(result.errors)) {
                            showFieldError(field, message);
                        }
                        if (result.csrfToken) {
                            form.csrfToken.value = result.csrfToken;
                        }
                        break;
                    case 401:
                        process401(result, form);
                        if (result.csrfToken) {
                            form.csrfToken.value = result.csrfToken;
                        }
                        break;
                    case 403:
                        alert(result.forbiddenError);
                        window.location.reload();
                        break;
                }

            } else {
                console.error('Server returned non-JSON response');
                const textResponse = await response.text();
                console.error('Response body:', textResponse);
            }

        } catch (error) {
            console.error('Network error:', error);
            alert('Network error occurred. Please try again.');
        } finally {
            // Always restore button states, even if there's an error
            buttons.forEach((btn, index) => {
                btn.disabled = originalStates[index].disabled;
                btn.innerHTML = originalStates[index].innerHTML;
            });
        }
    });

    <%@ include file="fieldErrorJs.jsp" %>
</script>