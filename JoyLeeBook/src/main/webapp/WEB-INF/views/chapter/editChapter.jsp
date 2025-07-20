<%--
    Document   : editChapter
    Created on : Jul 8, 2025, 1:53:43 PM
    Author     : NguyenNTCE191135
--%>

<%@page import="model.Chapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis()%>">
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
            .edit-form-container {
                max-width: 1150px;
                margin: auto;
                padding: 2rem 1rem;
                background-color: white;
            }
        </style>
    </head>
    <body>

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
            <div class="edit-form-container border border-2 rounded-4 p-4 bg-white my-5">
                <h3 class="fw-bold mb-4">Edit <span class="text-primary">Chapter ${chapter.chapterIndex} - ${chapter.seriesTitle}</span></h3>

                <% String message = (String) request.getAttribute("message"); %>
                <% if (message != null) {%>
                <div class="alert alert-danger"><%= message%></div>
                <% }%>
                <form action="updateChapter" method="post">
                    <input type="hidden" name="seriesId" value="${chapter.seriesId}">
                    <input type="hidden" name="chapterId" value="${chapter.chapterId}">
                    <input type="hidden" name="seriesTitle" value="${chapter.seriesTitle}">

                    <div class="mb-3">
                        <label for="chapterIndex" class="form-label fw-bold">Chapter Index <span class="text-danger">*</span></label>
                        <input type="number" class="form-control" id="chapterIndex" name="chapterIndex" value="${chapter.chapterIndex}" required min="0">
                    </div>
                    <div class="mb-3">
                        <label for="chapterTitle" class="form-label fw-bold">Chapter Title</label>
                        <input type="text" class="form-control" id="chapterTitle" name="chapterTitle" value="${chapter.chapterTitle}" maxlength="50">
                    </div>

                    <div class="mb-3">
                        <label for="chapterContent" class="form-label fw-bold">Chapter Content <span class="text-danger">*</span></label>
                        <textarea class="form-control" id="chapterContent" name="chapterContent" required rows="50">${chapter.content}</textarea>
                    </div>

                    <div class="d-flex justify-content-center">
                        <button type="submit" class="btn btn-primary mx-3"> Save </button>
                        <a href="readChapter?chapterId=${chapter.chapterId}&seriesId=${chapter.seriesId}" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </main>



        <jsp:include page="/WEB-INF/views/footer/_footer.jsp" />

        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis()%>"></script>
        <!-- Bootstrap JS (optional for interactivity) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
