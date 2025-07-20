<%-- 
    Document   : header_loggedIn
    Created on : Jul 20, 2025, 12:26:59 PM
    Author     : KHAI TOAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- AJAX Icon -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <!-- Select2 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <!-- Select2 JS -->
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <!-- Style CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <title>title</title>
    </head>

    <body>
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

                    <div class="menu justify-content-end" id="navbarNav">
                        <form class="d-flex me-3 pt-2 pb-2 bg-white">
                            <input class="ps-3 pe-2 bg-white border-none outline-none" placeholder="Search manga..."
                                   aria-label="Search">

                            <button class="search-submit pe-3 bg-white">
                                <i class="fas fa-search fa-2x"></i>
                            </button>
                        </form>

                        <!-- Authentication form -->
                        <div class="user-profile-layout position-relative">
                            <div class="align-items-center justify-content-center">
                                <i id="user-profile-icon" class="user-profile-icon fas fa-user-circle" style="cursor: pointer;"></i>
                            </div>
                            <div id="user-profile" class="user-profile mt-2 mb-2">
                                <a href="saveSeries" class="user-library page-hover w-100 p-2 d-flex align-items-center justify-content-center">
                                    User Library
                                </a>
                                <a href="viewHistory" class="history-reading page-hover w-100 p-2 d-flex align-items-center justify-content-center">
                                    History Reading
                                </a>
                                <a href="logout" class="log-out page-hover w-100 p-2 d-flex align-items-center justify-content-center">
                                    Log Out
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
        </header>

        <script src="${pageContext.request.contextPath}/js/index.js"></script>
    </body>

</html>
