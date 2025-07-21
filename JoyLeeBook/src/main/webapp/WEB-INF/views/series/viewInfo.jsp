<%@page import="model.Genre"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ page import="java.util.List, model.Series, model.Chapter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>${fn:escapeXml(series.seriesTitle)}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
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
        <jsp:include page="/WEB-INF/views/components/_header.jsp"/>

        <main>
            <div class="container mt-5">

                <div class="row g-1 border border-2 rounded-4 p-4 bg-white" style="background-color: #f7f8f9">
                    <div class="col-md-4 m-0 p-0">
                        <img src="${pageContext.request.contextPath}/${series.coverImageUrl}"
                             class="img-fluid rounded shadow" alt="${series.seriesTitle}" style="width: 370px;" />
                    </div>
                    <div class="col-md-8 pr-3">
                        <h1 class="fw-bold fs-1 text-primary">${fn:escapeXml(series.seriesTitle)}</h1>
                        <h5 class="text-muted">By <strong>${fn:escapeXml(series.authorName)}</strong></h5>
                        <p class="my-4">Status:
                            <c:choose>
                                <c:when test="${series.status eq 1}">
                                    <span class="text-success">Completed</span>
                                </c:when>
                                <c:when test="${series.status eq 0}">
                                    <span class="text-warning">Ongoing</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">Unknown</span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <div class="mb-3">
                            <h6>GENRE</h6>
                            <c:if test="${not empty series.genres}">
                                <c:forEach var="i" begin="0" end="${fn:length(series.genres) - 1}">
                                    <span class="badge genre-badge ${colors[i % 5]}">${fn:escapeXml(series.genres[i].genreName)}</span>
                                </c:forEach>
                            </c:if>
                        </div>
                        <div class="my-3">
                            <h6 class="fw-bold">Summary</h6>
                            <p class="mb-0">${fn:escapeXml(series.description)}</p>
                        </div>
                        <c:choose>
                            <c:when test="${sessionScope.loggedInUser != null and sessionScope.loggedInUser.roleName eq 'admin'}">
                                <div class="d-flex gap-2 my-5">
                                    <a class="btn btn-warning" href="${pageContext.request.contextPath}/updateSeries?seriesId=${series.seriesId}"><i class="bi bi-pen"></i> Edit</a>
                                    <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteModal"
                                            data-id="${series.seriesId}" data-type="series" data-url="deleteSeries"><i class="bi bi-trash3-fill"></i> Delete</button>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="d-flex gap-2 my-5">
                                    <c:if test="${not empty listChapter}">
                                        <a class="btn btn-primary text-white" href="${pageContext.request.contextPath}/readChapter?chapterIndex=${listChapter[0].chapterIndex}&seriesId=${series.seriesId}"><i class="bi bi-book"></i> Start Reading</a>
                                    </c:if>
                                    <c:if test="${sessionScope.role != null and sessionScope.role eq 'user'}">
                                        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/saveSeries?seriesId=${series.seriesId}"><i class="bi bi-bookmark"></i> Add Library</a>
                                    </c:if>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="mt-5 border border-2 rounded-4 p-4 m-0 g-4" style="background-color: #ffffff">
                    <div class="d-flex justify-content-between align-items-center mb-4 mx-2">
                        <div>
                            <h4 class="fw-bold">Chapters</h4>
                            <h5 class="text-muted small mt-1">Total: ${fn:length(listChapter)}</h5>
                        </div>
                        <c:if test="${sessionScope.loggedInUser != null and sessionScope.loggedInUser.roleName eq 'admin'}">
                            <a href="${pageContext.request.contextPath}/addChapter" class="btn btn-success">+ Add new chapter</a>
                        </c:if>
                    </div>
                    <c:if test="${not empty sessionScope.message}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            ${fn:escapeXml(sessionScope.message)}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <c:remove var="message" scope="session"/>
                    </c:if>
                    <div class="container my-4 py-4" style="max-height: 500px; overflow-y: auto">
                        <c:if test="${empty listChapter}">
                            <p class="text-muted text-center">No chapters available for this series.</p>
                        </c:if>
                        <c:forEach var="chapter" items="${listChapter}">
                            <div class="chapter-row d-flex justify-content-between align-items-center px-5 py-2 mb-3 rounded-3 border position-relative"
                                 onclick="window.location = '${pageContext.request.contextPath}/readChapter?chapterIndex=${chapter.chapterIndex}&seriesId=${chapter.seriesId}'" style="cursor: pointer">
                                <div class="d-flex align-items-center gap-5">
                                    <h6 class="fw-bold">Ch. ${chapter.chapterIndex}</h6>
                                    <div>
                                        <div class="d-flex align-items-center gap-2">
                                            <p class="chapter-title fw-semibold text-decoration-none text-dark">${fn:escapeXml(chapter.chapterTitle)}</p>
                                        </div>
                                        <div class="text-muted small mt-1"><fmt:formatDate value="${chapter.createdAt}" pattern="dd/MM/yyyy" /></div>
                                    </div>
                                </div>
                                <c:choose>
                                    <c:when test="${sessionScope.loggedInUser != null and sessionScope.loggedInUser.roleName eq 'admin'}">
                                        <div class="action-buttons gap-2">
                                            <a href="${pageContext.request.contextPath}/updateChapter?chapterId=${chapter.chapterId}" class="btn btn-sm btn-outline-warning" onclick="event.stopPropagation()"><i class="bi bi-pen"></i></a>
                                            <button type="button" class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteModal"
                                                    data-id="${chapter.chapterId}" data-series-id="${chapter.seriesId}" data-type="chapter" data-url="deleteChapter" onclick="event.stopPropagation()">
                                                <i class="bi bi-trash3-fill"></i>
                                            </button>
                                        </div>
                                    </c:when>
                                </c:choose>

                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </main>

        <script src="${pageContext.request.contextPath}/js/index.js"></script> 
        <script src="${pageContext.request.contextPath}/js/jQuery.js"></script>
        <jsp:include page="/WEB-INF/views/components/_footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-md modal-dialog-centered">
                <div class="modal-content">

                    <div class="modal-body text-center">
                        <p class="fs-5">Are you sure you want to <strong class="text-danger">delete</strong> this item?</p>
                        <p class="text-muted small">This action cannot be undone.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <form id="deleteForm" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="" id="deleteId" />
                            <input type="hidden" name="seriesId" id="seriesIdHidden" />
                            <button type="submit" class="btn btn-danger">Yes, Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                        const deleteModal = document.getElementById('deleteModal');
                        deleteModal.addEventListener('show.bs.modal', function (event) {
                            const button = event.relatedTarget;
                            const itemId = button.getAttribute('data-id');
                            const itemType = button.getAttribute('data-type');
                            const url = button.getAttribute('data-url');
                            const seriesId = button.getAttribute('data-series-id');

                            const input = deleteModal.querySelector('#deleteId');
                            input.name = itemType + "Id";
                            input.value = itemId;

                            const seriesInput = deleteModal.querySelector('#seriesIdHidden');
                            seriesInput.value = (itemType === 'chapter' && seriesId) ? seriesId : '';

                            const form = deleteModal.querySelector('#deleteForm');
                            form.action = url;
                        });
        </script>


    </body>
</html>
</body>
</html>
