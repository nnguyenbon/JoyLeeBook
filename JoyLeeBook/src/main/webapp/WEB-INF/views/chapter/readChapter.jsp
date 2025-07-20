<%@page import="model.Chapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>JoyLeeBook</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/style.css">

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
            .reader-container {
                max-width: 1150px;
                margin: auto;
                padding: 2rem 1rem;
                background-color: white;
            }


        </style>
    </head>

    <body class="d-flex flex-column min-vh-100">

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

        <main class="flex-grow-1 ">
            <div class="reader-container border border-2 rounded-4 p-4 bg-white my-5">
                <div class="d-flex align-items-center">
                    <h4 class=" mb-4 mx-4">Read</h4> <a href="viewSeriesInfo?seriesId=${chapter.seriesId}" class="text-primary fw-bold fs-4 mx-4 mb-4 link-offset-2 link-underline link-underline-opacity-0">Chapter ${chapter.chapterIndex} - ${chapter.seriesTitle}</a>
                </div>
                <c:if test="${fn:length(listChapter) gt 1}">
                    <!-- Nội dung chỉ hiện khi có hơn 1 chương -->
                    <div class="nav-reader row d-flex align-items-center text-center">

                        <!-- Prev Chapter -->
                        <div class="col-md-2">
                            <c:choose>
                                <c:when test="${chapter.chapterIndex eq firstIndex}">
                                    <a href="#" class="btn btn-outline-secondary btn-sm d-none">⟵ Prev Chapter</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="previousChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">
                                        ⟵ Prev Chapter
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <!-- Chapter title center with modal -->
                        <div class="col-md-8 d-flex justify-content-center">
                            <button type="button" class="btn bg-primary-subtle" data-bs-toggle="modal" data-bs-target="#chapterModal">
                                <i class="bi bi-list"></i> Chapter ${chapter.chapterIndex}
                            </button>
                        </div>

                        <!-- Next Chapter -->
                        <div class="col-md-2">
                            <c:choose>
                                <c:when test="${chapter.chapterIndex eq lastIndex}">
                                    <a href="#" class="btn btn-outline-secondary btn-sm d-none">Next Chapter ⟶</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="forwardChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">
                                        Next Chapter ⟶
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:if>


                <!-- Web novel reading content -->
                <div class="my-3" style="line-height: 1.8; font-size: 1.2rem;">
                    <p>
                        ${chapter.content}
                    </p>
                </div>

                <c:if test="${fn:length(listChapter) gt 1}">
                    <!-- Nội dung chỉ hiện khi có hơn 1 chương -->
                    <div class="nav-reader row d-flex align-items-center text-center">

                        <!-- Prev Chapter -->
                        <div class="col-md-2">
                            <c:choose>
                                <c:when test="${chapter.chapterIndex eq firstIndex}">
                                    <a href="#" class="btn btn-outline-secondary btn-sm d-none">⟵ Prev Chapter</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="previousChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">
                                        ⟵ Prev Chapter
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <!-- Chapter title center with modal -->
                        <div class="col-md-8 d-flex justify-content-center">
                            <button type="button" class="btn bg-primary-subtle" data-bs-toggle="modal" data-bs-target="#chapterModal">
                                <i class="bi bi-list"></i> ${chapter.chapterTitle}
                            </button>
                        </div>

                        <!-- Next Chapter -->
                        <div class="col-md-2">
                            <c:choose>
                                <c:when test="${chapter.chapterIndex eq lastIndex}">
                                    <a href="#" class="btn btn-outline-secondary btn-sm d-none">Next Chapter ⟶</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="forwardChapter?chapterId=${chapter.chapterId}" class="btn btn-outline-secondary btn-sm">
                                        Next Chapter ⟶
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:if>
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
                <h5 class="modal-title" id="chapterModalLabel">List Chapters</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>

            <!-- Body -->
            <div class="modal-body">
                <ul class="list-group">
                    <c:forEach var="chapterOfList" items="${listChapter}">
                        <a href="readChapter?chapterIndex=${chapterOfList.chapterIndex}&seriesId=${chapterOfList.seriesId}" class="list-group-item list-group-item-action
                           <c:if test='${chapterOfList.chapterIndex eq chapter.chapterIndex}'>active</c:if>">
                            Chapter ${chapterOfList.chapterIndex}
                        </a>
                    </c:forEach>
                </ul>
            </div>

            <!-- Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>
