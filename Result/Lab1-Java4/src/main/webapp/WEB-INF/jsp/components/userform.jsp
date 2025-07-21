<form method="post" action="/user" novalidate>
    <div class="mb-3">
        <label for="user" class="form-label">User ID</label>
        <input type="text" class="form-control shadow-sm" id="user" name="user"
            placeholder="Enter your user ID" value="${user}" required>
        <div id="userError" class="form-text ps-2 text-danger ${empty userError ? 'd-none' : ''}">
            ${userError}
        </div>
    </div>

    <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control shadow-sm" id="password" name="password"
            value="${password}" placeholder="Enter a password" required>
        <div id="passwordError" class="form-text ps-2 text-danger ${empty passwordError ? 'd-none' : ''}">
            ${passwordError}
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
        <label for="role" class="form-label">Role</label>
        <input type="text" class="form-control shadow-sm" id="role" name="role"
            value="${role}" placeholder="Enter role">
        <div id="roleError" class="form-text ps-2 text-danger ${empty roleError ? 'd-none' : ''}">
            ${roleError}
        </div>
    </div>

    <div class="d-flex flex-wrap gap-2">
        <button type="submit" name="action" value="create" class="btn btn-primary">Create</button>
        <button type="submit" name="action" value="update" class="btn btn-warning">Update</button>
        <button type="submit" name="action" value="delete" class="btn btn-danger">Delete</button>
        <button type="submit" name="action" value="reset" class="btn btn-secondary">Reset</button>
    </div>
</form>