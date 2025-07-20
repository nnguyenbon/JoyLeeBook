package controller.seriesController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.GenreDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Genre;
import model.Series;
import static utils.Validator.*;

/**
 * Servlet to handle searching for series.
 *
 * @author HaiDD-dev
 */
@WebServlet(name = "SearchSeriesServlet", urlPatterns = {"/search"})
public class SearchSeriesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        final int RESULTS_PER_PAGE = 6;

        String searchQuery = request.getParameter("searchQuery");
        if (searchQuery == null) {
            searchQuery = "";
        }

        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                if (isValidInteger(pageParam)) {
                    currentPage = Integer.parseInt(pageParam);
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        try (Connection conn = DBConnection.getConnection()) {
            SeriesDAO seriesDAO = new SeriesDAO(conn);
            GenreDAO genreDAO = new GenreDAO(conn);

            int totalResults = seriesDAO.getTotalSeriesCount(searchQuery);
            int totalPages = (int) Math.ceil((double) totalResults / RESULTS_PER_PAGE);

            ArrayList<Series> seriesList = seriesDAO.searchSeries(searchQuery, currentPage, RESULTS_PER_PAGE);
            ArrayList<Genre> genreList = genreDAO.getAll();

            // MỚI: Gửi các thông tin phân trang tới JSP
            request.setAttribute("seriesList", seriesList);
            request.setAttribute("genres", genreList);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery); // Giữ lại query để tạo link phân trang

            request.getRequestDispatcher("/WEB-INF/views/series/searchSeries.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error while searching for series.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
