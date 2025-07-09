/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.seriesController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Series;

/**
 *
 * @author PC
 */
@WebServlet(name = "UpdateSeriesServlet", urlPatterns = { "/updateSeries" })
public class UpdateSeriesServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateSeriesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateSeriesServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        processRequest(request, response);
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
        int seriesId = Integer.parseInt(request.getParameter("seriesId"));
        if (seriesId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid series ID");
            return;
        }
        String authorName = request.getParameter("authorName");
        String seriesTitle = request.getParameter("seriesTitle");
        String status = request.getParameter("status");
        String description = request.getParameter("description");

        if (authorName == null || authorName.trim().isEmpty() ||
                seriesTitle == null || seriesTitle.trim().isEmpty() ||
                status == null || status.trim().isEmpty() ||
                description == null || description.trim().isEmpty()) {
            dispatcherError(request, response, "All fields are required.",
                    seriesId, authorName, seriesTitle, status, description);
            return;
        }
        try {
            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            Series existingSeries = seriesDAO.getSeriesById(seriesId);
            if (existingSeries == null) {
                dispatcherError(request, response, "Series not found.",
                        seriesId, authorName, seriesTitle, status, description);
                return;
            }
            existingSeries.setAuthorName(authorName);
            existingSeries.setSeriesTitle(seriesTitle);
            existingSeries.setStatus(status);
            existingSeries.setDescription(description);

            boolean isUpdated = seriesDAO.updateSeries(existingSeries);
            if (isUpdated) {
                request.setAttribute("successMessage", "Series updated successfully.");
                request.getRequestDispatcher("views/series/editSeries.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to update series. Please try again.");
                request.getRequestDispatcher("views/series/editSeries.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "A database error occurred");
            request.getRequestDispatcher("views/series/editSeries.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server configuration error.");
            request.getRequestDispatcher("views/series/editSeries.jsp").forward(request, response);
        }
    }

    public void dispatcherError(HttpServletRequest request, HttpServletResponse response, String errorMessage,
            int seriesId, String authorName, String seriesTitle, String status, String description)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("seriesId", seriesId);
        request.setAttribute("authorName", authorName);
        request.setAttribute("seriesTitle", seriesTitle);
        request.setAttribute("status", status);
        request.setAttribute("description", description);
        request.getRequestDispatcher("views/series/editSeries.jsp").forward(request, response);
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
