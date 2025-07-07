<%
    /*
    Note:
    pageType: Name of folder contained main page
    isLoggedIn: State of user who is logged in page or not
    */

    String pageType = (String) request.getAttribute("pageType");
    Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
    
    pageContext.setAttribute("width", "");
    pageContext.setAttribute("display", "d-flex");
    pageContext.setAttribute("justify", "justify-content-between");

    if (pageType.equals("authentication")) {
        pageContext.setAttribute("width", "w-100");
        pageContext.setAttribute("display", "d-none");
        pageContext.setAttribute("justify", "justify-content-center text-center");
    }

%>

<header id="header" class="main-header p-3 d-flex align-items-center justify-content-center">
    <div class="main-context menu-layout">
        <div class="mobile-header <%= pageContext.getAttribute("width")%> d-flex align-items-center <%= pageContext.getAttribute("justify")%>">
            <h1 class="logo-header"><a href="/" class="text-white"><i>JoyLeeBook</i></a></h1>

            <label id="menu-icon" class="menu-icon <% if (pageType.equals("authentication")) {%> <%= pageContext.getAttribute("display")%> <% } %> text-white fa-2x">&#9776;</label>
        </div>

        <div id="menu" class="menu w-100 <% if (pageType.equals("authentication")) {%> <%= pageContext.getAttribute("display")%> <% } %>">
            <div class="search-bar pt-3 pb-3 ps-4 pe-3 d-flex align-items-center justify-content-center">
                <input class="search-field w-100 me-2" type="text" name="searching" placeholder="Search...">
                <button class="search-submit">
                    <i class="fas fa-search fa-2x"></i>
                </button>
                <div class="searching"></div>
            </div>

            <!-- Authentication form -->
            <%
                if (isLoggedIn == null) {
                } else if (isLoggedIn) {
            %>
            <jsp:include page="./_loggedIn.jsp" />
            <% } else { %>
            <jsp:include page="./_unLoggedIn.jsp" />
            <% }%>
        </div>
    </div>
</header>
<%%>