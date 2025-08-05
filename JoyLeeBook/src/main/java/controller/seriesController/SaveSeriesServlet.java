package controller.seriesController;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import dao.ChapterDAO;
import dao.LibraryDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Library;
import model.Series;
import model.User;
import static utils.Validator.isValidInteger;

/**
 *
 * @author PC
 */
@WebServlet(name = "SaveSeriesServlet", urlPatterns = {"/saveSeries"})
public class SaveSeriesServlet extends HttpServlet {

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
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            LibraryDAO libraryDao = new LibraryDAO(conn);
            SeriesDAO seriesDao = new SeriesDAO(conn);
            ChapterDAO chapterDao = new ChapterDAO(conn);

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("loggedInUser") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            User user = (User) session.getAttribute("loggedInUser");
            int userId = user.getUserId();

            ArrayList<Library> libraryOfUser = libraryDao.getLibrariesByUserId(userId);
            ArrayList<Series> librarySeries = new ArrayList<>();
            for (Library library : libraryOfUser) {
                Series series = seriesDao.getSeriesById(library.getSeriesId());
                librarySeries.add(series);
            }
            for (Series series : librarySeries) {
                series.setTotalChapters(chapterDao.getTotalChaptersBySeriesId(series.getSeriesId()));
            }

            int itemsPerPage = 10;
            int totalItems = librarySeries.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            String pageParam = request.getParameter("page");
            int currentPage = 1;
            if (pageParam != null && isValidInteger(pageParam)) {
                currentPage = Integer.parseInt(pageParam);
            }

// Điều chỉnh currentPage phù hợp
            if (totalPages == 0) {
                currentPage = 1;
            } else {
                if (currentPage < 1) {
                    currentPage = 1;
                }
                if (currentPage > totalPages) {
                    currentPage = totalPages;
                }
            }

            List<Series> paginatedSeries;
            if (totalItems == 0) {
                paginatedSeries = new ArrayList<>();
            } else {
                int startIndex = (currentPage - 1) * itemsPerPage;
                int endIndex = Math.min(startIndex + itemsPerPage, totalItems);
                paginatedSeries = librarySeries.subList(startIndex, endIndex);
            }

            request.setAttribute("librarySeries", paginatedSeries);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/WEB-INF/views/user/library.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String seriesIdStr = request.getParameter("seriesId");
        if (!isValidInteger(seriesIdStr)) {
            request.setAttribute("error", "Invalid series ID.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }
        int seriesId = Integer.parseInt(seriesIdStr);

        User user = (User) session.getAttribute("loggedInUser");
        int userId = user.getUserId();

        try {
            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            boolean isSeriesSaved = seriesDAO.isSeriesSaved(seriesId, userId);

            if (isSeriesSaved) {
                session.setAttribute("message", "This series is already saved in your library.");
                response.sendRedirect(request.getContextPath() + "/viewSeriesInfo?seriesId=" + seriesId);
                return;
            }

            boolean isSaved = seriesDAO.saveSeries(seriesId, userId);
            if (isSaved) {
                session.setAttribute("message", "Series saved successfully!");
                response.sendRedirect(request.getContextPath() + "/saveSeries");
            } else {
                request.setAttribute("error", "Failed to save the series.");
                request.setAttribute("seriesId", seriesId);
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
