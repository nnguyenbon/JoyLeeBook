/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.seriesController;

import java.io.IOException;

import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author PC
 */
@WebServlet(name = "RemoveSavedSeriesServlet", urlPatterns = { "/removeSavedSeries" })
public class RemoveSavedSeriesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("loggedInUser");
            int userId = user.getUserId();
            int seriesId = Integer.parseInt(request.getParameter("seriesId"));
            try {
                SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
                boolean isSeriesSaved = seriesDAO.isSeriesSaved(seriesId, userId);
                if (!isSeriesSaved) {
                    request.getSession().setAttribute("errorMessage", "This series is not saved in your library.");
                    response.sendRedirect(request.getContextPath() + "/saveSeries");
                    return;
                }
                boolean isRemoved = seriesDAO.deleteSavedSeries(seriesId, userId);
                if (isRemoved) {
                    request.getSession().setAttribute("successMessage", "Series removed successfully!");
                    response.sendRedirect(request.getContextPath() + "/saveSeries");
                } else {
                    request.setAttribute("errorMessage", "Failed to remove the series.");
                    request.setAttribute("seriesId", seriesId);
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", e);
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
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
