<%-- 
    Document   : viewInfo
    Created on : Jul 7, 2025, 2:57:16 PM
    Author     : PhucTDMCE190744
--%>

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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=<%= System.currentTimeMillis()%>">
        <title>VIEW INFO</title>
    </head>

    <body>
        <jsp:include page="/views/header/_header.jsp" />

        <main class="main-main d-flex align-items-center justify-content-center">
            <div class="content series-content m-5 d-flex flex-column align-items-center justify-content-center">
                <h2 class="layout-title w-100 p-2 text-center border border-danger">RUN INTO YOU</h2>

                <div class="series-infor p-3 d-flex border border-danger">
                    <div class="cover-image me-2">
                        <img class="w-100" src="/infor_test/test_img.jpg" />
                    </div>
                    
                    <div class="series-infor-content ms-2 gap-4 d-flex flex-column">
                        <span class="update-time"><i>Updated at 2025-05-27 22:00:10</i></span>
                        
                        <div class="series-infor-author d-flex">
                            <label><b>Author:</b></label> <div class="ms-5"> Jonathan B. Hollan </div>
                        </div>
                        
                        <div class="series-infor-status d-flex">
                            <label><b>Status:</b></label> <div class="ms-5"> On Going </div>
                        </div>
                        
                        <div class="series-infor-genre d-flex">
                            <label><b>Genre:</b></label> <div class="ms-5"> Game, Action, Adventure, Romance, Harem, Women's fiction </div>
                        </div>
                        
                        <button class="favourite-btn mt-5 p-2" type="submit" name="favouriteBtn">FAVOURITE</button>
                    </div>
                </div>

            </div>
        </main>

        <jsp:include page="/views/footer/_footer.jsp" />

        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis()%>"></script>
    </body>

</html>