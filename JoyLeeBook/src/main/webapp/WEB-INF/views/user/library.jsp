<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%
    /*
    Note:
    pageType: Name of folder contained main page
     */

    String[] pageTypeArray = ((String) request.getRequestURI()).split("/");
    String pageType = pageTypeArray[pageTypeArray.length - 1].replace(".jsp", "");

    request.setAttribute("pageType", pageType);

%>
<jsp:include page="/WEB-INF/views/components/_header.jsp" />
<!-- Trang Home (home.html) -->
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>MangaVerse - Home</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
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
            main {
                flex: 1 0 auto;
                min-height: calc(100vh - 200px); /* hoặc điều chỉnh 200px theo chiều cao header + footer */
            }
        </style>
    </head>

    <body class="bg-white d-flex flex-column min-vh-100">
        <main class="d-flex flex-column flex-grow-1">

            <div class="container my-5 flex-grow-1 d-flex flex-column">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h4 class="fw-bold">Story Management</h4>
                </div>

                <div class="table-responsive flex-grow-1">
                    <table class="table align-middle bg-white">
                        <thead>
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
                            <c:choose>
                                <c:when test="${not empty librarySeries}">
                                    <c:forEach var="series" items="${librarySeries}">
                                        <tr>
                                            <td>
                                                <img src="${pageContext.request.contextPath}/${series.coverImageUrl}"
                                                     alt="cover" class="rounded"
                                                     style="width: 70px; height: 90px; object-fit: cover;">
                                            </td>
                                            <td><strong>${series.seriesTitle}</strong></td>
                                            <td class="text-center">
                                                <span class="badge text-dark">${series.totalChapters}</span>
                                            </td>
                                            <td class="text-center">
                                                <span class="badge bg-warning text-dark">
                                                    <c:choose>
                                                        <c:when test="${series.status == 0}">Ongoing</c:when>
                                                        <c:otherwise>Completed</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </td>
                                            <td class="text-center">
                                                <span class="badge text-dark">${series.createdAt}</span>
                                            </td>
                                            <td class="text-center">
                                                <form action="removeSavedSeries" method="post" style="display:inline;">
                                                    <input type="hidden" name="seriesId" value="${series.seriesId}">
                                                    <button type="submit" class="btn btn-sm btn-warning">Remove</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" class="text-center text-muted py-4">
                                            No saved series found in your library.
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="mt-auto py-4 bg-light">
                <nav aria-label="Page navigation example" class="d-flex justify-content-center">
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

        <br>
        <script src="${pageContext.request.contextPath}/js/index.js"></script>
        <script src="${pageContext.request.contextPath}/js/jQuery.js"></script>
        <!-- Bootstrap JS -->
        <jsp:include page="/WEB-INF/views/components/_footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>