<%@ page language="java" 
         contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8"
         session="true"
         errorPage=""
         isErrorPage="false" %>

<%@ page import="java.util.List, model.Series" %>

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
                    <h3>LAST RELEASED</h3>
                </div>

                <div class="body-main w-100 pt-5 pb-4 ps-5 pe-5 d-flex align-items-center justify-content-center">
                    <div class="story">
                        <img class="w-100" src="/infor_test/test_img.jpg" alt="">
                        <h6 class="w-100 p-2">Komi Can't Communicate</h6>
                        <div class="lasted-chapter d-flex align-items-center justify-content-evenly">
                            <p class="chapter-volumn">Ch. 11</p>
                            <p class="lasted-time"><i>9m ago</i></p>
                        </div>
                    </div>
                    <%
                        int MAXIMUM_SERIES_IN_PAGE = 20;

                        List<Series> seriesList = (List<Series>) request.getAttribute("seriesList");
                        int seriesListSize = seriesList.size();

                        int currentPage = request.getParameter("page") == null 
                                || request.getParameter("page").equals("1") ? 1 : Integer.parseInt(request.getParameter("page"));
                                
                                
                        for (int i = (currentPage - 1) * MAXIMUM_SERIES_IN_PAGE; i < currentPage * MAXIMUM_SERIES_IN_PAGE; i++) {
                    %>
                    <div class="story">
                        <img class="w-100" src="<%= seriesList.get(i).getCoverImageUrl() %>" alt="">
                        <h6 class="w-100 p-2"><%= seriesList.get(i).getSeriesTitle()%></h6>
                        <div class="lasted-chapter d-flex align-items-center justify-content-evenly">
                            <p class="chapter-volumn">Ch. <%= seriesList.get(i).getTotalChapters() %></p>
                            <p class="lasted-time"><i><%= seriesList.get(i).getLatestChapterDate() %> ago</i></p>
                        </div>
                    </div>
                    <% }%>
                </div>

                <% 
                    int MAXIMUM_PAGE_NUMBER = seriesListSize / MAXIMUM_SERIES_IN_PAGE;
                    int nextPage = currentPage + 1 > MAXIMUM_PAGE_NUMBER ? MAXIMUM_PAGE_NUMBER : currentPage + 1;
                    
                %>
                <div id="page-navigation"
                     class="page-navigation w-100 pb-4 d-flex align-items-center justify-content-center">

                    <% if (currentPage >= 2) { %>
                    <a href="/?page=<%= currentPage - 1 %>" class="page-number d-flex align-items-center justify-content-center"><</a>
                    <a name="<%= currentPage - 1 %>" href="/?page=<%= currentPage - 1 %>" class="page-number d-flex align-items-center justify-content-center"><%= currentPage - 1 %></a>
                    <% }%>

                    <a name="${currentPage}" class="page-number page-number-active d-flex align-items-center justify-content-center"><%= currentPage %></a>

                    <% if (currentPage < MAXIMUM_PAGE_NUMBER) { %>
                    <a name="<%= currentPage + 1 %>" href="/?page=<%= currentPage + 1 %>" class="page-number d-flex align-items-center justify-content-center"><%= currentPage + 1 %></a>
                    <a href="/?page=<%= currentPage + 1 %>" class="page-number d-flex align-items-center justify-content-center">></a>
                    <% }%>
                </div>
            </div>
        </main>

        <jsp:include page="/views/footer/_footer.jsp" />

        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis() %>"></script>
    </body>

</html>