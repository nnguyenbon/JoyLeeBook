<%-- 
    Document   : readChapter
    Created on : Jul 5, 2025, 10:42:33 AM
    Author     : PC
--%>

<%@page import="model.Chapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>MangaVerse - Home</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="css/style.css">

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

        <%
            Chapter c = (Chapter) request.getAttribute("chapter");
            if (c == null) {
                out.println("Không nhận được chapter!");
            } else {
                out.println("Chapter nhận được: " + c.getChapterTitle());
            }
        %>


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
                            <li class="nav-item"><a class="nav-link  text-white" href="home.html">Home</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="genre.html">Genres</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="#">History</a></li>
                            <li class="nav-item"><a class="nav-link text-white" href="#">Library</a></li>
                        </ul>
                        <form class="d-flex me-3">
                            <input class="form-control me-2" type="search" placeholder="Search manga..." aria-label="Search">
                        </form>
                        <a class="btn me-2 login" href="#">LOGIN</a>
                        <a class="btn signup " href="#">SIGN UP</a>
                    </div>
                </div>
            </nav>
        </header>

        <main class="flex-grow-1">
            <div class="reader-container">
                <div class="nav-reader d-flex justify-content-between align-items-center">
                    <a href="previousChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">⟵ Prev Chapter</a>

                    <!-- Button để mở modal -->
                    <button type="button" class="btn bg-primary-subtle" data-bs-toggle="modal" data-bs-target="#chapterModal">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list"
                             viewBox="0 0 16 16">
                        <path fill-rule="evenodd"
                              d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5" />
                        </svg> ${chapter.chapterTitle}
                    </button>


                    <a href="forwardChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">Next Chapter ⟶</a>
                </div>

                <!-- Web novel reading content -->
                <div class="bg-light p-4 rounded shadow-sm m-3" style="line-height: 1.8; font-size: 1.1rem;">
                    <p>
                        ${chapter.content}
                    </p>
                </div>
                <div class="nav-reader d-flex justify-content-between align-items-center">
                    <a href="previousChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">⟵ Prev Chapter</a>

                    <!-- Button để mở modal -->
                    <button type="button" class="btn bg-primary-subtle" data-bs-toggle="modal" data-bs-target="#chapterModal">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list"
                             viewBox="0 0 16 16">
                        <path fill-rule="evenodd"
                              d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5" />
                        </svg> ${chapter.chapterTitle}
                    </button>


                    <a href="forwardChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">Next Chapter ⟶</a>
                </div>
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
                        Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
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
    </body>

</html>


<!-- Modal -->
<div class="modal fade" id="chapterModal" tabindex="-1" aria-labelledby="chapterModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md">
        <div class="modal-content">

            <!-- Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="chapterModalLabel">Danh Sách Chương</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>

            <!-- Body -->
            <div class="modal-body">

                <ul class="list-group">
                    <!-- Danh sách chương -->
                    <li class="list-group-item active">Chương 1 The Awakening</li>
                    <li class="list-group-item">Chương 2 Chronicles of the Azure Realm</li>
                    <li class="list-group-item">Chương 3 Chronicles of the Azure Realm</li>
                    <li class="list-group-item">Chương 4 Chronicles of the Azure Realm</li>
                    <li class="list-group-item">Chương 5 Chronicles of the Azure Realm</li>
                    <li class="list-group-item">Chương 6 Chronicles of the Azure Realm</li>
                    <li class="list-group-item">Chương 7 Chronicles of the Azure Realm</li>
                    <li class="list-group-item">Chương 8 Chronicles of the Azure Realm</li>
                    <li class="list-group-item">Chương 9</li>
                    <li class="list-group-item">Chương 10</li>
                    <li class="list-group-item">Chương 11</li>
                    <li class="list-group-item">Chương 12</li>
                    <li class="list-group-item">Chương 13</li>
                    <li class="list-group-item">Chương 14</li>
                </ul>
            </div>

            <!-- Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>