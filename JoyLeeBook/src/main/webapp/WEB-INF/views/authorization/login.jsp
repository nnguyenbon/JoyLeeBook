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
     */

    String[] pageTypeArray = ((String) request.getRequestURI()).split("/");
    String pageType = pageTypeArray[pageTypeArray.length - 1].replace(".jsp", "");

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
        <title>LOGIN</title>
    </head>

    <body>
        <jsp:include page="/views/header/_header.jsp" />

        <main class="main-main d-flex align-items-center justify-content-center">
            <div class="content mt-4 mb-4 d-flex align-items-center justify-content-center">

                <div id="sub-authentication-layout" class="login-layout w-50 sub-authentication-layout">
                    <div class="login-header w-100 mb-4">
                        <h1 class="text-center" name="login">WELCOME BACK</h1>
                    </div>
                    <div class="login-body w-100 d-flex flex-column gap-4 align-items-center justify-content-center">
                        <p class="w-75 mb-2">It's great to see you again. We hope you will have an enjoyable and chill moment here.</p>
                        <p class="w-75">Don't have an account?</p>
                        <a href="register.jsp" class="w-75 d-flex align-items-center justify-content-center">SIGNUP</a>
                    </div>
                </div>

                <div id="authentication-layout" class="login-layout w-50 authentication-layout">
                    <div class="login-header w-100 mb-4">
                        <h1 class="text-center">LOGIN</h1>
                    </div>

                    <div class="login-body w-100">
                        <form action="/login" method="POST" class="w-100 d-flex flex-column gap-4 align-items-center justify-content-center">
                            <input type="text" class="w-75 ps-4" name="username" placeholder="Username">
                            <input type="password" class="w-75 ps-4" name="password" placeholder="Password">
                            <input type="submit" class="w-75" value="LOGIN" />
                        </form>
                    </div>
                </div>

            </div>

        </main>

        <jsp:include page="/views/footer/_footer.jsp" />
        <script lang="text/javascript" src="${pageContext.request.contextPath}/js/index.js?v=<%= System.currentTimeMillis()%>"></script>

    </body>

</html>

