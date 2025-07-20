<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         session="true"
         errorPage=""
         isErrorPage="false" %>

<%@ page import="java.util.ArrayList, model.Series" %>
<%
    /*
    Note:
    pageType: Name of folder contained main page
     */

    String[] pageTypeArray = ((String) request.getRequestURI()).split("/");
    String pageType = pageTypeArray[pageTypeArray.length - 1].replace(".jsp", "");

    request.setAttribute("pageType", pageType);

%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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
        <header>
            <<<<<<< HEAD
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg border-bottom sticky-top">
                <div class="container">
                    <a class="navbar-brand fw-bold text-white" href="home"><strong
                            class="fs-3">JoyLeeBook</strong></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse " id="navbarNav">
                        <div class="d-flex ms-auto justify-content-center" style="max-width: 600px; width: 100%;">
                            <form class="d-flex flex-grow-1 me-2">
                                <input class="form-control w-100 me-3 " type="search" placeholder="Search manga..."
                                       aria-label="Search" />
                            </form>
                            <a class="btn me-2 login" href="authorization/login.html">LOGIN</a>
                            <a class="btn signup" href="authorization/register.html">SIGN UP</a>
                        </div>
                    </div>
                </div>
            </nav>
        </header>
        <main>
            <div class="container my-5">
                <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">

                    <!-- Nội dung slide -->
                    <div class="carousel-inner">
                        <%
                            ArrayList< Series> seriesList = (ArrayList<Series>) request.getAttribute("seriesList");
                            int maxSlide = Math.min(3, seriesList.size());
                            for (int i = 0; i < maxSlide; i++) {
                                boolean isActive = (i == 0);
                                Series s = seriesList.get(i);
                        %>
                        <div class="carousel-item <%= isActive ? "active" : ""%>">
                            <div class="carousel-slide d-flex flex-wrap align-items-center p-4 mb-5 bg-primary-subtle rounded shadow" >
                                <!-- Text -->
                                <div class="flex-grow-1 mx-5 w-50">
                                    <span class="badge bg-light text-primary mb-2">New</span>
                                    <h3 class="fw-bold"><%= s.getSeriesTitle()%></h3>
                                    <span class="text-muted">Ch. <%= s.getTotalChapters()%></span>
                                    <span class="text-muted mx-3">Updated: <%= s.getLatestChapterDate()%></span>
                                    <div class="d-flex gap-2 mt-3">
                                        <a href="viewSeriesInfo?seriesId=<%= s.getSeriesId()%>" class="btn btn-primary">Read</a>
                                        <% if (session.getAttribute("role") == "user") {%>

                                        <a href="saveHistory?seriesId=<%= s.getSeriesId()%>" class="btn btn-outline-dark">Add library</a>
                                        <%}%>
                                    </div>
                                </div>
                                <!-- Image -->
                                <div class="mx-5">
                                    <!--<img class="carousel-image" src="<%= s.getCoverImageUrl()%>" class="img-fluid rounded" style="max-height: 250px;" alt="<%= s.getSeriesTitle()%>">-->
                                    <img class="carousel-image rounded" src="https://tse3.mm.bing.net/th/id/OIP.F3yrH-SyeJJnLN7GQrA7kQHaJ4?rs=1&pid=ImgDetMain&o=7&rm=3" alt="<%= s.getSeriesTitle()%>">
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>

                    <!-- Nút điều hướng -->
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden"></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden"></span>
                    </button>

                </div>
            </div>


            <div class="container mb-5">
                <div class="section-title text-center m-4"> LASR RALEASED </div>
                <div class="row row-cols-2 row-cols-md-4 g-5 mx-5">
                    <%
                        int MAXIMUM_SERIES_IN_PAGE = 20;

                        int seriesListSize = seriesList.size();

                        int currentPage = request.getParameter("page") == null
                                || request.getParameter("page").equals("1") ? 1 : Integer.parseInt(request.getParameter("page"));

                        for (int i = (currentPage - 1) * MAXIMUM_SERIES_IN_PAGE + 3; i < currentPage * MAXIMUM_SERIES_IN_PAGE - 1; i++) {
                            Series s = seriesList.get(i);
                    %>
                    <div class="col">
                        <a href="viewSeriesInfo?seriesId=<%= s.getSeriesId()%>">
                            <div class="card">
                                <img src="https://tse3.mm.bing.net/th/id/OIP.F3yrH-SyeJJnLN7GQrA7kQHaJ4?rs=1&pid=ImgDetMain&o=7&rm=3"
                                     class="card-img-top carousel-image" alt="<%=s.getSeriesTitle()%>">
                                <div class="card-body">
                                    <h6 class="card-title mb-0 text-truncate"><%=s.getSeriesTitle()%></h6>
                                    <div class="d-flex justify-content-between text-muted small px-1">
                                        <div> Ch. <%=s.getTotalChapters()%></div>
                                        <div><em><%=s.getLatestChapterDate()%></em></div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                    <% }%>
                </div>
                <%
                    int MAXIMUM_PAGE_NUMBER = (int) Math.ceil((double) seriesListSize / MAXIMUM_SERIES_IN_PAGE);
                    int previousPage = currentPage > 1 ? currentPage - 1 : 1;
                    int nextPage = currentPage < MAXIMUM_PAGE_NUMBER ? currentPage + 1 : MAXIMUM_PAGE_NUMBER;
                    if (MAXIMUM_PAGE_NUMBER > 1) {
                %>

                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center mt-4">

                        <!-- Previous -->
                        <li class="page-item <%= currentPage == 1 ? "disabled" : ""%>">
                            <a class="page-link" href="?page=<%= previousPage%>">&laquo;</a>
                        </li>

                        <!-- Page numbers (3 numbers max: prev - current - next) -->
                        <% if (currentPage > 1) {%>
                        <li class="page-item">
                            <a class="page-link" href="?page=<%= currentPage - 1%>"><%= currentPage - 1%></a>
                        </li>
                        <% }%>

                        <!-- Current page -->
                        <li class="page-item active">
                            <a class="page-link" href="#"><%= currentPage%></a>
                        </li>

                        <% if (currentPage < MAXIMUM_PAGE_NUMBER) {%>
                        <li class="page-item">
                            <a class="page-link" href="?page=<%= currentPage + 1%>"><%= currentPage + 1%></a>
                        </li>
                        <% }%>

                        <!-- Next -->
                        <li class="page-item <%= currentPage == MAXIMUM_PAGE_NUMBER ? "disabled" : ""%>">
                            <a class="page-link" href="?page=<%= nextPage%>">&raquo;</a>
                        </li>

                    </ul>
                </nav>
                <% }%>
        </main>


        <jsp:include page="/WEB-INF/views/footer/_footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis()%>"></script>
    </body>

</html>

