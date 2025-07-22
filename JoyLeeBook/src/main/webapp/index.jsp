<%@page import="model.User"%>
<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         session="true"
         errorPage=""
         isErrorPage="false" %>

<%@ page import="java.util.ArrayList, model.Series" %>
<%
    String[] pageTypeArray = ((String) request.getRequestURI()).split("/");
    String pageType = pageTypeArray[pageTypeArray.length - 1].replace(".jsp", "");

    request.setAttribute("pageType", pageType);
    ArrayList<Series> seriesList = (ArrayList<Series>) request.getAttribute("seriesList");
    User user = (User) request.getSession().getAttribute("loggedInUser");
%>

<jsp:include page="/WEB-INF/views/components/_header.jsp" />

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
        <title><%= pageType.toUpperCase()%></title>
        <style>
            body {
                background-color: #eef4fe;
            }
            .navbar {
                background-color: #517594;
            }
            .login {
                color: #fff;
            }
            .signup {
                background-color: #8aab52;
                color: white;
            }
        </style>
    </head>

    <body>

        <main>
            <div class="container my-5">
                <%
                    if (seriesList != null && !seriesList.isEmpty()) {

                        int maxSlide = Math.min(3, seriesList.size());
                %>
                <!-- Carousel -->
                <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <%
                            for (int i = 0; i < maxSlide; i++) {
                                boolean isActive = (i == 0);
                                Series s = seriesList.get(i);
                        %>
                        <div class="carousel-item <%= isActive ? "active" : ""%>">
                            <div class="d-flex flex-wrap align-items-center p-4 mb-5 bg-primary-subtle rounded shadow" style="max-width: 1150px; margin: auto;">
                                <div class="information-series flex-grow-1 mx-5">
                                    <span class="badge bg-light text-primary mb-2">New</span>
                                    <h3 class="fw-bold"><%= s.getSeriesTitle()%></h3>
                                    <span class="text-muted">Ch. <%= s.getTotalChapters()%></span>
                                    <span class="text-muted mx-3">Updated: <%= s.getLatestChapterDate()%></span>
                                    <%
                                        User currentUser = (User) session.getAttribute("loggedInUser");
                                    %>
                                    <div class="d-flex gap-2 mt-3">
                                        <a href="viewSeriesInfo?seriesId=<%= s.getSeriesId()%>" class="btn btn-primary">Read</a>
                                        <% if (currentUser != null && "reader".equals(currentUser.getRoleName())) {%>
                                        <form action="saveSeries" method="post" style="display: inline;">
                                            <input type="hidden" name="seriesId" value="<%= s.getSeriesId()%>">
                                            <button type="submit" class="btn btn-outline-dark">Add library</button>
                                        </form>
                                        <% }%>
                                    </div>

                                </div>
                                <div class="image-series mx-5">
                                    <img class="carousel-image rounded" src="${pageContext.request.contextPath}/<%= s.getCoverImageUrl()%>" style="max-height: 250px;" alt="<%= s.getSeriesTitle()%>">
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                    <button class="carousel-control-prev custom-control border border-black" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next custom-control border border-black" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>

                <!-- List -->
                <div class="container mb-5">
                    <div class="section-title text-center m-4">LAST RELEASED</div>
                    <div class="row row-cols-2 row-cols-md-4 g-5 mx-5">
                        <%
                            int MAXIMUM_SERIES_IN_PAGE = 20;
                            int seriesListSize = seriesList.size();
                            int currentPage = request.getParameter("page") == null
                                    || request.getParameter("page").equals("1") ? 1 : Integer.parseInt(request.getParameter("page"));

                            int startIndex = (currentPage - 1) * MAXIMUM_SERIES_IN_PAGE + maxSlide;
                            int endIndex = Math.min(currentPage * MAXIMUM_SERIES_IN_PAGE, seriesListSize);

                            for (int i = startIndex; i < endIndex; i++) {
                                Series s = seriesList.get(i);
                        %>
                        <div class="card-layout">
                            <a href="viewSeriesInfo?seriesId=<%= s.getSeriesId()%>" class="text-decoration-none text-dark">
                                <div class="card manga-card">
                                    <div class="card-img-layout">
                                        <img src="${pageContext.request.contextPath}/<%= s.getCoverImageUrl()%>" class="card-img-top manga-cover" alt="<%=s.getSeriesTitle()%>">
                                    </div>
                                    <div class="card-body">
                                        <h6 class="card-title mb-0"><%=s.getSeriesTitle()%></h6>
                                        <div class="d-flex justify-content-between text-muted small px-1">
                                            <div>Ch. <%=s.getTotalChapters()%></div>
                                            <div><em><%=s.getLatestChapterDate()%></em></div>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <% } %>
                    </div>
                </div>

                <!-- Pagination -->
                <%
                    int MAXIMUM_PAGE_NUMBER = (int) Math.ceil((double) (seriesListSize - maxSlide) / MAXIMUM_SERIES_IN_PAGE);
                    if (MAXIMUM_PAGE_NUMBER > 1) {
                        int previousPage = currentPage > 1 ? currentPage - 1 : 1;
                        int nextPage = currentPage < MAXIMUM_PAGE_NUMBER ? currentPage + 1 : MAXIMUM_PAGE_NUMBER;
                %>
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center mt-4">
                        <li class="page-item <%= currentPage == 1 ? "disabled" : ""%>">
                            <a class="page-link" href="?page=<%= previousPage%>">&laquo;</a>
                        </li>
                        <% for (int pageNum = 1; pageNum <= MAXIMUM_PAGE_NUMBER; pageNum++) {%>
                        <li class="page-item <%= pageNum == currentPage ? "active" : ""%>">
                            <a class="page-link" href="?page=<%= pageNum%>"><%= pageNum%></a>
                        </li>
                        <% }%>
                        <li class="page-item <%= currentPage == MAXIMUM_PAGE_NUMBER ? "disabled" : ""%>">
                            <a class="page-link" href="?page=<%= nextPage%>">&raquo;</a>
                        </li>
                    </ul>
                </nav>
                <% } %>

                <% } else { %>
                <!-- No series found -->
                <p class="text-center fs-4 my-5">No series found.</p>
                <% }%>

            </div>
        </main>

        <jsp:include page="/WEB-INF/views/components/_footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis()%>"></script>
    </body>
</html>