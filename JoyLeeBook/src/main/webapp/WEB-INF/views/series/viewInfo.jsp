<%-- 
    Document   : viewInfo
    Created on : Jul 5, 2025, 10:41:11 AM
    Author     : PC
--%>

<%@page import= "model.Genre"%>
<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         session="true"
         errorPage=""
         isErrorPage="false" %>

<%@ page import="java.util.ArrayList, model.Series, model.Chapter" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <title>Chronicles of the Azure Realm</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
        <!-- Biểu tượng Bootstrap (optional nếu dùng icon như trong ảnh) -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis()%>">

        <style>
            body {
                background-color: #eef4fe;
            }

            .navbar {
                background-color: #517594;
            }

            .action-buttons {
                display: none;
            }

            div.chapter-row:hover .action-buttons {
                display: inline-flex !important;
            }

            /* .chapter-row {
                background-color: #EEF4FE;
                border: 1px solid #0d6efd;
                border-radius: 12px;
                vertical-align: middle;
            } */
            .chapter-row {
                background-color: #ffffff;
                border: 1px solid #dee2e6;
                transition: all 0.3s ease;
                color: black;
            }

            .chapter-row:hover {
                transform: translateY(-4px);
                background-color: #f0f8ff;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            }

            .chapter-row:hover a.chapter-title {
                color: blue !important;
            }
        </style>
    </head>

    <body>
        <% boolean isAdmin = session.getAttribute("role") == "admin";
        %>

        <header>
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg border-bottom sticky-top">
                <div class="container">
                    <a class="navbar-brand fw-bold text-white" href="#"><strong
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
            <!-- Main Content -->
            <div class="container mt-5">
                <div class="row g-1 border border-2 rounded-4 p-4 bg-white" style="background-color: #f7f8f9">
                    <!-- Manga Cover -->
                    <%  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                        Series series = (Series) request.getAttribute("series");
                        ArrayList<Chapter> listChapter = (ArrayList<Chapter>) request.getAttribute("listChapter");
                        String[] colorGenre = {
                            "bg-primary-subtle text-primary",
                            "bg-secondary-subtle text-secondary",
                            "bg-danger-subtle text-danger",
                            "bg-info-subtle text-info",
                            "bg-light-subtle text-dark"};
                    %>
                    <div class="col-md-4 m-0 p-0">
                        <img src="${pageContext.request.contextPath}/${series.coverImageUrl}"
                             class="img-fluid rounded shadow" alt="<%=series.getSeriesTitle()%>" style="width: 370px;" />
<!--                        <img src="<%=series.getCoverImageUrl()%>"
                             class="img-fluid rounded shadow" alt="Azure Realm" style="width: 370px" />-->
                    </div>

                    <!-- Manga Info -->
                    <div class="col-md-8 pr-3">
                        <h2 class="fw-bold"><%=series.getSeriesTitle()%></h2>
                        <h3 class="text-muted">By <strong><%=series.getAuthorName()%></strong> &nbsp;</h3>
                        <p class=" mb-4"> Status:
                            <%if (series.getStatus().equals("1")) {%>
                            <span class=" text-success">Completed</span>
                            <% } else { %>
                            <span class=" text-warning">Ongoing</span>
                            <% }%>
                        </p>
                        <!-- Genres -->
                        <div class="mb-3">
                            <h6>GENRE</h6>
                            <% ArrayList<Genre> genres = series.getGenres();
                                for (int i = 0; i < genres.size(); i++) {
                            %>
                            <span class="badge genre-badge <%=colorGenre[i % 5]%>"><%=genres.get(i).getGenreName()%></span>
                            <%
                                }
                            %>
                        </div>

                        <!-- Summary -->
                        <div class="my-3">
                            <h6 class="fw-bold">Summary</h6>
                            <p class="mb-0">
                                <%=series.getDescription()%>
                            </p>
                        </div>
                        <%  if (isAdmin) {%>
                        <div class="d-flex gap-2 my-5 ">
                            <div class="d-flex gap-2">
                                <a class="btn btn-warning" href="series/editSeries.html">Edit</a>
                                <button class="btn btn-outline-danger">Delete</button>
                            </div>
                        </div>
                        <%  } else {%>
                        <div class="d-flex gap-2 my-5 ">
                            <div class="d-flex gap-2">
                                <a class="btn btn-primary text-white" href="readChapter?chapterId=1&seriesId=<%=series.getSeriesId()%>">Start Reading</a>
                                <form action="saveSeries" method="post" style="display:inline;">
                                    <input type="hidden" name="seriesId" value="${series.seriesId}">
                                    <button type="submit" class="btn btn-outline-primary">Add Library</button>
                                </form>
                                <!--<a class="btn btn-outline-primary" href="saveSeries?seriesId=<%=series.getSeriesId()%>">Add Library</a>-->
                            </div>
                        </div>
                        <%
                            String errorMessage = (String) session.getAttribute("errorMessage");
                            if (errorMessage != null) {
                                session.removeAttribute("errorMessage"); // Xóa sau khi lấy
                        %>
                        <div class="alert alert-danger"><%= errorMessage%></div>
                        <%
                            }
                        %>
                        <% }%>
                        <!-- Buttons -->

                    </div>
                </div>

                <div class="mt-5 border border-2 rounded-4 p-4 m-0 g-4" style="background-color: #ffffff">
                    <div class="d-flex justify-content-between align-items-center mb-4 mx-2">
                        <div>
                            <h4 class="fw-bold">Chapters </h4>
                            <h5 class="text-muted small mt-1">Total: <%=series.getTotalChapters()%></h5>
                        </div>
                        <a href="add-story.html" class="btn btn-success">+ Add new chapter</a>
                    </div>
                    <div  class="container my-4 py-4" style="max-height: 500px; overflow-y: auto">
                        <% for (Chapter chapter : listChapter) {%>
                        <!-- Dòng chương -->
                        <div class="chapter-row d-flex justify-content-between align-items-center
                             px-5 py-2 mb-3 rounded-3 border position-relative"
                             onclick="window.location = 'readChapter?chapterId=<%=chapter.getChapterId()%>'" style="cursor: pointer">
                            <div class="d-flex align-items-center gap-5">
                                <!-- Số chương -->
                                <h6 class="fw-bold">Ch. <%=chapter.getChapterIndex()%></h6>

                                <!-- Nội dung chương -->
                                <div>
                                    <div class="d-flex align-items-center gap-2">
                                        <a href="#" class="chapter-title fw-semibold text-decoration-none text-dark"> <%=chapter.getChapterTitle()%></a>
                                    </div>
                                    <div class="text-muted small mt-1"><%=sdf.format(chapter.getCreatedAt())%></div>
                                </div>
                            </div>
                            <% if (isAdmin) {%>
                            <div class="action-buttons gap-2">
                                <a href="readChapter?chapterId=<%=chapter.getChapterId()%>" class="btn btn-sm btn-primary">
                                    <i class="bi bi-book"></i> Read</a>
                                <a gref="editChapter?chapterId=<%=chapter.getChapterId()%>" class="btn btn-sm btn-outline-warning">Edit</a>
                                <a href="deleteChapter?chapterId=<%=chapter.getChapterId()%>" class="btn btn-sm btn-outline-danger">Delete</a>
                            </div>
                            <% }%>
                        </div>
                        <% }%>

                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="/WEB-INF/views/components/footer.jsp" />
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>