<form method="post" action="/user" novalidate>
    <div class="mb-3">
        <label for="userId" class="form-label">User ID</label>
        <input type="text" class="form-control shadow-sm" id="userId" name="userId"
            placeholder="Enter your user ID" value="${userId}" required>
        <div id="userIdError" class="form-text ps-2 text-danger ${empty userIdError ? 'd-none' : ''}">
            ${userIdError}
        </div>
    </div>

    <div class="mb-3">
        <label for="passwordHash" class="form-label">Password</label>
        <input type="password" class="form-control shadow-sm" id="passwordHash" name="passwordHash"
            value="${passwordHash}" placeholder="Enter a password" required>
        <div id="passwordHashError" class="form-text ps-2 text-danger ${empty passwordHashError ? 'd-none' : ''}">
            ${passwordHashError}
        </div>
    </div>

    <div class="mb-3">
        <label for="fullName" class="form-label">Full Name</label>
        <input type="text" class="form-control shadow-sm" id="fullName" name="fullName"
            value="${fullName}" placeholder="Enter your full name">
        <div id="fullNameError" class="form-text ps-2 text-danger ${empty fullNameError ? 'd-none' : ''}">
            ${fullNameError}
        </div>
    </div>

    <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control shadow-sm" id="email" name="email"
            value="${email}" placeholder="Enter email address">
        <div id="emailError" class="form-text ps-2 text-danger ${empty emailError ? 'd-none' : ''}">
            ${emailError}
        </div>
    </div>

    <div class="mb-3">
        <label for="roleName" class="form-label">Role</label>
        <input type="text" class="form-control shadow-sm" id="roleName" name="roleName"
            value="${roleName}" placeholder="Enter role">
        <div id="roleNameError" class="form-text ps-2 text-danger ${empty roleNameError ? 'd-none' : ''}">
            ${roleNameError}
        </div>
    </div>

    <div class="d-flex flex-wrap gap-2">
        <button type="submit" name="action" value="create" class="btn btn-primary">Create</button>
        <button type="submit" name="action" value="update" class="btn btn-warning">Update</button>
        <button type="submit" name="action" value="delete" class="btn btn-danger">Delete</button>
        <button type="submit" name="action" value="reset" class="btn btn-secondary">Reset</button>
    </div>
</form>