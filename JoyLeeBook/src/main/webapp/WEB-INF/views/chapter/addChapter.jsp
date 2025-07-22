<%--
    Document   : editChapter
    Created on : Jul 8, 2025, 1:53:43 PM
    Author     : NguyenNTCE191135
--%>


<%@page import="model.User"%>
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
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null || !"admin".equals(user.getRoleName())) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
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

        <jsp:include page="/WEB-INF/views/components/_header.jsp" />
        
        <main class="flex-grow-1 ">
            <div class="edit-form-container border border-2 rounded-4 p-4 bg-white my-5">
                <h3 class="fw-bold mb-4">Add new chapter <span class="text-primary">${series.seriesTitle}</span></h3>

                <% String message = (String) request.getAttribute("message"); %>
                <% if (message != null) {%>
                <div class="alert alert-danger"><%= message%></div>
                <% }%>
                <form action="addChapter" method="post">
                    <input type="hidden" name="seriesId" value="${series.seriesId}">

                    <div class="mb-3">
                        <label for="chapterIndex" class="form-label fw-bold">Chapter Index <span class="text-danger">*</span></label>
                        <input type="number" class="form-control" id="chapterIndex" name="chapterIndex" value="" required min="0">
                    </div>
                    <div class="mb-3">
                        <label for="chapterTitle" class="form-label fw-bold">Chapter Title</label>
                        <input type="text" class="form-control" id="chapterTitle" name="chapterTitle" value="" maxlength="50">
                    </div>

                    <div class="mb-3">
                        <label for="chapterContent" class="form-label fw-bold">Chapter Content <span class="text-danger">*</span></label>
                        <textarea class="form-control" id="chapterContent" name="content" required rows="50"></textarea>
                    </div>

                    <div class="d-flex justify-content-center">
                        <button type="submit" class="btn btn-primary mx-3"> Save </button>
                        <a href="viewSeriesInfo?seriesId=${series.seriesId}" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </main>

        <br>
        <script src="${pageContext.request.contextPath}/js/index.js"></script> 
        <script src="${pageContext.request.contextPath}/js/jQuery.js"></script>
        <jsp:include page="/WEB-INF/views/components/_footer.jsp" />

        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis()%>"></script>
        <!-- Bootstrap JS (optional for interactivity) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
