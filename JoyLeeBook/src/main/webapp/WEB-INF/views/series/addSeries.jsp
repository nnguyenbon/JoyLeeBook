<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null || !"admin".equals(user.getRoleName())) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Series</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body class="bg-light">
<jsp:include page="/WEB-INF/views/components/_header.jsp"/>

<main class="container my-5">
    <div class="bg-white p-4 p-md-5 rounded shadow-sm">
        <h3 class="fw-bold mb-4 border-bottom pb-3">Add New Series</h3>

        <%-- Hiển thị thông báo lỗi nếu có --%>
        <c:if test="${not empty message}">
            <div class="alert alert-danger">${message}</div>
        </c:if>

        <form id="addSeriesForm" action="${pageContext.request.contextPath}/addSeries" method="post"
              enctype="multipart/form-data">
            <div class="row g-5">

                <div class="col-md-4">
                    <label for="coverImage" class="form-label fw-semibold">Cover Image</label>
                    <img id="imagePreview" src="https://via.placeholder.com/350x500.png?text=Cover+Image"
                         class="img-fluid rounded shadow-sm mb-2" alt="Cover preview"
                         style="width: 100%; aspect-ratio: 7/10; object-fit: cover;">
                    <input class="form-control" type="file" id="coverImage" name="coverImage" accept="image/*" required>
                </div>

                <div class="col-md-8">
                    <div class="mb-3">
                        <label for="seriesTitle" class="form-label">Title</label>
                        <input type="text" class="form-control" id="seriesTitle" name="seriesTitle" required>
                    </div>

                    <div class="mb-3">
                        <label for="authorName" class="form-label">Author</label>
                        <input type="text" class="form-control" id="authorName" name="authorName" required>
                    </div>

                    <div class="mb-3">
                        <label for="seriesStatus" class="form-label">Status</label>
                        <select class="form-select" id="seriesStatus" name="seriesStatus">
                            <option value="0" selected>Ongoing</option>
                            <option value="1">Completed</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="genreSelect" class="form-label">Genres</label>
                        <select class="form-select" id="genreSelect" name="genres" style="width: 100%;"
                                multiple="multiple" required>
                            <c:forEach items="${genres}" var="genre">
                                <option value="${genre.genreId}">${genre.genreName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="seriesDescription" class="form-label">Description</label>
                        <textarea class="form-control" id="seriesDescription" rows="5" name="seriesDescription"
                                  required></textarea>
                    </div>

                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-primary">Save Series</button>
                        <a href="${pageContext.request.contextPath}/adminDashboard" class="btn btn-secondary">Cancel</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
</main>

<jsp:include page="/WEB-INF/views/components/_footer.jsp"/>

<script>
    // Script để khởi tạo Select2 và preview ảnh
    $(document).ready(function () {
        $('#genreSelect').select2({
            placeholder: "Select one or more genres",
            allowClear: true
        });

        $('#coverImage').on('change', function (event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    $('#imagePreview').attr('src', e.target.result);
                };
                reader.readAsDataURL(file);
            }
        });
    });
</script>
</body>
</html>