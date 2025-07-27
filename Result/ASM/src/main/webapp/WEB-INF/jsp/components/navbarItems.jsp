<c:if test="${empty sessionScope.user}">
    <li class="py-2"><a class="dropdown-item" href="/login">Login</a></li>
    <li class="py-2"><a class="dropdown-item" href="/register">Register</a></li>
</c:if>

<c:if test="${not empty sessionScope.user}">
    <li class="py-2"><span class="dropdown-item-text">Welcome, ${sessionScope.user.fullName}</span></li>
    <li class="py-2"><a class="dropdown-item" href="/logout">Log out</a></li>
    <li class="py-2"><a class="dropdown-item" href="/user/profile">Edit Profile</a></li>
</c:if>