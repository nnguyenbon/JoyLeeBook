<%-- 
    Document   : editSeries
    Created on : Jul 5, 2025, 10:40:54 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Edit - Azure Realm</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="css/style.css">
        <!-- Select2 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />

    </head>

    <body class="bg-white">
        <header>
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg border-bottom sticky-top">
                <div class="container">
                    <a class="navbar-brand fw-bold text-white" href="#"><i class="bi bi-book"></i>
                        <strong>JoyLeeBook</strong></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav me-auto">
                            <li class="nav-item"><a class="nav-link  text-white" href="homeAdmin.html">Home</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="genre.html">Genres</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="#">History</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="#">Library</a></li>
                        </ul>
                        <form class="d-flex me-3">
                            <input class="form-control me-2" type="search" placeholder="Search manga..."
                                   aria-label="Search">
                        </form>
                        <a class="btn me-2 login" href="#">LOGIN</a>
                        <a class="btn signup " href="#">SIGN UP</a>
                    </div>
                </div>
            </nav>
        </header>
        <main>
            <div class="container my-5">
                <h3 class="fw-bold mb-4">Edit Story</h3>
                <!-- Thông báo từ Servlet -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${successMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <form action="updateSeries" method="post" enctype="multipart/form-data" id="editStoryForm">
                    <input type="hidden" name="seriesId" value="${series.seriesId}">
                    <div class="row g-4">
                        <!-- Manga Cover Preview -->
                        <!-- <div class="col-md-4">
                        
                    </div> -->
                        <div class="col-md-4">

                            <label class="form-label fw-semibold">Cover image</label><br>
                            <input type="hidden" name="coverImageUrl" value="${series.coverImageUrl}">
                            <img src="${pageContext.request.contextPath}/${series.coverImageUrl}"
                                 class="img-fluid rounded shadow mb-2" alt="Cover" style="width: 350px">
                            <div class="mb-3">
                                <input class="form-control" type="file" id="coverImage" name="coverImage" accept="image/*" style="width: 350px">
                            </div>
                        </div>

                        <!-- Manga Info Fields -->
                        <div class="col-md-8">
                            <div class="mb-3">
                                <label class="form-label">Story name</label>
                                <input type="text" class="form-control" name="seriesTitle" value="${series.seriesTitle}"
                                       required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Author</label>
                                <input type="text" class="form-control" name="authorName" value="${series.authorName}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Status</label>
                                <select class="form-select" name="status">
                                    <option value="0" <c:if test="${series.status eq '0'}">Selected</c:if>>Ongoing</option>
                                    <option value="1" <c:if test="${series.status eq '1'}">Selected</c:if>>Completed</option>
                                    <option value="2" <c:if test="${series.status eq '2'}">Selected</c:if>>Hiatus</option>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Genres</label>
                                    <select class="form-select" id="genreSelect" name="genres" multiple required>
                                    <c:forEach var="availableGenre" items="${genres}">
                                        <option value="${availableGenre.genreId}"
                                                <c:forEach var="seriesGenre" items="${series.genres}">
                                                    <c:if test="${availableGenre.genreId eq seriesGenre.genreId}">
                                                        selected
                                                    </c:if>
                                                </c:forEach>>
                                            ${availableGenre.genreName}
                                        </option>
                                    </c:forEach>
                                </select>

                            </div>



                            <div class="mb-3">
                                <label class="form-label">Description</label>
                                <textarea class="form-control" rows="5"
                                          name="description" required>${series.description}</textarea>
                            </div>

                            <!-- Action Buttons -->
                            <div class="d-flex gap-2">
                                <button type="submit" class="btn btn-success">Save</button> 
                                <!--<a href="detailAdmin.html">Lưu</a>-->
                                <button type="button" class="btn btn-secondary" onclick="customReset()">Reset</button>
                                <button type="button" class="btn btn-danger"
                                        onclick="confirm('Xác nhận xoá truyện này?') && deleteStory();">Delete</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </main>

        <footer class="mt-5">
            <!-- Section About -->
            <div class="text-white py-4" style="background-color: #8DA7C0;">
                <div class="container text-center">
                    <h5 class="fw-bold mb-3">ABOUT US</h5>
                    <p class="mx-auto mb-0" style="max-width: 1000px;">
                        JoyLeeBook is a Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                        incididunt ut
                        labore et dolore magna aliqua.
                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                        consequat.
                        Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                        pariatur.
                    </p>
                </div>
            </div>

            <!-- Section Social + Back to top -->
            <div class="py-3 w-100" style="background-color: #517594;  justify-self: center;">
                <div class="container d-flex flex-column flex-md-row justify-content-between align-items-center">

                    <!-- Social icons -->
                    <div class="offset-md-2 col-md-7 d-flex justify-content-center gap-4 mb-3 mb-md-0">
                        <a href="#" class="text-dark fs-4"><i class="fab fa-instagram"></i></a>
                        <a href="#" class="text-dark fs-4"><i class="fab fa-facebook"></i></a>
                        <a href="#" class="text-dark fs-4"><i class="fab fa-tiktok"></i></a>
                    </div>

                    <!-- Back to top -->
                    <a href="#header" class="btn btn-light rounded-circle d-flex align-items-center justify-content-center"
                       style="width: 40px; height: 40px;">
                        <i class="fas fa-arrow-up text-dark"></i>
                    </a>
                </div>
            </div>
        </footer>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- jQuery + Select2 JS -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

        <script>
                                            let initialGenres = [];

                                            function deleteStory() {

                                                const form = document.createElement('form');
                                                form.method = 'POST';
                                                form.action = 'deleteSeries';

                                                const input = document.createElement('input');
                                                input.type = 'hidden';
                                                input.name = 'seriesId';
                                                input.value = "${series.seriesId}";
                                                form.appendChild(input);

                                                document.body.appendChild(form);
                                                form.submit();
                                            }
        </script>
    </body>

</html>
