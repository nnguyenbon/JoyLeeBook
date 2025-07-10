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

/**
 *
 * @author PC
 */
@WebServlet(name = "SaveSeriesServlet", urlPatterns = { "/saveSeries" })
public class SaveSeriesServlet extends HttpServlet {

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
        try {
            response.sendRedirect(request.getContextPath() + "/views/series/viewInfo.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while processing your request.");
        }
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
            int userId = (Integer) session.getAttribute("userId");
            int seriesId = Integer.parseInt(request.getParameter("series_id"));
            try {
                SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
                boolean isSeriesSaved = seriesDAO.isSeriesSaved(seriesId, userId);
                if (isSeriesSaved) {
                    request.setAttribute("errorMessage", "This series is already saved in your library.");
                    request.setAttribute("seriesId", seriesId);
                    request.getRequestDispatcher("views/series/viewInfo.jsp").forward(request, response);
                    return;
                }
                boolean isSaved = seriesDAO.saveSeries(seriesId, userId);
                if (isSaved) {
                    request.getSession().setAttribute("successMessage", "Series saved successfully!");
                    response.sendRedirect(
                            request.getContextPath() + "/views/series/viewInfo.jsp?series_id=" + seriesId);
                } else {
                    request.setAttribute("errorMessage", "Failed to save the series.");
                    request.setAttribute("seriesId", seriesId);
                    request.getRequestDispatcher("views/series/viewInfo.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "An error occurred while processing your request.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "views/authorization/login.jsp");
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
