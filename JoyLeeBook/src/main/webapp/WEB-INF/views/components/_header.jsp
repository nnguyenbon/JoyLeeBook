<%-- 
    Document   : _header
    Created on : Jul 20, 2025, 9:40:58 PM
    Author     : KHAI TOAN
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<header>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg w-100 sticky-top">
        <div class="navbar-layout w-100 pt-2 pb-2 ps-4 pe-4">
            <div class="mobile-header d-flex align-items-center justify-content-between">

                <%
                    User user = (User) request.getSession().getAttribute("loggedInUser");
                    String brandLink = (user != null && "admin".equals(user.getRoleName())) ? "adminDashboard" : "home";
                %>

                <a class="navbar-brand fw-bold text-white" href="<%= brandLink%>">
                    <i class="bi bi-book"></i> <strong>JoyLeeBook</strong>
                </a>


                <label id="menu-icon" class="menu-icon text-white fa-2x">&#9776;</label>
            </div>

            <!-- Authentication form -->
            <% if (request.getSession().getAttribute("loggedInUser") != null) { %>
            <jsp:include page="_loggedIn.jsp" />
            <% } else { %>
            <jsp:include page="_unLoggedIn.jsp" />
            <% }%>
        </div>
    </nav>
</header>
