<%-- 
    Document   : header_unLoggedIn
    Created on : Jul 20, 2025, 12:27:23 PM
    Author     : KHAI TOAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="menu pt-2" id="navbarNav">
    <form class="d-flex me-3 pt-2 pb-2 bg-white" action="search" method="GET">
        <input type="text" name="searchQuery"  
               class="ps-3 pe-2 bg-white border-none outline-none"
               placeholder="Search ..." aria-label="Search">

        <button type="submit" class="search-submit pe-3 bg-white">
            <i class="fas fa-search fa-2x"></i>
        </button>
    </form>
    <a class="btn me-2 login" href="login">LOGIN</a>
    <a class="btn signup " href="register">SIGN UP</a>
</div>