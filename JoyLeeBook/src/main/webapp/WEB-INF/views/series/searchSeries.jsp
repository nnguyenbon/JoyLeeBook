<%--
    Document   : searchSeries
    Created on : Jul 5, 2025, 10:41:02 AM
    Author     : HaiDD-dev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Trang Home (home.html) -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>MangaVerse - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
    />
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    />

    <link rel="stylesheet" href="css/style.css"/>
    <style>
        .section-title {
            font-weight: bold;
            font-size: 1.3rem;
            margin-top: 0.5rem;
        }

        .manga-card {
            border-radius: 0.5rem;
            overflow: hidden;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            width: 250px;
        }

        .manga-cover {
            object-fit: cover;
            height: 300px;
        }
    </style>
</head>

<body class="bg-white">
<header>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg border-bottom sticky-top">
        <div class="container">
            <a class="navbar-brand fw-bold text-white" href="#"
            ><i class="bi bi-book"></i> <strong>JoyLeeBook</strong></a>
            <button
                    class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav"
            >
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="home.html">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="genre.html">Genres</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#">History</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#">Library</a>
                    </li>
                </ul>
                <form class="d-flex me-3">
                    <input
                            class="form-control me-2"
                            type="search"
                            placeholder="Search manga..."
                            aria-label="Search"
                    />
                </form>
                <a class="btn me-2 login" href="authorization/login.html">LOGIN</a>
                <a class="btn signup" href="authorization/register.html">SIGN UP</a>
            </div>
        </div>
    </nav>
</header>


<main>
    <div class="container mt-4">
        <div class="section-title text-center m-4">SEARCH</div>

        <div class="row">
            <!-- Sidebar (Search + Genre) -->
            <div class="col-md-4">
                <!-- Search Bar -->
                <form action="${pageContext.request.contextPath}/search" method="get" class="mb-4">
                    <div class="input-group">
                        <input
                                type="text"
                                name="searchQuery"
                                class="form-control"
                                placeholder="Search series..."
                                value="${param.searchQuery}"
                        />
                        <button class="btn btn-primary" type="submit">Search</button>
                    </div>
                </form>

                <!-- Genre Filter -->
                <div class="p-3 shadow-sm rounded bg-white">
                    <h5 class="fw-semibold mb-3">📚 Browse by Genre</h5>
                    <c:forEach var="genre" items="${genres}">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="genre${genre.id}" value="${genre.id}"
                                   name="genres"/>
                            <label class="form-check-label" for="genre${genre.id}">${genre.name}</label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Search Results -->
            <div class="col-md-8">
                <div class="row row-cols-2 row-cols-md-3 g-4">

                    <c:if test="${empty seriesList}">
                        <div class="col-12">
                            <p class="text-center text-muted">No series found matching your criteria.</p>
                        </div>
                    </c:if>

                    <c:forEach var="series" items="${seriesList}">
                        <div class="col">
                            <a href="${pageContext.request.contextPath}/viewSeriesInfo?seriesId=${series.seriesId}"
                               class="text-decoration-none">
                                <div class="card manga-card">
                                    <img
                                            src="${pageContext.request.contextPath}/${series.coverImageUrl}"
                                            class="card-img-top manga-cover"
                                            alt="${series.seriesTitle}"
                                    />
                                    <div class="card-body">
                                        <h6 class="card-title mb-0 text-truncate">${series.seriesTitle}</h6>
                                        <div class="d-flex justify-content-between text-muted small px-1">
                                            <div>Ch. ${series.totalChapters}</div>
                                            <div><em>${series.latestChapterDate}</em></div>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>

                <%-- Yêu cầu Servlet phải gửi 3 thuộc tính: currentPage, totalPages, và searchQuery --%>
                <c:if test="${totalPages > 1}">
                    <nav class="mt-4 d-flex justify-content-center" aria-label="Page navigation">
                        <ul class="pagination">
                                <%-- Nút Previous (Về trang trước) --%>
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/search?searchQuery=${searchQuery}&page=${currentPage - 1}">&lt;&lt;</a>
                            </li>

                                <%-- Vòng lặp để tạo các nút số trang --%>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/search?searchQuery=${searchQuery}&page=${i}">${i}</a>
                                </li>
                            </c:forEach>

                                <%-- Nút Next (Qua trang tiếp theo) --%>
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/search?searchQuery=${searchQuery}&page=${currentPage + 1}">&gt;&gt;</a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</main>

<footer class="mt-5">
    <!-- Section About -->
    <div class="text-white py-4" style="background-color: #8da7c0">
        <div class="container text-center">
            <h5 class="fw-bold mb-3">ABOUT US</h5>
            <p class="mx-auto mb-0" style="max-width: 1000px">
                JoyLeeBook is a Lorem ipsum dolor sit amet, consectetur adipiscing
                elit, sed do eiusmod tempor incididunt ut labore et dolore magna
                aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
                dolor in reprehenderit in voluptate velit esse cillum dolore eu
                fugiat nulla pariatur.
            </p>
        </div>
    </div>

    <!-- Section Social + Back to top -->
    <div class="py-3" style="background-color: #517594; justify-self: center">
        <div
                class="container d-flex flex-column flex-md-row justify-content-between align-items-center"
        >
            <!-- Social icons -->
            <div
                    class="offset-md-2 col-md-7 d-flex justify-content-center gap-4 mb-3 mb-md-0"
            >
                <a href="#" class="text-dark fs-4"
                ><i class="fab fa-instagram"></i
                ></a>
                <a href="#" class="text-dark fs-4"
                ><i class="fab fa-facebook"></i
                ></a>
                <a href="#" class="text-dark fs-4"><i class="fab fa-tiktok"></i></a>
            </div>

            <!-- Back to top -->
            <a
                    href="#header"
                    class="btn btn-light rounded-circle d-flex align-items-center justify-content-center"
                    style="width: 40px; height: 40px"
            >
                <i class="fas fa-arrow-up text-dark"></i>
            </a>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>