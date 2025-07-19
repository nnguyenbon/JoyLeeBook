/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.seriesController;

import java.io.IOException;
import java.sql.SQLException;

import dao.CategoryDAO;
import dao.ChapterDAO;
import dao.HistoryReadingDAO;
import dao.LibraryDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author PC
 */
@WebServlet(name = "DeleteSeriesServlet", urlPatterns = {"/deleteSeries"})
public class DeleteSeriesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

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
        java.sql.Connection conn = null;
        try {
            conn = DBConnection.getConnection();

            conn.setAutoCommit(false);
            SeriesDAO seriesDao = new SeriesDAO(conn);
            CategoryDAO categoryDao = new CategoryDAO(conn);
            HistoryReadingDAO historyDao = new HistoryReadingDAO(conn);
            ChapterDAO chapterDao = new ChapterDAO(conn);
            LibraryDAO libraryDao = new LibraryDAO(conn);

            int seriesId = Integer.parseInt(request.getParameter("seriesId"));
            boolean isDeletedCategory = categoryDao.deleteBySeriesId(seriesId);
            boolean isDeletedHistory = historyDao.deleteBySeriesId(seriesId);
            boolean isDeletedLibrary = libraryDao.deleteBySeriesId(seriesId);
            boolean isDeletedChapter = chapterDao.deleteBySeriesId(seriesId);
            boolean isDeletedSeries = seriesDao.deleteSeries(seriesId);

            if (isDeletedCategory && isDeletedHistory && isDeletedLibrary && isDeletedChapter && isDeletedSeries) {
                conn.commit();
            } else {
                conn.rollback();
                request.setAttribute("errorMessage", "Delete failed. Please check log for details.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
            response.sendRedirect(request.getContextPath() + "/adminDashboard");
        } catch (Exception e) {
            e.printStackTrace();
            // Đảm bảo rollback nếu có ngoại lệ không mong muốn xảy ra
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            request.setAttribute("message", "An error occurred during deletion.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try {

                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
