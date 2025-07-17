<%
    /*
    Note:
    pageType: Name of folder contained main page
     */

    String pageType = (String) request.getAttribute("pageType");

    if (pageType == null) {
        return;
    }

    pageContext.setAttribute("display", "d-flex");
    pageContext.setAttribute("justify", "justify-content-between");

    if (pageType.equals("login") || pageType.equals("register")) {
        pageContext.setAttribute("display", "d-none");
        pageContext.setAttribute("justify", "justify-content-center text-center");
    }

%>

<footer class="main-footer text-white">
    <div class="description-footer pt-2 pb-4 ps-2 pe-2 text-center">
        <h5 class="w-50 mx-auto m-3"><b>ABOUT US</b></h5>
        <p class="w-50 mx-auto">JoyLeeBook is a Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
            eiusmod tempor incididunt
            ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
            nisi ut
            aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
            cillum
            dolore
            eu fugiat nulla pariatur.</p>
    </div>

    <div class="main-context social-footer p-2 d-flex justify-content-center align-items-center">
        <div class="left-social-footer w-100 <%= pageContext.getAttribute("display")%> justify-content-end me-3"></div>
        <i class="instagram-icon fab fa-instagram fa-2x"></i>
        <i class="facebook-icon fab fa-facebook fa-2x m-3"></i>
        <i class="tiktok-icon fab fa-tiktok fa-2x"></i>
        <div class="right-social-footer w-100 <%= pageContext.getAttribute("display")%> justify-content-end ms-3">
            <!-- View Page -->
            <jsp:include page="_viewPage.jsp" />
        </div>
    </div>
</footer>