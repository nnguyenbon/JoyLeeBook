package controller.generalController;

import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Series;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import static utils.Validator.*;

/**
 *
 * @author PC
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = { "/adminDashboard" })
public class AdminDashboardServlet extends HttpServlet {
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
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            SeriesDAO seriesDao = new SeriesDAO(conn);
            ChapterDAO chapterDao = new ChapterDAO(conn);
            ArrayList<Series> allSeries = (ArrayList<Series>) seriesDao.getAllSeries();
            for (Series series : allSeries) {
                series.setTotalChapters(chapterDao.getTotalChaptersBySeriesId(series.getSeriesId()));
            }

            // Pagination
            int itemsPerPage = 10;
            int totalItems = allSeries.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            String pageParam = request.getParameter("page");
            int currentPage = 1;
            if (isValidInteger(pageParam)) {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1)
                    currentPage = 1;
                if (currentPage > totalPages)
                    currentPage = totalPages;
            }

            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

            List<Series> paginatedSeries =  allSeries.subList(startIndex, endIndex);

            request.setAttribute("allSeries", paginatedSeries);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/views/adminDashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Fail to connect the database for admin dashboard " + e);
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
