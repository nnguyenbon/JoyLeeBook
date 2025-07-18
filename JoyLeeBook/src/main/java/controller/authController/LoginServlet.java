/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authController;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import static utils.Validator.isValidString;

/**
 *
 * @author PC
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("loggedInUser") != null) {
            String userRole = ((User) session.getAttribute("loggedInUser")).getRoleName();
            redirectBasedOnRole(request, response, userRole);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String rememberedUser = null;
        String rememberedRole = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberedUsername".equals(cookie.getName())) {
                    rememberedUser = cookie.getValue();
                } else if ("rememberedRole".equals(cookie.getName())) {
                    rememberedRole = cookie.getValue();
                }
            }
        }

        if (rememberedUser != null && rememberedRole != null) {
            try {
                UserDAO userDAO = new UserDAO(DBConnection.getConnection());
                User user = userDAO.getUserByUsername(rememberedUser);

                if (user != null && user.getRoleName().equals(rememberedRole)) {
                    HttpSession newSession = request.getSession();
                    newSession.setAttribute("loggedInUser", user);
                    newSession.setMaxInactiveInterval(30 * 60);

                    redirectBasedOnRole(request, response, user.getRoleName());
                    return;
                } else {
                    for (Cookie cookie : cookies) {
                        if ("rememberedUsername".equals(cookie.getName()) || "rememberedRole".equals(cookie.getName())) {
                            cookie.setMaxAge(0);
                        }
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (rememberedUser != null) {
            request.setAttribute("rememberedUser", rememberedUser);
        }
        request.getRequestDispatcher("/WEB-INF/views/authorization/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        if (!isValidString(username)) {
            request.setAttribute("error", "Username cannot be empty.");
            request.getRequestDispatcher("/WEB-INF/views/authorization/login.jsp").forward(request, response);
            return;
        }
        if (!isValidString(password)) {
            request.setAttribute("error", "Password cannot be empty.");
            request.getRequestDispatcher("/WEB-INF/views/authorization/login.jsp").forward(request, response);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO(DBConnection.getConnection());
            User user = userDAO.checkLogin(username, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", user);
                session.setMaxInactiveInterval(30 * 60);

                if ("on".equals(rememberMe)) {
                    Cookie userCookie = new Cookie("rememberedUsername", username);
                    Cookie roleCookie = new Cookie("rememberedRole", user.getRoleName());

                    // Set thời gian sống 7 ngày
                    userCookie.setMaxAge(60 * 60 * 24 * 7);
                    roleCookie.setMaxAge(60 * 60 * 24 * 7);

                    // Set path và httpOnly để bảo mật
//                    userCookie.setPath("/");
//                    roleCookie.setPath("/");
//        userCookie.setHttpOnly(true);
//        roleCookie.setHttpOnly(true);
//        
                    response.addCookie(userCookie);
                    response.addCookie(roleCookie);
                } else {
                    Cookie userCookie = new Cookie("rememberedUsername", "");
                    Cookie roleCookie = new Cookie("rememberedRole", "");

                    userCookie.setMaxAge(0);
                    roleCookie.setMaxAge(0);

                    response.addCookie(userCookie);
                    response.addCookie(roleCookie);
                }

                redirectBasedOnRole(request, response, user.getRoleName());

            } else {
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("/WEB-INF/views/authorization/login.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("Database error occurred while processing login.", e);
        }
    }

    private void redirectBasedOnRole(HttpServletRequest request, HttpServletResponse response, String role)
            throws ServletException, IOException {
        if ("admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/adminDashboard");
        } else if ("reader".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("errorMessage", "Invalid role");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
