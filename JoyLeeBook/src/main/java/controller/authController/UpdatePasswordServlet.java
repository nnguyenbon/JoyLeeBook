/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authController;

import dao.UserDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.User;
import utils.PasswordUtil;

/**
 *
 * @author NguyenNTCE191135
 */
@WebServlet(name = "UpdatePasswordServlet", urlPatterns = {"/updatePassword"})
public class UpdatePasswordServlet extends HttpServlet {

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
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (!newPassword.equals(confirmPassword)) {
                request.getSession().setAttribute("messageError", "New password and confirm password do not match.");
                response.sendRedirect(request.getContextPath() + "/viewProfile?userId=" + userId);
                return;
            }
            UserDAO userDAO = new UserDAO(DBConnection.getConnection());
            User user = userDAO.getUserById(userId);
            if (user == null) {
                request.setAttribute("error", "User not found.");
                request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            if (!PasswordUtil.checkPassword(currentPassword, user.getPassword())) {
                request.getSession().setAttribute("messageError", "Current password is incorrect.");
                response.sendRedirect(request.getContextPath() + "/viewProfile?userId=" + userId);
                return;
            }

            user.setPassword(PasswordUtil.hashPassword(newPassword));
            userDAO.updateUser(user);
            request.getSession().setAttribute("message", "Password updated successfully.");
            response.sendRedirect(request.getContextPath() + "/viewProfile?userId=" + userId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the History List.");
            request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
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
