<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         session="true"
         errorPage=""
         isErrorPage="false" %>

<%@ page import="java.util.List, model.Series" %>
<%
    /*
    Note:
    pageType: Name of folder contained main page
     */

    String[] pageTypeArray = ((String) request.getRequestURI()).split("/");
    String pageType = pageTypeArray[pageTypeArray.length - 1].replace(".jsp", "");

    request.setAttribute("pageType", pageType);

%>
<!-- Trang Home (home.html) -->
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>MangaVerse - Home</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
                    <a class="navbar-brand fw-bold text-white" href="#"><i class="bi bi-book"></i>
                        <strong>JoyLeeBook</strong></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav me-auto">
                            <li class="nav-item"><a class="nav-link  text-white" href="homeAdmin.html">Home</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="genre.html">Genres</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="#">History</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="#">Library</a></li>
                        </ul>
                        <form class="d-flex me-3">
                            <input class="form-control me-2" type="search" placeholder="Search manga..."
                                   aria-label="Search">
                        </form>
                        <a class="btn me-2 login" href="authorization/login.html">LOGIN</a>
                        <a class="btn signup " href="authorization/register.html">SIGN UP</a>
                    </div>
                </div>
            </nav>
        </header>
        <main>
            <div class="container my-5">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h4 class="fw-bold">Series Management</h4>
                    <a href="addSeries" class="btn btn-success">+ Add new series</a>
                </div>

                <!-- Bảng truyện -->
                <div class="table-responsive">
                    <table class="table align-middle bg-white">
                        <thead class="">
                            <tr>
                                <th style="width: 100px;">Cover photo</th>
                                <th style="width: 300px;">Story name</th>
                                <th style="width: 120px;" class="text-center">Chapters</th>
                                <th style="width: 120px;" class="text-center">Status</th>
                                <th style="width: 150px;" class="text-center">Created Date</th>
                                <th style="width: 150px;" class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="series" items="${allSeries}">
                                <tr>
                                    <td>
                                        <img src="${pageContext.request.contextPath}/${series.coverImageUrl}"
                                             alt="cover" class="rounded" style="width: 70px; height: 90px; object-fit: cover;">
                                    </td>

                                    <td><strong>${series.seriesTitle}</strong></td>
                                    <td class="text-center">
                                        <span class="badge text-dark">${series.totalChapters}</span>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge bg-warning text-dark">
                                            <c:choose>
                                                <c:when test="${series.status == 0}">
                                                    Ongoing
                                                </c:when>
                                                <c:otherwise>
                                                    Completed
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge text-dark">${series.createdAt}</span>
                                    </td>
                                    <td class="text-center">
                                        <a href="viewSeriesInfo?seriesId=${series.seriesId}" class="btn btn-sm btn-primary">Detail</a>
                                        <a href="updateSeries?seriesId=${series.seriesId}" class="btn btn-sm btn-warning">Edit</a>
                                        <a href="deleteSeries?seriesId=${series.seriesId}"  class="btn btn-sm btn-danger">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div>
                <nav aria-label="Page navigation example" class="mt-4 d-flex justify-content-center">
                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage - 1}">&lt;&lt;</a>
                            </li>
                        </c:if>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage + 1}">&gt;&gt;</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </main>
        
        <jsp:include page="/WEB-INF/views/components/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
