<nav class="navbar navbar-expand-sm navbar-light bg-light">
    <div class="container-fluid">
        <!-- Toggler/collapsing button -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar links -->
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item d-flex align-items-center">
                    <div class="d-flex align-items-center p-2">
                        <a class="nav-link d-inline p-0 text-danger" href="/Home"><h3>Online Entertainment</h3></a>
                    </div>
                </li>
                <li class="nav-item d-flex align-items-center">
                    <div class="d-flex align-items-center p-2">
                        <a class="nav-link d-inline p-0" href="userfavourite">My Favourites</a>
                    </div>
                </li>

                <div class="nav-item d-flex align-items-center p-2">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle d-flex align-items-center d-none d-sm-flex" href="#"
                            id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            My Account
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="userDropdown">
                            <%@ include file="navbarDropdown.jsp" %>
                        </ul>

                        <a class="nav-link d-flex align-items-center d-sm-none" href="#userOptions"
                            data-bs-toggle="collapse" aria-expanded="false" aria-controls="userOptions">
                            My Account
                        </a>
                    </li>
                </div>
            </ul>

            <div class="d-flex align-items-center px-2">
                <div class="collapse d-lg-none" id="userOptions">
                    <ul class="navbar-nav">
                        <%@ include file="navbarDropdown.jsp" %>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>