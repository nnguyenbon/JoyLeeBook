package controller.chapterController;

import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Chapter;
import static utils.Validator.*;

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
            String chapterIndexParam = request.getParameter("chapterIndex");
            String seriesIdParam = request.getParameter("seriesId");

            // Validate parameters
            if (!isValidInteger(seriesIdParam) || !isValidInteger(chapterIndexParam)) {
                request.setAttribute("error", "Invalid seriesId or chapterIndex.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            int chapterIndex = Integer.parseInt(chapterIndexParam);
            int seriesId = Integer.parseInt(seriesIdParam);

            // Initialize DAOs
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            // Retrieve chapter and set series title
            Chapter chapter = chapterDAO.getChapterByIndex(seriesId, chapterIndex);
            chapter.setSeriesTitle(seriesDAO.getSeriesById(seriesId).getSeriesTitle());

            // Retrieve all chapters in series
            ArrayList<Chapter> chapters = chapterDAO.getAllChaptersBySeriesId(seriesId);

            // Compute first and last chapter indexes
            int firstIndex = chapters.get(0).getChapterIndex();
            int lastIndex = chapters.get(chapters.size() - 1).getChapterIndex();

            // Set attributes and forward to JSP
            request.setAttribute("firstIndex", firstIndex);
            request.setAttribute("lastIndex", lastIndex);
            request.setAttribute("chapter", chapter);
            request.setAttribute("chapters", chapters);
            request.getRequestDispatcher("/WEB-INF/views/chapter/readChapter.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Redirect to error page if parameters are not valid integers
            request.setAttribute("error", "Invalid seriesId or chapterIndex.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
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
