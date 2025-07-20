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
            String idParam = request.getParameter("seriesId");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/WEB-INF/views/error.jsp");
                return;
            }

            int seriesId = Integer.parseInt(idParam);

            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            CategoryDAO categoryDAO = new CategoryDAO(DBConnection.getConnection());

            Series series = seriesDAO.getSeriesById(seriesId);

            ArrayList<Chapter> listChapter = chapterDAO.getAllChaptersBySeriesId(seriesId);
            series.setTotalChapters(listChapter.size()); 

            ArrayList<Genre> listGenre = categoryDAO.getGenresBySeriesId(seriesId);
            series.setGenres(listGenre);

            request.setAttribute("listChapter", listChapter);
            request.setAttribute("series", series);

            request.getRequestDispatcher("WEB-INF/views/series/viewInfo.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series Information.");
            request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP POST method (just calls doGet).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet that handles displaying detailed series information, including chapters and genres.";
    }
}