package controller.seriesController;

import dao.CategoryDAO;
import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import model.Chapter;
import model.Genre;
import model.Series;
import utils.Validator.*;

/**
 * This servlet handles requests to view detailed information about a specific
 * series. It retrieves the series based on its ID, including its list of
 * chapters and genres, and forwards the data to the viewInfo.jsp page for
 * display.
 * 
 * @author
 */
@WebServlet(name = "ViewSeriesInfoServlet", urlPatterns = {"/viewSeriesInfo"})
public class ViewSeriesInfoServlet extends HttpServlet {

    // JSP path constant
    private static final String VIEW_JSP = "WEB-INF/views/series/viewInfo.jsp";
    private static final String ERROR_JSP = "WEB-INF/views/error.jsp";

    /**
     * Handles the HTTP GET method.
     *
     * @param request The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String seriesIdStr = request.getParameter("seriesId");

            if (!Validator.isValidInteger(seriesIdStr)) {
                throw new IllegalArgumentException("Invalid series ID.");
            }

            int seriesId = Integer.parseInt(seriesIdStr);

            try (Connection conn = DBConnection.getConnection()) {
                SeriesDAO seriesDAO = new SeriesDAO(conn);
                ChapterDAO chapterDAO = new ChapterDAO(conn);
                CategoryDAO categoryDAO = new CategoryDAO(conn);

                Series series = seriesDAO.getSeriesById(seriesId);
                if (series == null) {
                    throw new Exception("Series not found.");
                }

                ArrayList<Chapter> listChapter = chapterDAO.getAllChaptersBySeriesId(seriesId);
                ArrayList<Genre> listGenre = categoryDAO.getGenresBySeriesId(seriesId);

                series.setTotalChapters(listChapter != null ? listChapter.size() : 0);
                series.setGenres(listGenre != null ? listGenre : new ArrayList<>());

                request.setAttribute("series", series);
                request.setAttribute("listChapter", listChapter);

                // Define genre colors
                String[] colorGenre = {
                    "bg-primary-subtle text-primary",
                    "bg-secondary-subtle text-secondary",
                    "bg-danger-subtle text-danger",
                    "bg-info-subtle text-info",
                    "bg-light-subtle text-dark"
                };
                request.setAttribute("colors", colorGenre);

                request.getRequestDispatcher(VIEW_JSP).forward(request, response);
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ERROR_JSP).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot retrieve series information: " + e.getMessage());
            request.getRequestDispatcher(ERROR_JSP).forward(request, response);
        }
    }

    /**
     * Handles the HTTP POST method. Currently not implemented.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported.");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for displaying series information and chapters.";
    }
}