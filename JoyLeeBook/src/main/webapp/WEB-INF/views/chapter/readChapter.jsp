<%@page import="model.Chapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="/WEB-INF/views/components/_header.jsp" />
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
            .reader-container {
                max-width: 900px;
                margin: auto;
                padding: 2rem 1rem;
            }

            .reader-img {
                width: 100%;
                margin-bottom: 1rem;
                border-radius: 0.5rem;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .nav-reader {
                margin-bottom: 1.5rem;
            }
        </style>
    </head>

    <body class="bg-white d-flex flex-column min-vh-100">


        <main class="flex-grow-1">
            <div class="reader-container">
                <div class="nav-reader d-flex justify-content-between align-items-center">
                    <c:choose>
                        <c:when test="${chapter.chapterIndex eq firstIndex}">
                            <a href="#" class="btn btn-outline-secondary btn-sm disabled">⟵ Prev Chapter</a>
                        </c:when>
                        <c:otherwise>
                            <a href="previousChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">
                                ⟵ Prev Chapter
                            </a>
                        </c:otherwise>
                    </c:choose>

                    <!-- Button để mở modal -->
                    <button type="button" class="btn bg-primary-subtle" data-bs-toggle="modal" data-bs-target="#chapterModal">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list"
                             viewBox="0 0 16 16">
                        <path fill-rule="evenodd"
                              d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5" />
                        </svg> ${chapter.chapterTitle}
                    </button>


                    <c:choose>
                        <c:when test="${chapter.chapterIndex eq lastIndex}">
                            <a href="#" class="btn btn-outline-secondary btn-sm disabled">Next Chapter ⟶</a>
                        </c:when>
                        <c:otherwise>
                            <a href="forwardChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">
                                Next Chapter ⟶
                            </a>
                        </c:otherwise>
                    </c:choose>

                </div>

                <!-- Web novel reading content -->
                <div class="bg-light p-4 rounded shadow-sm m-3" style="line-height: 1.8; font-size: 1.1rem;">
                    <p>
                        ${chapter.content}
                    </p>
                </div>
                <div class="nav-reader d-flex justify-content-between align-items-center">
                    <c:choose>
                        <c:when test="${chapter.chapterIndex eq firstIndex}">
                            <a href="#" class="btn btn-outline-secondary btn-sm disabled">⟵ Prev Chapter</a>
                        </c:when>
                        <c:otherwise>
                            <a href="previousChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">
                                ⟵ Prev Chapter
                            </a>
                        </c:otherwise>
                    </c:choose>

                    <!-- Button để mở modal -->
                    <button type="button" class="btn bg-primary-subtle" data-bs-toggle="modal" data-bs-target="#chapterModal">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list"
                             viewBox="0 0 16 16">
                        <path fill-rule="evenodd"
                              d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5" />
                        </svg> ${chapter.chapterTitle}
                    </button>

                    <c:choose>
                        <c:when test="${chapter.chapterIndex eq lastIndex}">
                            <a href="#" class="btn btn-outline-secondary btn-sm disabled">Next Chapter ⟶</a>
                        </c:when>
                        <c:otherwise>
                            <a href="forwardChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">
                                Next Chapter ⟶
                            </a>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </main>
        <br>
        <script src="${pageContext.request.contextPath}/js/index.js"></script> 
        <script src="${pageContext.request.contextPath}/js/jQuery.js"></script>
        <jsp:include page="/WEB-INF/views/components/_footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>


<!-- Modal -->
<div class="modal fade" id="chapterModal" tabindex="-1" aria-labelledby="chapterModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md">
        <div class="modal-content">

            <!-- Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="chapterModalLabel">List of Chapters</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>

            <!-- Body -->
            <div class="modal-body">
                <ul class="list-group">
                    <c:forEach var="chapterOfList" items="${chapters}">
                        <li class="list-group-item <c:if test="${chapterOfList.chapterIndex eq chapter.chapterIndex}">active</c:if>">Chapter ${chapterOfList.chapterIndex}</li>
                        </c:forEach>
                </ul>
            </div>

            <!-- Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

