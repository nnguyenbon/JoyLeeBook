<%-- 
    Document   : footer
    Created on : Jul 20, 2025, 12:26:36 PM
    Author     : KHAI TOAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

   <div class="py-3 w-100" style="background-color: #517594;">
        <div class="container">
            <!-- Desktop layout -->
            <div class="d-none d-md-block">
                <div class="row align-items-center">
                    <!-- Empty column for balance -->
                    <div class="col-3"></div>
                    <!-- Social icons centered -->
                    <div class="col-6 text-center">
                        <a href="#" class="text-dark fs-4 me-4"><i class="fab fa-instagram"></i></a>
                        <a href="#" class="text-dark fs-4 me-4"><i class="fab fa-facebook"></i></a>
                        <a href="#" class="text-dark fs-4"><i class="fab fa-tiktok"></i></a>
                    </div>
                    <!-- Back to top button -->
                    <div class="col-3 text-end">
                        <button onclick="scrollToTop()" class="btn btn-light rounded-circle d-flex align-items-center justify-content-center ms-auto"
                                style="width: 40px; height: 40px;">
                            <i class="fas fa-arrow-up text-dark"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        function scrollToTop() {
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
        }
    </script>
</footer>