<%-- 
    Document   : header_loggedIn
    Created on : Jul 20, 2025, 12:26:59 PM
    Author     : KHAI TOAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="menu justify-content-end" id="navbarNav">
      <form class="d-flex me-3 pt-2 pb-2 bg-white" action="${pageContext.request.contextPath}/search" method="GET">
        <input class="ps-3 pe-2 bg-white border-none outline-none"
               name="searchQuery" placeholder="Search manga..."
               aria-label="Search">

        <button class="search-submit pe-3 bg-white" type="submit">
            <i class="fas fa-search fa-2x"></i>
        </button>
    </form>

    <div class="user-profile-layout">
        <div class="align-items-center justify-content-center">
            <i id="user-profile-icon" class="user-profile-icon fas fa-user-circle"></i>
        </div>

        <div id="user-profile" class="user-profile mt-2 mb-2 align-items-center justify-content-center">
            <a href="saveSeries"
               class="user-library page-hover w-100 p-2 d-flex align-items-center justify-content-center">Library</a>
            <a href="viewProfile?userId=${sessionScope.loggedInUser.userId}"
               class="history-reading page-hover w-100 p-2 d-flex align-items-center justify-content-center">
                View Profile
            </a>
            <a href="logout" class="log-out page-hover w-100 p-2 d-flex align-items-center justify-content-center">Log
                Out</a>
        </div>
    </div>
</div>
