<%--
    Document   : viewProfile
    Created on : Jul 20, 2025, 5:13:22 PM
    Author     : NguyenNTCE191135
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ page import="model.User, model.HistoryReading" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/WEB-INF/views/components/_header.jsp" />
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <title>Profile</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css" />


        <style>
            body {
                background: linear-gradient(135deg, #eef4fe 0%, #d9e4f5 100%);
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .navbar {
                background: linear-gradient(to right, #517594, #6b8ca8);
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .navbar-brand {
                font-size: 1.8rem;
                font-weight: 700;
            }

            .profile-card {
                background: #ffffff;
                border-radius: 15px;
                box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
                padding: 2rem;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .profile-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
            }

            .history-row {
                background: #ffffff;
                border-radius: 10px;
                border: 1px solid #e0e7ff;
                transition: all 0.3s ease;
                padding: 1rem;
                margin-bottom: 1rem;
            }

            .history-row:hover {
                background: #f8fafc;
                transform: translateY(-3px);
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            }

            .history-row:hover a.chapter-title {
                color: #2563eb !important;
                text-decoration: underline;
            }

            .action-buttons {
                display: none;
                gap: 0.5rem;
            }

            .history-row:hover .action-buttons {
                display: flex !important;
            }

            .btn-primary,
            .btn-success {
                background: linear-gradient(to right, #2563eb, #3b82f6);
                border: none;
                transition: background 0.3s ease;
            }

            .btn-primary:hover,
            .btn-success:hover {
                background: linear-gradient(to right, #1e40af, #2563eb);
            }

            .btn-outline-danger {
                border-color: #ef4444;
                color: #ef4444;
            }

            .btn-outline-danger:hover {
                background: #ef4444;
                color: #fff;
            }

            .history-container {
                max-height: 400px;
                overflow-y: auto;
                scrollbar-width: thin;
                scrollbar-color: #6b8ca8 #e0e7ff;
            }

            .history-container::-webkit-scrollbar {
                width: 8px;
            }

            .history-container::-webkit-scrollbar-track {
                background: #e0e7ff;
            }

            .history-container::-webkit-scrollbar-thumb {
                background: #6b8ca8;
                border-radius: 4px;
            }

            .footer {
                background: linear-gradient(to right, #517594, #8da7c0);
                color: #fff;
            }

            .footer a {
                color: #fff;
                transition: color 0.3s ease;
            }

            .footer a:hover {
                color: #dbeafe;
            }
        </style>
    </head>

    <body>
        <header>

        </header>

        <main class="container my-5">
            <div class="row d-flex justify-content-around">
                <div class="profile-card col-md-4 h-100">
                    <h1 class="fw-bold text-primary mb-3">${user.username}</h1>
                    <div class="mb-3">
                        <h5 class="text-muted"><i class="bi bi-person-circle me-2"></i>Role: <strong>${user.roleName}</strong></h5>
                        <h5 class="text-muted"><i class="bi bi-envelope me-2"></i>Email:
                            <strong>${user.email}</strong>
                        </h5>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                data-bs-target="#editProfileModal">
                            <i class="bi bi-pencil-square me-1"></i> Edit Profile
                        </button>
                        <button type="button" class="btn btn-secondary" data-bs-toggle="modal"
                                data-bs-target="#changePasswordModal">
                            <i class="bi bi-key me-1"></i> Change Password
                        </button>
                        <!-- <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal"
                            data-bs-target="#deleteModal" data-id="1" data-type="user" data-url="deleteUser">
                            <i class="bi bi-trash3-fill me-1"></i> Delete User
                        </button> -->
                    </div>
                    <c:if test="${not empty sessionScope.message}">
                        <div class="alert alert-success alert-dismissible fade show mt-5" role="alert">
                            ${fn:escapeXml(sessionScope.message)}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <c:remove var="message" scope="session"/>
                    </c:if>
                    <c:if test="${not empty sessionScope.messageError}">
                        <div class="alert alert-danger alert-dismissible fade show mt-5" role="alert">
                            ${fn:escapeXml(sessionScope.messageError)}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <c:remove var="messageError" scope="session"/>
                    </c:if>
                </div>

                <!-- Edit Profile Modal -->
                <div class="modal fade" id="editProfileModal" tabindex="-1" aria-labelledby="editProfileModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="editProfileModalLabel">Edit Profile</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form id="editProfileForm" action="updateInfo" method="post">
                                    <input type="hidden" name="userId" value="${user.userId}" />
                                    <input type="hidden" name="_csrf" value="sample-csrf-token" />
                                    <div class="mb-3">
                                        <label for="username" class="form-label">Username</label>
                                        <input type="text" class="form-control" id="username" name="username"
                                               value="${user.username}" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Email</label>
                                        <input type="email" class="form-control" id="email" name="email"
                                               value="${user.email}" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="currentPasswordProfile" class="form-label">Current Password</label>
                                        <input type="password" class="form-control" id="currentPasswordProfile"
                                               name="currentPassword" placeholder="Enter current password" required />
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">
                                            <i class="bi bi-save me-1"></i> Save Changes
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Change Password Modal -->
                <div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="editPasswordLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form action="updatePassword" method="post">
                                <input type="hidden" name="userId" value="${user.userId}" />

                                <div class="modal-header">
                                    <h5 class="modal-title" id="editPasswordLabel">Change Password</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>

                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="currentPassword" class="form-label">Current Password</label>
                                        <input type="password" name="currentPassword" id="currentPassword"
                                               class="form-control" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="newPassword" class="form-label">New Password</label>
                                        <input type="password" name="newPassword" id="newPassword"
                                               class="form-control" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                                        <input type="password" name="confirmPassword" id="confirmPassword"
                                               class="form-control" required />
                                    </div>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-save me-1"></i> Save Password
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="profile-card col-md-7">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <div>
                            <h4 class="fw-bold">Reading History</h4>
                            <p class="text-muted small mt-1">Total: ${fn:length(listHistory)} entries</p>
                        </div>
                    </div>
                    <!-- <div class="alert alert-success alert-dismissible fade show" role="alert">
                        Profile updated successfully.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div> -->
                    <div class="history-container">
                        <c:forEach var="history" items="${listHistory}">
                            <div class="history-row d-flex justify-content-between align-items-center"
                                 onclick="window.location = '${pageContext.request.contextPath}/readChapter?chapterIndex=${history.chapterIndex}&seriesId=${history.seriesId}'" style="cursor: pointer">
                                <div class="d-flex align-items-center gap-3">
                                    <h6 class="fw-bold text-primary mb-0"> ${history.chapterTitle}</h6>
                                    <div>
                                        <a class="chapter-title fw-semibold text-dark text-decoration-none">${history.chapterTitle}</a>
                                        <p class="text-muted small mb-0">Series: ${history.seriesTitle}</p>
                                        <p class="text-muted small mb-0">Last read: <fmt:formatDate value="${history.lastReadAt}" pattern="dd/MM/yyyy HH:mm"/></p>                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </main>
        <br>
        <script src="${pageContext.request.contextPath}/js/index.js"></script> 
        <script src="${pageContext.request.contextPath}/js/jQuery.js"></script>
        <jsp:include page="/WEB-INF/views/components/_footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>