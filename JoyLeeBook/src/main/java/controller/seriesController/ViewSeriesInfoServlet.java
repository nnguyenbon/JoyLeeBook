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
import utils.Validator;

/**
 * This servlet handles requests to view detailed information about a specific
 * series. It retrieves the series based on its ID, including its list of
 * chapters and genres, and forwards the data to the viewInfo.jsp page for
 * display.
 * author
 */
@WebServlet(name = "ViewSeriesInfoServlet", urlPatterns = {"/viewSeriesInfo"})
public class ViewSeriesInfoServlet extends HttpServlet {

    /**
     * Handles the HTTP GET method.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String seriesIdStr = request.getParameter("seriesId");
            if (!Validator.isValidInteger(seriesIdStr)) {
                request.setAttribute("error", "Invalid series ID.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
    
            int seriesId = Integer.parseInt(seriesIdStr);
    
            try (Connection conn = DBConnection.getConnection()) {
                SeriesDAO seriesDAO = new SeriesDAO(conn);
                ChapterDAO chapterDAO = new ChapterDAO(conn);
                CategoryDAO categoryDAO = new CategoryDAO(conn);
    
                Series series = seriesDAO.getSeriesById(seriesId);
                if (series == null) {
                    request.setAttribute("error", "Series not found.");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    return;
                }
    
                ArrayList<Chapter> listChapter = chapterDAO.getAllChaptersBySeriesId(seriesId);
                series.setTotalChapters(listChapter.size()); 
    
                ArrayList<Genre> listGenre = categoryDAO.getGenresBySeriesId(seriesId);
                series.setGenres(listGenre);
    
                request.setAttribute("listChapter", listChapter);
                request.setAttribute("series", series);
    
                request.getRequestDispatcher("/WEB-INF/views/series/viewInfo.jsp").forward(request, response);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series Information.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
