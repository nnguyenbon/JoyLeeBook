<%@ page language="java" 
         contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8"
         session="true"
         errorPage=""
         isErrorPage="false" %>
<%
    /*
    Note:
    pageType: Name of folder contained main page
    authenticationType: Name of sub-page included (Login/Register)
    */
    
    String[] pageTypeArray = ((String) request.getRequestURI()).split("/");
    String pageType = pageTypeArray.length > 3 ? pageTypeArray[pageTypeArray.length - 2] : "home";

    // Set folder contained main page
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
        <title><%= pageType.toUpperCase()%></title>
    </head>

    <body>
        <jsp:include page="/views/header/_header.jsp" />

        <main class="main-main d-flex align-items-center justify-content-center">
            <div class="content mt-4 mb-4 d-flex align-items-center justify-content-center">

                <% if (((String) request.getAttribute("authenticationType")).equals("register")) { %>
                <jsp:include page="/views/authentication/register.jsp" />
                <% } else {%>
                <jsp:include page="/views/authentication/login.jsp" />
                <% }%>

            </div>

        </main>

        <jsp:include page="/views/footer/_footer.jsp" />
        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis() %>"></script>

    </body>

</html>