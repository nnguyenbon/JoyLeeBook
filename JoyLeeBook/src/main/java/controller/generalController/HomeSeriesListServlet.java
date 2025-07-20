package controller.generalController;

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
import java.util.Collections;
import java.util.ArrayList;
import model.Series;

/**
 *
 * @author PC
 */
@WebServlet(name = "HomeSeriesListServlet", urlPatterns = {"/home"})
public class HomeSeriesListServlet extends HttpServlet {
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
        java.sql.Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            SeriesDAO seriesDAO = new SeriesDAO(conn);
            ChapterDAO chapterDAO = new ChapterDAO(conn);
            
            ArrayList<Series> seriesList = (ArrayList<Series>) seriesDAO.getAllSeries();
            for (Series series : seriesList) {
                series.setTotalChapters(chapterDAO.getTotalChaptersBySeriesId(series.getSeriesId()));
                series.setLatestChapterDate(chapterDAO.getLatestDate(series.getSeriesId()));
            }
            // Sort in day
            Collections.sort(seriesList, (Series s1, Series s2) -> s2.getLatestChapterDate().compareTo(s1.getLatestChapterDate()));

            request.setAttribute("seriesList", seriesList);
            System.out.println("home");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series List.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
