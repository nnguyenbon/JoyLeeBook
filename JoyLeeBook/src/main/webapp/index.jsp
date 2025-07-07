<%@ page language="java" 
         contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8"
         session="true"
         errorPage=""
         isErrorPage="false" %>

<%@ page import="java.util.ArrayList, model.Series" %>

<%
    /*
    Note:
    pageType: Name of folder contained main page
    */
    
    String[] pageTypeArray = ((String) request.getRequestURI()).split("/");
    String pageType = pageTypeArray.length > 3 ? pageTypeArray[pageTypeArray.length - 2] : "home";

    request.setAttribute("pageType", pageType);

%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis() %>">
        <title><%= pageType.toUpperCase() %></title>
    </head>

    <body>
        <jsp:include page="/views/header/_header.jsp" />

        <main class="main-main d-flex align-items-center justify-content-center">
            <div class="content m-5 d-flex flex-column align-items-center justify-content-center">
                <div class="header-main w-100 p-3 d-flex align-items-center justify-content-center text-white">
                    <h3>Last Released</h3>
                </div>

                <div class="body-main w-100 pt-5 pb-4 ps-5 pe-5 d-flex align-items-center justify-content-center">
                    <%
                        ArrayList<Series> series = new ArrayList<>();
                        
                        for (Series story : series) {
                    %>
                    <div class="story">
                        <img class="w-100" src="${story.getCoverImageUrl()}" alt="">
                        <h6 class="w-100 p-2">${story.getSeriesTitle()}</h6>
                        <div class="lasted-chapter d-flex align-items-center justify-content-evenly">
                            <p class="chapter-volumn">Ch. ${story.getTotalChapters()}</p>
                            <p class="lasted-time"><i>${story.getCreatedAt()} ago</i></p>
                        </div>
                    </div>

                    <% }%>
                </div>

                <%
                    int totalSeries = series.size();
                    String currentPage = request.getParameter("page") != null && request.getParameter("page").matches("\\d+") ? (String) request.getParameter("page") : "1" ;
                    
                    
                %>
                <div id="page-navigation"
                     class="page-navigation w-100 pb-4 d-flex align-items-center justify-content-center">
                </div>
                <%%>
            </div>
        </main>

        <jsp:include page="/views/footer/_footer.jsp" />

        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis() %>"></script>
    </body>

</html>