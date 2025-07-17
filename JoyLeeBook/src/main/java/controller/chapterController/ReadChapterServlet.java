package controller.chapterController;

import dao.ChapterDAO;
import dao.SeriesDAO;
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
 * Servlet for handling requests to read a specific chapter in a series. It
 * retrieves the chapter by ID and a list of all chapters in the same series,
 * then forwards the information to a JSP page for rendering.
 */
@WebServlet(name = "ReadChapterServlet", urlPatterns = {"/readChapter"})
public class ReadChapterServlet extends HttpServlet {

    /**
     * Processes both GET and POST requests. Retrieves the chapter by ID and the
     * chapter list by series ID, and forwards the data to the readChapter.jsp
     * view.
     *
     * @param request The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters from the request
//            String chapterIdParam = request.getParameter("chapterId");
//            String seriesIdParam = request.getParameter("seriesId");
            String seriesIdParam = "3";
            String chapterIndexParam = "1";

            // Validate parameters
            if (chapterIndexParam == null || chapterIndexParam.isEmpty()
                    || seriesIdParam == null || seriesIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }

            int chapterIndex = Integer.parseInt(chapterIndexParam);
            int seriesId = Integer.parseInt(seriesIdParam);

            // Initialize DAOs
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());

            // Retrieve chapter and list of chapters
            Chapter chapter = chapterDAO.getChapterByIndex(seriesId, chapterIndex);
            ArrayList<Chapter> listChapter = chapterDAO.getAllChaptersBySeriesId(seriesId);
            int firstIndex = chapterDAO.getFirstChapterIndex(seriesId);
            int lastIndex = chapterDAO.getLastChapterIndex(seriesId);

            // Set attributes and forward to JSP
            request.setAttribute("firstIndex", firstIndex);
            request.setAttribute("lastIndex", lastIndex);
            request.setAttribute("chapter", chapter);
            request.setAttribute("chapterList", listChapter);
            request.getRequestDispatcher("/WEB-INF/views/chapter/readChapter.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Redirect to error page if parameters are not valid integers
            response.sendRedirect("/WEB-INF/views/error.jsp");
        } catch (Exception e) {
            // Log exception and forward to error view
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Chapter Information.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request The HTTP request object.
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
        return "Servlet that handles chapter reading by retrieving chapter and chapter list data and forwarding to view";
    }
}
