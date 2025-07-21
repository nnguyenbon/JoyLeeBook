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
import java.util.Arrays;
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
     protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final int RESULTS_PER_PAGE = 9;

        String searchQuery = request.getParameter("searchQuery");
        if (searchQuery == null) {
            searchQuery = "";
        }

        String[] genreParams = request.getParameterValues("genres");
        ArrayList<String> selectedGenreIds = new ArrayList<>();
        if (genreParams != null) {
            selectedGenreIds.addAll(Arrays.asList(genreParams));
        }

        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1) currentPage = 1;
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        try (Connection conn = DBConnection.getConnection()) {
            SeriesDAO seriesDAO = new SeriesDAO(conn);
            GenreDAO genreDAO = new GenreDAO(conn);

            int totalResults = seriesDAO.getTotalSeriesCount(searchQuery, selectedGenreIds);
            int totalPages = (int) Math.ceil((double) totalResults / RESULTS_PER_PAGE);
            if (totalPages > 0 && currentPage > totalPages) {
                currentPage = totalPages;
            }

            ArrayList<Series> seriesList = seriesDAO.searchSeries(searchQuery, currentPage, RESULTS_PER_PAGE, selectedGenreIds);
            ArrayList<Genre> allGenres = genreDAO.getAll();

            request.setAttribute("seriesList", seriesList);
            request.setAttribute("genres", allGenres);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery);
            request.setAttribute("selectedGenres", selectedGenreIds);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
