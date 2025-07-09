/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.seriesController;

import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author PC
 */
@WebServlet(name = "RemoveSavedSeriesServlet", urlPatterns = {"/removeSavedSeries"})
public class RemoveSavedSeriesServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
//        HttpSession session = request.getSession(false);
//
//        User user = (User) session.getAttribute("user");
//        if (!user.getRoleName().equals("admin")) {
//            request.setAttribute("error", "Bạn không có quyền truy cập chức năng này.");
//            request.getRequestDispatcher("views/error.jsp").forward(request, response);
//            return;
//        }
        try {
            int seriesId = Integer.parseInt(request.getParameter("seriesId"));
            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());

            if (seriesDAO.deleteSeries(seriesId)) {
                request.setAttribute("message", "Delete successfully!");
                request.getRequestDispatcher("adminDashboard").forward(request, response);
            } else {
                request.setAttribute("message", "Delete failed!");
                request.getRequestDispatcher("adminDashboard").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Delete failed!");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
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
