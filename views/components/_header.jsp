<header>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg w-100 sticky-top">
        <div class="navbar-layout w-100 pt-2 pb-2 ps-4 pe-4">
            <div class="mobile-header d-flex align-items-center justify-content-between">
                <a class="navbar-brand fw-bold text-white" href="#"><i class="bi bi-book"></i>
                    <strong>JoyLeeBook</strong>
                </a>

                <label id="menu-icon" class="menu-icon text-white fa-2x">&#9776;</label>
            </div>

            <!-- Authentication form -->
            <% if (isLoggedIn) { %>
            <%@include file="_loggedIn.jsp" %>%>
            <% } else { %>
            <%@include file="_unLoggedIn.jsp" %>
            <% } %>
        </div>
    </nav>
</header>