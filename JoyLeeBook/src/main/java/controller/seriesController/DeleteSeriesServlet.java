package controller.seriesController;

import java.io.IOException;
import java.sql.SQLException;

import dao.CategoryDAO;
import dao.ChapterDAO;
import dao.HistoryReadingDAO;
import dao.LibraryDAO;
import dao.SeriesDAO;
import db.DBConnection;
import java.sql.Connection;
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
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();

            conn.setAutoCommit(false);
            SeriesDAO seriesDao = new SeriesDAO(conn);
            CategoryDAO categoryDao = new CategoryDAO(conn);
            HistoryReadingDAO historyDao = new HistoryReadingDAO(conn);
            ChapterDAO chapterDao = new ChapterDAO(conn);
            LibraryDAO libraryDao = new LibraryDAO(conn);

            String seriesIdRaw = request.getParameter("seriesId");
            if (!isValidInteger(seriesIdRaw)) {
                request.setAttribute("error", "Invalid series ID.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            int seriesId = Integer.parseInt(seriesIdRaw);
            boolean isDeletedCategory = categoryDao.deleteBySeriesId(seriesId);
            boolean isDeletedHistory = historyDao.deleteBySeriesId(seriesId);
            boolean isDeletedLibrary = libraryDao.deleteBySeriesId(seriesId);
            boolean isDeletedChapter = chapterDao.deleteBySeriesId(seriesId);
            boolean isDeletedSeries = seriesDao.deleteSeries(seriesId);

            if (isDeletedCategory && isDeletedHistory && isDeletedLibrary && isDeletedChapter && isDeletedSeries) {
                conn.commit();
            } else {
                conn.rollback();
                request.setAttribute("error", "Delete failed. Please check log for details.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
            response.sendRedirect(request.getContextPath() + "/adminDashboard");
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            request.setAttribute("error", "An error occurred during deletion.");
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
}
