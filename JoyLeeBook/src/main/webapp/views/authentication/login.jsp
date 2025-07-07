<%-- 
    Document   : login
    Created on : Jun 29, 2025, 12:30:13 AM
    Author     : minhp
--%>


<div id="sub-authentication-layout" class="login-layout w-50 sub-authentication-layout">
    <div class="login-header w-100 mb-4">
        <h1 class="text-center" name="login">WELCOME BACK</h1>
    </div>
    <div class="login-body w-100 d-flex flex-column gap-4 align-items-center justify-content-center">
        <p class="w-75 mb-2">It's great to see you again. We hope you will have an enjoyable and chill moment here.</p>
        <p class="w-75">Don't have an account?</p>
        <a href="/register" class="w-75 d-flex align-items-center justify-content-center">SIGNUP</a>
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