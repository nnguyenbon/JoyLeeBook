<%--
    Document   : searchSeries
    Created on : Jul 5, 2025, 10:41:02 AM
    Author     : HaiDD-dev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
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
        <!-- Select2 JS -->
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <!-- Style CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis()%>">
    </head>
    <body class="bg-white">

        <jsp:include page="/WEB-INF/views/components/_header.jsp"/>

        <main>
            <div class="container mt-4">
                <div class="section-title text-center m-4">SEARCH</div>
                <div class="row">
                    <div class="col-md-4">
                        <form action="${pageContext.request.contextPath}/search" method="get" class="mb-4">
                            <div class="input-group mb-4">
                                <input type="text" name="searchQuery" class="form-control" placeholder="Search series..." value="${searchQuery}"/>
                            </div>

                            <div class="p-3 shadow-sm rounded bg-white">
                                <h5 class="fw-semibold mb-3">Browse by Genre</h5>
                                <c:forEach var="genre" items="${genres}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="genre${genre.genreId}" value="${genre.genreId}" name="genres"
                                               <c:if test="${selectedGenres.contains(genre.genreId.toString())}">checked</c:if>/>
                                        <label class="form-check-label" for="genre${genre.genreId}">${genre.genreName}</label>
                                    </div>
                                </c:forEach>
                            </div>
                            <button class="btn btn-primary w-100 mt-3" type="submit">Filter Results</button>
                        </form>
                    </div>

                    <div class="col-md-8">
                        <c:if test="${empty seriesList}">
                            <div class="col-12"><p class="text-center text-muted">No series found matching your criteria.</p></div>
                        </c:if>

                        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
                            <c:forEach var="series" items="${seriesList}">
                                <div class="col">
                                    <a href="${pageContext.request.contextPath}/viewSeriesInfo?seriesId=${series.seriesId}" class="text-decoration-none">
                                        <div class="card manga-card h-100">
                                            <img src="${pageContext.request.contextPath}/${series.coverImageUrl}" class="card-img-top manga-cover" alt="${series.seriesTitle}"/>
                                            <div class="card-body">
                                                <h6 class="card-title mb-0 text-truncate">${series.seriesTitle}</h6>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>

                        <%-- Phân trang --%>
                        <c:if test="${totalPages > 1}">
                            <%-- Xây dựng chuỗi tham số genre cho link phân trang --%>
                            <c:set var="genreParams" value=""/>
                            <c:forEach var="genreId" items="${selectedGenres}">
                                <c:set var="genreParams" value="${genreParams}&genres=${genreId}"/>
                            </c:forEach>

                            <nav class="mt-4 d-flex justify-content-center">
                                <ul class="pagination">
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/search?searchQuery=${searchQuery}&page=${currentPage - 1}${genreParams}">&laquo;</a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/search?searchQuery=${searchQuery}&page=${i}${genreParams}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/search?searchQuery=${searchQuery}&page=${currentPage + 1}${genreParams}">&raquo;</a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
            </div>
        </main>
        <br>
        <script src="${pageContext.request.contextPath}/js/index.js"></script> 
        <script src="${pageContext.request.contextPath}/js/jQuery.js"></script>
        <jsp:include page="/WEB-INF/views/components/_footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

    </body>
</html>