<%-- 
    Document   : register
    Created on : Jun 29, 2025, 12:30:24 AM
    Author     : minhp
--%>

<div id="authentication-layout" class="register-layout w-50 authentication-layout">
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

<div id="sub-authentication-layout" class="register-layout w-50 sub-authentication-layout">
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



