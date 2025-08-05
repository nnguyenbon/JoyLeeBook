<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thông báo lỗi</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }
            .error-box {
                background: #fff;
                border: 1px solid #ddd;
                border-left: 5px solid #dc3545;
                padding: 30px;
                max-width: 600px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }
            .error-title {
                font-size: 1.5rem;
                color: #dc3545;
                margin-bottom: 15px;
            }
            .error-message {
                font-size: 1rem;
                color: #333;
            }
            .btn-back {
                display: inline-block;
                margin-top: 20px;
                text-decoration: none;
                background: #007bff;
                color: #fff;
                padding: 10px 20px;
                border-radius: 4px;
            }
            .btn-back:hover {
                background: #0056b3;
            }
        </style>
    </head>
    <body>

        <div class="error-box">
            <div class="error-title">Đã xảy ra lỗi!</div>

            <div class="error-message">
                <div class="error-message">
                    ${errorMessage != null ? errorMessage : (error != null ? error : "Hệ thống gặp sự cố. Hãy thử lại sau")}
                </div>
            </div>
            <a href="javascript:history.back()" class="btn-back">Quay lại</a>
        </div>
    </body>
</html>
