<%-- 
    Document   : register
    Created on : Jul 5, 2025, 10:40:21 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="../../css/styles.css">
    <title>LOGIN</title>
</head>

<body>
<header id="header" class="main-header p-3 d-flex align-items-center justify-content-center">
    <div class="main-context menu-layout">
        <div class="mobile-header w-100 d-flex align-items-center justify-content-center text-center">
            <h1 class="logo-header"><a href="/" class="text-white"><i>JoyLeeBook</i></a></h1>

            <label id="menu-icon" class="menu-icon d-none text-white fa-2x">&#9776;</label>
        </div>

        <div id="menu" class="menu w-100 d-none">
            <div class="search-bar pt-3 pb-3 ps-4 pe-3 d-flex align-items-center justify-content-center">
                <input class="search-field w-100 me-2" type="text" name="searching" placeholder="Search...">
                <button class="search-submit">
                    <i class="fas fa-search fa-2x"></i>
                </button>
                <div class="searching"></div>
            </div>

            <!-- Authentication form -->
            <div class="user-profile-layout">
                <div class="align-items-center justify-content-center">
                    <i id="user-profile-icon" class="user-profile-icon fas fa-user-circle fa-3x"></i>
                </div>

                <div id="user-profile" class="user-profile mt-2 mb-2 align-items-center justify-content-center">
                    <a href="#userLibrary"
                       class="user-library page-hover w-100 p-2 d-flex align-items-center justify-content-center">User
                        Library</a>
                    <a href="#historyReading"
                       class="history-reading page-hover w-100 p-2 d-flex align-items-center justify-content-center">History
                        Reading</a>
                    <a href="#logOut" class="log-out page-hover w-100 p-2 d-flex align-items-center justify-content-center">Log
                        Out</a>
                </div>
            </div>
        </div>
    </div>
</header>

<main class="main-main d-flex align-items-center justify-content-center">
    <div class="content mt-4 mb-4 d-flex align-items-center justify-content-center">

        <div id="authentication-layout" class="register-layout authentication-layout w-50 d-flex flex-column align-items-center justify-content-center">
            <div class="register-header w-100 mb-4">
                <h1 class="text-center">SIGNUP</h1>
            </div>

            <div class="register-body w-100">
                <form action="/register" method="POST" class="w-100 d-flex gap-4 flex-column align-items-center justify-content-center">
                    <input type="email" class="w-75 ps-4" name="email" placeholder="Email" required="">
                    <input type="text" class="w-75 ps-4" name="username" placeholder="Username">
                    <input type="password" class="w-75 ps-4" name="password" placeholder="Password">
                    <input type="submit" class="w-75" value="SIGNUP" />
                </form>
            </div>
        </div>

        <div id="sub-authentication-layout" class="register-layout sub-authentication-layout w-50 d-flex flex-column align-items-center justify-content-center">
            <div class="register-header w-100">
                <h3 class="text-center" name="register">WELCOME TO</h3>
                <h1 class="text-center" name="register"><i>JOYLEEBOOK</i></h1>
            </div>
            <div class="register-body w-100 d-flex flex-column gap-4 align-items-center justify-content-center">
                <p class="w-75 mb-3">The endless pleasure of immersing yourself in books will lead you to the garden of peace for soul.</p>
                <p class="w-75">Already have an account?</p>
                <a href="/login" class="w-75 d-flex align-items-center justify-content-center">LOGIN</a>
            </div>
        </div>


    </div>
</main>

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

    <div class="main-context social-footer w-100 h-100 p-2 d-flex justify-content-center align-items-center">
        <div class="left-social-footer w-100 d-flex justify-content-end me-3"></div>
        <i class="instagram-icon fab fa-instagram fa-2x"></i>
        <i class="facebook-icon fab fa-facebook fa-2x m-3"></i>
        <i class="tiktok-icon fab fa-tiktok fa-2x"></i>
        <div class="right-social-footer w-100 d-flex justify-content-end ms-3">
            <!-- View Page -->
            <a href="#header" class="d-none align-items-center justify-content-center"><i class="fas fa-arrow-up fa-md"></i></a>
        </div>
    </div>
</footer>

<script src="../../js/index.js"></script>

</body>

</html>
