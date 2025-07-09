package controller.historyController;

import dao.HistoryReadingDAO;
import dao.UserDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.HistoryReading;
import model.User;

import java.io.IOException;

/**
 * Servlet that handles saving a user's reading history.
 * It retrieves the current user from the session, reads parameters from the request,
 * creates or updates a HistoryReading record, and redirects the user accordingly.
 *
 * URL pattern: /save-history
 */
@WebServlet("/save-history")
public class SaveHistoryServlet extends HttpServlet {

    /**
     * Handles POST requests to save or update a user's reading history.
     * The method ensures the user is logged in, retrieves series and chapter info from the request,
     * saves the reading progress using the HistoryReadingDAO, and redirects the user.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the current session (do not create a new one if it doesn't exist)
        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        // If user is not logged in, redirect to login page
        if (username == null) {
            response.sendRedirect("views/authorization/login");
            return;
        }

        try {
            // Get user information from database
            UserDAO userDAO = new UserDAO(DBConnection.getConnection());
            User user = userDAO.getUserByName(username);

            if (user == null) {
                response.sendRedirect("views/authorization/login");
                return;
            }

            // Get parameters from the request
            String seriesIdStr = request.getParameter("seriesId");
            String chapterIdStr = request.getParameter("chapterId");
            String seriesTitle = request.getParameter("seriesTitle");
            String chapterTitle = request.getParameter("chapterTitle");

            // Parse IDs to integers
            int userId = user.getUserId();
            int seriesId = Integer.parseInt(seriesIdStr);
            int chapterId = Integer.parseInt(chapterIdStr);

            // Create a new HistoryReading object and populate its fields
            HistoryReading history = new HistoryReading();
            history.setUserId(userId);
            history.setSeriesId(seriesId);
            history.setChapterId(chapterId);
            history.setSeriesTitle(seriesTitle);
            history.setChapterTitle(chapterTitle);

            // Save or update the reading history in the database
            HistoryReadingDAO dao = new HistoryReadingDAO(DBConnection.getConnection());
            dao.saveOrUpdate(history);

            // Redirect the user to the chapter reading page
            response.sendRedirect("read?chapterId=" + chapterId);

        } catch (Exception e) {
            // If an error occurs, log it and forward to an error page
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Chapter Information.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }
}