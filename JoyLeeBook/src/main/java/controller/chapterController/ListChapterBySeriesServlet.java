package controller.chapterController;

import dao.ChapterDAO;
import db.DBConnection;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Chapter;

/**
 * Servlet that displays the list of chapters belonging to a specific series.
 * Used for admin operations to manage chapters of a series.
 * Retrieves chapter list by series ID and forwards to a JSP view.
 * 
 * URL pattern: /chapterList
 * 
 * Author: PC
 */
@WebServlet(name = "ListChapterBySeriesServlet", urlPatterns = {"/chapterList"})
public class ListChapterBySeriesServlet extends HttpServlet {

    /**
     * Processes both GET and POST requests.
     * Retrieves the list of chapters based on the series ID passed as a request parameter.
     * If successful, forwards to the admin chapter list JSP.
     * If an error occurs, forwards to the error JSP page.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String seriesIdParam = request.getParameter("seriesId");

            if (seriesIdParam == null || seriesIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "views/error.jsp");
                return;
            }

            int seriesId = Integer.parseInt(seriesIdParam);
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            ArrayList<Chapter> chapterList = chapterDAO.getAllChaptersBySeriesId(seriesId);

            request.setAttribute("chapterList", chapterList);
            request.setAttribute("seriesId", seriesId);
            request.getRequestDispatcher("/views/chapter/adminListChapter.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get Chapter List.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP GET method by calling processRequest().
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP POST method by calling processRequest().
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of this servlet.
     *
     * @return A string containing servlet description.
     */
    @Override
    public String getServletInfo() {
        return "Servlet that handles displaying chapter list for a given series ID in admin view";
    }
}
