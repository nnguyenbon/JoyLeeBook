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
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis()%>">
        <title><%= pageType.toUpperCase()%></title>
        <style>
            .dashboard-image {
                width: 70px;
                height: 90px;
                object-fit: cover;
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
            tr:hover {
                background-color: #f8f9fa;
            }

        </style>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-dark border-bottom sticky-top">
                <div class="container">
                    <!-- Logo -->
                    <a class="navbar-brand fw-bold" href="#">
                        <i class="bi bi-book"></i> <strong>JoyLeeBook</strong>
                    </a>

                    <!-- Toggler (Mobile) -->
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <!-- Main content -->
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <!-- Search form: Center -->
                        <form class="d-flex mx-auto my-2 my-lg-0" role="search">
                            <input class="form-control me-2" type="search" placeholder="Search manga..." aria-label="Search">
                            <button class="btn btn-outline-light" type="submit">Search</button>
                        </form>

                        <!-- Buttons: Right -->
                        <div class="d-flex">
                            <a class="btn btn-outline-light me-2" href="#">LOGIN</a>
                            <a class="btn btn-primary" href="#">SIGN UP</a>
                        </div>
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
                    <table class="table align-middle bg-white table-hover">
                        <thead class="">
                            <tr>
                                <th style="width: 100px;">Cover</th>
                                <th style="width: 300px;">Series Title</th>
                                <th style="width: 120px;" class="text-center">Chapters</th>
                                <th style="width: 120px;" class="text-center">Status</th>
                                <th style="width: 150px;" class="text-center">Created Date</th>

                            </tr>
                        </thead>
                        <tbody>
                            <%  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

                                List< Series> seriesList = (List<Series>) request.getAttribute("seriesList");
                                int MAXIMUM_SERIES_IN_PAGE = 20;

                                int seriesListSize = seriesList.size();

                                int currentPage = request.getParameter("page") == null
                                        || request.getParameter("page").equals("1") ? 1 : Integer.parseInt(request.getParameter("page"));

                                for (int i = (currentPage - 1) * MAXIMUM_SERIES_IN_PAGE; i < currentPage * MAXIMUM_SERIES_IN_PAGE - 1; i++) {
                                    Series s = seriesList.get(i);
                            %>

                            <tr  onclick="window.location = 'viewSeriesInfo?seriesId=<%=s.getSeriesId()%>'" style="cursor: pointer;">
                                <td>
                                    <img src="https://tse3.mm.bing.net/th/id/OIP.F3yrH-SyeJJnLN7GQrA7kQHaJ4?rs=1&pid=ImgDetMain&o=7&rm=3"
                                         alt="cover" class="rounded dashboard-image" >
<!--                                    <img src="<%=s.getCoverImageUrl()%>"
                                         alt="cover" class="dashboard-image rounded" >-->
                                </td>

                                <td><strong><%=s.getSeriesTitle()%></strong></td>
                                <td class="text-center">
                                    <span class="badge text-dark"><%=s.getTotalChapters()%></span>
                                </td>
                                <td class="text-center">
                                    <%if (s.getStatus().equals("1")) {%>
                                    <span class="badge bg-success-subtle text-success">Completed</span>
                                    <% } else { %>
                                    <span class="badge bg-warning-subtle text-warning">Ongoing</span>
                                    <% }%>
                                </td>
                                <td class="text-center">
                                    <span class="badge text-dark"><%=sdf.format(s.getCreatedAt())%></span>
                                </td>
                            </tr>

                            <%}%>
                        </tbody>
                    </table>
                </div>
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


        <jsp:include page="footer/_footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis()%>"></script>
    </body>
</html>
