package controller.seriesController;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import db.DBConnection;
import dao.SeriesDAO;
import dao.ChapterDAO;
import dao.CategoryDAO;
import java.util.ArrayList;
import model.Series;
import model.Chapter;
import model.Genre;

/**
 * This servlet handles requests to view detailed information about a specific
 * series. It retrieves the series based on its ID, including its list of
 * chapters and genres, and forwards the data to the viewInfo.jsp page for
 * display.
 */
@WebServlet(name = "ViewSeriesInfoServlet", urlPatterns = {"/viewSeriesInfo"})
public class ViewSeriesInfoServlet extends HttpServlet {

    /**
     * Handles both GET and POST requests. Retrieves the series ID from the
     * request, fetches the corresponding series from the database along with
     * its related chapters and genres. If successful, forwards to the series
     * view page. If the series ID is missing or an error occurs, redirects to
     * an error page.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Check id if user operate with url
            String idParam = request.getParameter("seriesId");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }

            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            CategoryDAO categoryDAO = new CategoryDAO(DBConnection.getConnection());

            int seriesId = Integer.parseInt(idParam);
            Series series = seriesDAO.getSeriesById(seriesId);

            ArrayList<Chapter> listChapter = chapterDAO.getAllChaptersBySeriesId(seriesId);
            series.setChapter(listChapter);

            ArrayList<Genre> listGenre = categoryDAO.getGenresBySeriesId(seriesId);
            series.setGenres(listGenre);

            request.setAttribute("series", series);
            request.getRequestDispatcher("/views/series/viewInfo.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series Information.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP GET requests by calling the processRequest method.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles HTTP POST requests by calling the processRequest method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of this servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet that handles displaying detailed series information, including chapters and genres.";
    }
}
