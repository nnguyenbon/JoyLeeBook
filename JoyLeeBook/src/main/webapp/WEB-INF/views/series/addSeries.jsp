<%-- 
    Document   : addSeries
    Created on : Jul 17, 2025, 10:40:42 AM
    Author     : HaiDD-dev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        <title>Add Series</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <!-- Select2 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <!-- Select2 JS -->
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <!-- Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    </head>

    <body class="bg-white">
        <header>
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg border-bottom sticky-top border border-danger">
                <div class="container d-flex border border-danger">
                    <a class="navbar-brand fw-bold text-white border border-danger" href="#"><strong>JoyLeeBook</strong></a>

                    <div class="navbar-collapse justify-content-end border border-danger" id="navbarNav">
                        <form class="d-flex me-3">
                            <input class="form-control me-2" type="search" placeholder="Search manga..." aria-label="Search">
                        </form>
                        <a class="btn me-2 login" href="#">LOGIN</a>
                        <a class="btn signup" href="#">SIGN UP</a>
                    </div>
                </div>
            </nav>
        </header>

        <main>
            <div class="container w-100 my-5 d-flex flex-column align-items-center justify-content-center">
                <h3 class="fw-bold w-100 mb-4">Add New Series</h3>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-3">${error}</div>
                </c:if>
                <form id="addSeriesForm" action="${pageContext.request.contextPath}/addSeries" method="post" enctype="multipart/form-data" class="w-100">
                    <div class="row g-4">

                        <!-- Cover Image Preview -->
                        <div class="col-md-4 me-2">
                            <label class="form-label fw-semibold">Cover Image</label><br>
                            <img src="" class="img-fluid rounded shadow mb-2" style="width: 350px">
                            <div class="mb-3">
                                <input class="form-control" type="file" id="coverImage" name="coverImage" accept="image/*" style="width: 350px">
                            </div>
                        </div>

                        <!-- Series Information Fields -->
                        <div class="col-md-8 w-100">
                            <div class="mb-3 d-flex flex-column">
                                <label class="form-label">Title</label>
                                <input type="text" class="form-control" name="seriesTitle" required>
                            </div>

                            <div class="mb-3 d-flex flex-column">
                                <label class="form-label">Author</label>
                                <input type="text" class="form-control" name="authorName">
                            </div>

                            <div class="mb-3 w-100 d-flex flex-column">
                                <label class="form-label">Status</label>
                                <select class="form-select w-100" name="seriesStatus">
                                    <option value="Ongoing" selected>Ongoing</option>
                                    <option value="Completed" >Completed</option>
                                </select>
                            </div>

                            <div class="mb-3 w-100 d-flex flex-column">
                                <label class="form-label">Genres</label>
                                <select class="form-select" id="genreSelect" name="genres[]" style="width: 100%;" multiple required>
                                    <c:forEach items="${genres}" var="genre">
                                        <option value="${genre.id}">${genre.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3 w-100 d-flex flex-column">
                                <label class="form-label">Description</label>
                                <textarea class="form-control" rows="5" name="seriesDescription"></textarea>
                            </div>

                            <!-- Action Buttons -->
                            <div class="d-flex gap-2">
                                <button type="submit" class="btn btn-success">Save</button>
                                <a href="${pageContext.request.contextPath}/adminDashboard" class="btn btn-secondary">Cancel</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </main>

        <jsp:include page="/WEB-INF/views/components/footer.jsp" />
        <script src="${pageContext.request.contextPath}/js/index.js"></script>
        <script>
            $(document).ready(function () {
                $('#genreSelect').select2({
                    placeholder: "Select genres"
                });

                $('#coverImage').on('change', function (event) {
                    const file = event.target.files[0];
                    if (file) {
                        const reader = new FileReader();
                        reader.onload = function (e) {
                            $('img').first().attr('src', e.target.result);
                        };
                        reader.readAsDataURL(file);
                    }
                });
            });
        </script>
    </body>
</html>