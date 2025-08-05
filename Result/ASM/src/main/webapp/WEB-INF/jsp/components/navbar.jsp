<nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container-fluid">
        <!-- Brand/Logo - always visible -->
        <a class="navbar-brand text-danger" href="/home">
            <h3 class="mb-0">Online Entertainment</h3>
        </a>

        <!-- Toggler/collapsing button - floated to the right -->
        <button class="navbar-toggler ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar links -->
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav me-auto">
                <li class="nav-item d-flex align-items-center">
                    <div class="d-flex align-items-center p-2">
                        <a class="nav-link d-inline p-0" href="/user/favourite">My Favourites</a>
                    </div>
                </li>

                <div class="nav-item d-flex align-items-center p-2">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle d-flex align-items-center d-none d-md-flex" href="#"
                            id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            My Account
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="userDropdown" style="width: max-content; min-width: 200px;">
                            <%@ include file="navbarItems.jsp" %>
                        </ul>

                        <a class="nav-link d-flex align-items-center d-md-none" href="#userOptions"
                            data-bs-toggle="collapse" aria-expanded="false" aria-controls="userOptions">
                            My Account
                        </a>
                    </li>
                </div>
            </ul>

            <div class="d-flex align-items-center px-2">
                <div class="collapse d-lg-none" id="userOptions">
                    <ul class="navbar-nav">
                        <%@ include file="navbarItems.jsp" %>
                    </ul>
                </div>
            </div>

            <ul class="navbar-nav ms-auto">
                <c:if test="${sessionScope.user.roleName == 'Admin' || sessionScope.user.roleName == 'Employee' || sessionScope.user.roleName == 'Manager'}">
                    <li class="nav-item d-flex align-items-center">
                        <div class="d-flex align-items-center p-2">
                            <a class="nav-link d-inline p-0" href="/admin/videos">Admin</a>
                        </div>
                    </li>
                </c:if>
                <li class="nav-item d-flex align-items-center">
                    <div class="d-flex align-items-center p-2">
                        Visits: ${applicationScope.visits}
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>